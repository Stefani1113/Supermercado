// service/EmpleadoService.java
package com.proyecto.supermercado.service;

import com.proyecto.supermercado.dto.EmpleadoRequestDTO;
import com.proyecto.supermercado.dto.EmpleadoResponseDTO;
import java.time.LocalDate;
import java.util.List;

public interface EmpleadoService {
    
    EmpleadoResponseDTO crear(EmpleadoRequestDTO dto);
    
    EmpleadoResponseDTO obtenerPorId(Long id);
    
    List<EmpleadoResponseDTO> listarTodos();
    
    EmpleadoResponseDTO actualizar(Long id, EmpleadoRequestDTO dto);
    
    void eliminar(Long id);
    
    List<EmpleadoResponseDTO> listarPorCargo(String cargo);
    
    List<EmpleadoResponseDTO> listarPorRangoFechas(LocalDate desde, LocalDate hasta);
}