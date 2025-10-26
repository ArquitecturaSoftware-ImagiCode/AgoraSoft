package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.ItemInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Long> {
    List<ItemInventario> findByInventarioIdAndProducto_Id(String inventarioId, Long productoId);
    List<ItemInventario> findByInventarioId(String inventarioId);
}
