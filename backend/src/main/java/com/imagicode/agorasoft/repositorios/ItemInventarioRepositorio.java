package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.ItemInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemInventarioRepositorio extends JpaRepository<ItemInventario, Long> {
    List<ItemInventario> findByInventarioId(Long inventarioId);
    List<ItemInventario> findByProductoId(Long productoId);
    ItemInventario findByInventarioIdAndProductoId(Long inventarioId, Long productoId);
}
