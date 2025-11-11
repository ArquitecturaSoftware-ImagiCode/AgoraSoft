package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.entidades.Usuario;
import com.imagicode.agorasoft.repositorios.ProductoRepository;
import com.imagicode.agorasoft.repositorios.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void listarPorUsuarioProveedor_debeDelegarEnRepositorio() {
        Usuario u = new Usuario("u1", "Pedro", "Pérez", "pedro@example.com", "PROVEEDOR", "OrgX");
        Producto a = Producto.builder().id(10L).nombre("A").precio(1.0).usuarioProveedor(u).build();
        Producto b = Producto.builder().id(11L).nombre("B").precio(2.0).usuarioProveedor(u).build();

        when(productoRepository.findByUsuarioProveedorId("u1")).thenReturn(Arrays.asList(a, b));

        List<Producto> resultado = productoService.listarPorUsuarioProveedor("u1");

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(productoRepository, times(1)).findByUsuarioProveedorId("u1");
    }

    @Test
    void guardar_conUsuarioProveedorExistente_guardadoCorrecto() {
        Usuario u = new Usuario("u2", "María", "Gómez", "maria@example.com", "PROVEEDOR", "OrgY");
        Producto input = Producto.builder()
                .nombre("Nuevo Producto")
                .precio(5.0)
                .usuarioProveedor(new Usuario("u2", null, null, null, null, null))
                .build();
        Producto saved = Producto.builder()
                .id(100L)
                .nombre("Nuevo Producto")
                .precio(5.0)
                .usuarioProveedor(u)
                .build();

        when(usuarioRepository.findById("u2")).thenReturn(Optional.of(u));
        when(productoRepository.save(any(Producto.class))).thenReturn(saved);

        Producto result = productoService.guardar(input);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Nuevo Producto", result.getNombre());
        assertEquals(u.getId(), result.getUsuarioProveedor().getId());
        verify(usuarioRepository, times(1)).findById("u2");
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void guardar_sinUsuarioProveedor_debeLanzarExcepcion() {
        Producto input = Producto.builder().nombre("SinProveedor").precio(3.0).build();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productoService.guardar(input));
        assertTrue(ex.getMessage().toLowerCase().contains("usuario"));
        verify(productoRepository, never()).save(any());
    }

    @Test
    void eliminar_noExiste_debeLanzarExcepcion() {
        when(productoRepository.existsById(999L)).thenReturn(false);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> productoService.eliminar(999L));
        assertTrue(ex.getMessage().toLowerCase().contains("no encontrado"));
        verify(productoRepository, never()).deleteById(anyLong());
    }
}
