package com.imagicode.agorasoft.entidades;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "inventario_id", nullable = false)
    @JsonIgnoreProperties({"items"})
    private Inventario inventario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;
}
