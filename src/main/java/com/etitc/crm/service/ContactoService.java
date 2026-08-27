package com.etitc.crm.service;

import com.etitc.crm.entity.Contacto;
import com.etitc.crm.repository.ContactoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    public List<Contacto> listarTodos() {
        return contactoRepository.findAll();
    }

    public Optional<Contacto> buscarPorId(Long id) {
        return contactoRepository.findById(id);
    }

    public Contacto guardar(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    public void eliminar(Long id) {
        contactoRepository.deleteById(id);
    }
}