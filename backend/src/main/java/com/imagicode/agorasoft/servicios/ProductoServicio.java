package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.repositorios.ProductoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicio {

    private final ProductoRepositorio productoRepositorio;

    public ProductoServicio(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    public List<Producto> listar() {
        return productoRepositorio.findAll();
    }

    public Producto guardar(Producto producto) {
        return productoRepositorio.save(producto);
    }

    public Producto obtenerPorId(Long id) {
        return productoRepositorio.findById(id).orElseThrow();
    }

    public Producto actualizar(Long id, Producto producto) {
        Producto productoExistente = productoRepositorio.findById(id).orElseThrow();
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setUnidadMedida(producto.getUnidadMedida());
        productoExistente.setCategoria(producto.getCategoria());
        productoExistente.setImagenUrl(producto.getImagenUrl());
        return productoRepositorio.save(productoExistente);
    }

    public void eliminar(Long id) {
        productoRepositorio.deleteById(id);
    }
}
