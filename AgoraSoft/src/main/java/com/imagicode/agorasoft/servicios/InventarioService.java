package com.imagicode.agorasoft.servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagicode.agorasoft.entidades.Inventario;
import com.imagicode.agorasoft.repositorios.InventarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    // Obtener todos los inventarios
    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    // Obtener inventarios por local_id
    public List<Inventario> findByLocalId(Long localId) {
        return inventarioRepository.findByLocalId(localId);
    }

    // Obtener un inventario por ID
    public Optional<Inventario> findById(Long id) {
        return inventarioRepository.findById(id);
    }

    // Guardar un nuevo inventario
    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    // Verificar si existe un inventario por ID
    public boolean existsById(Long id) {
        return inventarioRepository.existsById(id);
    }

    // Eliminar un inventario
    public void delete(Long id) {
        inventarioRepository.deleteById(id);
    }
}
