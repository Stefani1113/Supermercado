// service/EmpleadoServiceImpl.java
package com.proyecto.supermercado.service.impl;

import com.proyecto.supermercado.dto.EmpleadoRequestDTO;
import com.proyecto.supermercado.dto.EmpleadoResponseDTO;
import com.proyecto.supermercado.entity.Empleado;
import com.proyecto.supermercado.repository.EmpleadoRepository;
import com.proyecto.supermercado.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository repository;
    
    // Lista de cargos permitidos
    private static final List<String> CARGOS_PERMITIDOS = Arrays.asList("ADMINISTRADOR", "CAJERO", "AUXILIAR");

    // ===== MÉTODOS PRIVADOS DE CONVERSIÓN =====
    
    private EmpleadoResponseDTO toDTO(Empleado empleado) {
        return new EmpleadoResponseDTO(
            empleado.getId(),
            empleado.getCedula(),
            empleado.getNombre(),
            empleado.getCargo(),
            empleado.getFechaIngreso(),
            empleado.getSalario()
        );
    }
    
    private Empleado toEntity(EmpleadoRequestDTO dto) {
        return Empleado.builder()
            .cedula(dto.getCedula())
            .nombre(dto.getNombre())
            .cargo(dto.getCargo().toUpperCase())
            .fechaIngreso(dto.getFechaIngreso())
            .salario(dto.getSalario())
            .build();
    }
    
    // ===== MÉTODO PRIVADO DE VALIDACIÓN =====
    
    private void validarCargo(String cargo) {
        if (cargo == null || cargo.trim().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "El cargo es obligatorio"
            );
        }
        
        String cargoUpper = cargo.toUpperCase();
        if (!CARGOS_PERMITIDOS.contains(cargoUpper)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Cargo no válido. Permitidos: ADMINISTRADOR, CAJERO, AUXILIAR"
            );
        }
    }
    
    // ===== MÉTODOS PÚBLICOS =====
    
    @Override
    public EmpleadoResponseDTO crear(EmpleadoRequestDTO dto) {
        // 1. Validar cargo
        validarCargo(dto.getCargo());
        
        // 2. Verificar que la cédula no exista
        if (repository.existsByCedula(dto.getCedula())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Ya existe un empleado con la cédula: " + dto.getCedula()
            );
        }
        
        // 3. Convertir y guardar
        Empleado empleado = toEntity(dto);
        empleado.setCargo(dto.getCargo().toUpperCase());
        
        return toDTO(repository.save(empleado));
    }
    
    @Override
    public EmpleadoResponseDTO obtenerPorId(Long id) {
        Empleado empleado = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Empleado no encontrado con ID: " + id
            ));
        return toDTO(empleado);
    }
    
    @Override
    public List<EmpleadoResponseDTO> listarTodos() {
        return repository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public EmpleadoResponseDTO actualizar(Long id, EmpleadoRequestDTO dto) {
        // 1. Validar cargo
        validarCargo(dto.getCargo());
        
        // 2. Verificar que el empleado existe
        Empleado empleado = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Empleado no encontrado con ID: " + id
            ));
        
        // 3. Verificar que la cédula no pertenezca a otro empleado
        if (!empleado.getCedula().equals(dto.getCedula()) && repository.existsByCedula(dto.getCedula())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "La cédula ya está registrada por otro empleado"
            );
        }
        
        // 4. Actualizar datos
        empleado.setCedula(dto.getCedula());
        empleado.setNombre(dto.getNombre());
        empleado.setCargo(dto.getCargo().toUpperCase());
        empleado.setFechaIngreso(dto.getFechaIngreso());
        empleado.setSalario(dto.getSalario());
        
        return toDTO(repository.save(empleado));
    }
    
    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Empleado no encontrado con ID: " + id
            );
        }
        repository.deleteById(id);
    }
    
    @Override
    public List<EmpleadoResponseDTO> listarPorCargo(String cargo) {
        // Validar cargo
        validarCargo(cargo);
        
        // Buscar por cargo
        List<Empleado> empleados = repository.findByCargo(cargo.toUpperCase());
        
        if (empleados.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No hay empleados con el cargo: " + cargo.toUpperCase()
            );
        }
        
        return empleados.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<EmpleadoResponseDTO> listarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        // Validar fechas
        if (desde == null || hasta == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Las fechas desde y hasta son obligatorias"
            );
        }
        
        if (desde.isAfter(hasta)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "La fecha 'desde' no puede ser mayor que la fecha 'hasta'"
            );
        }
        
        // Buscar por rango de fechas
        List<Empleado> empleados = repository.findByFechaIngresoBetween(desde, hasta);
        
        if (empleados.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No hay empleados que ingresaron entre " + desde + " y " + hasta
            );
        }
        
        return empleados.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}