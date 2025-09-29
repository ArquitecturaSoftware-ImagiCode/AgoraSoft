package com.imagicode.agorasoft.agorasoft.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "inventario",schema="inventario")
public class Producto {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String seccion;
    private String nombreProducto;
    private String cantiLibra;
    private String preciLibra;

    public String getCantiLibra() {
        return cantiLibra;
    }

    public void setCantiLibra(String cantiLibra) {
        this.cantiLibra = cantiLibra;
    }

    public String getPreciLibra() {
        return preciLibra;
    }

    public void setPreciLibra(String preciLibra) {
        this.preciLibra = preciLibra;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Producto(){}


    public Producto(String id, String preciLibra, String cantiLibra, String nombreProducto, String seccion) {
        this.id = id;
        this.preciLibra = preciLibra;
        this.cantiLibra = cantiLibra;
        this.nombreProducto = nombreProducto;
        this.seccion = seccion;
    }




}
