package com.proyecto.supermercado.security;

import com.proyecto.supermercado.security.dto.CrearUsuarioRequest;
import com.proyecto.supermercado.security.dto.LoginRequest;
import com.proyecto.supermercado.security.dto.LoginResponse;
import com.proyecto.supermercado.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/crear-admin")
    public ResponseEntity<String> crearAdmin() {
        return ResponseEntity.ok(authService.crearAdminInicial());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/crear-cajero")
    public ResponseEntity<String> crearCajero(
            @Valid @RequestBody CrearUsuarioRequest request
    ) {
        return ResponseEntity.ok(authService.crearCajero(request));
    }
}
