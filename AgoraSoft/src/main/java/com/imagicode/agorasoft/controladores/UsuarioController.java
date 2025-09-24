package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.Usuario;
import com.imagicode.agorasoft.servicios.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerUsuarios();
    }
    @GetMapping("/")
    public Usuario obtenerUsuarioPorId() {
        return new Usuario( 1L, "Juan Perez","" );
    }
}
