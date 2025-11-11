package com.imagicode.agorasoft.repositorios;

import com.imagicode.agorasoft.entidades.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
}
