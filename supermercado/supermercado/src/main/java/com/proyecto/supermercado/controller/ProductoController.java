package com.proyecto.supermercado.controller;

import com.proyecto.supermercado.dto.ProductoDTO.ProductoRequestDTO;
import com.proyecto.supermercado.dto.ProductoDTO.ProductoRespuestaDTO;
import com.proyecto.supermercado.dto.ProductoDTO.ProductoUpdateDTO;
import com.proyecto.supermercado.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoRespuestaDTO> crear(
            @Valid @RequestBody ProductoRequestDTO dto) {

        ProductoRespuestaDTO respuesta = productoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<ProductoRespuestaDTO>> listarActivos() {
        return ResponseEntity.ok(productoService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoRespuestaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoRespuestaDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoUpdateDTO dto) {

        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);

        Map<String, String> respuesta = new HashMap<>();
    respuesta.put("mensaje", "Producto eliminado exitosamente");

    return ResponseEntity.ok(respuesta);
    }
}