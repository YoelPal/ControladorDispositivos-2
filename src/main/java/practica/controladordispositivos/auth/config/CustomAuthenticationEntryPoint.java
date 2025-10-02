package practica.ControladorDispositivos.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException; // Importa ExpiredJwtException
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Establece el estado HTTP a 401

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");

        // Verifica si la excepción es específicamente un JWT caducado
        if (authException.getCause() instanceof ExpiredJwtException) {
            body.put("message", "JWT token has expired");
            body.put("expired", true); // Opcional: una bandera para que el frontend lo reconozca específicamente
        } else {
            body.put("message", authException.getMessage());
        }

        body.put("path", request.getServletPath());

        // Escribe la respuesta JSON
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}