package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Buscar productos por proveedor
    List<Producto> findByUsuarioProveedorId(String usuarioProveedorId);

}
