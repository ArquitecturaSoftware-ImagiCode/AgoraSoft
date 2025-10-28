package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.DetalleCompra;
import com.imagicode.agorasoft.servicios.DetalleCompraService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/detalles-compra")
@CrossOrigin(origins = "*")
public class DetalleCompraController {

    private final DetalleCompraService detalleCompraService;

    public DetalleCompraController(DetalleCompraService detalleCompraService) {
        this.detalleCompraService = detalleCompraService;
    }

    @GetMapping
    public List<DetalleCompra> listar() {
        return detalleCompraService.listarDetalles();
    }

    @GetMapping("/{id}")
    public DetalleCompra obtener(@PathVariable Long id) {
        return detalleCompraService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        detalleCompraService.eliminar(id);
    }
}
