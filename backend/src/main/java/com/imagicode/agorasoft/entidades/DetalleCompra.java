package com.imagicode.agorasoft.entidades;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la compra
    @ManyToOne(optional = false)
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    // Producto comprado
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Cantidad de unidades del producto
    @Column(nullable = false)
    private Integer cantidad;

    // Precio unitario (al momento de la compra)
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // Subtotal = cantidad * precioUnitario
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @PrePersist
    @PreUpdate
    public void calcularSubtotal() {
        if (precioUnitario != null && cantidad != null) {
            this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
    }
}
