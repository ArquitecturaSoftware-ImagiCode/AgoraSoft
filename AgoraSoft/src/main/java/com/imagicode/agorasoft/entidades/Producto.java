package com.imagicode.agorasoft.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String seccion;
    private String nombreProducto;
    private int cantiLibra;
    private float preciLibra;

    //constructor vacio
    public Producto(){}
    //contructor con variables
    public Producto(String seccion, String nombreProducto, int cantiLibra, float preciLibra){
        this.seccion = seccion;
        this.nombreProducto = nombreProducto;
        this.cantiLibra = cantiLibra;
        this.preciLibra = preciLibra;
    }
    //getters
    public long getId(){return Id;}
    public String getSeccion(){return seccion;}
    public String getNombreProducto(){return nombreProducto;}
    public int getCantiLibra(){return cantiLibra;}
    public float getPreciLibra(){return preciLibra;}
    //setters
    public void setId(Long id){this.Id = id;}
    public void setSeccion(String seccion){this.seccion = seccion;}
    public void setNombreProducto(String nombreProducto){this.nombreProducto = nombreProducto;}
    public void setCantiLibra(int cantiLibra){this.cantiLibra = cantiLibra;}
    public void setPreciLibra(float preciLibra){this.preciLibra = preciLibra;}


}
