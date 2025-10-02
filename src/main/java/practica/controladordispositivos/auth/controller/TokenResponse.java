package practica.controladordispositivos.auth.controller;

public record TokenResponse(
        String tokenResponse,
        String refreshToken
) {
}
