package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.*;
import com.imagicode.agorasoft.repositorios.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final ItemInventarioService itemInventarioService;

    public VentaService(VentaRepository ventaRepository,
            ProductoRepository productoRepository,
            ItemInventarioService itemInventarioService) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.itemInventarioService = itemInventarioService;
    }

    @Transactional
    public Venta registrarVenta(Venta venta) {
        venta.setFechaVenta(LocalDateTime.now());

        // Generar ID del inventario del usuario
        String inventarioId = "i_" + venta.getUsuario().getId();

        // Validar stock y asignar detalles
        for (DetalleVenta detalle : venta.getDetalles()) {
            // Validar que el producto existe
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Producto no encontrado: " + detalle.getProducto().getId()));

            // Validar stock disponible
            Integer stockDisponible = itemInventarioService.obtenerStockDisponible(
                    inventarioId, producto.getId());
            
            if (stockDisponible < detalle.getCantidad()) {
                throw new RuntimeException(
                        String.format("Stock insuficiente para el producto '%s'. Disponible: %d, Solicitado: %d",
                                producto.getNombre(), stockDisponible, detalle.getCantidad()));
            }

            // Vincular producto y calcular subtotal
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.calcularSubtotal();
        }

        // Calcular total
        venta.calcularTotal();
        
        // Guardar la venta
        Venta ventaGuardada = ventaRepository.save(venta);

        // Actualizar inventario (restando stock)
        actualizarInventarioUsuario(ventaGuardada);

        return ventaGuardada;
    }

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public List<Venta> listarVentasPorUsuario(String usuarioId) {
        return ventaRepository.findByUsuario_Id(usuarioId);
    }

    public Venta obtenerPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }

    private void actualizarInventarioUsuario(Venta venta) {
        // Generar ID del inventario usando el mismo patrón que InventarioService
        String inventarioId = "i_" + venta.getUsuario().getId();

        for (DetalleVenta detalle : venta.getDetalles()) {
            try {
                // Restar stock del inventario
                itemInventarioService.restarProducto(
                        inventarioId,
                        detalle.getProducto().getId(),
                        detalle.getCantidad()
                );
                System.out.println("Inventario actualizado (venta): " + detalle.getProducto().getNombre() +
                        " - Cantidad restada: " + detalle.getCantidad());
            } catch (Exception e) {
                System.err.println("Error al actualizar inventario para producto " +
                        detalle.getProducto().getId() + ": " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Error al actualizar inventario: " + e.getMessage(), e);
            }
        }
    }
}

