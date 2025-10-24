package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.Inventario;
import com.imagicode.agorasoft.repositorios.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public List<Inventario> listarPorUsuario(String usuarioId) {
        return inventarioRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Crea y guarda un nuevo inventario para un usuario.
     * Si el inventario aún no tiene ID, se genera automáticamente
     * usando el patrón "i_{usuarioId}".
     */
    public Inventario guardar(String usuarioId) {
        Inventario inventario = new Inventario();
        inventario.setUsuarioId(usuarioId);
        inventario.setId("i_" + usuarioId);
        return inventarioRepository.save(inventario);
    }
    

    public void eliminar(String id) {
        inventarioRepository.deleteById(id);
    }
}
