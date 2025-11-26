package com.alcaldia.licencias.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RegistrarSolicitudDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Recuperar variables del formulario de recepción
        String nombreSolicitante = (String) execution.getVariable("nombreSolicitante");
        String documentoIdentidad = (String) execution.getVariable("documentoIdentidad");
        String direccionObra = (String) execution.getVariable("direccionObra");
        String tipoTramite = (String) execution.getVariable("tipoTramite");

        // Generar ID único de solicitud
        LocalDateTime ahora = LocalDateTime.now();
        String solicitudId = String.format("SOL-%s-%06d",
                ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                System.currentTimeMillis() % 1000000);

        // Generar fecha de registro
        String fechaRegistro = ahora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Guardar variables en el proceso
        execution.setVariable("solicitudId", solicitudId);
        execution.setVariable("fechaRegistro", fechaRegistro);
        execution.setVariable("estadoSolicitud", "REGISTRADA");

        // Log para seguimiento
        System.out.println("========================================");
        System.out.println("    SOLICITUD REGISTRADA EN EL SISTEMA");
        System.out.println("========================================");
        System.out.println("ID Solicitud: " + solicitudId);
        System.out.println("Fecha: " + fechaRegistro);
        System.out.println("Solicitante: " + nombreSolicitante);
        System.out.println("Documento: " + documentoIdentidad);
        System.out.println("Dirección: " + direccionObra);
        System.out.println("Tipo Trámite: " + tipoTramite);
        System.out.println("Estado: REGISTRADA");
        System.out.println("========================================");
    }
}
