package com.proyecto.supermercado.service.impl;

import com.proyecto.supermercado.dto.CategoriaDTO.CategoriaRequestDTO;
import com.proyecto.supermercado.dto.CategoriaDTO.CategoriaRespuestaDTO;
import com.proyecto.supermercado.dto.ProductoDTO.ProductoRespuestaDTO;
import com.proyecto.supermercado.entity.Categoria;
import com.proyecto.supermercado.entity.Producto;
import com.proyecto.supermercado.exception.DuplicateResourceException;
import com.proyecto.supermercado.exception.ResourceNotFoundException;
import com.proyecto.supermercado.repository.CategoriaRepository;
import com.proyecto.supermercado.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional 
    public CategoriaRespuestaDTO crear(CategoriaRequestDTO dto) {

        if (categoriaRepository.existsByNombre(dto.nombre())) {
            throw new DuplicateResourceException(
                "Ya existe una categoría con el nombre: " + dto.nombre()
            );
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());

        Categoria guardada = categoriaRepository.save(categoria);

        return mapearARespuesta(guardada);
    }

    @Override
    @Transactional(readOnly = true) 
    public List<CategoriaRespuestaDTO> listarTodas() {
        return categoriaRepository.findAll()
            .stream()
            .map(this::mapearARespuesta)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaRespuestaDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)

            .orElseThrow(() ->
                new ResourceNotFoundException("Categoría no encontrada con ID: " + id)
            );

        return mapearARespuesta(categoria);
    }

    @Override
    @Transactional
    public CategoriaRespuestaDTO actualizar(Long id, CategoriaRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Categoría no encontrada con ID: " + id)
            );

        categoriaRepository.findByNombre(dto.nombre())
            .filter(c -> !c.getId().equals(id))
            .ifPresent(c -> {
                throw new DuplicateResourceException(
                    "Ya existe otra categoría con el nombre: " + dto.nombre()
                );
            });

        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());

        Categoria actualizada = categoriaRepository.save(categoria);
        return mapearARespuesta(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {

        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaRespuestaDTO mapearARespuesta(Categoria categoria) {

        List<ProductoRespuestaDTO> productosDTO = categoria.getProductos()
            .stream()
            .filter(Producto::getActivo) 
            .map(this::mapearProducto)
            .toList();

        return new CategoriaRespuestaDTO(
            categoria.getId(),
            categoria.getNombre(),
            categoria.getDescripcion(),
            productosDTO
        );
    }

    private ProductoRespuestaDTO mapearProducto(Producto p) {
        return new ProductoRespuestaDTO(
            p.getId(),
            p.getNombre(),
            p.getDescripcion(),
            p.getCodigoBarras(),
            p.getPrecio(),
            p.getStock(),
            p.getActivo(),
            p.getCategoria().getId(),
            p.getCategoria().getNombre()
        );
    }
}

