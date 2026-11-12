package com.etitc.crm.reactivo.service;

import com.etitc.crm.reactivo.modelo.ContactoReactivo;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ContactoReactivoService {

    // Cuatro contactos de ejemplo del CRM académico.
    // Tres están activos y uno está inactivo.
    private final List<ContactoReactivo> contactos = List.of(

        new ContactoReactivo(
            1L,
            "Ana Torres",
            "Tecnología en Desarrollo de Software",
            "ACTIVO"
        ),

        new ContactoReactivo(
            2L,
            "Carlos Ruiz",
            "Tecnología en Desarrollo de Software",
            "ACTIVO"
        ),

        new ContactoReactivo(
            3L,
            "Laura Gómez",
            "Tecnología en Gestión de Redes",
            "INACTIVO"
        ),

        new ContactoReactivo(
            4L,
            "Miguel Pérez",
            "Tecnología en Desarrollo de Software",
            "ACTIVO"
        )
    );

    // 1. FLUX: emitir todos los contactos.
    public Flux<ContactoReactivo> obtenerTodos() {
        return Flux.fromIterable(contactos);
    }

    // 2. FILTER: seleccionar solamente los contactos activos.
    public Flux<ContactoReactivo> obtenerActivos() {
        return obtenerTodos()
                .filter(ContactoReactivo::isActivo);
    }

    // 3. MAP: transformar los contactos en información textual.
    public Flux<String> obtenerResumenActivos() {
        return obtenerActivos()
                .map(contacto ->
                    "Nombre: " + contacto.getNombre()
                    + " | Programa: " + contacto.getPrograma()
                    + " | Estado: " + contacto.getEstado()
                );
    }

    // 4. MONO: obtener un único contacto activo.
    public Mono<ContactoReactivo> obtenerPrimerActivo() {
        return obtenerActivos().next();
    }

    // 5. COUNT: contar los contactos activos.
    // El resultado se entrega mediante Mono<Long>.
    public Mono<Long> contarActivos() {
        return obtenerActivos().count();
    }

    // 6. Caso sin resultados:
    // Buscar contactos activos de un programa inexistente.
    public Mono<Long> contarActivosPorPrograma(String programa) {
        return obtenerActivos()
                .filter(contacto ->
                    contacto.getPrograma().equalsIgnoreCase(programa)
                )
                .count();
    }

    // 7. Flux para demostrar BaseSubscriber y la demanda.
    public Flux<ContactoReactivo> flujoParaDemanda() {
        return obtenerTodos();
    }
}
