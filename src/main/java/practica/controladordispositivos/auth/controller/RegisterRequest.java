package practica.controladordispositivos.auth.controller;

public record RegisterRequest(String email,
                              String password,
                              String nombre,
                              String rol) {

}
