package com.etitc.crm.service;

import com.etitc.crm.dto.ContactoRequest;
import com.etitc.crm.modelo.Contacto;
import com.etitc.crm.repository.ContactoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }


// LISTAR TODOS

    public List<Contacto> listarTodos() {
        return contactoRepository.findAll();
    }


// BUSCAR POR ID

    public Optional<Contacto> buscarPorId(Long id) {
        return contactoRepository.findById(id);
    }

    // OBTENER CONTACTO O GENERAR 404
    public Contacto obtenerPorId(Long id) {
        return contactoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el contacto con ID: " + id
                ));
    }

// PAGINACIÓN Y BÚSQUEDA
    public Page<Contacto> buscar(String q, int page, int size) {

        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La página debe ser positiva y el tamaño debe estar entre 1 y 100"
            );
        }

        Pageable pageable = PageRequest.of(page, size);

        if (q == null || q.trim().isEmpty()) {
            return contactoRepository.findAll(pageable);
        }

        return contactoRepository
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        q.trim(), q.trim(), q.trim(), pageable
                );
    }

// VALIDAR EMAIL DUPLICADO
    public boolean emailDuplicado(Contacto contacto) {

        Optional<Contacto> existente =
                contactoRepository.findByEmail(contacto.getEmail());

        if (existente.isEmpty()) {
            return false;
        }

        return contacto.getId() == null ||
                !existente.get().getId().equals(contacto.getId());
    }

// CREAR CONTACTO DESDE LA API
    public Contacto crearDesdeApi(ContactoRequest request) {

        Contacto contacto = new Contacto();

        copiarDatos(request, contacto);

        if (emailDuplicado(contacto)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El correo electrónico ya está registrado"
            );
        }

        return contactoRepository.save(contacto);
    }

// ACTUALIZAR CONTACTO DESDE LA API
    public Contacto actualizarDesdeApi(Long id, ContactoRequest request) {

        Contacto existente = obtenerPorId(id);

        copiarDatos(request, existente);

        if (emailDuplicado(existente)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El correo electrónico ya está registrado"
            );
        }

        return contactoRepository.save(existente);
    }

// COPIAR DATOS DEL DTO A LA ENTIDAD
    private void copiarDatos(
            ContactoRequest request,
            Contacto contacto
    ) {
        contacto.setNombre(request.nombre());
        contacto.setApellido(request.apellido());
        contacto.setEmail(request.email());
        contacto.setTelefono(request.telefono());
        contacto.setPrograma(request.programa());
        contacto.setEstado(request.estado());
    }

// GUARDAR PARA LAS VISTAS WEB EXISTENTES
    public Contacto guardar(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

// ELIMINAR

    public void eliminar(Long id) {
        contactoRepository.deleteById(id);
    }
}
