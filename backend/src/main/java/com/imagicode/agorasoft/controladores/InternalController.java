package com.imagicode.agorasoft.controladores;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imagicode.agorasoft.servicios.RevokedPlazaService;

/**
 * Endpoints internos para administración por Arquitectura (solo llamadas
 * servidor a servidor).
 */
@RestController
@RequestMapping("/internal")
public class InternalController {

    private final RevokedPlazaService revokedService;

    public InternalController(RevokedPlazaService revokedService) {
        this.revokedService = revokedService;
    }

    @PostMapping("/invalidate-sessions")
    public ResponseEntity<?> invalidateSessions(@RequestBody Map<String, Object> payload) {
        Object idObj = payload.get("plazaId");
        if (idObj == null)
            return ResponseEntity.badRequest().body(Map.of("error", "plazaId required"));
        Long plazaId = Long.valueOf(String.valueOf(idObj));
        revokedService.revokePlaza(plazaId);
        return ResponseEntity.ok(Map.of("plazaId", plazaId, "revoked", true));
    }

    @PostMapping("/clear-invalidation")
    public ResponseEntity<?> clearInvalidation(@RequestBody Map<String, Object> payload) {
        Object idObj = payload.get("plazaId");
        if (idObj == null)
            return ResponseEntity.badRequest().body(Map.of("error", "plazaId required"));
        Long plazaId = Long.valueOf(String.valueOf(idObj));
        revokedService.clearRevocation(plazaId);
        return ResponseEntity.ok(Map.of("plazaId", plazaId, "cleared", true));
    }
}