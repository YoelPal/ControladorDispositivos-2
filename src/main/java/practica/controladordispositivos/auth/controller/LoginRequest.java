package practica.controladordispositivos.auth.controller;

public record LoginRequest(
        String nombre,
        String password
) {
}
