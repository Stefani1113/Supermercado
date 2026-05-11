package com.proyecto.supermercado.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.supermercado.dto.UserResponseDTO;
import com.proyecto.supermercado.entity.Users;
import com.proyecto.supermercado.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    
    private final UserRepository userRepository;

    
    public List<UserResponseDTO> listUsers() {
        List<Users> usersFound = userRepository.findAll();
        List<UserResponseDTO> response = new ArrayList<>();

        for (Users users : usersFound) {
            UserResponseDTO user = new UserResponseDTO();
            user.setId(users.getId());
            user.setName(users.getName());
            user.setEmail(users.getEmail());
            user.setRol(users.getRolId());
            response.add(user);
        }

        return response;
    }
}
