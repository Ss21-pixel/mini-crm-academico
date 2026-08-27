
package com.etitc.crm.controller;

import com.etitc.crm.entity.Contacto;
import com.etitc.crm.service.ContactoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
public class ContactoRestController {

    private final ContactoService contactoService;

    public ContactoRestController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    // LISTAR CONTACTOS
    @GetMapping
    public List<Contacto> listar() {
        return contactoService.listarTodos();
    }

    // BUSCAR CONTACTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Contacto> buscar(@PathVariable Long id) {

        return contactoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREAR CONTACTO
    @PostMapping
    public Contacto crear(@RequestBody Contacto contacto) {
        return contactoService.guardar(contacto);
    }

    // ACTUALIZAR CONTACTO
    @PutMapping("/{id}")
    public ResponseEntity<Contacto> actualizar(
            @PathVariable Long id,
            @RequestBody Contacto contacto) {

        return contactoService.buscarPorId(id)
                .map(contactoExistente -> {

                    contactoExistente.setNombre(contacto.getNombre());
                    contactoExistente.setApellido(contacto.getApellido());
                    contactoExistente.setEmail(contacto.getEmail());
                    contactoExistente.setTelefono(contacto.getTelefono());
                    contactoExistente.setPrograma(contacto.getPrograma());
                    contactoExistente.setEstado(contacto.getEstado());

                    return ResponseEntity.ok(
                            contactoService.guardar(contactoExistente)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ELIMINAR CONTACTO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (contactoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        contactoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}