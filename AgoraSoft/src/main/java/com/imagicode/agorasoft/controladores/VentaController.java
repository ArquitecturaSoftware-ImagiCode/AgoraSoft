package com.imagicode.agorasoft.controladores;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imagicode.agorasoft.entidades.Venta;
import com.imagicode.agorasoft.servicios.LocalService;
import com.imagicode.agorasoft.servicios.VentaService;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/ventas")
public class VentaController {

     @Autowired
    private VentaService ventaService;

    @Autowired
    private LocalService localService;

    // Obtener todas las ventas
    @GetMapping
    public List<Venta> getAllVentas() {
        return ventaService.findAll();
    }

    // Obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        Optional<Venta> venta = ventaService.findById(id);
        return venta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener ventas por local_id
    @GetMapping("/local/{localId}")
    public List<Venta> getVentasByLocalId(@PathVariable Long localId) {
        return ventaService.findByLocalId(localId);
    }

    

    

    // Eliminar una venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        if (!ventaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ventaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
