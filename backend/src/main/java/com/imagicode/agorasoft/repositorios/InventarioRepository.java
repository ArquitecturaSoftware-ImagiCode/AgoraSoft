package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, String> {
    List<Inventario> findByUsuarioId(String usuarioId);
}
