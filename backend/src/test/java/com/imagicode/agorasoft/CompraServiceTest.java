package com.imagicode.agorasoft;

import com.imagicode.agorasoft.entidades.*;
import com.imagicode.agorasoft.repositorios.*;
import com.imagicode.agorasoft.servicios.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    CompraRepository compraRepository;
    @Mock
    ProductoRepository productoRepository;
    @Mock
    ProveedorRepository proveedorRepository;
    @Mock
    ItemInventarioService itemInventarioService;

    @InjectMocks
    CompraService compraService;

    @Test
    void registrarCompra_CaminoFeliz() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);

        Producto producto = new Producto();
        producto.setId(10L);
        producto.setNombre("Prod1");

        DetalleCompra detalle = new DetalleCompra();
        detalle.setProducto(producto);
        detalle.setCantidad(2);

        Usuario usuario = new Usuario();
        usuario.setId("user1");

        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        compra.setDetalles(Collections.singletonList(detalle));
        compra.setUsuario(usuario);

        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        when(productoRepository.findById(10L)).thenReturn(Optional.of(producto));
        when(compraRepository.save(any())).thenAnswer(i -> {
            Compra c = i.getArgument(0);
            c.setId(100L);
            return c;
        });
        when(itemInventarioService.agregarProducto(anyString(), anyLong(), anyInt())).thenReturn(new ItemInventario());

        Compra resultado = compraService.registrarCompra(compra);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
        assertNotNull(resultado.getFechaCompra());

        verify(proveedorRepository).findById(1L);
        verify(productoRepository).findById(10L);
        verify(compraRepository).save(any());
        verify(itemInventarioService).agregarProducto(eq("i_user1"), eq(10L), eq(2));
    }

    @Test
    void registrarCompra_ProveedorNoEncontrado_Lanza() {
        Compra compra = new Compra();
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);
        compra.setProveedor(proveedor);

        when(proveedorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> compraService.registrarCompra(compra));
        assertTrue(ex.getMessage().contains("Proveedor no encontrado"));
    }

    @Test
    void registrarCompra_ProductoNoEncontrado_Lanza() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);

        Producto producto = new Producto();
        producto.setId(10L);

        DetalleCompra detalle = new DetalleCompra();
        detalle.setProducto(producto);
        detalle.setCantidad(2);

        Usuario usuario = new Usuario();
        usuario.setId("user1");

        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        compra.setDetalles(Collections.singletonList(detalle));
        compra.setUsuario(usuario);

        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        when(productoRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> compraService.registrarCompra(compra));
        assertTrue(ex.getMessage().contains("Producto no encontrado"));
    }
}
