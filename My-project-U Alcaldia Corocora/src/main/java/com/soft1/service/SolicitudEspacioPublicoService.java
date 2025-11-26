package com.soft1.service;

import com.soft1.entity.SolicitudEspacioPublico;
import com.soft1.repository.SolicitudEspacioPublicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitudEspacioPublicoService {

    @Autowired
    private SolicitudEspacioPublicoRepository repository;

    @Transactional
    public SolicitudEspacioPublico guardar(SolicitudEspacioPublico entity) {
        return repository.save(entity);
    }

    @Transactional
    public SolicitudEspacioPublico actualizarPago(Long id, Double variableTotal, Double pagoRecibido, String nuevoEstado) {
        SolicitudEspacioPublico e = repository.findById(id).orElse(null);
        if (e == null) return null;
        if (variableTotal != null) e.setVariableTotal(variableTotal);
        if (pagoRecibido != null) e.setPagoRecibido(pagoRecibido);
        if (nuevoEstado != null) e.setEstado(nuevoEstado);
        return repository.save(e);
    }
}

