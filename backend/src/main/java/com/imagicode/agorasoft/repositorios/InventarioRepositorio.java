package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioRepositorio extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByUsuarioId(Long usuarioId);
}
