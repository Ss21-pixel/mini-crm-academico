package com.etitc.crm.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class Comunicacion {

    private Long id;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(min = 2, max = 100, message = "El asunto debe tener entre 2 y 100 caracteres")
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 2, max = 500, message = "El mensaje debe tener entre 2 y 500 caracteres")
    private String mensaje;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    private Contacto contacto;

    public Comunicacion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }
}