package com.imagicode.agorasoft.agorasoft.servicios;

import com.imagicode.agorasoft.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.agorasoft.repositorios.ProductoRepositorio;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.util.ReflectionUtils;
import java.util.Map;
import java.lang.reflect.Field;

@Service
public class ProductoService {
    private final ProductoRepositorio productoRepositorio;
    public List<Producto> obtenerProductos() {
        return productoRepositorio.findAll();
    }

    public ProductoService(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

/*

    public Producto obtenerProductoPorId(String id) {
        return productoRepositorio.findById(id).orElse(null);
    }

    public Producto guardarProducto(Producto prodcuto) {
        return productoRepositorio.save(producto);
    }
    public List<Producto> obtenerProductos() {
        return productoRepositorio.findAll();
    }
    public void eliminarInventario(String id) {
        productoRepositorio.deleteById(id);
    }*/
/*
    public ProductoService(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }



    public Producto obtenerProductoPorId(String Id) {
        return productoRepositorio.findById(Id).orElse(null);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    public Producto actualizarProducto(String id, Producto productoEditado) {
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

    public void eliminarProducto(String Id) {
        productoRepositorio.deleteById(Id);
    }

    public Producto actualizarParcial(String id, Map<String, Object> updates) {
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
*/
}
