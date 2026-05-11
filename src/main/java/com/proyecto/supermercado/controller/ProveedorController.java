package com.proyecto.supermercado.controller;
 
import com.proyecto.supermercado.dto.EntradaAlmacenRequestDTO;
import com.proyecto.supermercado.dto.ProveedorRequestDTO;
import com.proyecto.supermercado.dto.ProveedorResponseDTO;
import com.proyecto.supermercado.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(@Valid @RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(proveedorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.ok(proveedorService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/entrada-almacen")
    public ResponseEntity<Void> entradaAlmacen(@Valid @RequestBody EntradaAlmacenRequestDTO dto) {
        proveedorService.entradaAlmacen(dto);
        return ResponseEntity.ok().build();
    }
}