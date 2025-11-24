package com.imagicode.agorasoft;

import com.imagicode.agorasoft.entidades.ItemInventario;
import com.imagicode.agorasoft.entidades.Producto;
import com.imagicode.agorasoft.repositorios.ItemInventarioRepository;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import com.imagicode.agorasoft.servicios.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemInventarioServiceTest {

    @Mock
    ItemInventarioRepository itemInventarioRepository;

    @InjectMocks
    ItemInventarioService itemInventarioService;

    @Test
    void listarPorInventario_DevuelveLista() {
        when(itemInventarioRepository.findByInventarioId("inv1"))
                .thenReturn(Collections.singletonList(new ItemInventario()));

        List<ItemInventario> items = itemInventarioService.listarPorInventario("inv1");

        assertFalse(items.isEmpty());
        verify(itemInventarioRepository).findByInventarioId("inv1");
    }

    @Test
    void guardar_DevuelveGuardado() {
        ItemInventario item = new ItemInventario();
        when(itemInventarioRepository.save(item)).thenReturn(item);

        ItemInventario result = itemInventarioService.guardar(item);

        assertNotNull(result);
        verify(itemInventarioRepository).save(item);
    }

    @Test
    void actualizarCantidad_CuandoItemExiste_Y_CoincideInventario() {
        ItemInventario item = new ItemInventario();
        item.setId(1L);
        item.setInventarioId("inv1");
        item.setCantidad(5);

        when(itemInventarioRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemInventarioRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        ItemInventario actualizado = itemInventarioService.actualizarCantidad("inv1", 1L, 10);

        assertEquals(10, actualizado.getCantidad());
        verify(itemInventarioRepository).save(item);
    }

    @Test
    void actualizarCantidad_CuandoInventarioNoCoincide_Lanza() {
        ItemInventario item = new ItemInventario();
        item.setInventarioId("inv2");

        when(itemInventarioRepository.findById(1L)).thenReturn(Optional.of(item));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> itemInventarioService.actualizarCantidad("inv1", 1L, 10));

        assertTrue(ex.getMessage().contains("no pertenece"));
    }

    @Test
    void agregarProducto_CuandoExisteSumaCantidad() {
        ItemInventario existente = new ItemInventario();
        existente.setId(1L);
        existente.setInventarioId("inv1");
        existente.setCantidad(5);
        Producto producto = new Producto();
        producto.setId(100L);
        existente.setProducto(producto);

        when(itemInventarioRepository.findByInventarioIdAndProducto_Id("inv1", 100L))
                .thenReturn(Collections.singletonList(existente));
        when(itemInventarioRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        ItemInventario resultado = itemInventarioService.agregarProducto("inv1", 100L, 3);

        assertEquals(8, resultado.getCantidad());
        verify(itemInventarioRepository).save(existente);
    }

    @Test
    void agregarProducto_CuandoNoExisteCrea() {
        when(itemInventarioRepository.findByInventarioIdAndProducto_Id("inv1", 100L))
                .thenReturn(Collections.emptyList());
        when(itemInventarioRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        ItemInventario resultado = itemInventarioService.agregarProducto("inv1", 100L, 5);

        assertEquals("inv1", resultado.getInventarioId());
        assertEquals(5, resultado.getCantidad());
    }

    @Test
    void eliminar_LlamaDelete() {
        itemInventarioService.eliminar(1L);
        verify(itemInventarioRepository).deleteById(1L);
    }
}
