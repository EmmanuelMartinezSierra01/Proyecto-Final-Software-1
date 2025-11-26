package com.soft1.sistemaciudadano;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
    @RequestMapping("/api/espacio-publico")
@CrossOrigin(origins = "*")
public class CiudadanoSolicitudEspacioController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarEspacioPublico(@RequestBody Map<String, Object> datos) {
        String camundaUrl = "http://localhost:8080/engine-rest/process-definition/key/AlcaldiaSolicitudEspacio/start";

        Map<String, Object> variables = Map.of("variables", datosToCamundaVariables(datos));

        restTemplate.postForEntity(camundaUrl, variables, String.class);

        return ResponseEntity.ok(Map.of("mensaje", "Solicitud de espacio público registrada correctamente"));
    }

    private Map<String, Object> datosToCamundaVariables(Map<String, Object> datos) {
        Map<String, Object> result = new HashMap<>();
        datos.forEach((k, v) -> result.put(k, Map.of("value", v)));
        return result;
    }
}
