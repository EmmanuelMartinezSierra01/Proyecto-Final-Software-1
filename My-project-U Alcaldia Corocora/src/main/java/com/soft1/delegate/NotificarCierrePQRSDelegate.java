package com.soft1.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component("notificarCierrePQRSDelegate")
public class NotificarCierrePQRSDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        // Obtener información del proceso
        String processInstanceId = execution.getProcessInstanceId();
        String nombreSolicitante = (String) execution.getVariable("nombreSolicitante");
        String correoElectronico = (String) execution.getVariable("correoElectronico");
        String tipoSolicitud = (String) execution.getVariable("tipoSolicitud");
        String detalleSolicitud = (String) execution.getVariable("detalleSolicitud");

        // Obtener datos adicionales si existen
        String motivoCierre = (String) execution.getVariable("motivoCierre");
        String respuestaFinal = (String) execution.getVariable("respuestaFinal");

        // Si no hay motivo específico, usar genérico
        if (motivoCierre == null || motivoCierre.isEmpty()) {
            motivoCierre = "Proceso completado satisfactoriamente";
        }

        // Fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaCierre = ahora.format(formatter);

        // Simular envío de correo (consola)
        System.out.println("\n");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("📧 NOTIFICACIÓN DE CIERRE - PQRS");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("Para: " + correoElectronico);
        System.out.println("Asunto: Su " + formatearTipo(tipoSolicitud) + " ha sido cerrada");
        System.out.println("───────────────────────────────────────────────────────────────");
        System.out.println();
        System.out.println("Estimado(a) " + nombreSolicitante + ",");
        System.out.println();
        System.out.println("Le informamos que su " + formatearTipo(tipoSolicitud) + " ha sido cerrada.");
        System.out.println();
        System.out.println("DETALLES DE LA SOLICITUD:");
        System.out.println("  • Número de radicado: " + processInstanceId);
        System.out.println("  • Tipo: " + formatearTipo(tipoSolicitud));
        System.out.println("  • Fecha de cierre: " + fechaCierre);
        System.out.println("  • Motivo del cierre: " + motivoCierre);
        System.out.println();

        if (respuestaFinal != null && !respuestaFinal.isEmpty()) {
            System.out.println("RESPUESTA FINAL:");
            System.out.println("  " + respuestaFinal);
            System.out.println();
        }

        System.out.println("DETALLE DE SU SOLICITUD:");
        System.out.println("  " + detalleSolicitud);
        System.out.println();
        System.out.println("Gracias por utilizar nuestros servicios.");
        System.out.println();
        System.out.println("Atentamente,");
        System.out.println("Alcaldía de Corocora");
        System.out.println("───────────────────────────────────────────────────────────────");
        System.out.println("Este es un mensaje automático, por favor no responder.");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("\n");

        // Guardar fecha de cierre en las variables del proceso
        execution.setVariable("fechaCierre", fechaCierre);
        execution.setVariable("estadoPQRS", "CERRADA");

        System.out.println("✅ Notificación de cierre enviada exitosamente a: " + correoElectronico);
    }

    /**
     * Formatea el tipo de solicitud para que se vea mejor en el correo
     */
    private String formatearTipo(String tipo) {
        if (tipo == null) return "solicitud";

        switch (tipo.toLowerCase()) {
            case "peticion":
                return "petición";
            case "queja":
                return "queja";
            case "reclamo":
                return "reclamo";
            default:
                return tipo;
        }
    }
}