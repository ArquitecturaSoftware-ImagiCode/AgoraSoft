package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.entidades.Proveedor;
import com.imagicode.agorasoft.servicios.ProductoService;
import com.imagicode.agorasoft.servicios.UsuarioService;
import com.imagicode.agorasoft.repositorios.ProveedorRepository;
import com.imagicode.agorasoft.dto.ProductoDTO;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final ProveedorRepository proveedorRepository;

    public ProductoController(ProductoService productoService, UsuarioService usuarioService,
            ProveedorRepository proveedorRepository) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.proveedorRepository = proveedorRepository;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    // Listar productos de un proveedor
    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<Producto>> listarPorProveedor(@PathVariable Long proveedorId) {
        return ResponseEntity.ok(productoService.listarPorProveedor(proveedorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.obtener(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody @jakarta.validation.Valid ProductoDTO dto,
            jakarta.servlet.http.HttpServletRequest request) {
        try {
            // obtener clerkUserId del request (seteado por ClerkAuthFilter)
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
            }

            var usuario = usuarioService.obtenerUsuarioPorId(clerkUserId);
            if (usuario == null || usuario.getCorreo() == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Usuario no tiene datos de proveedor asociados");
            }

            Proveedor proveedor = proveedorRepository.findByEmail(usuario.getCorreo()).orElse(null);
            if (proveedor == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario autenticado no es proveedor");
            }

            Producto producto = new Producto();
            producto.setNombre(dto.getNombre());
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setCategoria(dto.getCategoria());
            producto.setImagenUrl(dto.getImagenUrl());
            producto.setProveedor(proveedor);

            Producto nuevoProducto = productoService.guardar(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @jakarta.validation.Valid ProductoDTO dto,
            jakarta.servlet.http.HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
            }

            var usuario = usuarioService.obtenerUsuarioPorId(clerkUserId);
            if (usuario == null || usuario.getCorreo() == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Usuario no tiene datos de proveedor asociados");
            }

            Proveedor proveedor = proveedorRepository.findByEmail(usuario.getCorreo()).orElse(null);
            if (proveedor == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario autenticado no es proveedor");
            }

            // comprobar que el producto pertenece al proveedor
            Producto existente = productoService.obtener(id);
            if (!existente.getProveedor().getId().equals(proveedor.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado para modificar este producto");
            }

            // Mapear campos del DTO al existente
            if (dto.getNombre() != null)
                existente.setNombre(dto.getNombre());
            if (dto.getDescripcion() != null)
                existente.setDescripcion(dto.getDescripcion());
            if (dto.getPrecio() != null)
                existente.setPrecio(dto.getPrecio());
            if (dto.getCategoria() != null)
                existente.setCategoria(dto.getCategoria());
            if (dto.getImagenUrl() != null)
                existente.setImagenUrl(dto.getImagenUrl());

            Producto productoActualizado = productoService.actualizar(id, existente);
            return ResponseEntity.ok(productoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint básico para subir imagen asociada a un producto (multipart)
    @PostMapping("/{id}/imagen")
    public ResponseEntity<?> subirImagen(@PathVariable Long id, @RequestParam("file") MultipartFile file,
            jakarta.servlet.http.HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");

            var usuario = usuarioService.obtenerUsuarioPorId(clerkUserId);
            if (usuario == null || usuario.getCorreo() == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Usuario no tiene datos de proveedor asociados");

            Proveedor proveedor = proveedorRepository.findByEmail(usuario.getCorreo()).orElse(null);
            if (proveedor == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario autenticado no es proveedor");

            Producto producto = productoService.obtener(id);
            if (!producto.getProveedor().getId().equals(proveedor.getId()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado para modificar este producto");

            // Guardar archivo localmente (implementación simple)
            Path uploadsDir = Path.of("uploads");
            if (!Files.exists(uploadsDir))
                Files.createDirectories(uploadsDir);
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path target = uploadsDir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            producto.setImagenUrl(target.toString());
            productoService.actualizar(id, producto);

            return ResponseEntity.ok(Map.of("imagenUrl", producto.getImagenUrl()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
