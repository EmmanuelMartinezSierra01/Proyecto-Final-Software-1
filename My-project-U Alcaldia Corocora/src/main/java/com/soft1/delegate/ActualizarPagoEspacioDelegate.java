package com.soft1.delegate;

import com.soft1.entity.SolicitudEspacioPublico;
import com.soft1.repository.SolicitudEspacioPublicoRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("actualizarPagoEspacioDelegate")
public class ActualizarPagoEspacioDelegate implements JavaDelegate {

    @Autowired
    private SolicitudEspacioPublicoRepository repository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long id = (Long) execution.getVariable("solicitudEspacioId");
        if (id == null) {
            System.err.println("❌ No se encontró 'solicitudEspacioId' en el proceso");
            return;
        }

        SolicitudEspacioPublico e = repository.findById(id).orElse(null);
        if (e == null) {
            System.err.println("❌ No existe SolicitudEspacioPublico con ID: " + id);
            return;
        }

        Object vt = execution.getVariable("VariableTotal");
        Object rp = execution.getVariable("RecibirPago");
        Object nombre = execution.getVariable("Nombre");
        Object direccion = execution.getVariable("Direccion");

        Double variableTotal = vt instanceof Number ? ((Number) vt).doubleValue() : null;
        Double pagoRecibido = rp instanceof Number ? ((Number) rp).doubleValue() : null;

        if (variableTotal != null) e.setVariableTotal(variableTotal);
        if (pagoRecibido != null) e.setPagoRecibido(pagoRecibido);
        if (nombre instanceof String && !((String) nombre).isBlank()) e.setNombre((String) nombre);
        if (direccion instanceof String && !((String) direccion).isBlank()) e.setDireccion((String) direccion);

        repository.save(e);

        System.out.println("✅ Pago actualizado para SolicitudEspacioPublico ID=" + id);
    }
}
