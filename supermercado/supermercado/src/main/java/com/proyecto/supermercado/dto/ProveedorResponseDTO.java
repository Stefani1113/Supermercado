package com.proyecto.supermercado.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorResponseDTO {

    private Long id;
    private String nit;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
}