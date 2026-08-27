package com.etitc.crm.controller;

import com.etitc.crm.entity.Contacto;
import com.etitc.crm.entity.Oportunidad;
import com.etitc.crm.repository.ContactoRepository;
import com.etitc.crm.repository.OportunidadRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oportunidades")
@CrossOrigin(origins = "*")
public class OportunidadController {

    private final OportunidadRepository repository;
    private final ContactoRepository contactoRepository;

    public OportunidadController(
            OportunidadRepository repository,
            ContactoRepository contactoRepository) {

        this.repository = repository;
        this.contactoRepository = contactoRepository;
    }

    @GetMapping
    public List<Oportunidad> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oportunidad> obtener(@PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Oportunidad> crear(
            @RequestBody Oportunidad oportunidad) {

        if (oportunidad.getContacto() != null &&
            oportunidad.getContacto().getId() != null) {

            Long contactoId = oportunidad.getContacto().getId();

            Contacto contacto = contactoRepository
                    .findById(contactoId)
                    .orElse(null);

            if (contacto == null) {
                return ResponseEntity.badRequest().build();
            }

            oportunidad.setContacto(contacto);
        }

        return ResponseEntity.ok(repository.save(oportunidad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Oportunidad> actualizar(
            @PathVariable Long id,
            @RequestBody Oportunidad datos) {

        return repository.findById(id)
                .map(oportunidad -> {

                    oportunidad.setTitulo(datos.getTitulo());
                    oportunidad.setDescripcion(datos.getDescripcion());
                    oportunidad.setEstado(datos.getEstado());
                    oportunidad.setPrioridad(datos.getPrioridad());
                    oportunidad.setFechaCreacion(
                            datos.getFechaCreacion());
                    oportunidad.setFechaSeguimiento(
                            datos.getFechaSeguimiento());

                    if (datos.getContacto() != null &&
                        datos.getContacto().getId() != null) {

                        contactoRepository
                                .findById(datos.getContacto().getId())
                                .ifPresent(oportunidad::setContacto);
                    }

                    return ResponseEntity.ok(
                            repository.save(oportunidad));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}