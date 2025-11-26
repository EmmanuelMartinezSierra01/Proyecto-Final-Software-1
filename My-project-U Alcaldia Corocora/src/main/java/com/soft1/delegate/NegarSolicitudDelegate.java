package com.alcaldia.licencias.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class NegarSolicitudDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Recuperar información de la solicitud
        String solicitudId = (String) execution.getVariable("solicitudId");
        String nombreSolicitante = (String) execution.getVariable("nombreSolicitante");

        // Recuperar observaciones de las evaluaciones
        String observacionesTecnicas = (String) execution.getVariable("observacionesTecnicas");
        String observacionesJuridicas = (String) execution.getVariable("observacionesJuridicas");
        String conceptoFinal = (String) execution.getVariable("conceptoFinal");

        // Construir motivo de rechazo detallado
        StringBuilder motivoRechazo = new StringBuilder();
        motivoRechazo.append("La solicitud ha sido RECHAZADA por las siguientes razones:\n\n");

        if (observacionesTecnicas != null && !observacionesTecnicas.isEmpty()) {
            motivoRechazo.append("OBSERVACIONES TÉCNICAS:\n");
            motivoRechazo.append(observacionesTecnicas).append("\n\n");
        }

        if (observacionesJuridicas != null && !observacionesJuridicas.isEmpty()) {
            motivoRechazo.append("OBSERVACIONES JURÍDICAS:\n");
            motivoRechazo.append(observacionesJuridicas).append("\n\n");
        }

        if (conceptoFinal != null && !conceptoFinal.isEmpty()) {
            motivoRechazo.append("CONCEPTO FINAL:\n");
            motivoRechazo.append(conceptoFinal);
        }

        // Generar fecha de rechazo
        String fechaRechazo = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Guardar variables en el proceso
        execution.setVariable("motivoRechazo", motivoRechazo.toString());
        execution.setVariable("fechaRechazo", fechaRechazo);
        execution.setVariable("solicitudNegada", true);
        execution.setVariable("estadoSolicitud", "RECHAZADA");

        // Log detallado
        System.out.println("========================================");
        System.out.println("       SOLICITUD RECHAZADA");
        System.out.println("========================================");
        System.out.println("Solicitud ID: " + solicitudId);
        System.out.println("Solicitante: " + nombreSolicitante);
        System.out.println("Fecha Rechazo: " + fechaRechazo);
        System.out.println("----------------------------------------");
        System.out.println("MOTIVO:");
        System.out.println(motivoRechazo.toString());
        System.out.println("========================================");
    }
}
