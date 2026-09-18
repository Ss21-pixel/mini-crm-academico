package com.etitc.crm.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class Oportunidad {

    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 2, max = 100, message = "El título debe tener entre 2 y 100 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 2, max = 500, message = "La descripción debe tener entre 2 y 500 caracteres")
    private String descripcion;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotBlank(message = "La prioridad es obligatoria")
    private String prioridad;

    private Contacto contacto;

    @NotNull(message = "La fecha de creación es obligatoria")
    private LocalDate fechaCreacion;

    @NotNull(message = "La fecha de seguimiento es obligatoria")
    private LocalDate fechaSeguimiento;

    public Oportunidad() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaSeguimiento() {
        return fechaSeguimiento;
    }

    public void setFechaSeguimiento(LocalDate fechaSeguimiento) {
        this.fechaSeguimiento = fechaSeguimiento;
    }
}