package com.imagicode.agorasoft.servicios;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagicode.agorasoft.entidades.Venta;
import com.imagicode.agorasoft.repositorios.VentaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

     @Autowired
    private VentaRepository ventaRepository;

    // Obtener todas las ventas
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    // Obtener una venta por ID
    public Optional<Venta> findById(Long id) {
        return ventaRepository.findById(id);
    }

    // Obtener ventas por local_id
    public List<Venta> findByLocalId(Long localId) {
        return ventaRepository.findByLocalId(localId);
    }

    // Guardar una nueva venta
    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    // Verificar si existe una venta por ID
    public boolean existsById(Long id) {
        return ventaRepository.existsById(id);
    }

    // Eliminar una venta
    public void delete(Long id) {
        ventaRepository.deleteById(id);
    }
}

