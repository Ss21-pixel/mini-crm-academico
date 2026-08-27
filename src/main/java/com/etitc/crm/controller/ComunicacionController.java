package com.etitc.crm.controller;

import com.etitc.crm.entity.Comunicacion;
import com.etitc.crm.entity.Contacto;
import com.etitc.crm.repository.ComunicacionRepository;
import com.etitc.crm.repository.ContactoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunicaciones")
@CrossOrigin(origins = "*")
public class ComunicacionController {

    private final ComunicacionRepository repository;
    private final ContactoRepository contactoRepository;

    public ComunicacionController(
            ComunicacionRepository repository,
            ContactoRepository contactoRepository) {

        this.repository = repository;
        this.contactoRepository = contactoRepository;
    }

    @GetMapping
    public List<Comunicacion> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comunicacion> obtener(@PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comunicacion> crear(
            @RequestBody Comunicacion comunicacion) {

        if (comunicacion.getContacto() != null &&
            comunicacion.getContacto().getId() != null) {

            Long contactoId =
                    comunicacion.getContacto().getId();

            Contacto contacto = contactoRepository
                    .findById(contactoId)
                    .orElse(null);

            if (contacto == null) {
                return ResponseEntity.badRequest().build();
            }

            comunicacion.setContacto(contacto);
        }

        return ResponseEntity.ok(
                repository.save(comunicacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comunicacion> actualizar(
            @PathVariable Long id,
            @RequestBody Comunicacion datos) {

        return repository.findById(id)
                .map(comunicacion -> {

                    comunicacion.setTipo(datos.getTipo());
                    comunicacion.setAsunto(datos.getAsunto());
                    comunicacion.setMensaje(datos.getMensaje());
                    comunicacion.setFecha(datos.getFecha());

                    if (datos.getContacto() != null &&
                        datos.getContacto().getId() != null) {

                        contactoRepository
                                .findById(
                                    datos.getContacto().getId()
                                )
                                .ifPresent(
                                    comunicacion::setContacto
                                );
                    }

                    return ResponseEntity.ok(
                            repository.save(comunicacion));
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