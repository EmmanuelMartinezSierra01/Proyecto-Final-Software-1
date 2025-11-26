package com.soft1.controller;

import com.soft1.entity.SolicitudPQRS;
import com.soft1.service.SolicitudPQRSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pqrs")
public class SolicitudPQRSConsultaController {

    @Autowired
    private SolicitudPQRSService solicitudService;

    @GetMapping("/consultar")
    public List<SolicitudPQRS> consultarPorCedula(@RequestParam String cedula) {
        return solicitudService.buscarPorCedula(cedula);
    }

}
