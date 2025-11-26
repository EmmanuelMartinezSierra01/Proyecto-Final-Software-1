package com.alcaldia.licencias.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RecibirDocumentacionDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Recuperar información de la solicitud
        String solicitudId = (String) execution.getVariable("solicitudId");
        String nombreSolicitante = (String) execution.getVariable("nombreSolicitante");
        String informacionSolicitada = (String) execution.getVariable("informacionSolicitada");
        String fechaLimiteDocumentos = (String) execution.getVariable("fechaLimiteDocumentos");

        // Verificar qué documentos fueron recibidos
        Boolean escrituraRecibida = (Boolean) execution.getVariable("escrituraRecibida");
        Boolean planosRecibidos = (Boolean) execution.getVariable("planosRecibidos");
        Boolean estudiosRecibidos = (Boolean) execution.getVariable("estudiosRecibidos");
        Boolean certificadosRecibidos = (Boolean) execution.getVariable("certificadosRecibidos");

        // Recuperar observaciones y decisión
        String observacionesDocumentacion = (String) execution.getVariable("observacionesDocumentacion");
        Boolean documentacionRecibida = (Boolean) execution.getVariable("documentacionRecibida");
        Boolean documentacionCompleta = (Boolean) execution.getVariable("documentacionCompleta");

        // Registrar fecha de recepción
        String fechaRecepcion = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Construir resumen de documentos recibidos
        StringBuilder resumenDocumentos = new StringBuilder();
        resumenDocumentos.append("DOCUMENTOS RECIBIDOS:\n");
        resumenDocumentos.append("- Escritura: ")
                .append(Boolean.TRUE.equals(escrituraRecibida) ? "SÍ" : "NO").append("\n");
        resumenDocumentos.append("- Planos: ")
                .append(Boolean.TRUE.equals(planosRecibidos) ? "SÍ" : "NO").append("\n");
        resumenDocumentos.append("- Estudios: ")
                .append(Boolean.TRUE.equals(estudiosRecibidos) ? "SÍ" : "NO").append("\n");
        resumenDocumentos.append("- Certificados: ")
                .append(Boolean.TRUE.equals(certificadosRecibidos) ? "SÍ" : "NO").append("\n");

        if (observacionesDocumentacion != null && !observacionesDocumentacion.isEmpty()) {
            resumenDocumentos.append("\nOBSERVACIONES:\n").append(observacionesDocumentacion);
        }

        // Guardar variables
        execution.setVariable("fechaRecepcionDocumentos", fechaRecepcion);
        execution.setVariable("resumenDocumentosRecibidos", resumenDocumentos.toString());

        // Determinar estado
        String estadoDocumentacion;
        if (Boolean.TRUE.equals(documentacionRecibida) && Boolean.TRUE.equals(documentacionCompleta)) {
            estadoDocumentacion = "DOCUMENTACION_COMPLETA";
        } else if (Boolean.TRUE.equals(documentacionRecibida) && Boolean.FALSE.equals(documentacionCompleta)) {
            estadoDocumentacion = "DOCUMENTACION_INCOMPLETA";
        } else {
            estadoDocumentacion = "NO_PRESENTADA";
        }

        execution.setVariable("estadoDocumentacion", estadoDocumentacion);

        // Log detallado
        System.out.println("========================================");
        System.out.println("  RECEPCIÓN DE DOCUMENTACIÓN COMPLEMENTARIA");
        System.out.println("========================================");
        System.out.println("Solicitud ID: " + solicitudId);
        System.out.println("Solicitante: " + nombreSolicitante);
        System.out.println("Fecha recepción: " + fechaRecepcion);
        System.out.println("Fecha límite: " + fechaLimiteDocumentos);
        System.out.println("----------------------------------------");
        System.out.println(resumenDocumentos.toString());
        System.out.println("----------------------------------------");
        System.out.println("Documentación recibida: " +
                (Boolean.TRUE.equals(documentacionRecibida) ? "SÍ" : "NO"));
        System.out.println("Documentación completa: " +
                (Boolean.TRUE.equals(documentacionCompleta) ? "SÍ" : "NO"));
        System.out.println("Estado: " + estadoDocumentacion);
        System.out.println("========================================");
    }
}
