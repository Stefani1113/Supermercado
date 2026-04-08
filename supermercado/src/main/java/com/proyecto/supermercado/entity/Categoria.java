package com.proyecto.supermercado.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data               
@NoArgsConstructor  
@AllArgsConstructor 
@Entity             
@Table(name = "categorias") 
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @OneToMany(
            mappedBy    = "categoria",
            cascade     = CascadeType.ALL,
            fetch       = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Producto> productos = new ArrayList<>();
}