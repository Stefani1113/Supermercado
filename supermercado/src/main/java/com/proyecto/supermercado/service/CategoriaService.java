package com.proyecto.supermercado.service;

import com.proyecto.supermercado.dto.CategoriaDTO.CategoriaRequestDTO;
import com.proyecto.supermercado.dto.CategoriaDTO.CategoriaRespuestaDTO;

import java.util.List;

public interface CategoriaService {

    CategoriaRespuestaDTO crear(CategoriaRequestDTO dto);

    List<CategoriaRespuestaDTO> listarTodas();
    CategoriaRespuestaDTO buscarPorId(Long id);
    CategoriaRespuestaDTO actualizar(Long id, CategoriaRequestDTO dto);
    void eliminar(Long id);
}