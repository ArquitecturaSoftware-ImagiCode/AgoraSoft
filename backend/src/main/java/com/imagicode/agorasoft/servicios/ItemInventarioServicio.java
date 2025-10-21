package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.ItemInventario;
import com.imagicode.agorasoft.entidades.Inventario;
import com.imagicode.agorasoft.repositorios.ItemInventarioRepositorio;
import com.imagicode.agorasoft.repositorios.InventarioRepositorio;
import com.imagicode.agorasoft.repositorios.ProductoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemInventarioServicio {

    private final ItemInventarioRepositorio itemInventarioRepositorio;
    private final InventarioRepositorio inventarioRepositorio;
    private final ProductoRepositorio productoRepositorio;

    public ItemInventarioServicio(ItemInventarioRepositorio itemInventarioRepositorio,
                                  InventarioRepositorio inventarioRepositorio,
                                  ProductoRepositorio productoRepositorio) {
        this.itemInventarioRepositorio = itemInventarioRepositorio;
        this.inventarioRepositorio = inventarioRepositorio;
        this.productoRepositorio = productoRepositorio;
    }

    public List<ItemInventario> listar() {
        return itemInventarioRepositorio.findAll();
    }

    public List<ItemInventario> listarPorInventario(Long inventarioId) {
        return itemInventarioRepositorio.findByInventarioId(inventarioId);
    }

    public ItemInventario obtenerPorId(Long id) {
        return itemInventarioRepositorio.findById(id).orElseThrow(() -> 
            new RuntimeException("Item de inventario no encontrado con ID: " + id));
    }

    public ItemInventario crear(ItemInventario itemInventario) {
        // Verificar que el inventario y producto existen
        inventarioRepositorio.findById(itemInventario.getInventario().getId())
            .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        productoRepositorio.findById(itemInventario.getProducto().getId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        return itemInventarioRepositorio.save(itemInventario);
    }

    public ItemInventario actualizar(Long id, ItemInventario itemInventario) {
        ItemInventario itemExistente = obtenerPorId(id);
        itemExistente.setProducto(itemInventario.getProducto());
        itemExistente.setCantidad(itemInventario.getCantidad());
        return itemInventarioRepositorio.save(itemExistente);
    }

    public void eliminar(Long id) {
        itemInventarioRepositorio.deleteById(id);
    }

    public void eliminarPorInventario(Long inventarioId) {
        List<ItemInventario> items = listarPorInventario(inventarioId);
        itemInventarioRepositorio.deleteAll(items);
    }

    public ItemInventario agregarProducto(Long inventarioId, Long productoId, Integer cantidad) {
        ItemInventario itemExistente = itemInventarioRepositorio
            .findByInventarioIdAndProductoId(inventarioId, productoId);
        
        if (itemExistente != null) {
            // Si ya existe, sumar la cantidad
            itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
            return itemInventarioRepositorio.save(itemExistente);
        } else {
            // Si no existe, crear nuevo item
            Inventario inventario = inventarioRepositorio.findById(inventarioId).orElseThrow();
            ItemInventario nuevoItem = ItemInventario.builder()
                .inventario(inventario)
                .producto(productoRepositorio.findById(productoId).orElseThrow())
                .cantidad(cantidad)
                .build();
            return itemInventarioRepositorio.save(nuevoItem);
        }
    }

    public ItemInventario actualizarCantidad(Long inventarioId, Long productoId, Integer nuevaCantidad) {
        ItemInventario item = itemInventarioRepositorio
            .findByInventarioIdAndProductoId(inventarioId, productoId);
        
        if (item == null) {
            throw new RuntimeException("Item no encontrado en el inventario");
        }
        
        item.setCantidad(nuevaCantidad);
        return itemInventarioRepositorio.save(item);
    }
}
