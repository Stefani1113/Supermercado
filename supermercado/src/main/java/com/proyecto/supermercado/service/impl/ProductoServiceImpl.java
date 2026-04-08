package com.proyecto.supermercado.service.impl;

import com.proyecto.supermercado.dto.ProductoDTO.ProductoRequestDTO;
import com.proyecto.supermercado.dto.ProductoDTO.ProductoRespuestaDTO;
import com.proyecto.supermercado.dto.ProductoDTO.ProductoUpdateDTO;
import com.proyecto.supermercado.entity.Categoria;
import com.proyecto.supermercado.entity.Producto;
import com.proyecto.supermercado.exception.DuplicateResourceException;
import com.proyecto.supermercado.exception.ResourceNotFoundException;
import com.proyecto.supermercado.repository.CategoriaRepository;
import com.proyecto.supermercado.repository.ProductoRepository;
import com.proyecto.supermercado.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public ProductoRespuestaDTO crear(ProductoRequestDTO dto) {

        if (productoRepository.existsByCodigoBarras(dto.codigoBarras())) {
            throw new DuplicateResourceException(
                "Ya existe un producto con el código de barras: " + dto.codigoBarras()
            );
        }

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Categoría no encontrada con ID: " + dto.categoriaId()
                )
            );

        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        producto.setCodigoBarras(dto.codigoBarras());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
        producto.setCategoria(categoria);
        producto.setActivo(true); 

        Producto guardado = productoRepository.save(producto);
        return mapearARespuesta(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoRespuestaDTO> listarActivos() {
        return productoRepository.findByActivoTrue()
            .stream()
            .map(this::mapearARespuesta)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoRespuestaDTO buscarPorId(Long id) {
        // findByIdAndActivoTrue devuelve vacío si está soft-deleted → 404
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Producto no encontrado con ID: " + id)
            );
        return mapearARespuesta(producto);
    }


    @Override
    @Transactional
    public ProductoRespuestaDTO actualizar(Long id, ProductoUpdateDTO dto) {

        Producto producto = productoRepository.findByIdAndActivoTrue(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Producto no encontrado con ID: " + id)
            );

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Categoría no encontrada con ID: " + dto.categoriaId()
                )
            );

        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
        producto.setCategoria(categoria);

        Producto actualizado = productoRepository.save(producto);
        return mapearARespuesta(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Producto no encontrado con ID: " + id)
            );

        producto.setActivo(false);
        productoRepository.save(producto);
    }

    private ProductoRespuestaDTO mapearARespuesta(Producto p) {
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