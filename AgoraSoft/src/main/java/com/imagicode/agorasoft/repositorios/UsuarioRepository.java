package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
