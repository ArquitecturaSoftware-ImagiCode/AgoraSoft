package com.imagicode.agorasoft.agorasoft.controladores;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import com.imagicode.agorasoft.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.agorasoft.servicios.ProductoService;

@RestController
@RequestMapping("/inventario")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // GET: trae todos los productos
    @GetMapping
    public List<Producto> listaProductos() {
        return productoService.obtenerProductos();
    }
/*
    // GET: traer producto por id
    @GetMapping("/{id}")
    public Producto obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }

    // POST: Insertar nuevos productos
    @PostMapping
    public Producto creaProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    // PUT: Actualizar usuario por id
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto productoEditado) {
        return productoService.actualizarProducto(id, productoEditado);
    }

    // DELETE: Eleminar producto por id
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    @PatchMapping("/{id}")
    public Producto actualizarProductoParcial(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return productoService.actualizarParcial(id, updates);
    }
*/
}
