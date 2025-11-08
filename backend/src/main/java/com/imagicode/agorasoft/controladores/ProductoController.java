package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.servicios.ProductoService;
import com.imagicode.agorasoft.servicios.UsuarioService;
import com.imagicode.agorasoft.dto.ProductoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public ProductoController(ProductoService productoService, UsuarioService usuarioService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    // 🔹 Listar productos del usuario proveedor autenticado
    @GetMapping("/mis-productos")
    public ResponseEntity<?> listarMisProductos(jakarta.servlet.http.HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
            }

            var usuario = usuarioService.obtenerUsuarioPorId(clerkUserId);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario no encontrado");
            }

            if (!"proveedor".equalsIgnoreCase(usuario.getRol())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Solo los proveedores pueden listar sus productos");
            }

            List<Producto> productos = productoService.listarPorUsuarioProveedor(usuario.getId());
            return ResponseEntity.ok(productos);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.obtener(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔹 Crear producto (usuario proveedor autenticado)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody @jakarta.validation.Valid ProductoDTO dto,
                                   jakarta.servlet.http.HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");

            var usuario = usuarioService.obtenerUsuarioPorId(clerkUserId);
            if (usuario == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario no encontrado");

            if (!"proveedor".equalsIgnoreCase(usuario.getRol()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo los proveedores pueden crear productos");

            Producto producto = new Producto();
            producto.setNombre(dto.getNombre());
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setCategoria(dto.getCategoria());
            producto.setImagenUrl(dto.getImagenUrl());
            producto.setUsuarioProveedorId(usuario.getId());

            Producto nuevoProducto = productoService.guardar(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🔹 Actualizar producto (solo si pertenece al usuario proveedor)
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @RequestBody @jakarta.validation.Valid ProductoDTO dto,
                                        jakarta.servlet.http.HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");

            var usuario = usuarioService.obtenerUsuarioPorId(clerkUserId);
            if (usuario == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario no encontrado");

            Producto existente = productoService.obtener(id);

            // Verificar que el producto pertenece al usuario proveedor
            if (!existente.getUsuarioProveedorId().equals(usuario.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No autorizado para modificar este producto");
            }

            // Actualizar campos
            if (dto.getNombre() != null) existente.setNombre(dto.getNombre());
            if (dto.getDescripcion() != null) existente.setDescripcion(dto.getDescripcion());
            if (dto.getPrecio() != null) existente.setPrecio(dto.getPrecio());
            if (dto.getCategoria() != null) existente.setCategoria(dto.getCategoria());
            if (dto.getImagenUrl() != null) existente.setImagenUrl(dto.getImagenUrl());

            Producto productoActualizado = productoService.actualizar(id, existente);
            return ResponseEntity.ok(productoActualizado);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🔹 Eliminar producto (solo si pertenece al usuario proveedor)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id,
                                      jakarta.servlet.http.HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");

            var usuario = usuarioService.obtenerUsuarioPorId(clerkUserId);
            if (usuario == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario no encontrado");

            Producto producto = productoService.obtener(id);
            if (!producto.getUsuarioProveedorId().equals(usuario.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No autorizado para eliminar este producto");
            }

            productoService.eliminar(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🔹 Subir imagen (solo si pertenece al usuario proveedor)
    @PostMapping("/{id}/imagen")
    public ResponseEntity<?> subirImagen(@PathVariable Long id,
                                         @RequestParam("file") MultipartFile file,
                                         jakarta.servlet.http.HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");

            var usuario = usuarioService.obtenerUsuarioPorId(clerkUserId);
            if (usuario == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario no encontrado");

            Producto producto = productoService.obtener(id);
            if (!producto.getUsuarioProveedorId().equals(usuario.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No autorizado para modificar este producto");
            }

            // Guardar archivo localmente
            Path uploadsDir = Path.of("uploads");
            if (!Files.exists(uploadsDir)) Files.createDirectories(uploadsDir);

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path target = uploadsDir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            producto.setImagenUrl(target.toString());
            productoService.actualizar(id, producto);

            return ResponseEntity.ok(Map.of("imagenUrl", producto.getImagenUrl()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
