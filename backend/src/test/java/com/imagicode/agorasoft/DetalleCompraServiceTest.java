package com.imagicode.agorasoft;

import com.imagicode.agorasoft.entidades.DetalleCompra;
import com.imagicode.agorasoft.repositorios.DetalleCompraRepository;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import com.imagicode.agorasoft.servicios.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DetalleCompraServiceTest {

    @Mock
    DetalleCompraRepository detalleCompraRepository;

    @InjectMocks
    DetalleCompraService detalleCompraService;

    @Test
    void listarDetalles_DevuelveLista() {
        DetalleCompra d1 = new DetalleCompra();
        DetalleCompra d2 = new DetalleCompra();
        when(detalleCompraRepository.findAll()).thenReturn(Arrays.asList(d1, d2));

        List<DetalleCompra> lista = detalleCompraService.listarDetalles();

        assertEquals(2, lista.size());
        verify(detalleCompraRepository).findAll();
    }

    @Test
    void obtenerPorId_CuandoExiste_RetornaDetalle() {
        DetalleCompra d = new DetalleCompra();
        d.setId(1L);
        when(detalleCompraRepository.findById(1L)).thenReturn(Optional.of(d));

        DetalleCompra resultado = detalleCompraService.obtenerPorId(1L);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void obtenerPorId_CuandoNoExiste_Lanza() {
        when(detalleCompraRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> detalleCompraService.obtenerPorId(2L));
        assertTrue(ex.getMessage().contains("no encontrado"));
    }

    @Test
    void eliminar_LlamaDelete() {
        detalleCompraService.eliminar(1L);
        verify(detalleCompraRepository).deleteById(1L);
    }
}
