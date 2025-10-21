package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.Inventario;
import com.imagicode.agorasoft.repositorios.InventarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioServicio {

    private final InventarioRepositorio inventarioRepositorio;

    public InventarioServicio(InventarioRepositorio inventarioRepositorio) {
        this.inventarioRepositorio = inventarioRepositorio;
    }

    public List<Inventario> listar() {
        return inventarioRepositorio.findAll();
    }

    public Inventario obtenerPorId(Long id) {
        return inventarioRepositorio.findById(id).orElseThrow(() -> 
            new RuntimeException("Inventario no encontrado con ID: " + id));
    }

    public Inventario obtenerPorUsuario(Long usuarioId) {
        return inventarioRepositorio.findByUsuarioId(usuarioId).orElse(null);
    }

    public Inventario crear(Inventario inventario) {
        return inventarioRepositorio.save(inventario);
    }

    public Inventario actualizar(Long id, Inventario inventario) {
        Inventario inventarioExistente = obtenerPorId(id);
        inventarioExistente.setUsuarioId(inventario.getUsuarioId());
        // Los items se manejan por separado a través de ItemInventarioServicio
        return inventarioRepositorio.save(inventarioExistente);
    }

    public void eliminar(Long id) {
        inventarioRepositorio.deleteById(id);
    }
}
