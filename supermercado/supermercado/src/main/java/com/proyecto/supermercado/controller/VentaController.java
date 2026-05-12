package com.proyecto.supermercado.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.supermercado.dto.VentaRequestDTO;
import com.proyecto.supermercado.entity.Venta;
import com.proyecto.supermercado.service.VentaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController                          
@RequestMapping("/api/ventas")           
@RequiredArgsConstructor                 
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@Valid @RequestBody VentaRequestDTO request) {
        return ResponseEntity.ok(ventaService.procesarVenta(request));
    }
}
