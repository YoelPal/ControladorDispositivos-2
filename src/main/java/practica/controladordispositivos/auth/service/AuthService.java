package practica.controladordispositivos.auth.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import practica.controladordispositivos.auth.controller.LoginRequest;
import practica.controladordispositivos.auth.controller.RegisterRequest;
import practica.controladordispositivos.auth.controller.TokenResponse;
import practica.controladordispositivos.auth.repository.Token;
import practica.controladordispositivos.auth.repository.TokenRepository;
import practica.controladordispositivos.auth.usuario.User;
import practica.controladordispositivos.auth.usuario.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
        saveUserToken(savedUSer,jwtToken, Token.TokenType.BEARER);
        saveUserToken(user, refreshToken, Token.TokenType.REFRESH);
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
        saveUserToken(user,jwtToken, Token.TokenType.BEARER);
        saveUserToken(user, refreshToken, Token.TokenType.REFRESH);
        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse refreshToken(final String refreshToken){
        if (refreshToken == null|| refreshToken.isEmpty() ){
            throw new IllegalArgumentException("Invalid Refresh token");
        }

        //String userName = null;
        Long userId = null;
        try {
            userId = jwtService.extractUserId(refreshToken);
            if (userId == null){
                throw new IllegalArgumentException("Invalid User ID in token.");
            }
        } catch (ExpiredJwtException e) {
            log.error("Refresh token has expired: {}", refreshToken);
            throw new IllegalArgumentException("Refresh token has expired.");
        } catch (Exception e) {
            log.error("Error extracting username from refresh token: {}", refreshToken, e);
            throw new IllegalArgumentException("Invalid refresh token.");
        }

        /*if (userName == null || userName.isEmpty()){
            throw new IllegalArgumentException("Invalid User token.");
        }*/

        //String finalUserName = userName;
        final User user = userRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("User not found with this ID"));

        var storedRefreshToken = tokenRepository.findByTokenAndTokenTypeAndUser(refreshToken, Token.TokenType.REFRESH, user)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token not found in database."));

        boolean revoked = storedRefreshToken.isRevoked();
        boolean expired = storedRefreshToken.isExpired();
        String Username= storedRefreshToken.getUser().getNombre();
        log.info("Is refreshToken revoked: {}, expired: {}, belongs to user: {}", revoked, expired, user.getNombre());


        if (revoked||expired){
            log.error("Invalid refresh token: revoked={}, expired={}", revoked, expired);
            throw new IllegalArgumentException("Invalid refresh token.");
        }

        if (!jwtService.isTokenValid(refreshToken,user)){
            log.error("Invalid refresh token: JWT signature or claims are invalid for user: {}", user.getNombre());
            throw new IllegalArgumentException("Invalid refresh token.");
        }

        var newRefreshToken = jwtService.generateRefreshToken(user);
        final String accessToken = jwtService.generateToken(user);
        log.info("Generated new accessToken: {}", accessToken); // Añade logging (cuidado con loguear tokens en producción)
        log.info("Generated new refreshToken: {}", newRefreshToken); // Añade logging (cuidado con loguear tokens en producción)
        revokeAllUserTokens(user);
        saveUserToken(user,accessToken, Token.TokenType.BEARER);
        saveUserToken(user,newRefreshToken, Token.TokenType.REFRESH);
        return new TokenResponse(accessToken,newRefreshToken);

    }



    private void saveUserToken(User user, String jwtToken, Token.TokenType tokenType){
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(tokenType)
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

            }
            tokenRepository.saveAll(validUserToken);
    }

    private void revokeUserRefreshTokens(final User user){
        final List<Token> validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserToken.isEmpty()){
            for (final Token token: validUserToken){
                if ( token.tokenType.equals(Token.TokenType.REFRESH)){
                    token.setRevoked(true);
                    token.setExpired(true);
                }

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
            saveUserToken(savedAdmin,jwtToken, Token.TokenType.BEARER);
            saveUserToken(savedAdmin,refreshToken, Token.TokenType.REFRESH);

        }
    }
}
