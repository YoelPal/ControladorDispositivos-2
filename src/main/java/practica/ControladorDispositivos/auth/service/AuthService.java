package practica.ControladorDispositivos.auth.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.auth.controller.LoginRequest;
import practica.ControladorDispositivos.auth.controller.RegisterRequest;
import practica.ControladorDispositivos.auth.controller.TokenResponse;
import practica.ControladorDispositivos.auth.repository.Token;
import practica.ControladorDispositivos.auth.repository.TokenRepository;
import practica.ControladorDispositivos.auth.usuario.User;
import practica.ControladorDispositivos.auth.usuario.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest request){
        var user = User.builder()
                .nombre(request.nombre())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .rol(request.rol())
                .build();
        var savedUSer = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUSer,jwtToken);
        return new TokenResponse(jwtToken,refreshToken);
    }

    public TokenResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.nombre(),
                        request.password()
                )
        );
        var user = userRepository.findByNombre(request.nombre())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse refreshToken(final String autHeader){
        if (autHeader == null || !autHeader.startsWith("Bearer ")){
            throw new IllegalArgumentException("Invalid Bearer token");
        }

        final String refreshToken = autHeader.substring(7);
        final String userName = jwtService.extractUsername(refreshToken);

        if (userName == null){
            throw new IllegalArgumentException("Invalid refresh token.");
        }

        final User user = userRepository.findByNombre(userName)
                .orElseThrow(()->new UsernameNotFoundException(userName));

        if (!jwtService.isTokenValid(refreshToken,user)){
            throw new IllegalArgumentException("Invalid refresh token.");
        }

        final String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,accessToken);
        return new TokenResponse(accessToken,refreshToken);

    }



    private void saveUserToken(User user, String jwtToken){
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final User user){
        final List<Token> validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserToken.isEmpty()){
            for (final Token token: validUserToken){
                token.setRevoked(true);
                token.setExpired(true);
            }
            tokenRepository.saveAll(validUserToken);
        }

    }


    @Value("${security.admin.password}")
    private String adminPassword;

    @PostConstruct
    public void createInitialAdmin(){
        if (userRepository.findByNombre("admin").isEmpty()){
            User adminUser = User.builder()
                    .nombre("admin")
                    .email("admin@info.com")
                    .password(passwordEncoder.encode(adminPassword))
                    .rol("ADMIN")
                    .build();

            User savedAdmin = userRepository.save(adminUser);
            var jwtToken = jwtService.generateToken(adminUser);
            var refreshToken = jwtService.generateRefreshToken(adminUser);
            saveUserToken(savedAdmin,jwtToken);
        }
    }
}
