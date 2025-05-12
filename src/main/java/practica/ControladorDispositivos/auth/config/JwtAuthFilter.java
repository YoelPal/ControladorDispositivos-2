package practica.ControladorDispositivos.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import practica.ControladorDispositivos.auth.repository.Token;
import practica.ControladorDispositivos.auth.repository.TokenRepository;
import practica.ControladorDispositivos.auth.service.JwtService;
import practica.ControladorDispositivos.auth.usuario.User;
import practica.ControladorDispositivos.auth.usuario.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("Petición a: {}", request.getServletPath());

       if(request.getServletPath().contains("/auth/refresh")){
           log.info("Ruta para refrescar Token de acceso.");
           filterChain.doFilter(request, response);
           return;
       }

        if(request.getServletPath().contains("/auth/login")){
            log.info("Ruta de autenticación, permitiendo sin filtro.");
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Cabecera Authorization: {}", authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authHeader.substring(7);
        log.info("Token JWT extraído: {}", jwtToken);
        final String userName = jwtService.extractUsername(jwtToken);
        log.info("Nombre de usuario extraído del token: {}", userName);
        if (userName == null || SecurityContextHolder.getContext().getAuthentication() != null){
            log.warn("Nombre de usuario nulo en el token o ya hay autenticación.");
            filterChain.doFilter(request, response);
            return;
        }

        final Token token = tokenRepository.findByToken(jwtToken)
                .orElse(null);
        if (token == null || token.isExpired() || token.isRevoked()){
            log.warn("Token no encontrado, expirado o revocado: {}", token);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Token válido encontrado en la base de datos.");

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
        log.info("UserDetails cargado para el usuario: {}", userDetails.getUsername());
        final Optional<User> user = userRepository.findByNombre(userDetails.getUsername());

        if (user.isEmpty()){
            log.error("Usuario no encontrado en UserDetailsService: {}", userName);
            filterChain.doFilter(request,response);
            return;
        }
        log.info("Usuario encontrado en UserRepository: {}", user.get().getNombre());

        final var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        log.info("Autenticación establecida para el usuario: {}", userDetails.getUsername());

        filterChain.doFilter(request, response);
        log.info("Filtro completado para la petición: {}", request.getServletPath());
    }
}
