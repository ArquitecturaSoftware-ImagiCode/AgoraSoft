package com.imagicode.agorasoft.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    private String unidadMedida; // Ejemplo: kg, unidad, litro, etc.

    private String categoria;

    private String imagenUrl; // Ruta o URL pública del producto
}
