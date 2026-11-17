package com.etitc.crm.webflux.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.etitc.crm.webflux.modelo.RespuestaReactiva;

import reactor.test.StepVerifier;

class ReactivoServiceTest {

    private final ReactivoService reactivoService = new ReactivoService();

    @Test
    void debeGenerarBienvenidaReactiva() {

        StepVerifier.create(reactivoService.bienvenida("Stiven"))
                .assertNext(respuesta -> {
                    assertEquals("Stiven", respuesta.usuario());
                    assertEquals(
                            "Bienvenido a la API reactiva del Mini CRM Academico",
                            respuesta.mensaje()
                    );
                })
                .verifyComplete();
    }

    @Test
    void debeGenerarCincoEventosProgresivos() {

        StepVerifier.create(reactivoService.generarEventos())
                .expectNext("Evento reactivo numero 1")
                .expectNext("Evento reactivo numero 2")
                .expectNext("Evento reactivo numero 3")
                .expectNext("Evento reactivo numero 4")
                .expectNext("Evento reactivo numero 5")
                .verifyComplete();
    }
}
