package com.imagicode.agorasoft.config;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.repositorios.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository productoRepository) {
        return args -> {
            System.out.println("🔍 Verificando base de datos...");

            if (productoRepository.count() == 0) {
                System.out.println("🌱 Poblando productos de prueba...");

                // 🔹 Simular IDs de usuario (proveedores)
                String usuario1 = "user_abc123";
                String usuario2 = "user_def456";
                String usuario3 = "user_ghi789";

                productoRepository.save(Producto.builder()
                        .nombre("Tomate")
                        .descripcion("Tomate chonto fresco por kilo")
                        .precio(2500.0)
                        .categoria("Verduras")
                        .usuarioProveedorId(usuario1)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Papa Criolla")
                        .descripcion("Papa criolla de primera calidad")
                        .precio(1800.0)
                        .categoria("Tubérculos")
                        .usuarioProveedorId(usuario1)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Cebolla Cabezona")
                        .descripcion("Cebolla cabezona blanca por kilo")
                        .precio(2000.0)
                        .categoria("Verduras")
                        .usuarioProveedorId(usuario1)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Zanahoria")
                        .descripcion("Zanahoria fresca de la región")
                        .precio(1500.0)
                        .categoria("Verduras")
                        .usuarioProveedorId(usuario2)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Lechuga")
                        .descripcion("Lechuga crespa hidropónica")
                        .precio(1200.0)
                        .categoria("Verduras")
                        .usuarioProveedorId(usuario2)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Banano")
                        .descripcion("Banano maduro por kilo")
                        .precio(2800.0)
                        .categoria("Frutas")
                        .usuarioProveedorId(usuario3)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Manzana")
                        .descripcion("Manzana roja importada")
                        .precio(4500.0)
                        .categoria("Frutas")
                        .usuarioProveedorId(usuario3)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Naranja")
                        .descripcion("Naranja Valencia dulce")
                        .precio(2200.0)
                        .categoria("Frutas")
                        .usuarioProveedorId(usuario3)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Limón")
                        .descripcion("Limón tahití para jugo")
                        .precio(3000.0)
                        .categoria("Frutas")
                        .usuarioProveedorId(usuario2)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Aguacate")
                        .descripcion("Aguacate Hass de Antioquia")
                        .precio(5500.0)
                        .categoria("Frutas")
                        .usuarioProveedorId(usuario2)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Cilantro")
                        .descripcion("Cilantro fresco en rama")
                        .precio(800.0)
                        .categoria("Hierbas")
                        .usuarioProveedorId(usuario1)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Yuca")
                        .descripcion("Yuca blanca para freír")
                        .precio(1600.0)
                        .categoria("Tubérculos")
                        .usuarioProveedorId(usuario1)
                        .build());

                System.out.println("✅ Base de datos poblada con " +
                        productoRepository.count() + " productos");
            } else {
                System.out.println("ℹ️  Ya existen productos en la base de datos (" +
                        productoRepository.count() + " productos). Omitiendo población inicial.");
            }
        };
    }
}
