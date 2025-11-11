package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.*;
import com.imagicode.agorasoft.repositorios.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProductoRepository productoRepository;
    private final ItemInventarioService itemInventarioService;
    private final UsuarioRepository usuarioRepository;

    public CompraService(CompraRepository compraRepository,
            ProductoRepository productoRepository,
            UsuarioRepository usuarioRepository,
            ItemInventarioService itemInventarioService) {
        this.usuarioRepository= usuarioRepository;
        this.compraRepository = compraRepository;
        this.productoRepository = productoRepository;
        this.itemInventarioService = itemInventarioService;
    }

    @Transactional
    public Compra registrarCompra(Compra compra) {
        System.out.println(compra);
        compra.setFechaCompra(LocalDateTime.now());

        // Buscar usuario comprador
        Usuario usuario = usuarioRepository.findById(compra.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario comprador no encontrado"));

        // Buscar proveedor
        Usuario proveedor = usuarioRepository.findById(compra.getProveedor().getId())
                .orElseThrow(() -> new RuntimeException("Usuario proveedor no encontrado"));

        if (!"PROVEEDOR".equalsIgnoreCase(proveedor.getRol())) {
            throw new RuntimeException("El usuario seleccionado no tiene rol de proveedor");
        }

        // Reasignar las entidades gestionadas (JPA necesita entidades persistentes)
        compra.setUsuario(usuario);
        compra.setProveedor(proveedor);

        // Procesar detalles de la compra
        compra.getDetalles().forEach(detalle -> {
            detalle.setCompra(compra);
            detalle.setProducto(
                    productoRepository.findById(detalle.getProducto().getId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"))
            );
            detalle.calcularSubtotal();
        });

        compra.calcularTotal();

        Compra compraGuardada = compraRepository.save(compra);

        // Actualizar inventario
        actualizarInventarioUsuario(compraGuardada);

        return compraGuardada;
    }

    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    public List<Compra> listarComprasPorUsuario(String usuarioId) {
        return compraRepository.findByUsuario_Id(usuarioId);
    }

    public Compra obtenerPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
    }

    public void eliminarCompra(Long id) {
        compraRepository.deleteById(id);
    }
    
    private void actualizarInventarioUsuario(Compra compra) {
        // Generar ID del inventario usando el mismo patrón que InventarioService
        String inventarioId = "i_" + compra.getUsuario().getId();
        
        for (DetalleCompra detalle : compra.getDetalles()) {
            try {
                // Agregar o actualizar el producto en el inventario
                itemInventarioService.agregarProducto(
                    inventarioId,
                    detalle.getProducto().getId(),
                    detalle.getCantidad()
                );
                System.out.println("Inventario actualizado: " + detalle.getProducto().getNombre() + 
                    " - Cantidad: " + detalle.getCantidad());
            } catch (Exception e) {
                System.err.println("Error al actualizar inventario para producto " + 
                    detalle.getProducto().getId() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
