package com.etitc.crm.service;

import com.etitc.crm.entity.Comunicacion;
import com.etitc.crm.repository.ComunicacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComunicacionService {

    private final ComunicacionRepository comunicacionRepository;

    public ComunicacionService(ComunicacionRepository comunicacionRepository) {
        this.comunicacionRepository = comunicacionRepository;
    }

    public List<Comunicacion> listarTodos() {
        return comunicacionRepository.findAll();
    }

    public Optional<Comunicacion> buscarPorId(Long id) {
        return comunicacionRepository.findById(id);
    }

    public Comunicacion guardar(Comunicacion comunicacion) {
        return comunicacionRepository.save(comunicacion);
    }

    public void eliminar(Long id) {
        comunicacionRepository.deleteById(id);
    }
}