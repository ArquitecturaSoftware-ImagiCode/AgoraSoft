package com.imagicode.agorasoft.controladores;

import com.imagicode.agorasoft.servicios.ClienteService;
import com.imagicode.agorasoft.entidades.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins="*")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    //Registro
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Cliente cliente){
        try{
            Cliente nuevo = clienteService.registrar(cliente);
            return ResponseEntity.ok(Map.of(
                "Mensaje","Registro Exitoso",
                "Cliente", nuevo
            ));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> datos){
        String email = datos.get("email");
        String password = datos.get("password");
        return clienteService.login(email, password)
        .map(cliente -> ResponseEntity.ok(Map.of(
            "mensaje", "Login Exitoso",
            "cliente", cliente
        )))
        .orElse(ResponseEntity.status(401).body(Map.of("error","Credenciales Invalidas")));
    }

    //Listar todos los cliente
    @GetMapping
    public ResponseEntity<List<Cliente>> listar(){
        return ResponseEntity.ok(clienteService.listar());
    }

    //Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id){
        return clienteService.obtenerPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    //Desactivar cliente
    @PutMapping("/{id}")
    public ResponseEntity<?> desactivar(@PathVariable Long id){
        clienteService.desactivar(id);
        return ResponseEntity.ok(Map.of("mensaje","Cliente desactivado"));
    }
    
}
