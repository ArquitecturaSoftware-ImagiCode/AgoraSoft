package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.ItemInventario;
import com.imagicode.agorasoft.servicios.ItemInventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items-inventario")
@CrossOrigin(origins = "*")
public class ItemInventarioController {

    private final ItemInventarioService itemInventarioService;

    public ItemInventarioController(ItemInventarioService itemInventarioService) {
        this.itemInventarioService = itemInventarioService;
    }

    @GetMapping("/inventario/{inventarioId}")
    public List<ItemInventario> listarPorInventario(@PathVariable String inventarioId) {
        return itemInventarioService.listarPorInventario(inventarioId);
    }

    @PostMapping
    public List<ItemInventario> guardarTodos(@RequestBody List<ItemInventario> items) {
        return itemInventarioService.guardarTodos(items);
    }

    @PutMapping("/inventario/{inventarioId}/item/{itemId}")
    public ItemInventario actualizarCantidad(
            @PathVariable String inventarioId,
            @PathVariable Long itemId,
            @RequestBody Map<String, Integer> body) {

        Integer cantidad = body.get("cantidad");
        return itemInventarioService.actualizarCantidad(inventarioId, itemId, cantidad);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        itemInventarioService.eliminar(id);
    }
}
