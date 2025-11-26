package com.soft1.sistemaciudadano;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/licencia-construccion")
@CrossOrigin(origins = "*")
public class CiudadanoLicenciaController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarLicencia(@RequestBody Map<String, Object> datos) {
        // Cambia el key por el ID EXACTO de tu proceso en Camunda Modeler/Engine
        String camundaUrl = "http://localhost:8080/engine-rest/process-definition/key/Solicitud-de-Licencia-de-Construccion/start";

        // Envía las variables en el formato que requiere Camunda REST
        Map<String, Object> variables = new HashMap<>();
        variables.put("variables", datosToCamundaVariables(datos));

        restTemplate.postForEntity(camundaUrl, variables, String.class);

        return ResponseEntity.ok(Map.of("mensaje", "Solicitud de licencia registrada correctamente"));
    }

    private Map<String, Object> datosToCamundaVariables(Map<String, Object> datos) {
        Map<String, Object> result = new HashMap<>();
        datos.forEach((k, v) -> result.put(k, Map.of("value", v)));
        return result;
    }
}
