package com.soft1.repository;

import com.soft1.entity.SolicitudPQRS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudPQRSRepository extends JpaRepository<SolicitudPQRS, Long> {
    List<SolicitudPQRS> findByCedula(String cedula);
    SolicitudPQRS findByProcessInstanceId(String processInstanceId);
}
