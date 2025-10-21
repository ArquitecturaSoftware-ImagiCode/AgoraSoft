package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(String categoria);
}
