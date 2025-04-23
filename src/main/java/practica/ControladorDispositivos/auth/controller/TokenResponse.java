package practica.ControladorDispositivos.auth.controller;

public record TokenResponse(
        String tokenResponse,
        String refreshToken
) {
}
