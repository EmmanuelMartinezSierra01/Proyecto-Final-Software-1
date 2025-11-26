package com.alcaldia.licencias.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class NotificarDecisionDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Recuperar información básica
        String solicitudId = (String) execution.getVariable("solicitudId");
        String nombreSolicitante = (String) execution.getVariable("nombreSolicitante");
        String documentoIdentidad = (String) execution.getVariable("documentoIdentidad");
        Boolean conceptoFavorable = (Boolean) execution.getVariable("conceptoFavorable");

        String asunto;
        String mensaje;

        // Construir mensaje según la decisión
        if (Boolean.TRUE.equals(conceptoFavorable)) {
            // Licencia aprobada
            String numeroLicencia = (String) execution.getVariable("numeroLicencia");
            String fechaExpedicion = (String) execution.getVariable("fechaExpedicion");
            String fechaVencimiento = (String) execution.getVariable("fechaVencimiento");
            String direccionObra = (String) execution.getVariable("direccionObra");

            asunto = "LICENCIA DE CONSTRUCCIÓN APROBADA";
            mensaje = String.format(
                    "Estimado/a %s,\n\n" +
                            "Nos complace informarle que su solicitud de licencia de construcción " +
                            "ha sido APROBADA.\n\n" +
                            "DATOS DE LA LICENCIA:\n" +
                            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                            "Número de Licencia: %s\n" +
                            "Solicitud: %s\n" +
                            "Documento: %s\n" +
                            "Dirección de la obra: %s\n" +
                            "Fecha de expedición: %s\n" +
                            "Fecha de vencimiento: %s\n" +
                            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                            "Por favor, acérquese a nuestras oficinas en un plazo de 10 días hábiles " +
                            "para retirar el documento físico de la licencia.\n\n" +
                            "Cordialmente,\n" +
                            "Secretaría de Planeación",
                    nombreSolicitante,
                    numeroLicencia,
                    solicitudId,
                    documentoIdentidad,
                    direccionObra,
                    fechaExpedicion,
                    fechaVencimiento
            );
        } else {
            // Solicitud rechazada
            String motivoRechazo = (String) execution.getVariable("motivoRechazo");
            String fechaRechazo = (String) execution.getVariable("fechaRechazo");

            asunto = "SOLICITUD DE LICENCIA RECHAZADA";
            mensaje = String.format(
                    "Estimado/a %s,\n\n" +
                            "Lamentamos informarle que su solicitud de licencia de construcción " +
                            "ha sido RECHAZADA.\n\n" +
                            "DATOS DE LA SOLICITUD:\n" +
                            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                            "Solicitud: %s\n" +
                            "Documento: %s\n" +
                            "Fecha de rechazo: %s\n" +
                            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                            "%s\n\n" +
                            "Puede presentar una nueva solicitud subsanando las observaciones indicadas.\n\n" +
                            "Para mayor información, puede comunicarse con nuestra oficina.\n\n" +
                            "Cordialmente,\n" +
                            "Secretaría de Planeación",
                    nombreSolicitante,
                    solicitudId,
                    documentoIdentidad,
                    fechaRechazo,
                    motivoRechazo
            );
        }

        // Guardar variables de notificación
        execution.setVariable("notificacionEnviada", true);
        execution.setVariable("asuntoNotificacion", asunto);
        execution.setVariable("mensajeNotificacion", mensaje);

        // Simular envío de notificación (aquí podrías integrar email, SMS, etc.)
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          NOTIFICACIÓN ENVIADA AL CIUDADANO                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("Destinatario: " + nombreSolicitante);
        System.out.println("Documento: " + documentoIdentidad);
        System.out.println("\nASUNTO: " + asunto);
        System.out.println("\n" + mensaje);
        System.out.println("\n════════════════════════════════════════════════════════════\n");
    }
}
