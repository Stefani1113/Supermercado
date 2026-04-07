package com.proyecto.supermercado.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorRequestDTO {

    @NotBlank(message = "El NIT es obligatorio")
    private String nit;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String telefono;
    private String email;
    private String direccion;
}