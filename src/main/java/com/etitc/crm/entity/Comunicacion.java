package com.etitc.crm.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comunicaciones")
public class Comunicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String tipo;

    @Column(length = 150)
    private String asunto;

    @Column(length = 500)
    private String mensaje;

    @Column
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "contacto_id")
    private Contacto contacto;

    // Constructor vacío
    public Comunicacion() {
    }

    // Getters y Setters

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