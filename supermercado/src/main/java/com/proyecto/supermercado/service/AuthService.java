package com.proyecto.supermercado.service;

import com.proyecto.supermercado.dto.HttpGlobalResponse;
import com.proyecto.supermercado.dto.JwtDTO;
import com.proyecto.supermercado.dto.LoginRequestDTO;
import com.proyecto.supermercado.dto.RegisterRequestDTO;
import com.proyecto.supermercado.dto.RegisterResponseDTO;
import com.proyecto.supermercado.entity.Users;
import com.proyecto.supermercado.repository.UserRepository;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final UserRepository userRepository;

    
    private final PasswordEncoder passwordEncoder;

    
    private final JwtService jwtService;

    
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        RegisterResponseDTO response = new RegisterResponseDTO();

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            response.setMessage("El correo ya está en uso");
            return response;
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRolId(request.getRol());
        userRepository.save(user);

        response.setMessage("Se ha registrado correctamente");
        return response;
    }

    
    public HttpGlobalResponse<JwtDTO> login(LoginRequestDTO request) {
        HttpGlobalResponse<JwtDTO> response = new HttpGlobalResponse<>();
        Optional<Users> userFound = userRepository.findByEmail(request.getEmail());

        if (userFound.isEmpty()) {
            response.setMessage("Este usuario no se encuentra registrado");
            return response;
        }

        Users user = userFound.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.setMessage("Correo o contraseña son incorrectos");
            return response;
        }

        JwtDTO jwtDTO = new JwtDTO();
        String jwt = jwtService.generateToken(user.getId(), user.getRolId(), user.getEmail());
        jwtDTO.setJwt(jwt);
        response.setMessage("Inicio de sesión exitoso");
        response.setData(jwtDTO);
        return response;
    }

        
    public JwtDTO refreshToken(String token) throws Exception {
        JwtDTO response = new JwtDTO();
        String jwt = jwtService.refreshToken(token);
        response.setJwt(jwt);
        return response;
    }
}
