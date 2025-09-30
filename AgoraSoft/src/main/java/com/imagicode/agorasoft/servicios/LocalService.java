package com.imagicode.agorasoft.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagicode.agorasoft.entidades.Local;
import com.imagicode.agorasoft.repositorios.LocalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;

    public List<Local> findAll() {
        return localRepository.findAll();
    }

    public Optional<Local> findById(Long id) {
        return localRepository.findById(id);
    }

    public Local save(Local local) {
        return localRepository.save(local);
    }

    public void delete(Long id) {
        localRepository.deleteById(id);
    }
}
