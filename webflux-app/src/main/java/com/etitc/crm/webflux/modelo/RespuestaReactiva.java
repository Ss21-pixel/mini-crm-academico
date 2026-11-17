
package com.etitc.crm.webflux.modelo;

import java.time.LocalDateTime;

public record RespuestaReactiva(
        String mensaje,
        String usuario,
        LocalDateTime fecha
) {
}
