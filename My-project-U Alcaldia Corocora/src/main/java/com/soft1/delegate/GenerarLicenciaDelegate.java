package com.alcaldia.licencias.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class GenerarLicenciaDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Recuperar información de la solicitud
        String solicitudId = (String) execution.getVariable("solicitudId");
        String nombreSolicitante = (String) execution.getVariable("nombreSolicitante");
        String direccionObra = (String) execution.getVariable("direccionObra");

        // Generar número de licencia único
        LocalDate hoy = LocalDate.now();
        String numeroLicencia = String.format("LIC-%d-%06d",
                hoy.getYear(),
                System.currentTimeMillis() % 1000000);

        // Calcular fechas
        String fechaExpedicion = hoy.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String fechaVencimiento = hoy.plusYears(2)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);

        // Guardar variables en el proceso
        execution.setVariable("numeroLicencia", numeroLicencia);
        execution.setVariable("fechaExpedicion", fechaExpedicion);
        execution.setVariable("fechaVencimiento", fechaVencimiento);
        execution.setVariable("licenciaGenerada", true);
        execution.setVariable("estadoSolicitud", "LICENCIA_APROBADA");

        // Log para seguimiento
        System.out.println("=== LICENCIA GENERADA ===");
        System.out.println("Número: " + numeroLicencia);
        System.out.println("Solicitante: " + nombreSolicitante);
        System.out.println("Dirección: " + direccionObra);
        System.out.println("Expedición: " + fechaExpedicion);
        System.out.println("Vencimiento: " + fechaVencimiento);
        System.out.println("========================");
    }
}
