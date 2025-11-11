package com.imagicode.agorasoft.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventarios", schema="inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {

    @Id
    // UNIQUE
    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String usuarioId; // ID del dueño del inventario
}
