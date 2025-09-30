package com.imagicode.agorasoft.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;

import com.imagicode.agorasoft.entidades.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
