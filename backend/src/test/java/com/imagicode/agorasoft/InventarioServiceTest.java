package com.imagicode.agorasoft;

import com.imagicode.agorasoft.entidades.Inventario;
import com.imagicode.agorasoft.repositorios.InventarioRepository;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import com.imagicode.agorasoft.servicios.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    InventarioRepository inventarioRepository;

    @InjectMocks
    InventarioService inventarioService;

    @Test
    void listarPorUsuario_DevuelveLista() {
        when(inventarioRepository.findByUsuarioId("user1"))
                .thenReturn(Collections.singletonList(new Inventario()));

        List<Inventario> lista = inventarioService.listarPorUsuario("user1");

        assertFalse(lista.isEmpty());
        verify(inventarioRepository).findByUsuarioId("user1");
    }

    @Test
    void guardar_CreaInventarioConId() {
        Inventario inventario = new Inventario();
        inventario.setUsuarioId("user1");
        inventario.setId("i_user1");

        when(inventarioRepository.save(any())).thenReturn(inventario);

        Inventario creado = inventarioService.guardar("user1");

        assertEquals("i_user1", creado.getId());
        verify(inventarioRepository).save(any());
    }

    @Test
    void obtenerOCrearInventario_CuandoExiste_RetornaExistente() {
        Inventario inv = new Inventario();
        when(inventarioRepository.findByUsuarioId("user1"))
                .thenReturn(Collections.singletonList(inv));

        Inventario res = inventarioService.obtenerOCrearInventario("user1");

        assertEquals(inv, res);
    }

    @Test
    void obtenerOCrearInventario_CuandoNoExiste_CreaYRetorna() {
        when(inventarioRepository.findByUsuarioId("user1"))
                .thenReturn(Collections.emptyList());
        Inventario nuevo = new Inventario();
        nuevo.setId("i_user1");
        nuevo.setUsuarioId("user1");
        when(inventarioRepository.save(any())).thenReturn(nuevo);

        Inventario res = inventarioService.obtenerOCrearInventario("user1");

        assertEquals("i_user1", res.getId());
        verify(inventarioRepository).save(any());
    }

    @Test
    void eliminar_LlamaDelete() {
        inventarioService.eliminar("id");
        verify(inventarioRepository).deleteById("id");
    }
}
