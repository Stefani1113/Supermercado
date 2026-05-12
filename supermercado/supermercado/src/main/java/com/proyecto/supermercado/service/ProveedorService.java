package com.proyecto.supermercado.service;

import com.proyecto.supermercado.dto.EntradaAlmacenRequestDTO;
import com.proyecto.supermercado.dto.ProveedorRequestDTO;
import com.proyecto.supermercado.dto.ProveedorResponseDTO;
import java.util.List;

public interface ProveedorService {
    ProveedorResponseDTO crear(ProveedorRequestDTO dto);
    ProveedorResponseDTO obtenerPorId(Long id);
    List<ProveedorResponseDTO> obtenerTodos();
    ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO dto);
    void eliminar(Long id);
    void entradaAlmacen(EntradaAlmacenRequestDTO dto);
}