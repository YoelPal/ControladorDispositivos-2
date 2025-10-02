package practica.controladordispositivos.auth.config;

import lombok.RequiredArgsConstructor;
import practica.controladordispositivos.auth.repository.Token;
import practica.controladordispositivos.auth.repository.TokenRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final TokenRepository tokenRepository;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((req ->
                        req.requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/"), // Permite la ruta raíz (para index.html)
                                        AntPathRequestMatcher.antMatcher("/*.html"), // Permite acceso directo a index.html
                                        AntPathRequestMatcher.antMatcher("/*.ico"),
                                        AntPathRequestMatcher.antMatcher("/*.png"),// Permite el favicon
                                        AntPathRequestMatcher.antMatcher("/assets/**"), // Permite todos los archivos dentro de la carpeta assets
                                        AntPathRequestMatcher.antMatcher("/error")).permitAll()
                                .requestMatchers("/auth/login","/auth/refresh").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/auth/register")
                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                ))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint))

                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler((request, response, authentication) -> {
                                    final var autHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                                    logout(autHeader);
                                })
                                .logoutSuccessHandler((request, response, authentication) ->
                                        SecurityContextHolder.clearContext())
                                );


        return httpSecurity.build();
    }

    /*public static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\":\"Token inválido o expirado.\"}");
        }
    }*/

    private void logout(final String token){
        if (token == null || !token.startsWith("Bearer ")){
            throw new IllegalArgumentException("Invalid token");
        }

        final String jwtToken = token.substring(7);
        final Token foundToken = tokenRepository.findByToken(jwtToken)
                .orElseThrow(()-> new IllegalArgumentException("Invalid token."));
        foundToken.setExpired(true);
        foundToken.setRevoked(true);
        tokenRepository.save(foundToken);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Permite tu frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a todas las rutas
        return source;
    }
}
