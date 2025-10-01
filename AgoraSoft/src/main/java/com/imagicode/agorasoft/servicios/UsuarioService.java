package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.Usuario;
import com.imagicode.agorasoft.repositorios.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(String id) {
        usuarioRepository.deleteById(id);
    }
        public Usuario actualizarUsuario(String id, Usuario usuario) {
        return usuarioRepository.findById(id).map(u -> {
            u.setNombre(usuario.getNombre());
            u.setApellido(usuario.getApellido());
            u.setCorreo(usuario.getCorreo());
            u.setOrganizacion(usuario.getOrganizacion());
            u.setRol(usuario.getRol());
            return usuarioRepository.save(u);
        }).orElse(null);
    }
}
