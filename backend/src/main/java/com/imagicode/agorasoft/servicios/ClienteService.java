package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.repositorios.ClienteRepository;
import com.imagicode.agorasoft.entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository; 

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Registrar nuevo cliente
    public Cliente registrar(Cliente cliente){
        if(clienteRepository.findByEmail(cliente.getEmail()).isPresent()){
            throw new RuntimeException("El correo ya esta registrado");
        }
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        return clienteRepository.save(cliente);
    }

    //Login cliente
    public Optional<Cliente> login(String email, String password){
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(email);
        if(clienteOpt.isPresent() && passwordEncoder.matches(password, clienteOpt.get().getPassword())){
            return clienteOpt;
        }
        return Optional.empty();
    }

    //Obtener todos los clientes
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    //Obtener clientes por id
    public Optional<Cliente> obtenerPorId(Long id){
        return clienteRepository.findById(id);
    }

    //Desactivar cliente
    public void desactivar(Long id){
        clienteRepository.findById(id).ifPresent(cliente ->{
            cliente.setActivo(false);
            clienteRepository.save(cliente);
        });
    }
}
