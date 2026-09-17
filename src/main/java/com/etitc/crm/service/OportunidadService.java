package com.etitc.crm.service;

import com.etitc.crm.entity.Oportunidad;
import com.etitc.crm.memory.MemoriaCRM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OportunidadService {

    private final MemoriaCRM memoriaCRM;

    public OportunidadService(MemoriaCRM memoriaCRM) {
        this.memoriaCRM = memoriaCRM;
    }

    public List<Oportunidad> listarTodos() {
        return memoriaCRM.listarOportunidades();
    }

    public Optional<Oportunidad> buscarPorId(Long id) {
        return memoriaCRM.buscarOportunidad(id);
    }

    public Oportunidad guardar(Oportunidad oportunidad) {
        return memoriaCRM.guardarOportunidad(oportunidad);
    }

    public void eliminar(Long id) {
        memoriaCRM.eliminarOportunidad(id);
    }
}