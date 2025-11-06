package com.imagicode.agorasoft.servicios;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 * Mantiene en memoria las plazas revocadas temporalmente.
 * Implementación simple: map plazaId -> revocationTime.
 * Puede extenderse para persistir.
 */
@Service
public class RevokedPlazaService {

    private final Map<Long, Instant> revoked = new ConcurrentHashMap<>();

    public void revokePlaza(Long plazaId) {
        revoked.put(plazaId, Instant.now());
    }

    public void clearRevocation(Long plazaId) {
        revoked.remove(plazaId);
    }

    public boolean isPlazaRevoked(Long plazaId) {
        return revoked.containsKey(plazaId);
    }

    public Instant getRevocationTime(Long plazaId) {
        return revoked.get(plazaId);
    }
}