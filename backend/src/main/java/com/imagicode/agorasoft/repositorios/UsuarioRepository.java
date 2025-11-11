    package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByRolIgnoreCase(String rol);

}
