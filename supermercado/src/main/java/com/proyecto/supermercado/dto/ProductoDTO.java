package com.proyecto.supermercado.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductoDTO {

    public record ProductoRequestDTO(

        @NotBlank(message = "El nombre del producto es obligatorio")
        @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
        String nombre,

        @Size(max = 300, message = "La descripción no puede superar 300 caracteres")
        String descripcion,

        @NotBlank(message = "El código de barras es obligatorio")
        @Size(min = 4, max = 50, message = "El código de barras debe tener entre 4 y 50 caracteres")
        String codigoBarras,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @NotNull(message = "La categoría es obligatoria")
        Long categoriaId

    ) {}

    public record ProductoUpdateDTO(

    @NotBlank(message = "El nombre del producto es obligatorio")
        @Size(min = 2, max = 150)
        String nombre,

        @Size(max = 300)
        String descripcion,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @NotNull(message = "La categoría es obligatoria")
        Long categoriaId

    ) {}

    public record ProductoRespuestaDTO(
        Long id,
        String nombre,
        String descripcion,
        String codigoBarras,
        java.math.BigDecimal precio,
        Integer stock,
        Boolean activo,
        Long categoriaId,
        String categoriaNombre
    ) {}
}