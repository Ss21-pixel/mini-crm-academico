package com.etitc.crm.webflux.service;
import com.etitc.crm.webflux.modelo.RespuestaReactiva;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactivoService {

    public Mono<RespuestaReactiva> bienvenida(String usuario) {

        return Mono.fromSupplier(() ->
                new RespuestaReactiva(
                        "Bienvenido a la API reactiva del Mini CRM Academico",
                        usuario,
                        LocalDateTime.now()
                )
        );
    }

    public Flux<String> generarEventos() {

        return Flux.interval(Duration.ofSeconds(1))
                .map(numero -> "Evento reactivo numero " + (numero + 1))
                .take(5);
    }
}
