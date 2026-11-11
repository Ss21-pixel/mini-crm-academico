
package com.etitc.crm.controller;

import com.etitc.crm.dto.ContactoRequest;
import com.etitc.crm.dto.ContactoResponse;
import com.etitc.crm.modelo.Contacto;
import com.etitc.crm.service.ContactoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.net.URI;

import java.util.List;


@RestController
@RequestMapping("/api/contactos")
public class ContactoRestController {

    private final ContactoService contactoService;

    public ContactoRestController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    // GET: LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<ContactoResponse>> listar() {

        List<ContactoResponse> contactos =
                contactoService.listarTodos()
                        .stream()
                        .map(ContactoResponse::desde)
                        .toList();

        return ResponseEntity.ok(contactos);
    }

    // GET: BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactoResponse> buscar(
            @PathVariable Long id
    ) {

        Contacto contacto = contactoService.obtenerPorId(id);

        return ResponseEntity.ok(
                ContactoResponse.desde(contacto)
        );
    }

    // POST: CREAR
    @PostMapping
    public ResponseEntity<ContactoResponse> crear(
            @Valid @RequestBody ContactoRequest request
    ) {

        Contacto nuevo = contactoService.crearDesdeApi(request);

        URI location = URI.create(
                "/api/contactos/" + nuevo.getId()
        );

        return ResponseEntity
                .created(location)
                .body(ContactoResponse.desde(nuevo));
    }

    // PUT: ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<ContactoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ContactoRequest request
    ) {

        Contacto actualizado =
                contactoService.actualizarDesdeApi(id, request);

        return ResponseEntity.ok(
                ContactoResponse.desde(actualizado)
        );
    }

    // DELETE: ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        contactoService.obtenerPorId(id);
        contactoService.eliminar(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
