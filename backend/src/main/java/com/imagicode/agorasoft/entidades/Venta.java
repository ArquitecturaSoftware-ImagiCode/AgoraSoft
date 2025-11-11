package com.imagicode.agorasoft.entidades;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usuario (operador) que realiza la venta
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Cliente que realiza la compra (opcional, puede ser venta directa)
    @Column(name = "cliente_nombre")
    private String clienteNombre;

    // Fecha de la venta
    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;

    // Total de la venta (se calcula con base en los detalles)
    @Builder.Default
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    // Lista de productos vendidos (detalles)
    @Builder.Default
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void calcularTotal() {
        this.total = detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

