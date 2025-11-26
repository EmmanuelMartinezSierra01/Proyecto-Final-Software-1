package com.soft1.delegate;

import com.soft1.entity.SolicitudPQRS;
import com.soft1.service.SolicitudPQRSService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("registrarEnSistemaDelegate")
public class RegistrarEnSistemaDelegate implements JavaDelegate {

    @Autowired
    private SolicitudPQRSService solicitudService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Obtener variables del proceso
        String nombreSolicitante = (String) execution.getVariable("nombreSolicitante");
        String cedula = (String) execution.getVariable("cedula");
        String numeroCelular = (String) execution.getVariable("numeroCelular");
        String correoElectronico = (String) execution.getVariable("correoElectronico");
        String direccion = (String) execution.getVariable("direccion");
        String detalleSolicitud = (String) execution.getVariable("detalleSolicitud");
        String tipoSolicitud = (String) execution.getVariable("tipoSolicitud");

        // Crear entidad
        SolicitudPQRS solicitud = new SolicitudPQRS();
        solicitud.setNombreSolicitante(nombreSolicitante);
        solicitud.setCedula(cedula);
        solicitud.setNumeroCelular(numeroCelular);
        solicitud.setCorreoElectronico(correoElectronico);
        solicitud.setDireccion(direccion);
        solicitud.setDetalleSolicitud(detalleSolicitud);
        solicitud.setTipoSolicitud(tipoSolicitud);
        solicitud.setProcessInstanceId(execution.getProcessInstanceId());

        // Guardar en BD
        SolicitudPQRS solicitudGuardada = solicitudService.guardarSolicitud(solicitud);

        // Guardar el ID en el proceso (útil para referencias posteriores)
        execution.setVariable("solicitudId", solicitudGuardada.getId());

        System.out.println("✅ Solicitud PQRS registrada con ID: " + solicitudGuardada.getId());
        System.out.println("   Solicitante: " + nombreSolicitante);
        System.out.println("   Cédula: " + cedula);
        System.out.println("   Tipo: " + tipoSolicitud);
    }
}
