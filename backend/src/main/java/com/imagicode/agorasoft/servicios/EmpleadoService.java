package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.Empleado;
import com.imagicode.agorasoft.repositorios.EmpleadoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> obtenerEmpleados() {
        return empleadoRepository.findAll();
    }

    public Empleado obtenerEmpleadoPorId(String id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        return empleado.orElse(null);
    }

    public Empleado crearEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizarEmpleado(String id, Empleado empleadoActualizado) {
        return empleadoRepository.findById(id).map(empleado -> {
            empleado.setNombre(empleadoActualizado.getNombre());
            empleado.setApellido(empleadoActualizado.getApellido());
            empleado.setCorreo(empleadoActualizado.getCorreo());
            empleado.setRol(empleadoActualizado.getRol());
            empleado.setDepartamento(empleadoActualizado.getDepartamento());
            empleado.setTelefono(empleadoActualizado.getTelefono());
            empleado.setActivo(empleadoActualizado.getActivo());
            return empleadoRepository.save(empleado);
        }).orElse(null);
    }

    public void eliminarEmpleado(String id) {
        empleadoRepository.deleteById(id);
    }
}
