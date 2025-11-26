package com.soft1.delegate;

import com.soft1.entity.SolicitudPQRS;
import com.soft1.service.SolicitudPQRSService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component("notificarRespuestaDelegate")
public class NotificarRespuestaDelegate implements JavaDelegate {

    @Autowired
    private SolicitudPQRSService solicitudPQRSService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        // Obtener información del solicitante y proceso
        String processInstanceId = execution.getProcessInstanceId();
        String nombreSolicitante = (String) execution.getVariable("nombreSolicitante");
        String correoElectronico = (String) execution.getVariable("correoElectronico");
        String cedula = (String) execution.getVariable("cedula");
        String numeroCelular = (String) execution.getVariable("numeroCelular");
        String direccion = (String) execution.getVariable("direccion");
        String tipoSolicitud = (String) execution.getVariable("tipoSolicitud");
        String detalleSolicitud = (String) execution.getVariable("detalleSolicitud");

        // Obtener la propuesta desarrollada por la alcaldía
        String propuestaDesarrollada = (String) execution.getVariable("propuestaDesarrollada");

        // Información adicional
        String dependenciaAsignada = (String) execution.getVariable("dependenciaAsignada");
        String estadoAprobacion = (String) execution.getVariable("estadoAprobacion");

        // Valores por defecto
        if (propuestaDesarrollada == null || propuestaDesarrollada.isEmpty()) {
            propuestaDesarrollada = "Su solicitud ha sido aprobada y está en proceso de implementación. " +
                    "En breve recibirá información detallada sobre los próximos pasos.";
        }

        if (estadoAprobacion == null || estadoAprobacion.isEmpty()) {
            estadoAprobacion = "APROBADA";
        }

        if (dependenciaAsignada == null || dependenciaAsignada.isEmpty()) {
            dependenciaAsignada = "Dependencia Competente";
        }

        // Fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaRespuesta = ahora.format(formatter);

        List<SolicitudPQRS> solicitudes = solicitudPQRSService.buscarPorCedula(cedula);
        SolicitudPQRS solicitud = null;
        if (solicitudes != null && !solicitudes.isEmpty()) {
            solicitud = solicitudes.get(solicitudes.size() - 1);
        }

        if (solicitud != null) {
            solicitud.setEstado("Completado"); // Cambia estado a Completado
            solicitud.setRespuestaSistema(propuestaDesarrollada); // Guarda propuesta en respuestaSistema
            solicitudPQRSService.guardarSolicitud(solicitud);
        } else {
            System.err.println("No se encontró la solicitud PQRS con cedula: " + cedula);
        }


