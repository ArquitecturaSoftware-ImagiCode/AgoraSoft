package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.ItemInventario;
import com.imagicode.agorasoft.repositorios.ItemInventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemInventarioService {

    private final ItemInventarioRepository itemInventarioRepository;

    public ItemInventarioService(ItemInventarioRepository itemInventarioRepository) {
        this.itemInventarioRepository = itemInventarioRepository;
    }

    public List<ItemInventario> listarPorInventario(String inventarioId) {
        return itemInventarioRepository.findByInventarioId(inventarioId);
    }

    public ItemInventario guardar(ItemInventario item) {
        return itemInventarioRepository.save(item);
    }

    public List<ItemInventario> guardarTodos(List<ItemInventario> items) {
        return itemInventarioRepository.saveAll(items);
    }

    // Actualizar la cantidad de un item del inventario
    public ItemInventario actualizarCantidad(String inventarioId, Long itemId, Integer nuevaCantidad) {
        ItemInventario item = itemInventarioRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
        if (!item.getInventarioId().equals(inventarioId)) {
            throw new RuntimeException("El item no pertenece al inventario especificado");
        }
        item.setCantidad(nuevaCantidad);
        return itemInventarioRepository.save(item);
    }

    public void eliminar(Long id) {
        itemInventarioRepository.deleteById(id);
    }
}
