package com.imagicode.agorasoft.config;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.entidades.Usuario;
import com.imagicode.agorasoft.repositorios.ProductoRepository;
import com.imagicode.agorasoft.repositorios.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        return args -> {
            System.out.println("🔍 Verificando base de datos...");

            // Crear un usuario proveedor si no existe
            String correoProveedor = "proveedor1@yopmail.com";
            Usuario proveedor = usuarioRepository.findByCorreo(correoProveedor)
                    .orElseGet(() -> {
                        Usuario u = new Usuario();
                        u.setId("user_proveedor1");
                        u.setNombre("Proveedor");
                        u.setApellido("Demo");
                        u.setCorreo(correoProveedor);
                        u.setRol("PROVEEDOR");
                        u.setOrganizacion("DemoOrg");
                        System.out.println("🌱 Creando usuario proveedor: " + correoProveedor);
                        return usuarioRepository.save(u);
                    });

            // Crear productos solo si la tabla está vacía
            if (productoRepository.count() == 0) {
                System.out.println("🌱 Poblando productos de prueba...");

                productoRepository.save(Producto.builder()
                        .nombre("Tomate")
                        .descripcion("Tomate chonto fresco por kilo")
                        .precio(2500.0)
                        .categoria("Verduras")
                        .usuarioProveedor(proveedor)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Papa Criolla")
                        .descripcion("Papa criolla de primera calidad")
                        .precio(1800.0)
                        .categoria("Tubérculos")
                        .usuarioProveedor(proveedor)
                        .build());

                productoRepository.save(Producto.builder()
                        .nombre("Cebolla Cabezona")
                        .descripcion("Cebolla cabezona blanca por kilo")
                        .precio(2000.0)
                        .categoria("Verduras")
                        .usuarioProveedor(proveedor)
                        .build());

                System.out.println("✅ Base de datos poblada con productos para el proveedor: " + proveedor.getCorreo());
            } else {
                System.out.println("ℹ️ Productos ya existen en la base de datos. Omitiendo población inicial.");
            }
        };
    }
}
