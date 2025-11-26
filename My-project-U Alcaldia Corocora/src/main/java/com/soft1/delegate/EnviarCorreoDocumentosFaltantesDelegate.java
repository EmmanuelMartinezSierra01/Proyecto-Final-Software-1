package com.soft1.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.UUID;

public class EnviarCorreoDocumentosFaltantesDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Obtener variables del proceso
        String nombreSolicitante = (String) execution.getVariable("nombreSolicitante");
        String correoElectronico = (String) execution.getVariable("correoElectronico");
        String processInstanceId = execution.getProcessInstanceId();

        // Generar token único para seguridad
        String token = UUID.randomUUID().toString();
        execution.setVariable("tokenFormulario", token);

        // URL del formulario externo
        String urlFormulario = "http://localhost:8080/completar-documentos.html?token=" + token
                + "&processId=" + processInstanceId;

        // Imprimir el "correo" en consola (por ahora)
        System.out.println("═══════════════════════════════════════");
        System.out.println("✉️  CORREO A ENVIAR");
        System.out.println("═══════════════════════════════════════");
        System.out.println("Para: " + correoElectronico);
        System.out.println("Asunto: Documentos faltantes - PQRS");
        System.out.println("Mensaje:");
        System.out.println("  Estimado/a " + nombreSolicitante + ",");
        System.out.println("  Por favor complete los documentos faltantes en:");
        System.out.println("  " + urlFormulario);
        System.out.println("═══════════════════════════════════════");
    }
}
