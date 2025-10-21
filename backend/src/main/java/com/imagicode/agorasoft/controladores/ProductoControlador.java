package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.servicios.ProductoServicio;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoControlador {

    private final ProductoServicio productoServicio;

    public ProductoControlador(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }

    @GetMapping
    public List<Producto> listar() {
        return productoServicio.listar();
    }

    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {
        return productoServicio.obtenerPorId(id);
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoServicio.guardar(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return productoServicio.actualizar(id, producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoServicio.eliminar(id);
    }

    @PostMapping("/poblar-datos")
    public String poblarDatosEjemplo() {
        // Crear productos de ejemplo
        List<Producto> productosEjemplo = Arrays.asList(
            Producto.builder()
                .nombre("Tomate")
                .descripcion("Tomate fresco de invernadero")
                .precio(2500.0)
                .unidadMedida("kg")
                .categoria("Vegetales")
                .build(),
            Producto.builder()
                .nombre("Papa")
                .descripcion("Papa criolla de alta calidad")
                .precio(1800.0)
                .unidadMedida("kg")
                .categoria("Tubérculos")
                .build(),
            Producto.builder()
                .nombre("Lechuga")
                .descripcion("Lechuga crespa fresca")
                .precio(1200.0)
                .unidadMedida("unidad")
                .categoria("Vegetales")
                .build(),
            Producto.builder()
                .nombre("Cebolla")
                .descripcion("Cebolla cabezona blanca")
                .precio(2200.0)
                .unidadMedida("kg")
                .categoria("Vegetales")
                .build(),
            Producto.builder()
                .nombre("Zanahoria")
                .descripcion("Zanahoria orgánica")
                .precio(2000.0)
                .unidadMedida("kg")
                .categoria("Vegetales")
                .build(),
            Producto.builder()
                .nombre("Pimentón")
                .descripcion("Pimentón rojo fresco")
                .precio(3500.0)
                .unidadMedida("kg")
                .categoria("Vegetales")
                .build()
        );

        for (Producto producto : productosEjemplo) {
            try {
                productoServicio.guardar(producto);
            } catch (Exception e) {
                // Ignorar errores de productos duplicados
            }
        }

        return "Productos de ejemplo creados exitosamente";
    }
}
