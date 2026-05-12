package com.proyecto.supermercado.service;

import com.proyecto.supermercado.dto.ProductoDTO.ProductoRequestDTO;
import com.proyecto.supermercado.dto.ProductoDTO.ProductoRespuestaDTO;
import com.proyecto.supermercado.dto.ProductoDTO.ProductoUpdateDTO;

import java.util.List;

public interface ProductoService {

    ProductoRespuestaDTO crear(ProductoRequestDTO dto);

    List<ProductoRespuestaDTO> listarActivos();

    ProductoRespuestaDTO buscarPorId(Long id);

    ProductoRespuestaDTO actualizar(Long id, ProductoUpdateDTO dto);

    void eliminar(Long id);
}
