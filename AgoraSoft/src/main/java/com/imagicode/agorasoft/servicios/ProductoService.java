package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.repositorios.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.util.ReflectionUtils;
import java.util.Map;
import java.lang.reflect.Field;

@Service
public class ProductoService {
    private final ProductoRepository productoRepositorio;

    public ProductoService(ProductoRepository productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    public List<Producto> obtenerProductos() {
        return productoRepositorio.findAll();
    }

    public Producto obtenerProductoPorId(Long Id) {
        return productoRepositorio.findById(Id).orElse(null);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto productoEditado) {
        Producto productoExistente = productoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + id));
        if (productoEditado.getSeccion() != null && !productoEditado.getSeccion().isEmpty()) {
            productoExistente.setSeccion(productoEditado.getSeccion());
        }
        if (productoEditado.getNombreProducto() != null && !productoEditado.getNombreProducto().isEmpty()) {
            productoExistente.setNombreProducto(productoEditado.getNombreProducto());
        }
        if (productoEditado.getCantiLibra() != 0) {
            productoExistente.setCantiLibra(productoEditado.getCantiLibra());
        }
        if (productoEditado.getPreciLibra() != 0) {
            productoExistente.setPreciLibra(productoEditado.getPreciLibra());
        }
        return productoRepositorio.save(productoExistente);
    }

    public void eliminarProducto(Long Id) {
        productoRepositorio.deleteById(Id);
    }

    public Producto actualizarParcial(Long id, Map<String, Object> updates) {
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + id));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Producto.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, producto, value);
            }
        });

        return productoRepositorio.save(producto);
    }

}
