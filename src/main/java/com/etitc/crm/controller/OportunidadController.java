package com.etitc.crm.controller;

import com.etitc.crm.entity.Contacto;
import com.etitc.crm.entity.Oportunidad;
import com.etitc.crm.service.ContactoService;
import com.etitc.crm.service.OportunidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oportunidades")
@CrossOrigin(origins = "*")
public class OportunidadController {

    private final OportunidadService oportunidadService;
    private final ContactoService contactoService;

    public OportunidadController(
            OportunidadService oportunidadService,
            ContactoService contactoService) {

        this.oportunidadService = oportunidadService;
        this.contactoService = contactoService;
    }

    // =========================
    // LISTAR OPORTUNIDADES
    // =========================

    @GetMapping
    public List<Oportunidad> listar() {
        return oportunidadService.listarTodos();
    }

    // =========================
    // BUSCAR POR ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<Oportunidad> obtener(@PathVariable Long id) {

        return oportunidadService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================
    // CREAR OPORTUNIDAD
    // =========================

    @PostMapping
    public ResponseEntity<Oportunidad> crear(
            @RequestBody Oportunidad oportunidad) {

        if (oportunidad.getContacto() != null &&
                oportunidad.getContacto().getId() != null) {

            Long contactoId = oportunidad.getContacto().getId();

            Contacto contacto = contactoService
                    .buscarPorId(contactoId)
                    .orElse(null);

            if (contacto == null) {
                return ResponseEntity.badRequest().build();
            }

            oportunidad.setContacto(contacto);
        }

        return ResponseEntity.ok(
                oportunidadService.guardar(oportunidad)
        );
    }

    // =========================
    // ACTUALIZAR OPORTUNIDAD
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<Oportunidad> actualizar(
            @PathVariable Long id,
            @RequestBody Oportunidad datos) {

        return oportunidadService.buscarPorId(id)
                .map(oportunidad -> {

                    oportunidad.setTitulo(datos.getTitulo());
                    oportunidad.setDescripcion(datos.getDescripcion());
                    oportunidad.setEstado(datos.getEstado());
                    oportunidad.setPrioridad(datos.getPrioridad());
                    oportunidad.setFechaCreacion(
                            datos.getFechaCreacion()
                    );
                    oportunidad.setFechaSeguimiento(
                            datos.getFechaSeguimiento()
                    );

                    if (datos.getContacto() != null &&
                            datos.getContacto().getId() != null) {

                        contactoService
                                .buscarPorId(datos.getContacto().getId())
                                .ifPresent(oportunidad::setContacto);
                    }

                    return ResponseEntity.ok(
                            oportunidadService.guardar(oportunidad)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================
    // ELIMINAR OPORTUNIDAD
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (oportunidadService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        oportunidadService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}