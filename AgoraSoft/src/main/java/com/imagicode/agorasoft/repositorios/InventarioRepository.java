package com.imagicode.agorasoft.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagicode.agorasoft.entidades.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    // Método para obtener productos por local
    List<Inventario> findByLocalId(Long localId);
}
