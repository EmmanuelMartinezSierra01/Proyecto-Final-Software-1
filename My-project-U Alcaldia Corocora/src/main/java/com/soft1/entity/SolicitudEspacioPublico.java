package com.soft1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_espacio_publico")
public class SolicitudEspacioPublico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "tipo_uso")
    private String tipoUso;

    @Column(name = "cobro_extra")
    private Double cobroExtra;

    @Column(name = "variable_total")
    private Double variableTotal;

    @Column(name = "pago_recibido")
    private Double pagoRecibido;

    @Column(name = "estado")
    private String estado;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    public SolicitudEspacioPublico() {
        this.fechaRegistro = LocalDateTime.now();
        this.estado = "REGISTRADA";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }

    public Double getCobroExtra() {
        return cobroExtra;
    }

    public void setCobroExtra(Double cobroExtra) {
        this.cobroExtra = cobroExtra;
    }

    public Double getVariableTotal() {
        return variableTotal;
    }

    public void setVariableTotal(Double variableTotal) {
        this.variableTotal = variableTotal;
    }

    public Double getPagoRecibido() {
        return pagoRecibido;
    }

    public void setPagoRecibido(Double pagoRecibido) {
        this.pagoRecibido = pagoRecibido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}

