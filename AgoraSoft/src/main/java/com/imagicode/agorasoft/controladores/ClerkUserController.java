package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.Usuario;
import com.imagicode.agorasoft.servicios.UsuarioService;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ClerkUserController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario")
    public Object getUsuario(HttpServletRequest request) {
        Long userId = (long) request.getAttribute("clerkUserId");
        if (userId == null) {
            return Map.of("error", "No Clerk userId en el request");
        }
        Usuario usuario = usuarioService.findById(userId).orElse(null);
        if (usuario != null) {
            return usuario;
        } else {
            return Map.of("error", "Usuario no encontrado en la base de datos");
        }
    }
}
