package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByUsuario_Id(String usuarioId);
}
