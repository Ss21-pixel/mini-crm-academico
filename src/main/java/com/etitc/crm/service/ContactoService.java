package com.etitc.crm.service;

import com.etitc.crm.entity.Contacto;
import com.etitc.crm.memory.MemoriaCRM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactoService {

    private final MemoriaCRM memoriaCRM;

    public ContactoService(MemoriaCRM memoriaCRM) {
        this.memoriaCRM = memoriaCRM;
    }

    public List<Contacto> listarTodos() {
        return memoriaCRM.listarContactos();
    }

    public Optional<Contacto> buscarPorId(Long id) {
        return memoriaCRM.buscarContacto(id);
    }

    public Contacto guardar(Contacto contacto) {
        return memoriaCRM.guardarContacto(contacto);
    }

    public void eliminar(Long id) {
        memoriaCRM.eliminarContacto(id);
    }
}