package practica.controladordispositivos.auth.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import practica.controladordispositivos.auth.repository.Token;
import practica.controladordispositivos.auth.repository.TokenRepository;
import practica.controladordispositivos.auth.service.JwtService;
import practica.controladordispositivos.auth.usuario.User;
import practica.controladordispositivos.auth.usuario.UserRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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

        String userName = null;
        try{
            userName = jwtService.extractUsername(jwtToken);
            log.info("Nombre de usuario extraído del token: {}", userName);
        }catch (ExpiredJwtException e) {
            log.error("JWT ha expirado para el token: {}. Mensaje: {}", jwtToken, e.getMessage());
            // Lanza una excepción de autenticación para que el AuthenticationEntryPoint la capture
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT expirado: " + e.getMessage());
            return; // Termina la cadena de filtros aquí
        } catch (Exception e) {
            log.error("Error inesperado al validar JWT: {}. Mensaje: {}", jwtToken, e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error al validar token JWT: " + e.getMessage());
            return; // Termina la cadena de filtros aquí
        }


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

        if (jwtService.isTokenValid(jwtToken, user.get())) {
            final var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            log.info("Autenticación establecida para el usuario: {}", userDetails.getUsername());
        }else {
            log.warn("Token JWT no válido para el usuario: {}", userDetails.getUsername());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT inválido para el usuario.");
            return;

        }

        filterChain.doFilter(request, response);
        log.info("Filtro completado para la petición: {}", request.getServletPath());
    }
}
