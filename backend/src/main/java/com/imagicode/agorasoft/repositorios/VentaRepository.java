package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByUsuario_Id(String usuarioId);
}

