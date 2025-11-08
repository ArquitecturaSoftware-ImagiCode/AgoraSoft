package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.repositorios.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto obtener(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    public Producto guardar(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new RuntimeException("El precio debe ser mayor a 0");
        }
        if (producto.getUsuarioProveedorId() == null) {
            throw new RuntimeException("El producto debe tener un usuario proveedor asignado");
        }

        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto producto) {
        Producto productoExistente = obtener(id);

        if (producto.getNombre() != null && !producto.getNombre().trim().isEmpty()) {
            productoExistente.setNombre(producto.getNombre());
        }
        if (producto.getDescripcion() != null) {
            productoExistente.setDescripcion(producto.getDescripcion());
        }
        if (producto.getPrecio() != null && producto.getPrecio() > 0) {
            productoExistente.setPrecio(producto.getPrecio());
        }
        if (producto.getCategoria() != null) {
            productoExistente.setCategoria(producto.getCategoria());
        }
        if (producto.getImagenUrl() != null) {
            productoExistente.setImagenUrl(producto.getImagenUrl());
        }
        if (producto.getUsuarioProveedorId() != null) {
            productoExistente.setUsuarioProveedorId(producto.getUsuarioProveedorId());
        }

        return productoRepository.save(productoExistente);
    }

    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
    public List<Producto> listarPorUsuarioProveedor(String usuarioProveedorId) {
        return productoRepository.findByUsuarioProveedorId(usuarioProveedorId);
    }

}
