package com.proyecto.supermercado.dto;    

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaRequestDTO {

    @NotNull
    private Long empleadoId;

    @NotEmpty
    @Valid                                
    private List<DetalleVentaDTO> detalles;
}