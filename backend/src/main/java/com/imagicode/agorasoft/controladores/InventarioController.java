package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.Inventario;
import com.imagicode.agorasoft.servicios.InventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@CrossOrigin(origins = "*")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Inventario> listarPorUsuario(@PathVariable String usuarioId) {
        return inventarioService.listarPorUsuario(usuarioId);
    }

    @PostMapping
    public Inventario crearInventario(@RequestBody Map<String, String> body) {
        String usuarioId = body.get("usuarioId");
        return inventarioService.guardar(usuarioId);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        inventarioService.eliminar(id);
    }
}
