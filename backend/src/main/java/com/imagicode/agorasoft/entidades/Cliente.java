package com.imagicode.agorasoft.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @Column(unique=true, nullable=false)
    private String email;
    @Column(nullable=false)
    private String password;

    private String telefono;

    private boolean activo = true;
}
