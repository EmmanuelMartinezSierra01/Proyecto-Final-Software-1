package com.soft1.sistemaciudadano;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pqrs")
public class CiudadanoPQRSController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarPQRS(@RequestBody Map<String, Object> datos) {
        String camundaUrl = "http://localhost:8080/engine-rest/process-definition/key/AlcaldiaPQRS/start";
        Map<String, Object> variables = Map.of("variables", datosToCamundaVariables(datos));
        restTemplate.postForEntity(camundaUrl, variables, String.class);
        return ResponseEntity.ok(Map.of("mensaje", "Solicitud registrada correctamente"));
    }

    @GetMapping("/consultar")
    public ResponseEntity<?> consultarPorCedula(@RequestParam String cedula) {
        // Llama al backend principal (Alcaldía Corocora)
        String backendUrl = "http://localhost:8080/api/pqrs/consultar?cedula=" + cedula;
        ResponseEntity<String> response = restTemplate.getForEntity(backendUrl, String.class);
        // Devuelve directamente la respuesta, o puedes procesarla si necesitas
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    private Map<String, Object> datosToCamundaVariables(Map<String, Object> datos) {
        Map<String, Object> result = new HashMap<>();
        datos.forEach((k, v) -> result.put(k, Map.of("value", v)));
        return result;
    }

}

