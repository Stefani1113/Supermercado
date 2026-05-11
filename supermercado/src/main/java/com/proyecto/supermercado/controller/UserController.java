package com.proyecto.supermercado.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserController {
        
    private final UserService userService;

    @GetMapping("/list-users")
    public ResponseEntity<List<UserResponseDTO>> listUsers(
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        
        if (rolId == null || !rolId.equals(RolEnum.ADMIN.getId())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Acceso denegado: solo ADMIN puede listar usuarios\"}");
            return null;
        }

        try {
            List<UserResponseDTO> res = userService.listUsers();
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

}
