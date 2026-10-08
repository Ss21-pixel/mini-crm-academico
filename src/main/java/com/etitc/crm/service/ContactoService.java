package com.etitc.crm.service;

import com.etitc.crm.modelo.Contacto;
import com.etitc.crm.repository.ContactoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    // ==============================
    // LISTAR TODOS
    // ==============================

    public List<Contacto> listarTodos() {
        return contactoRepository.findAll();
    }

    // ==============================
    // BUSCAR POR ID
    // ==============================

    public Optional<Contacto> buscarPorId(Long id) {
        return contactoRepository.findById(id);
    }

    // ==============================
    // BUSCAR + PAGINAR
    // ==============================

    public Page<Contacto> buscar(String q, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (q == null || q.trim().isEmpty()) {
            return contactoRepository.findAll(pageable);
        }

        return contactoRepository
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        q,
                        q,
                        q,
                        pageable
                );
    }

    // ==============================
    // VERIFICAR EMAIL DUPLICADO
    // ==============================

    public boolean emailDuplicado(Contacto contacto) {

        Optional<Contacto> contactoExistente =
                contactoRepository.findByEmail(contacto.getEmail());

        if (contactoExistente.isEmpty()) {
            return false;
        }

        // Si estamos editando, permitir que conserve su propio email
        if (contacto.getId() != null &&
                contactoExistente.get().getId().equals(contacto.getId())) {
            return false;
        }

        return true;
    }

    // ==============================
    // GUARDAR / ACTUALIZAR
    // ==============================

    public Contacto guardar(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    // ==============================
    // ELIMINAR
    // ==============================

    public void eliminar(Long id) {
        contactoRepository.deleteById(id);
    }
}
