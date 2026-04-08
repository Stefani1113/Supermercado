package com.proyecto.supermercado.dto;

import com.proyecto.supermercado.dto.ProductoDTO.ProductoRespuestaDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CategoriaDTO {

    public record CategoriaRequestDTO(

        @NotBlank(message = "El nombre de la categoría es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
        String descripcion

    ) {}

    public record CategoriaRespuestaDTO(
        Long id,
        String nombre,
        String descripcion,
        List<ProductoRespuestaDTO> productos
    ) {}
}

