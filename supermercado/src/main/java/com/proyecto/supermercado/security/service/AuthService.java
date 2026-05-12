package com.proyecto.supermercado.security.service;

import com.proyecto.supermercado.security.dto.CrearUsuarioRequest;
import com.proyecto.supermercado.security.dto.LoginRequest;
import com.proyecto.supermercado.security.dto.LoginResponse;
import com.proyecto.supermercado.security.entity.RolEnum;
import com.proyecto.supermercado.security.entity.Usuario;
import com.proyecto.supermercado.security.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado: " + request.getUsername())
                );

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contrasena incorrecta");
        }

        String token = jwtService.generarToken(
                usuario.getUsername(),
                usuario.getRolId()
        );

        String rolNombre = RolEnum.fromId(usuario.getRolId()).name();

        return new LoginResponse(token, rolNombre, usuario.getUsername());
    }

    public String crearAdminInicial() {

        if (usuarioRepository.existsByUsername("admin")) {
            return "El administrador ya existe. Usa POST /auth/login con username=admin";
        }

        Usuario admin = Usuario.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .rolId(RolEnum.ADMIN.getId())
                .build();

        usuarioRepository.save(admin);
        return "Administrador creado. username=admin | password=admin123";
    }

    public String crearCajero(CrearUsuarioRequest request) {

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException(
                    "Ya existe un usuario con username: " + request.getUsername()
            );
        }

        Usuario cajero = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .rolId(RolEnum.CAJERO.getId())
                .build();

        usuarioRepository.save(cajero);
        return "Cajero creado. username=" + request.getUsername();
    }
}
