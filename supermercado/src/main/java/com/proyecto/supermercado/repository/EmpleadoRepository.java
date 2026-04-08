// repository/EmpleadoRepository.java
package com.proyecto.supermercado.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.supermercado.entity.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    boolean existsByCedula(String cedula);
    List<Empleado> findByCargo(String cargo);
    List<Empleado> findByFechaIngresoBetween(LocalDate desde, LocalDate hasta);
}