package com.proyecto.supermercado.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    
    private String name;

    
    private String email;

    

    
    private String password;

    
    private Long rol;
}
