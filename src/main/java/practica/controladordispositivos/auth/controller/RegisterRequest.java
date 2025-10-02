package practica.ControladorDispositivos.auth.controller;

public record RegisterRequest(String email,
                              String password,
                              String nombre,
                              String rol) {

}
