package com.soft1.delegate;

import com.soft1.entity.SolicitudEspacioPublico;
import com.soft1.service.SolicitudEspacioPublicoService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("registrarSolicitudEspacioDelegate")
public class RegistrarSolicitudEspacioDelegate implements JavaDelegate {

    @Autowired
    private SolicitudEspacioPublicoService service;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processInstanceId = execution.getProcessInstanceId();

        String nombre = (String) execution.getVariable("Nombre");
        String direccion = (String) execution.getVariable("Direccion");
        String tipoUso = (String) execution.getVariable("Tipo_Uso");

        Object cobroExtraObj = execution.getVariable("CobroExtra");
        if (cobroExtraObj == null) {
            cobroExtraObj = execution.getVariable("Cobro");
        }

        Double cobroExtra = cobroExtraObj instanceof Number ? ((Number) cobroExtraObj).doubleValue() : null;

        SolicitudEspacioPublico entity = new SolicitudEspacioPublico();
        entity.setNombre(nombre);
        entity.setDireccion(direccion);
        entity.setTipoUso(tipoUso);
        entity.setCobroExtra(cobroExtra);
        entity.setProcessInstanceId(processInstanceId);

        SolicitudEspacioPublico saved = service.guardar(entity);
        execution.setVariable("solicitudEspacioId", saved.getId());

        System.out.println("✅ Solicitud Espacio Público registrada: ID=" + saved.getId());
    }
}

