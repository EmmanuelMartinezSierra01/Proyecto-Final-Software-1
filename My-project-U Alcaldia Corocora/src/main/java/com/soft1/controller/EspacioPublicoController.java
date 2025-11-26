package com.soft1.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/espacio-publico")
@CrossOrigin(origins = "*")
public class EspacioPublicoController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @PostMapping("/iniciar")
    public ResponseEntity<Map<String, Object>> iniciar(@RequestBody Map<String, Object> payload) {
        try {
            Map<String, Object> vars = new HashMap<>();
            vars.put("Solicitud", payload.getOrDefault("Solicitud", true));
            vars.put("Documentos", payload.getOrDefault("Documentos", true));
            vars.put("EspacioLibre", payload.getOrDefault("EspacioLibre", true));
            vars.put("UsoCorrecto", payload.getOrDefault("UsoCorrecto", true));

            vars.put("Nombre", payload.get("Nombre"));
            vars.put("Direccion", payload.get("Direccion"));
            vars.put("Tipo_Uso", payload.get("Tipo_Uso"));
            if (payload.containsKey("CobroExtra")) {
                vars.put("CobroExtra", payload.get("CobroExtra"));
            }

            var pi = runtimeService.startProcessInstanceByKey("AlcaldiaSolicitudEspacio", vars);

            return ResponseEntity.ok(Map.of(
                    "mensaje", "Proceso de Espacio Público iniciado",
                    "processInstanceId", pi.getProcessInstanceId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("estado", "error", "mensaje", e.getMessage()));
        }
    }

    @PostMapping("/revision-documentos/completar")
    public ResponseEntity<Map<String, Object>> completarRevisionDocumentos(
            @RequestParam("processInstanceId") String processInstanceId,
            @RequestBody Map<String, Object> payload) {
        try {
            Task task = taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .taskDefinitionKey("Activity_1mspgog")
                    .singleResult();

            if (task == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("estado", "error", "mensaje", "Tarea de revisión no encontrada"));
            }

            Map<String, Object> vars = new HashMap<>();
            vars.put("Solicitud", payload.get("Solicitud"));
            vars.put("Documentos", payload.get("Documentos"));

            taskService.complete(task.getId(), vars);
            return ResponseEntity.ok(Map.of("estado", "completado", "taskId", task.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("estado", "error", "mensaje", e.getMessage()));
        }
    }

    @PostMapping("/evaluacion/completar")
    public ResponseEntity<Map<String, Object>> completarEvaluacion(
            @RequestParam("processInstanceId") String processInstanceId,
            @RequestBody Map<String, Object> payload) {
        try {
            Task task = taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .taskDefinitionKey("Activity_1ihplv9")
                    .singleResult();

            if (task == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("estado", "error", "mensaje", "Tarea de evaluación no encontrada"));
            }

            Map<String, Object> vars = new HashMap<>();
            Object checklist = payload.get("RevEsp");
            boolean usoCorrecto = false;
            boolean espacioLibre = false;
            if (checklist instanceof Iterable<?>) {
                for (Object v : (Iterable<?>) checklist) {
                    if ("UsoCorrecto".equals(v)) usoCorrecto = true;
                    if ("EspacioLibre".equals(v)) espacioLibre = true;
                }
            }
            if (payload.containsKey("UsoCorrecto")) {
                Object uc = payload.get("UsoCorrecto");
                if (uc instanceof Boolean) usoCorrecto = (Boolean) uc;
            }
            if (payload.containsKey("EspacioLibre")) {
                Object el = payload.get("EspacioLibre");
                if (el instanceof Boolean) espacioLibre = (Boolean) el;
            }

            vars.put("UsoCorrecto", usoCorrecto);
            vars.put("EspacioLibre", espacioLibre);

            if (payload.containsKey("Tipo_Uso")) {
                vars.put("Tipo_Uso", payload.get("Tipo_Uso"));
            }
            if (payload.containsKey("CobroExtra")) {
                vars.put("CobroExtra", payload.get("CobroExtra"));
            }

            taskService.complete(task.getId(), vars);
            return ResponseEntity.ok(Map.of("estado", "completado", "taskId", task.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("estado", "error", "mensaje", e.getMessage()));
        }
    }

    @PostMapping("/pago/completar")
    public ResponseEntity<Map<String, Object>> completarPago(
            @RequestParam("processInstanceId") String processInstanceId,
            @RequestBody Map<String, Object> payload) {
        try {
            Task task = taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .taskDefinitionKey("Activity_19i35t2")
                    .singleResult();

            if (task == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("estado", "error", "mensaje", "Tarea de pago no encontrada"));
            }

            Map<String, Object> vars = new HashMap<>();
            Object pago = payload.get("RecibirPago");
            if (pago == null) pago = payload.get("RecibirPAgo");
            vars.put("RecibirPago", pago);

            taskService.complete(task.getId(), vars);
            return ResponseEntity.ok(Map.of("estado", "completado", "taskId", task.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("estado", "error", "mensaje", e.getMessage()));
        }
    }

    @PostMapping("/mensaje/confirmacion-pago")
    public ResponseEntity<Map<String, Object>> correlacionarConfirmacionPago(
            @RequestParam("processInstanceId") String processInstanceId) {
        try {
            runtimeService.createMessageCorrelation("MSG_PAGO_CONF")
                    .processInstanceId(processInstanceId)
                    .correlate();
            return ResponseEntity.ok(Map.of("estado", "correlado"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("estado", "error", "mensaje", e.getMessage()));
        }
    }
}
