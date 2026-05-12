package com.proyecto.supermercado.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "proveedores")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nit;

    @Column(nullable = false)
    private String nombre;

    private String telefono;
    private String email;
    private String direccion;

    @ManyToMany(mappedBy = "proveedores")
    private Set<Producto> productos = new HashSet<>();
}