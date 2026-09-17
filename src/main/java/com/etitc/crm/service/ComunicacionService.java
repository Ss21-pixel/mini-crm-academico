package com.etitc.crm.service;

import com.etitc.crm.entity.Comunicacion;
import com.etitc.crm.memory.MemoriaCRM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComunicacionService {

    private final MemoriaCRM memoriaCRM;

    public ComunicacionService(MemoriaCRM memoriaCRM) {
        this.memoriaCRM = memoriaCRM;
    }

    public List<Comunicacion> listarTodos() {
        return memoriaCRM.listarComunicaciones();
    }

    public Optional<Comunicacion> buscarPorId(Long id) {
        return memoriaCRM.buscarComunicacion(id);
    }

    public Comunicacion guardar(Comunicacion comunicacion) {
        return memoriaCRM.guardarComunicacion(comunicacion);
    }

    public void eliminar(Long id) {
        memoriaCRM.eliminarComunicacion(id);
    }
}