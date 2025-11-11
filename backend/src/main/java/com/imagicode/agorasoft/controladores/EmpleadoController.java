package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.Empleado;
import com.imagicode.agorasoft.servicios.EmpleadoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<Empleado> listarEmpleados() {
        return empleadoService.obtenerEmpleados();
    }

    @GetMapping("/{id}")
    public Empleado obtenerEmpleadoPorId(@PathVariable String id) {
        return empleadoService.obtenerEmpleadoPorId(id);
    }

    @PostMapping
    public Empleado crearEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.crearEmpleado(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable String id, @RequestBody Empleado empleado) {
        return empleadoService.actualizarEmpleado(id, empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable String id) {
        empleadoService.eliminarEmpleado(id);
    }
}
