package com.imagicode.agorasoft.entidades;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId; // Relación lógica con el usuario

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "inventario_id") // relación unidireccional
    //Se crea una lista de items de inventario
    @Builder.Default
    @JsonIgnore
    private List<ItemInventario> items = new ArrayList<>();
}
