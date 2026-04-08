package com.proyecto.supermercado.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.supermercado.dto.DetalleVentaDTO;
import com.proyecto.supermercado.dto.VentaRequestDTO;
import com.proyecto.supermercado.entity.DetalleVenta;
import com.proyecto.supermercado.entity.Empleado;
import com.proyecto.supermercado.entity.Producto;
import com.proyecto.supermercado.entity.Venta;
import com.proyecto.supermercado.exception.StockInsuficienteException;
import com.proyecto.supermercado.repository.EmpleadoRepository;
import com.proyecto.supermercado.repository.ProductoRepository;
import com.proyecto.supermercado.repository.VentaRepository;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final EmpleadoRepository empleadoRepository;

    // Constructor manual en vez de @RequiredArgsConstructor
    public VentaService(VentaRepository ventaRepository,
                        ProductoRepository productoRepository,
                        EmpleadoRepository empleadoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @Transactional
    public Venta procesarVenta(VentaRequestDTO request) {

        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setEmpleado(empleado);

        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (DetalleVentaDTO dto : request.getDetalles()) {

            Producto producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Regla de Negocio 2: validar stock
            if (producto.getStock() < dto.getCantidad()) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para: " + producto.getNombre());
            }

            
            producto.setStock(producto.getStock() - dto.getCantidad());
            productoRepository.save(producto);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnit(producto.getPrecio());

            BigDecimal subtotalLinea = producto.getPrecio()
                    .multiply(BigDecimal.valueOf(dto.getCantidad()));
            detalle.setSubtotal(subtotalLinea);
            detalle.setVenta(venta);

            subtotal = subtotal.add(subtotalLinea);
            detalles.add(detalle);
        }

        
        BigDecimal iva = subtotal.multiply(BigDecimal.valueOf(0.19));
        BigDecimal total = subtotal.add(iva);

        venta.setSubtotal(subtotal);
        venta.setIva(iva);
        venta.setTotal(total);
        venta.setDetalles(detalles);

        return ventaRepository.save(venta);
    }
}