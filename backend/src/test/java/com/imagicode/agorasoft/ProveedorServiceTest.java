package com.imagicode.agorasoft;

import com.imagicode.agorasoft.entidades.Proveedor;
import com.imagicode.agorasoft.repositorios.ProveedorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.imagicode.agorasoft.servicios.*;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    @Mock
    ProveedorRepository proveedorRepository;

    @InjectMocks
    ProveedorService proveedorService;

    @Test
    void listarTodos_RetornaLista() {
        Proveedor p1 = new Proveedor();
        Proveedor p2 = new Proveedor();
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Proveedor> lista = proveedorService.listarTodos();

        assertEquals(2, lista.size());
        verify(proveedorRepository).findAll();
    }

    @Test
    void guardar_DelegaSave() {
        Proveedor p = new Proveedor();
        when(proveedorRepository.save(p)).thenReturn(p);

        Proveedor resultado = proveedorService.guardar(p);

        assertNotNull(resultado);
        verify(proveedorRepository).save(p);
    }
}
