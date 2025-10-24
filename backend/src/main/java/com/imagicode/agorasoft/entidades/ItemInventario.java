package com.imagicode.agorasoft.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items_inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación lógica con el inventario (sin recursión)
    @Column(name = "inventario_id", nullable = false)
    private String inventarioId;

    // Relación real con el producto
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;
}
