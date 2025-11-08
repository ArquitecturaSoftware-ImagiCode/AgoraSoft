package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.Venta;
import com.imagicode.agorasoft.servicios.VentaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public Venta crearVenta(@RequestBody Venta venta) {
        return ventaService.registrarVenta(venta);
    }

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaService.listarVentas();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Venta> listarVentasPorUsuario(@PathVariable String usuarioId) {
        return ventaService.listarVentasPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public Venta obtenerVenta(@PathVariable Long id) {
        return ventaService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
    }
}

