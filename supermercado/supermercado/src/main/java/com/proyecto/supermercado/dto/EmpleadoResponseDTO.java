package com.proyecto.supermercado.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoResponseDTO {
    private Long id;
    private String cedula;
    private String nombre;
    private String cargo;
    private LocalDate fechaIngreso;
    private Double salario;
}