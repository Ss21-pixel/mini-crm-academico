
package com.etitc.crm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactoRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
        String apellido,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "Ingrese un correo electrónico válido")
        String email,

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 caracteres")
        String telefono,

        @NotBlank(message = "El programa es obligatorio")
        String programa,

        @NotBlank(message = "El estado es obligatorio")
        String estado

) {
}
