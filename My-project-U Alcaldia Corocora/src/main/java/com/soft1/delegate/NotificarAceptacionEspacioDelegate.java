package com.soft1.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("notificarAceptacionEspacioDelegate")
public class NotificarAceptacionEspacioDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String nombre = (String) execution.getVariable("Nombre");
        String direccion = (String) execution.getVariable("Direccion");
        Object total = execution.getVariable("VariableTotal");
        System.out.println("Notificación de aceptación enviada: " + nombre + ", " + direccion + ", total=" + total);
        execution.setVariable("notificacionAceptacion", true);
    }
}

