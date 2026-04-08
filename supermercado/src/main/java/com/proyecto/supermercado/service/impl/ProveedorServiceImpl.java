package com.proyecto.supermercado.service.impl;

import com.proyecto.supermercado.dto.EntradaAlmacenRequestDTO;
import com.proyecto.supermercado.dto.ProveedorRequestDTO;
import com.proyecto.supermercado.dto.ProveedorResponseDTO;
import com.proyecto.supermercado.entity.EntradaAlmacen;
import com.proyecto.supermercado.entity.Producto;
import com.proyecto.supermercado.entity.Proveedor;
import com.proyecto.supermercado.repository.EntradaAlmacenRepository;
import com.proyecto.supermercado.repository.ProductoRepository;
import com.proyecto.supermercado.repository.ProveedorRepository;
import com.proyecto.supermercado.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final EntradaAlmacenRepository entradaAlmacenRepository;
    private final ProductoRepository productoRepository;

    @Override
    public ProveedorResponseDTO crear(ProveedorRequestDTO dto) {
        if (proveedorRepository.existsByNit(dto.getNit())) {
            throw new RuntimeException("Ya existe un proveedor con el NIT: " + dto.getNit());
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setNit(dto.getNit());
        proveedor.setNombre(dto.getNombre());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setEmail(dto.getEmail());
        proveedor.setDireccion(dto.getDireccion());
        return toDTO(proveedorRepository.save(proveedor));
    }

    @Override
    public ProveedorResponseDTO obtenerPorId(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
        return toDTO(proveedor);
    }

    @Override
    public List<ProveedorResponseDTO> obtenerTodos() {
        return proveedorRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
        proveedor.setNit(dto.getNit());
        proveedor.setNombre(dto.getNombre());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setEmail(dto.getEmail());
        proveedor.setDireccion(dto.getDireccion());
        return toDTO(proveedorRepository.save(proveedor));
    }

    @Override
    public void eliminar(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
        proveedorRepository.delete(proveedor);
    }

    @Override
    @Transactional
    public void entradaAlmacen(EntradaAlmacenRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + dto.getProveedorId()));

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getProductoId()));

        producto.setStock(producto.getStock() + dto.getCantidad());
        producto.getProveedor().add(proveedor);

        EntradaAlmacen entrada = new EntradaAlmacen();
        entrada.setProducto(producto);
        entrada.setProveedor(proveedor);
        entrada.setCantidad(dto.getCantidad());
        entradaAlmacenRepository.save(entrada);
    }

    private ProveedorResponseDTO toDTO(Proveedor p) {
        return new ProveedorResponseDTO(
                p.getId(), p.getNit(), p.getNombre(),
                p.getTelefono(), p.getEmail(), p.getDireccion()
        );
    }
}