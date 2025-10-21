
package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.ItemInventario;
import com.imagicode.agorasoft.servicios.ItemInventarioServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items-inventario")
@CrossOrigin(origins = "*")
public class ItemInventarioControlador {

    private final ItemInventarioServicio itemInventarioServicio;

    public ItemInventarioControlador(ItemInventarioServicio itemInventarioServicio) {
        this.itemInventarioServicio = itemInventarioServicio;
    }

    @GetMapping
    public List<ItemInventario> listar() {
        return itemInventarioServicio.listar();
    }

    @GetMapping("/{id}")
    public ItemInventario obtener(@PathVariable Long id) {
        return itemInventarioServicio.obtenerPorId(id);
    }

    @GetMapping("/inventario/{inventarioId}")
    public List<ItemInventario> listarPorInventario(@PathVariable Long inventarioId) {
        return itemInventarioServicio.listarPorInventario(inventarioId);
    }


    @PostMapping
    public ItemInventario crear(@RequestBody ItemInventario itemInventario) {
        return itemInventarioServicio.crear(itemInventario);
    }

    @PutMapping("/{id}")
    public ItemInventario actualizar(@PathVariable Long id, @RequestBody ItemInventario itemInventario) {
        return itemInventarioServicio.actualizar(id, itemInventario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        itemInventarioServicio.eliminar(id);
    }

    @DeleteMapping("/inventario/{inventarioId}")
    public void eliminarPorInventario(@PathVariable Long inventarioId) {
        itemInventarioServicio.eliminarPorInventario(inventarioId);
    }

    @PostMapping("/agregar")
    public ItemInventario agregarProducto(@RequestParam Long inventarioId, 
                                        @RequestParam Long productoId, 
                                        @RequestParam Integer cantidad) {
        return itemInventarioServicio.agregarProducto(inventarioId, productoId, cantidad);
    }

    @PutMapping("/cantidad")
    public ItemInventario actualizarCantidad(@RequestParam Long inventarioId,
                                           @RequestParam Long productoId,
                                           @RequestParam Integer nuevaCantidad) {
        return itemInventarioServicio.actualizarCantidad(inventarioId, productoId, nuevaCantidad);
    }
}
