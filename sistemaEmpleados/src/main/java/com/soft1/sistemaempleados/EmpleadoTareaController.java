package com.soft1.sistemaempleados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoTareaController {

    @Autowired
    private RestTemplate restTemplate;

    // Listar todas las tareas activas
    @GetMapping("/tareas-pendientes")
    public ResponseEntity<?> tareasPendientes() {
        String camundaUrl = "http://localhost:8080/engine-rest/task";
        ResponseEntity<String> response = restTemplate.getForEntity(camundaUrl, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    // Consultar datos de una tarea específica (opcional)
    @GetMapping("/tarea/{taskId}")
    public ResponseEntity<?> detalleTarea(@PathVariable String taskId) {
        String camundaUrl = "http://localhost:8080/engine-rest/task/" + taskId + "/variables";
        ResponseEntity<String> response = restTemplate.getForEntity(camundaUrl, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    // Completar una tarea con variables (resuelve la tarea)
    @PostMapping("/tarea/{taskId}/completar")
    public ResponseEntity<?> completarTarea(@PathVariable String taskId, @RequestBody Map<String, Object> variables) {
        String camundaUrl = "http://localhost:8080/engine-rest/task/" + taskId + "/complete";
        // Formato de variables que espera Camunda
        Map<String, Object> body = Map.of("variables", variables);
        ResponseEntity<String> response = restTemplate.postForEntity(camundaUrl, body, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
