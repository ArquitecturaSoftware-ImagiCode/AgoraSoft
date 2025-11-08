package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.entidades.Proveedor;
import com.imagicode.agorasoft.repositorios.ProductoRepository;
import com.imagicode.agorasoft.repositorios.ProveedorRepository;
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
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void listarPorProveedor_debeDelegarEnRepositorio() {
        Proveedor p = Proveedor.builder().id(1L).nombre("P").email("p@example.com").build();
        Producto a = Producto.builder().id(10L).nombre("A").precio(1.0).proveedor(p).build();
        Producto b = Producto.builder().id(11L).nombre("B").precio(2.0).proveedor(p).build();
        when(productoRepository.findByProveedorId(1L)).thenReturn(Arrays.asList(a, b));

        List<Producto> resultado = productoService.listarPorProveedor(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(productoRepository, times(1)).findByProveedorId(1L);
    }

    @Test
    void guardar_conProveedorExistente_guardadoCorrecto() {
        Proveedor p = Proveedor.builder().id(2L).nombre("Proveedor").email("prov@example.com").build();
        Producto input = Producto.builder().nombre("Nuevo").precio(5.0).proveedor(Proveedor.builder().id(2L).build())
                .build();
        Producto saved = Producto.builder().id(100L).nombre("Nuevo").precio(5.0).proveedor(p).build();

        when(proveedorRepository.findById(2L)).thenReturn(Optional.of(p));
        when(productoRepository.save(any(Producto.class))).thenReturn(saved);

        Producto result = productoService.guardar(input);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Nuevo", result.getNombre());
        assertEquals(p.getId(), result.getProveedor().getId());
        verify(proveedorRepository, times(1)).findById(2L);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void guardar_sinProveedor_debeLanzarExcepcion() {
        Producto input = Producto.builder().nombre("NoProv").precio(3.0).build();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productoService.guardar(input));
        assertTrue(ex.getMessage().toLowerCase().contains("proveedor"));
        verify(productoRepository, never()).save(any());
    }

    @Test
    void eliminar_noExiste_debeLanzarExcepcion() {
        when(productoRepository.existsById(999L)).thenReturn(false);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> productoService.eliminar(999L));
        assertTrue(ex.getMessage().toLowerCase().contains("no encontrado")
                || ex.getMessage().toLowerCase().contains("no encontrado"));
        verify(productoRepository, never()).deleteById(anyLong());
    }
}
