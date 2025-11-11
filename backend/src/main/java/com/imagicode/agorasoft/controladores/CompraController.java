package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.Compra;
import com.imagicode.agorasoft.servicios.CompraService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public Compra crearCompra(@RequestBody Compra compra) {
        return compraService.registrarCompra(compra);
    }

    @GetMapping
    public List<Compra> listarCompras() {
        return compraService.listarCompras();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Compra> listarComprasPorUsuario(@PathVariable String usuarioId) {
        return compraService.listarComprasPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public Compra obtenerCompra(@PathVariable Long id) {
        return compraService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarCompra(@PathVariable Long id) {
        compraService.eliminarCompra(id);
    }
}
