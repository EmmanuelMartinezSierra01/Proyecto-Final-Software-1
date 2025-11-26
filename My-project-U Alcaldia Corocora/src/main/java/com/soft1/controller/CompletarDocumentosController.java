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
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*")
public class CompletarDocumentosController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping("/completar")
    public ResponseEntity<Map<String, Object>> completarDocumentos(
            @RequestParam("processInstanceId") String processInstanceId,  // ✅ Nombre explícito
            @RequestParam("token") String token,                          // ✅ Nombre explícito
            @RequestBody Map<String, Object> documentos) {

        try {
            // 1. Validar token de seguridad
            String tokenGuardado = (String) runtimeService.getVariable(
                    processInstanceId,
                    "tokenFormulario"
            );

            if (tokenGuardado == null || !tokenGuardado.equals(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(
                                "mensaje", "Token inválido o expirado",
                                "estado", "error"
                        ));
            }

            // 2. Buscar la tarea "Recibir documentos rellenados" pendiente
            Task task = taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .taskDefinitionKey("recibirDocumentosRellenados")
                    .singleResult();

            if (task == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "mensaje", "No se encontró la tarea pendiente o ya fue completada",
                                "estado", "error"
                        ));
            }

            // 3. Preparar las variables con los datos recibidos
            Map<String, Object> variables = new HashMap<>();
            variables.put("nombreSolicitante", documentos.get("nombreSolicitante"));
            variables.put("cedula", documentos.get("cedula"));
            variables.put("numeroCelular", documentos.get("numeroCelular"));
            variables.put("correoElectronico", documentos.get("correoElectronico"));
            variables.put("direccion", documentos.get("direccion"));
            variables.put("tipoSolicitud", documentos.get("tipoSolicitud"));
            variables.put("detalleSolicitud", documentos.get("detalleSolicitud"));

            // 4. Completar la tarea con las variables
            taskService.complete(task.getId(), variables);

            // Log para debugging
            System.out.println("✅ Tarea 'Recibir documentos rellenados' completada");
            System.out.println("   Process ID: " + processInstanceId);
            System.out.println("   Task ID: " + task.getId());
            System.out.println("   Solicitante: " + documentos.get("nombreSolicitante"));
            System.out.println("   Cédula: " + documentos.get("cedula"));

            // 5. Respuesta exitosa
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Documentos recibidos correctamente. Su solicitud continuará en proceso.",
                    "processInstanceId", processInstanceId,
                    "taskId", task.getId(),
                    "estado", "completado"
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "mensaje", "Error al procesar los documentos: " + e.getMessage(),
                            "estado", "error"
                    ));
        }
    }
}
