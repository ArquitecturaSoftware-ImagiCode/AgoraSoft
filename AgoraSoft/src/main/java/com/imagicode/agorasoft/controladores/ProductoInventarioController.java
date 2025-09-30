package com.imagicode.agorasoft.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imagicode.agorasoft.entidades.Inventario;
import com.imagicode.agorasoft.servicios.InventarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventarios")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoInventarioController {

    @Autowired
    private InventarioService inventarioService;

    // Obtener todos los productos en inventarios
    @GetMapping
    public List<Inventario> getAllInventarios() {
        return inventarioService.findAll();
    }

    // Obtener productos de un local específico
    @GetMapping("/local/{localId}")
    public List<Inventario> getInventariosByLocal(@PathVariable Long localId) {
        return inventarioService.findByLocalId(localId);
    }

    // Obtener un inventario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getInventarioById(@PathVariable Long id) {
        Optional<Inventario> inventario = inventarioService.findById(id);
        return inventario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo inventario
    @PostMapping
    public Inventario createInventario(@RequestBody Inventario inventario) {
        return inventarioService.save(inventario);
    }

    // Actualizar las cantidades de un producto en el inventario
    @PutMapping("/{id}")
    public ResponseEntity<Inventario> updateInventario(@PathVariable Long id, @RequestBody Inventario inventario) {
        if (!inventarioService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(inventarioService.save(inventario));
    }

    // Eliminar un inventario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventario(@PathVariable Long id) {
        if (!inventarioService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

