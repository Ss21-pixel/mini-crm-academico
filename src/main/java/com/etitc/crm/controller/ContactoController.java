package com.etitc.crm.controller;

import com.etitc.crm.entity.Contacto;
import com.etitc.crm.repository.ContactoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@CrossOrigin(origins = "*")
public class ContactoController {

    private final ContactoRepository repository;

    public ContactoController(ContactoRepository repository) {
        this.repository = repository;
    }

    // LISTAR CONTACTOS
    @GetMapping
    public List<Contacto> listar() {
        return repository.findAll();
    }

    // BUSCAR CONTACTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Contacto> obtener(@PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GUARDAR CONTACTO
    @PostMapping
    public Contacto crear(@RequestBody Contacto contacto) {
        return repository.save(contacto);
    }

    // EDITAR CONTACTO
    @PutMapping("/{id}")
    public ResponseEntity<Contacto> actualizar(
            @PathVariable Long id,
            @RequestBody Contacto datos) {

        return repository.findById(id)
                .map(contacto -> {

                    contacto.setNombre(datos.getNombre());
                    contacto.setApellido(datos.getApellido());
                    contacto.setEmail(datos.getEmail());
                    contacto.setTelefono(datos.getTelefono());
                    contacto.setPrograma(datos.getPrograma());
                    contacto.setEstado(datos.getEstado());

                    return ResponseEntity.ok(
                            repository.save(contacto)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ELIMINAR CONTACTO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}