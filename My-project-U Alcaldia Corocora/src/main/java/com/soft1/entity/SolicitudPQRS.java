package com.soft1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_pqrs")
public class SolicitudPQRS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_solicitante")
    private String nombreSolicitante;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "numero_celular")
    private String numeroCelular;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "detalle_solicitud", length = 2000)
    private String detalleSolicitud;

    @Column(name = "tipo_solicitud")
    private String tipoSolicitud;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "estado")
    private String estado;

    @Column(name = "respuesta_sistema", length = 2000)
    private String respuestaSistema;

    // Constructor vacío
    public SolicitudPQRS() {
        this.fechaRegistro = LocalDateTime.now();
        this.estado = "En trámite";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreSolicitante() { return nombreSolicitante; }
    public void setNombreSolicitante(String nombreSolicitante) { this.nombreSolicitante = nombreSolicitante; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNumeroCelular() { return numeroCelular; }
    public void setNumeroCelular(String numeroCelular) { this.numeroCelular = numeroCelular; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getDetalleSolicitud() { return detalleSolicitud; }
    public void setDetalleSolicitud(String detalleSolicitud) { this.detalleSolicitud = detalleSolicitud; }

    public String getTipoSolicitud() { return tipoSolicitud; }
    public void setTipoSolicitud(String tipoSolicitud) { this.tipoSolicitud = tipoSolicitud; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getProcessInstanceId() { return processInstanceId; }
    public void setProcessInstanceId(String processInstanceId) { this.processInstanceId = processInstanceId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getRespuestaSistema() { return respuestaSistema; }
    public void setRespuestaSistema(String respuestaSistema) { this.respuestaSistema = respuestaSistema; }
}
