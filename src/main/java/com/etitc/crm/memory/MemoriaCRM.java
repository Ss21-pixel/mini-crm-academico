package com.etitc.crm.memory;

import com.etitc.crm.entity.Contacto;
import com.etitc.crm.entity.Comunicacion;
import com.etitc.crm.entity.Oportunidad;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class MemoriaCRM {

    private final List<Contacto> contactos = new ArrayList<>();
    private final List<Comunicacion> comunicaciones = new ArrayList<>();
    private final List<Oportunidad> oportunidades = new ArrayList<>();

    private final AtomicLong contactoId = new AtomicLong(0);
    private final AtomicLong comunicacionId = new AtomicLong(0);
    private final AtomicLong oportunidadId = new AtomicLong(0);

    // =========================
    // CONTACTOS
    // =========================

    public List<Contacto> listarContactos() {
        return new ArrayList<>(contactos);
    }

    public Optional<Contacto> buscarContacto(Long id) {
        return contactos.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public Contacto guardarContacto(Contacto contacto) {

        if (contacto.getId() == null) {
            contacto.setId(contactoId.incrementAndGet());
            contactos.add(contacto);
        } else {
            eliminarContactoInterno(contacto.getId());
            contactos.add(contacto);
        }

        return contacto;
    }

    public boolean eliminarContacto(Long id) {
        return contactos.removeIf(c -> c.getId().equals(id));
    }

    private void eliminarContactoInterno(Long id) {
        contactos.removeIf(c -> c.getId().equals(id));
    }

    // =========================
    // COMUNICACIONES
    // =========================

    public List<Comunicacion> listarComunicaciones() {
        return new ArrayList<>(comunicaciones);
    }

    public Optional<Comunicacion> buscarComunicacion(Long id) {
        return comunicaciones.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public Comunicacion guardarComunicacion(Comunicacion comunicacion) {

        if (comunicacion.getId() == null) {
            comunicacion.setId(comunicacionId.incrementAndGet());
            comunicaciones.add(comunicacion);
        } else {
            comunicaciones.removeIf(
                    c -> c.getId().equals(comunicacion.getId())
            );
            comunicaciones.add(comunicacion);
        }

        return comunicacion;
    }

    public boolean eliminarComunicacion(Long id) {
        return comunicaciones.removeIf(
                c -> c.getId().equals(id)
        );
    }

    // =========================
    // OPORTUNIDADES
    // =========================

    public List<Oportunidad> listarOportunidades() {
        return new ArrayList<>(oportunidades);
    }

    public Optional<Oportunidad> buscarOportunidad(Long id) {
        return oportunidades.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    public Oportunidad guardarOportunidad(Oportunidad oportunidad) {

        if (oportunidad.getId() == null) {
            oportunidad.setId(oportunidadId.incrementAndGet());
            oportunidades.add(oportunidad);
        } else {
            oportunidades.removeIf(
                    o -> o.getId().equals(oportunidad.getId())
            );
            oportunidades.add(oportunidad);
        }

        return oportunidad;
    }

    public boolean eliminarOportunidad(Long id) {
        return oportunidades.removeIf(
                o -> o.getId().equals(id)
        );
    }
}