package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    Optional<Cliente> findByEmail(String email);
    
}
