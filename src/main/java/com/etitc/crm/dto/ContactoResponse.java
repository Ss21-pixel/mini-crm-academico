
package com.etitc.crm.dto;

import com.etitc.crm.modelo.Contacto;

public record ContactoResponse(
        Long id,
        String nombre,
        String apellido,
        String email,
        String telefono,
        String programa,
        String estado
) {

    public static ContactoResponse desde(Contacto contacto) {
        return new ContactoResponse(
                contacto.getId(),
                contacto.getNombre(),
                contacto.getApellido(),
                contacto.getEmail(),
                contacto.getTelefono(),
                contacto.getPrograma(),
                contacto.getEstado()
        );
    }
}
