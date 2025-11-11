package com.imagicode.agorasoft.config;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.entidades.Proveedor;
import com.imagicode.agorasoft.repositorios.ProductoRepository;
import com.imagicode.agorasoft.repositorios.ProveedorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProveedorRepository proveedorRepository, 
                                   ProductoRepository productoRepository) {
        return args -> {
            System.out.println("🔍 Verificando base de datos...");
            
            // Crear proveedores si no existen
            List<Proveedor> proveedores = proveedorRepository.findAll();
            
            if (proveedores.isEmpty()) {
                System.out.println("🌱 Creando proveedores de prueba...");
                
                Proveedor proveedor1 = proveedorRepository.save(
                    Proveedor.builder()
                        .nombre("Frutas y Verduras La Plaza")
                        .email("contacto@laplaza.com")
                        .build()
                );
                
                Proveedor proveedor2 = proveedorRepository.save(
                    Proveedor.builder()
                        .nombre("Distribuidora El Campo")
                        .email("ventas@elcampo.com")
                        .build()
                );
                
                Proveedor proveedor3 = proveedorRepository.save(
                    Proveedor.builder()
                        .nombre("Abastos Central")
                        .email("info@abastoscentral.com")
                        .build()
                );
                
                proveedores = List.of(proveedor1, proveedor2, proveedor3);
                System.out.println("✅ " + proveedores.size() + " proveedores creados");
            } else {
                System.out.println("ℹ️  Proveedores existentes: " + proveedores.size());
            }
            
            // Crear productos si no existen
            if (productoRepository.count() == 0) {
                System.out.println("🌱 Poblando productos de prueba...");
                
                // Usar los proveedores (ahora garantizados)
                Proveedor proveedor1 = proveedores.get(0);
                Proveedor proveedor2 = proveedores.size() > 1 ? proveedores.get(1) : proveedor1;
                Proveedor proveedor3 = proveedores.size() > 2 ? proveedores.get(2) : proveedor1;
                
                // Crear productos
                productoRepository.save(Producto.builder()
                    .nombre("Tomate")
                    .descripcion("Tomate chonto fresco por kilo")
                    .precio(2500.0)
                    .categoria("Verduras")
                    .proveedor(proveedor1)
                    .build());
                
                productoRepository.save(Producto.builder()
                    .nombre("Papa Criolla")
                    .descripcion("Papa criolla de primera calidad")
                    .precio(1800.0)
                    .categoria("Tubérculos")
                    .proveedor(proveedor1)
                    .build());
                
                productoRepository.save(Producto.builder()
                    .nombre("Cebolla Cabezona")
                    .descripcion("Cebolla cabezona blanca por kilo")
                    .precio(2000.0)
                    .categoria("Verduras")
                    .proveedor(proveedor1)
                    .build());
                
                productoRepository.save(Producto.builder()
                    .nombre("Zanahoria")
                    .descripcion("Zanahoria fresca de la región")
                    .precio(1500.0)
                    .categoria("Verduras")
                    .proveedor(proveedor2)
                    .build());
                
                productoRepository.save(Producto.builder()
                    .nombre("Lechuga")
                    .descripcion("Lechuga crespa hidropónica")
                    .precio(1200.0)
                    .categoria("Verduras")
                    .proveedor(proveedor2)
                    .build());
                
                productoRepository.save(Producto.builder()
                    .nombre("Banano")
                    .descripcion("Banano maduro por kilo")
                    .precio(2800.0)
                    .categoria("Frutas")
                    .proveedor(proveedor3)
                    .build());
                
                productoRepository.save(Producto.builder()
                    .nombre("Manzana")
                    .descripcion("Manzana roja importada")
                    .precio(4500.0)
                    .categoria("Frutas")
                    .proveedor(proveedor3)
                    .build());
                
                productoRepository.save(Producto.builder()
                    .nombre("Naranja")
                    .descripcion("Naranja Valencia dulce")
                    .precio(2200.0)
                    .categoria("Frutas")
                    .proveedor(proveedor3)
                    .build());
                
                productoRepository.save(Producto.builder()
                    .nombre("Limón")
                    .descripcion("Limón tahití para jugo")
                    .precio(3000.0)
                    .categoria("Frutas")
                    .proveedor(proveedor2)
                    .build());
                
                productoRepository.save(Producto.builder()
                    .nombre("Aguacate")
                    .descripcion("Aguacate Hass de Antioquia")
                    .precio(5500.0)
                    .categoria("Frutas")
                    .proveedor(proveedor2)
                    .build());
                
                productoRepository.save(Producto.builder()
                    .nombre("Cilantro")
                    .descripcion("Cilantro fresco en rama")
                    .precio(800.0)
                    .categoria("Hierbas")
                    .proveedor(proveedor1)
                    .build());
                
                productoRepository.save(Producto.builder()
                    .nombre("Yuca")
                    .descripcion("Yuca blanca para freír")
                    .precio(1600.0)
                    .categoria("Tubérculos")
                    .proveedor(proveedor1)
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

