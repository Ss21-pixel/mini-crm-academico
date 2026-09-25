package com.etitc.crm.service;

import com.etitc.crm.entity.Oportunidad;
import com.etitc.crm.repository.OportunidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OportunidadService {

    private final OportunidadRepository oportunidadRepository;

    public OportunidadService(OportunidadRepository oportunidadRepository) {
        this.oportunidadRepository = oportunidadRepository;
    }

    public List<Oportunidad> listarTodos() {
        return oportunidadRepository.findAll();
    }

    public Optional<Oportunidad> buscarPorId(Long id) {
        return oportunidadRepository.findById(id);
    }

    public Oportunidad guardar(Oportunidad oportunidad) {
        return oportunidadRepository.save(oportunidad);
    }

    public void eliminar(Long id) {
        oportunidadRepository.deleteById(id);
    }
}