package com.imagicode.agorasoft.controladores;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Registro público de usuarios (plazas).
 * Reenvía la solicitud a AgoraSoftAdmin para aprobación manual.
 */
@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class RegistroPublicoController {

    private final RestTemplate rest = new RestTemplate();

    @Value("${admin.intake.url:}")
    private String adminIntakeUrl;

    @Value("${admin.intake.token:}")
    private String adminIntakeToken;

    /**
     * Body esperado:
     * {
     * "correo": "string",
     * "nombrePlaza": "string",
     * "representante": "string?",
     * "rolSolicitado":
     * "Proveedor|OperadorComercial|AdministradorPlaza|DistribuidorLogistico|TrabajadorAuxiliar|Parqueadero|Cliente",
     * "observaciones": "string?"
     * }
     */
    @PostMapping("/registro-usuario")
    public ResponseEntity<?> registrar(@RequestBody Map<String, Object> payload) {
        if (!StringUtils.hasText(adminIntakeUrl) || !StringUtils.hasText(adminIntakeToken)) {
            return ResponseEntity.status(503).body(Map.of("error", "Servicio de intake no configurado"));
        }

        // Validación mínima en origen
        String correo = (String) payload.get("correo");
        String nombrePlaza = (String) payload.get("nombrePlaza");
        String rolSolicitado = (String) payload.get("rolSolicitado");
        if (!StringUtils.hasText(correo) || !StringUtils.hasText(nombrePlaza) || !StringUtils.hasText(rolSolicitado)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "correo, nombrePlaza y rolSolicitado son obligatorios"));
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-INTAKE-TOKEN", adminIntakeToken);

            HttpEntity<Map<String, Object>> req = new HttpEntity<>(payload, headers);
            ResponseEntity<String> resp = rest.postForEntity(adminIntakeUrl, req, String.class);

            return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(502)
                    .body(Map.of("error", "Error reenviando al administrador", "detalle", e.getMessage()));
        }
    }
}