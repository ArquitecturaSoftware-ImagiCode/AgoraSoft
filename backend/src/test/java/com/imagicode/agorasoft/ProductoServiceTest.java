package com.imagicode.agorasoft;

import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.entidades.Proveedor;
import com.imagicode.agorasoft.repositorios.ProductoRepository;
import com.imagicode.agorasoft.repositorios.ProveedorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import com.imagicode.agorasoft.servicios.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    ProductoRepository productoRepository;

    @Mock
    ProveedorRepository proveedorRepository;

    @InjectMocks
    ProductoService productoService;

    Producto producto;
    Proveedor proveedor;

    @BeforeEach
    void setup() {
        proveedor = Proveedor.builder()
                .id(1L)
                .nombre("Proveedor1")
                .build();

        producto = Producto.builder()
                .id(1L)
                .nombre("Prod1")
                .precio(10.0)
                .proveedor(proveedor)
                .build();
    }

    @Test
    void listar_RetornaLista() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));
        List<Producto> list = productoService.listar();
        assertEquals(1, list.size());
    }

    @Test
    void obtener_CuandoExiste_RetornaProducto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        Producto p = productoService.obtener(1L);
        assertEquals("Prod1", p.getNombre());
    }

    @Test
    void obtener_CuandoNoExiste_LanzaException() {
        when(productoRepository.findById(2L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> productoService.obtener(2L));
        assertTrue(ex.getMessage().contains("no encontrado"));
    }

    @Test
    void guardar_ConDatosValidos_GuardaProducto() {
            when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
            when(productoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            Producto p = Producto.builder()
            .nombre("Nuevo")
            .precio(5.0)
            .proveedor(proveedor)
            .build();

            Producto saved = productoService.guardar(p);
            assertEquals("Nuevo", saved.getNombre());
            verify(productoRepository).save(any());
        }


    @Test
    void guardar_SinNombre_LanzaException() {
        Producto p = Producto.builder()
                .precio(5.0)
                .proveedor(proveedor)
                .build();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productoService.guardar(p));
        assertTrue(ex.getMessage().contains("nombre"));
    }

    @Test
    void guardar_SinProveedor_LanzaException() {
        Producto p = Producto.builder()
                .nombre("Prod")
                .precio(5.0)
                .build();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productoService.guardar(p));
        assertTrue(ex.getMessage().contains("proveedor"));
    }

    @Test
    void actualizar_CambiaCamposCorrectamente() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        when(productoRepository.save(any())).thenReturn(producto);

        Producto update = Producto.builder()
                .nombre("NuevoNombre")
                .precio(20.0)
                .proveedor(proveedor)
                .build();

        Producto result = productoService.actualizar(1L, update);

        assertEquals("NuevoNombre", result.getNombre());
        assertEquals(20.0, result.getPrecio());
        verify(productoRepository).save(any());
    }

    @Test
    void eliminar_CuandoExiste_Elimina() {
        when(productoRepository.existsById(1L)).thenReturn(true);
        productoService.eliminar(1L);
        verify(productoRepository).deleteById(1L);
    }

    @Test
    void eliminar_CuandoNoExiste_Lanza() {
        when(productoRepository.existsById(2L)).thenReturn(false);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> productoService.eliminar(2L));
        assertTrue(ex.getMessage().contains("no encontrado"));
    }
}
