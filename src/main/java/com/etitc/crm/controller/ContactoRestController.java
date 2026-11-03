
package com.etitc.crm.controller;

import com.etitc.crm.modelo.Contacto;
import com.etitc.crm.service.ContactoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contactos")
public class ContactoRestController {

    private final ContactoService contactoService;

    public ContactoRestController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    // =====================================
    // GET: LISTAR TODOS LOS CONTACTOS
    // =====================================

    @GetMapping
    public ResponseEntity<List<Contacto>> listar() {

        return ResponseEntity.ok(
                contactoService.listarTodos()
        );
    }

    // =====================================
    // GET: BUSCAR CONTACTO POR ID
    // =====================================

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        return contactoService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "mensaje", "No se encontró el contacto con ID: " + id
                        )));
    }

    // =====================================
    // POST: CREAR CONTACTO
    // =====================================

    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody Contacto contacto) {

        if (contactoService.emailDuplicado(contacto)) {
            return ResponseEntity.badRequest().body(
                    Map.of("mensaje", "El correo electrónico ya está registrado")
            );
        }

        Contacto nuevoContacto = contactoService.guardar(contacto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevoContacto);
    }

    // =====================================
    // PUT: ACTUALIZAR CONTACTO
    // =====================================

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Contacto contacto) {

        return contactoService.buscarPorId(id)
                .<ResponseEntity<?>>map(contactoExistente -> {

                    contacto.setId(id);

                    if (contactoService.emailDuplicado(contacto)) {
                        return ResponseEntity.badRequest().body(
                                Map.of("mensaje",
                                        "El correo electrónico ya está registrado")
                        );
                    }

                    contactoExistente.setNombre(contacto.getNombre());
                    contactoExistente.setApellido(contacto.getApellido());
                    contactoExistente.setEmail(contacto.getEmail());
                    contactoExistente.setTelefono(contacto.getTelefono());
                    contactoExistente.setPrograma(contacto.getPrograma());
                    contactoExistente.setEstado(contacto.getEstado());

                    Contacto actualizado =
                            contactoService.guardar(contactoExistente);

                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "mensaje", "No se encontró el contacto con ID: " + id
                        )));
    }

    // =====================================
    // DELETE: ELIMINAR CONTACTO
    // =====================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        if (contactoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "mensaje", "No se encontró el contacto con ID: " + id
                    ));
        }

        contactoService.eliminar(id);

        return ResponseEntity.ok(
                Map.of("mensaje", "Contacto eliminado correctamente")
        );
    }
}
