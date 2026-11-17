package com.etitc.crm.webflux.controller;
import com.etitc.crm.webflux.modelo.RespuestaReactiva;
import com.etitc.crm.webflux.service.ReactivoService;
import com.etitc.crm.webflux.service.WebClientService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reactive")
public class ReactivoController {

    private final ReactivoService reactivoService;
    private final WebClientService webClientService;

    public ReactivoController(
            ReactivoService reactivoService,
            WebClientService webClientService) {

        this.reactivoService = reactivoService;
        this.webClientService = webClientService;
    }

    @GetMapping("/bienvenida/{usuario}")
    public Mono<RespuestaReactiva> bienvenida(
            @PathVariable String usuario) {

        return reactivoService.bienvenida(usuario);
    }

    @GetMapping(
            value = "/eventos",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<ServerSentEvent<String>> eventos() {

        return reactivoService.generarEventos()
                .map(evento -> ServerSentEvent.<String>builder()
                        .event("notificacion")
                        .data(evento)
                        .build()
                );
    }

    @GetMapping("/fuente")
    public Mono<RespuestaReactiva> fuente() {

        return Mono.just(
                new RespuestaReactiva(
                        "Respuesta recibida correctamente desde la fuente HTTP",
                        "WebClient",
                        java.time.LocalDateTime.now()
                )
        );
    }

    @GetMapping("/consultar-fuente")
    public Mono<RespuestaReactiva> consultarFuente() {

        return webClientService.consultarFuente();
    }
}
