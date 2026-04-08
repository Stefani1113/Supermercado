// dto/EmpleadoRequestDTO.java
package com.proyecto.supermercado.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequestDTO {

    @NotBlank(message = "La cédula es obligatoria")
    @Size(min = 6, max = 20, message = "La cédula debe tener entre 6 y 20 caracteres")
    private String cedula;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El cargo es obligatorio")
    @Pattern(regexp = "ADMINISTRADOR|CAJERO|AUXILIAR", message = "Cargo no permitido")
    private String cargo;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate fechaIngreso;

    @NotNull(message = "El salario es obligatorio")
    @Positive(message = "El salario debe ser mayor a 0")
    private Double salario;
}