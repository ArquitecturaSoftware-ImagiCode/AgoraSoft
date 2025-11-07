package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.ItemInventario;
import com.imagicode.agorasoft.entidades.Producto;
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

    public ItemInventario agregarProducto(String inventarioId, Long productoId, Integer cantidad) {
        // Verificar si ya existe el item en el inventario
        List<ItemInventario> itemsExistentes = itemInventarioRepository.findByInventarioIdAndProducto_Id(inventarioId, productoId);
        
        if (!itemsExistentes.isEmpty()) {
            // Actualizar cantidad existente
            ItemInventario itemExistente = itemsExistentes.get(0);
            itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
            return itemInventarioRepository.save(itemExistente);
        } else {
            // Crear nuevo item en inventario
            ItemInventario nuevoItem = ItemInventario.builder()
                    .inventarioId(inventarioId)
                    .producto(Producto.builder().id(productoId).build())
                    .cantidad(cantidad)
                    .build();
            return itemInventarioRepository.save(nuevoItem);
        }
    }

    public void eliminar(Long id) {
        itemInventarioRepository.deleteById(id);
    }

    /**
     * Resta cantidad de un producto del inventario (para ventas).
     * Valida que haya stock suficiente antes de restar.
     * 
     * @param inventarioId ID del inventario
     * @param productoId ID del producto
     * @param cantidad Cantidad a restar
     * @return ItemInventario actualizado
     * @throws RuntimeException si no hay stock suficiente o el producto no existe en el inventario
     */
    public ItemInventario restarProducto(String inventarioId, Long productoId, Integer cantidad) {
        // Verificar si existe el item en el inventario
        List<ItemInventario> itemsExistentes = itemInventarioRepository.findByInventarioIdAndProducto_Id(inventarioId, productoId);
        
        if (itemsExistentes.isEmpty()) {
            throw new RuntimeException("El producto no existe en el inventario");
        }
        
        ItemInventario itemExistente = itemsExistentes.get(0);
        int cantidadActual = itemExistente.getCantidad();
        
        // Validar stock suficiente
        if (cantidadActual < cantidad) {
            throw new RuntimeException(
                String.format("Stock insuficiente. Disponible: %d, Solicitado: %d", 
                    cantidadActual, cantidad)
            );
        }
        
        // Restar la cantidad
        int nuevaCantidad = cantidadActual - cantidad;
        itemExistente.setCantidad(nuevaCantidad);
        
        return itemInventarioRepository.save(itemExistente);
    }

    /**
     * Obtiene la cantidad disponible de un producto en el inventario.
     * 
     * @param inventarioId ID del inventario
     * @param productoId ID del producto
     * @return Cantidad disponible (0 si no existe)
     */
    public Integer obtenerStockDisponible(String inventarioId, Long productoId) {
        List<ItemInventario> items = itemInventarioRepository.findByInventarioIdAndProducto_Id(inventarioId, productoId);
        if (items.isEmpty()) {
            return 0;
        }
        return items.get(0).getCantidad();
    }
}
