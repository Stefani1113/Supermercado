package com.proyecto.supermercado.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductoController {
    private final ProductoRepository productoRepository;

    
    private boolean soloAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long rolId = (Long) request.getAttribute("rolId");
        if (rolId == null || !rolId.equals(RolEnum.ADMIN.getId())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Acceso denegado: solo ADMIN puede gestionar productos\"}");
            return false;
        }
        return true;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!soloAdmin(request, response)) return null;
        return ResponseEntity.ok(productoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Producto> crear(
            @RequestBody Producto producto,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!soloAdmin(request, response)) return null;
        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepository.save(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @RequestBody Producto datos,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!soloAdmin(request, response)) return null;
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        p.setNombre(datos.getNombre());
        p.setPrecio(datos.getPrecio());
        p.setStock(datos.getStock());
        return ResponseEntity.ok(productoRepository.save(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!soloAdmin(request, response)) return null;
        productoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

}
