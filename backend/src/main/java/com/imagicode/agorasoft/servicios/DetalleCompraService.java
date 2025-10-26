package com.imagicode.agorasoft.servicios;

import com.imagicode.agorasoft.entidades.DetalleCompra;
import com.imagicode.agorasoft.repositorios.DetalleCompraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleCompraService {

    private final DetalleCompraRepository detalleCompraRepository;

    public DetalleCompraService(DetalleCompraRepository detalleCompraRepository) {
        this.detalleCompraRepository = detalleCompraRepository;
    }

    public List<DetalleCompra> listarDetalles() {
        return detalleCompraRepository.findAll();
    }

    public DetalleCompra obtenerPorId(Long id) {
        return detalleCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de compra no encontrado"));
    }

    public void eliminar(Long id) {
        detalleCompraRepository.deleteById(id);
    }
}
