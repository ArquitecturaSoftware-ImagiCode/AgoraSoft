package com.imagicode.agorasoft.UsuarioControllerTest;


import com.imagicode.agorasoft.controladores.UsuarioController;
import com.imagicode.agorasoft.entidades.Usuario;
import com.imagicode.agorasoft.servicios.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.MockitoAnnotations;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarUsuarios() {
        // Arrange
        Usuario u1 = new Usuario();
        u1.setId("1");
        u1.setNombre("Juan");

        Usuario u2 = new Usuario();
        u2.setId("2");
        u2.setNombre("Ana");

        List<Usuario> mockUsuarios = Arrays.asList(u1, u2);

        when(usuarioService.obtenerUsuarios()).thenReturn(mockUsuarios);

        // Act
        List<Usuario> resultado = usuarioController.listarUsuarios();

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Juan");
        verify(usuarioService, times(1)).obtenerUsuarios();
    }

    @Test
    void testObtenerUsuarioPorId() {
        Usuario u = new Usuario();
        u.setId("1");
        u.setNombre("Carlos");

        when(usuarioService.obtenerUsuarioPorId("1")).thenReturn(u);

        Usuario resultado = usuarioController.obtenerUsuarioPorId("1");

        assertThat(resultado.getNombre()).isEqualTo("Carlos");
        verify(usuarioService, times(1)).obtenerUsuarioPorId("1");
    }

    @Test
    void testCrearUsuario() {
        Usuario u = new Usuario();
        u.setId("3");
        u.setNombre("Laura");

        when(usuarioService.crearUsuario(u)).thenReturn(u);

        Usuario resultado = usuarioController.crearUsuario(u);

        assertThat(resultado.getId()).isEqualTo("3");
        verify(usuarioService, times(1)).crearUsuario(u);
    }

    @Test
    void testEliminarUsuario() {
        String id = "4";

        usuarioController.eliminarUsuario(id);

        verify(usuarioService, times(1)).eliminarUsuario(id);
    }

    @Test
    void testActualizarUsuario() {
        Usuario u = new Usuario();
        u.setId("5");
        u.setNombre("Pedro");

        when(usuarioService.actualizarUsuario("5", u)).thenReturn(u);

        Usuario resultado = usuarioController.actualizarUsuario("5", u);

        assertThat(resultado.getNombre()).isEqualTo("Pedro");
        verify(usuarioService, times(1)).actualizarUsuario("5", u);
    }
}
