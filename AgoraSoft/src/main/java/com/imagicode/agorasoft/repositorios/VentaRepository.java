package com.imagicode.agorasoft.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagicode.agorasoft.entidades.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    //encontrar por local_id
    // Método para obtener ventas por local_id
    List<Venta> findByLocalId(Long localId);
}
