package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.Inventario;
import com.imagicode.agorasoft.servicios.InventarioServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@CrossOrigin(origins = "*")
public class InventarioControlador {

    private final InventarioServicio inventarioServicio;

    public InventarioControlador(InventarioServicio inventarioServicio) {
        this.inventarioServicio = inventarioServicio;
    }

    @GetMapping
    public List<Inventario> listar() {
        List<Inventario> inventarios = inventarioServicio.listar();
        // Limpiar items para evitar recursión
        inventarios.forEach(inv -> inv.setItems(null));
        return inventarios;
    }

    @GetMapping("/{id}")
    public Inventario obtener(@PathVariable Long id) {
        return inventarioServicio.obtenerPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public Inventario obtenerPorUsuario(@PathVariable Long usuarioId) {
        Inventario inventario = inventarioServicio.obtenerPorUsuario(usuarioId);
        if (inventario != null) {
            // Limpiar la lista de items para evitar recursión
            inventario.setItems(null);
        }
        return inventario;
    }

    @PostMapping
    public Inventario crear(@RequestBody Inventario inventario) {
        return inventarioServicio.crear(inventario);
    }

    @PutMapping("/{id}")
    public Inventario actualizar(@PathVariable Long id, @RequestBody Inventario inventario) {
        return inventarioServicio.actualizar(id, inventario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        inventarioServicio.eliminar(id);
    }
}
