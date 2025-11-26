package com.soft1.repository;

import com.soft1.entity.SolicitudEspacioPublico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudEspacioPublicoRepository extends JpaRepository<SolicitudEspacioPublico, Long> {
}