        // Construir el mensaje de notificación
        StringBuilder correo = new StringBuilder();
        correo.append("\n");
        correo.append("═══════════════════════════════════════════════════════════════\n");
        correo.append("  NOTIFICACIÓN DE RESPUESTA - PQRS APROBADA\n");
        correo.append("   ALCALDÍA DE COROCORA\n");
        correo.append("═══════════════════════════════════════════════════════════════\n");
        correo.append("\n");
        correo.append("Para: ").append(correoElectronico).append("\n");
        correo.append("Asunto: Su ").append(formatearTipo(tipoSolicitud)).append(" ha sido APROBADA \n");
        correo.append("───────────────────────────────────────────────────────────────\n");
        correo.append("\n");
        correo.append("¡Estimado(a) ").append(nombreSolicitante).append("!\n");
        correo.append("\n");
        correo.append("Nos complace informarle que su ").append(formatearTipo(tipoSolicitud));
        correo.append(" ha sido APROBADA por nuestra administración.\n");
        correo.append("\n");
        correo.append("┌─────────────────────────────────────────────────────────────┐\n");
        correo.append("│ INFORMACIÓN DEL SOLICITANTE                                 │\n");
        correo.append("├─────────────────────────────────────────────────────────────┤\n");
        correo.append("│ Nombre completo:     ").append(nombreSolicitante).append("\n");
        correo.append("│ Cédula:              ").append(cedula != null ? cedula : "N/A").append("\n");
        correo.append("│ Celular:             ").append(numeroCelular != null ? numeroCelular : "N/A").append("\n");
        correo.append("│ Correo:              ").append(correoElectronico).append("\n");
        correo.append("│ Dirección:           ").append(direccion != null ? direccion : "N/A").append("\n");
        correo.append("└─────────────────────────────────────────────────────────────┘\n");
        correo.append("\n");
        correo.append("┌─────────────────────────────────────────────────────────────┐\n");
        correo.append("│ INFORMACIÓN DE LA SOLICITUD                                 │\n");
        correo.append("├─────────────────────────────────────────────────────────────┤\n");
        correo.append("│ Número de radicado:  ").append(processInstanceId).append("\n");
        correo.append("│ Tipo de solicitud:   ").append(formatearTipo(tipoSolicitud)).append("\n");
        correo.append("│ Estado:              ").append(estadoAprobacion).append("\n");
        correo.append("│ Fecha de respuesta:  ").append(fechaRespuesta).append("\n");
        correo.append("│ Dependencia:         ").append(dependenciaAsignada).append("\n");
        correo.append("└─────────────────────────────────────────────────────────────┘\n");
        correo.append("\n");
        correo.append("═══════════════════════════════════════════════════════════════\n");
        correo.append("  SU SOLICITUD ORIGINAL\n");
        correo.append("═══════════════════════════════════════════════════════════════\n");
        correo.append("\n");
        correo.append(detalleSolicitud).append("\n");
        correo.append("\n");
        correo.append("═══════════════════════════════════════════════════════════════\n");
        correo.append("  PROPUESTA DESARROLLADA POR LA ALCALDÍA\n");
        correo.append("═══════════════════════════════════════════════════════════════\n");
        correo.append("\n");
        correo.append(propuestaDesarrollada).append("\n");
        correo.append("\n");
        correo.append("═══════════════════════════════════════════════════════════════\n");
        correo.append("\n");
        correo.append("  PRÓXIMOS PASOS:\n");
        correo.append("\n");
        correo.append("  1. Revise detalladamente la propuesta presentada\n");
        correo.append("  2. Si tiene alguna duda o consulta, puede comunicarse con\n");
        correo.append("     la dependencia asignada: ").append(dependenciaAsignada).append("\n");
        correo.append("  3. Mantenga este número de radicado para futuras consultas:\n");
        correo.append("     ").append(processInstanceId).append("\n");
        correo.append("\n");
        correo.append("───────────────────────────────────────────────────────────────\n");
        correo.append("\n");
        correo.append("  INFORMACIÓN DE CONTACTO:\n");
        correo.append("\n");
        correo.append("    Horario de atención:\n");
        correo.append("    Lunes a Viernes: 8:00 AM - 12:00 PM y 2:00 PM - 6:00 PM\n");
        correo.append("\n");
        correo.append("    Teléfono: (XXX) XXX-XXXX\n");
        correo.append("    Dirección: Alcaldía Municipal de Corocora\n");
        correo.append("\n");
        correo.append("───────────────────────────────────────────────────────────────\n");
        correo.append("\n");
        correo.append("Agradecemos su confianza en nuestra administración y esperamos\n");
        correo.append("que la propuesta presentada cumpla con sus expectativas.\n");
        correo.append("\n");
        correo.append("Atentamente,\n");
        correo.append("\n");
        correo.append("").append(dependenciaAsignada).append("\n");
        correo.append("Alcaldía Municipal de Corocora\n");
        correo.append("\n");
        correo.append("───────────────────────────────────────────────────────────────\n");
        correo.append("Este es un mensaje automático, por favor no responder.\n");
        correo.append("Para consultas, utilice los canales oficiales de atención.\n");
        correo.append("═══════════════════════════════════════════════════════════════\n");
        correo.append("\n");

        // Mostrar en consola (simulación de envío de correo)
        System.out.println(correo.toString());

        // Guardar información de la respuesta en las variables del proceso
        execution.setVariable("fechaRespuesta", fechaRespuesta);
        execution.setVariable("estadoPQRS", "RESPONDIDA");
        execution.setVariable("notificacionRespuestaEnviada", true);

        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("  Notificación de respuesta enviada exitosamente");
        System.out.println("   Destinatario: " + correoElectronico);
        System.out.println("   Solicitante: " + nombreSolicitante);
        System.out.println("   Radicado: " + processInstanceId);
        System.out.println("   Estado: " + estadoAprobacion);
        System.out.println("═══════════════════════════════════════════════════════════════");
    }

    /**
     * Formatea el tipo de solicitud para que se vea mejor en el mensaje
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
