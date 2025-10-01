package com.imagicode.agorasoft.entidades;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class VentaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Venta venta;

    @ManyToOne
    private Producto producto;

    private int cantidad;
    private double precioPorLibra;
    private double total;

    public VentaProducto() {}
    public VentaProducto(Venta venta, Producto producto, int cantidad, double precioPorLibra, double total) {
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioPorLibra = precioPorLibra;
        this.total = total;    
    }
    public Long getId() {
        return id;
    }
    public Venta getVenta() {
        return venta;
    }
    public void setVenta(Venta venta) {
        this.venta = venta;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public double getPrecioPorLibra() {
        return precioPorLibra;
    }
    public void setPrecioPorLibra(double precioPorLibra) {
        this.precioPorLibra = precioPorLibra;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    
}
