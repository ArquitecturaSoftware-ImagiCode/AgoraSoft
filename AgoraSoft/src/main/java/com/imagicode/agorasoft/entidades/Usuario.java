
package com.imagicode.agorasoft.entidades;

import jakarta.persistence.*;

@Entity
@Table(schema ="inventario",name = "usuario")
public class Usuario {
    @Id
    private Long id; // Se debe pasar manualmente

    private String nombre;
    private String apellido;
    private String correo;
    private String rol;
    private String organizacion;

    public Usuario() {}

    public Usuario(Long id, String nombre, String apellido, String correo, String rol, String organizacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rol = rol;
        this.organizacion = organizacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getOrganizacion() { return organizacion; }
    public void setOrganizacion(String organizacion) { this.organizacion = organizacion; }
}
