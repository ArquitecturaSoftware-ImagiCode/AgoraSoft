package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.entidades.Proveedor;
import com.imagicode.agorasoft.servicios.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> listarTodos() {
        return proveedorService.listarTodos();
    }

    @PostMapping
    public Proveedor crear(@RequestBody Proveedor proveedor) {
        return proveedorService.guardar(proveedor);
    }
}
