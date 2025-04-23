package practica.ControladorDispositivos.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.auth.repository.Token;
import practica.ControladorDispositivos.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autorización", description = "Seguridad: Registro, login y refresco de token")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    @Operation(summary = "Registro de nuevos usuarios", description = "Permite crear nuevos usuarios, se necesita rol de ADMIN. Devuelve token de autorización y de refresco.")
    public ResponseEntity<TokenResponse> register(@Parameter(description = "Archivo JSON con el nuevo usuario") @RequestBody final RegisterRequest request){
        final TokenResponse token = service.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    @Operation(summary = "Login/Autenticación", description = "Permite logear a usuarios registrados obteniendo el token de autorización. Devuelve token de autorización y de refresco. Permitido sin autorización.")
    public ResponseEntity<TokenResponse> authenticate (@RequestBody final LoginRequest loginRequest){
        final TokenResponse token = service.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refrescar Autorización", description = "Permite refrescar el token de autoriación. Devuelve token de autorización y de refresco.")
    public TokenResponse refresh(@Parameter(description = "Cabecera de autorizacion. Bearer {token}") @RequestHeader(HttpHeaders.AUTHORIZATION)final String authHeader){
        return service.refreshToken(authHeader);
    }


}
