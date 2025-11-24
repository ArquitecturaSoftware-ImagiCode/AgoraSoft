package com.imagicode.agorasoft;

import com.imagicode.agorasoft.entidades.Usuario;
import com.imagicode.agorasoft.repositorios.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import com.imagicode.agorasoft.servicios.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void obtenerUsuarios_RetornaLista() {
        Usuario u1 = new Usuario();
        Usuario u2 = new Usuario();
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Usuario> usuarios = usuarioService.obtenerUsuarios();

        assertEquals(2, usuarios.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void obtenerUsuarioPorId_CuandoExiste_RetornaUsuario() {
        Usuario u = new Usuario();
        u.setId("1");
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(u));

        Usuario resultado = usuarioService.obtenerUsuarioPorId("1");

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
    }

    @Test
    void obtenerUsuarioPorId_CuandoNoExiste_RetornaNull() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.obtenerUsuarioPorId("1");

        assertNull(resultado);
    }

    @Test
    void crearUsuario_DelegaSave() {
        Usuario u = new Usuario();
        when(usuarioRepository.save(u)).thenReturn(u);

        Usuario resultado = usuarioService.crearUsuario(u);

        assertNotNull(resultado);
        verify(usuarioRepository).save(u);
    }

    @Test
    void eliminarUsuario_LlamaDelete() {
        usuarioService.eliminarUsuario("1");
        verify(usuarioRepository).deleteById("1");
    }
}
