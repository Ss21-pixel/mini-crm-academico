package com.etitc.crm.controller;

import com.etitc.crm.entity.Comunicacion;
import com.etitc.crm.entity.Contacto;
import com.etitc.crm.service.ComunicacionService;
import com.etitc.crm.service.ContactoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunicaciones")
@CrossOrigin(origins = "*")
public class ComunicacionController {

    private final ComunicacionService comunicacionService;
    private final ContactoService contactoService;

    public ComunicacionController(
            ComunicacionService comunicacionService,
            ContactoService contactoService) {

        this.comunicacionService = comunicacionService;
        this.contactoService = contactoService;
    }

    @GetMapping
    public List<Comunicacion> listar() {
        return comunicacionService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comunicacion> obtener(@PathVariable Long id) {

        return comunicacionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comunicacion> crear(
            @RequestBody Comunicacion comunicacion) {

        if (comunicacion.getContacto() != null &&
                comunicacion.getContacto().getId() != null) {

            Long contactoId = comunicacion.getContacto().getId();

            Contacto contacto = contactoService
                    .buscarPorId(contactoId)
                    .orElse(null);

            if (contacto == null) {
                return ResponseEntity.badRequest().build();
            }

            comunicacion.setContacto(contacto);
        }

        return ResponseEntity.ok(
                comunicacionService.guardar(comunicacion)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comunicacion> actualizar(
            @PathVariable Long id,
            @RequestBody Comunicacion datos) {

        return comunicacionService.buscarPorId(id)
                .map(comunicacion -> {

                    comunicacion.setTipo(datos.getTipo());
                    comunicacion.setAsunto(datos.getAsunto());
                    comunicacion.setMensaje(datos.getMensaje());
                    comunicacion.setFecha(datos.getFecha());

                    if (datos.getContacto() != null &&
                            datos.getContacto().getId() != null) {

                        contactoService
                                .buscarPorId(datos.getContacto().getId())
                                .ifPresent(comunicacion::setContacto);
                    }

                    return ResponseEntity.ok(
                            comunicacionService.guardar(comunicacion)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (comunicacionService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        comunicacionService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}