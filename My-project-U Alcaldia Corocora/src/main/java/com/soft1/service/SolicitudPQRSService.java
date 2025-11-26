package com.soft1.service;

import com.soft1.entity.SolicitudPQRS;
import com.soft1.repository.SolicitudPQRSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SolicitudPQRSService {

    @Autowired
    private SolicitudPQRSRepository repository;

    @Transactional
    public SolicitudPQRS guardarSolicitud(SolicitudPQRS solicitud) {
        return repository.save(solicitud);
    }

    public List<SolicitudPQRS> buscarPorCedula(String cedula) {
        return repository.findByCedula(cedula);
    }

    public SolicitudPQRS buscarPorProcessInstanceId(String processInstanceId) {
        return repository.findByProcessInstanceId(processInstanceId);
    }

}
