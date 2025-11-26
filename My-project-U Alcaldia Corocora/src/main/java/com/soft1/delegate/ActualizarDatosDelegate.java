package com.soft1.delegate;

import com.soft1.entity.SolicitudPQRS;
import com.soft1.repository.SolicitudPQRSRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("actualizarDatosDelegate")
public class ActualizarDatosDelegate implements JavaDelegate {

    @Autowired
    private SolicitudPQRSRepository repository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long solicitudId = (Long) execution.getVariable("solicitudId");

        if (solicitudId == null) {
            System.err.println("❌ Error: No se encontró solicitudId");
            return;
        }

        SolicitudPQRS solicitud = repository.findById(solicitudId).orElse(null);

        if (solicitud == null) {
            System.err.println("❌ Error: No se encontró solicitud con ID: " + solicitudId);
            return;
        }

        // Actualizar todos los campos
        String nombreSolicitante = (String) execution.getVariable("nombreSolicitante");
        String cedula = (String) execution.getVariable("cedula");
        String numeroCelular = (String) execution.getVariable("numeroCelular");
        String correo = (String) execution.getVariable("correoElectronico");
        String direccion = (String) execution.getVariable("direccion");
        String tipoSolicitud = (String) execution.getVariable("tipoSolicitud");
        String detalleSolicitud = (String) execution.getVariable("detalleSolicitud");

        // Solo actualizar si tienen valor
        if (nombreSolicitante != null && !nombreSolicitante.trim().isEmpty()) {
            solicitud.setNombreSolicitante(nombreSolicitante);
        }
        if (cedula != null && !cedula.trim().isEmpty()) {
            solicitud.setCedula(cedula);
        }
        if (numeroCelular != null && !numeroCelular.trim().isEmpty()) {
            solicitud.setNumeroCelular(numeroCelular);
        }
        if (correo != null && !correo.trim().isEmpty()) {
            solicitud.setCorreoElectronico(correo);
        }
        if (direccion != null && !direccion.trim().isEmpty()) {
            solicitud.setDireccion(direccion);
        }
        if (tipoSolicitud != null && !tipoSolicitud.trim().isEmpty()) {
            solicitud.setTipoSolicitud(tipoSolicitud);
        }
        if (detalleSolicitud != null && !detalleSolicitud.trim().isEmpty()) {
            solicitud.setDetalleSolicitud(detalleSolicitud);
        }

        // Guardar
        repository.save(solicitud);

        System.out.println("✅ Datos actualizados para solicitud ID: " + solicitudId);
    }
}
