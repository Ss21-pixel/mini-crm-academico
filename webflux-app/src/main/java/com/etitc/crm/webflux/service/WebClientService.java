package com.etitc.crm.webflux.service;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.etitc.crm.webflux.modelo.RespuestaReactiva;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

    private final WebClient webClient;
    private final Duration timeout;

    public WebClientService(
            WebClient webClient,
            Duration timeoutCliente) {

        this.webClient = webClient;
        this.timeout = timeoutCliente;
    }

    public Mono<RespuestaReactiva> consultarFuente() {

        return webClient.get()
                .uri("/api/v1/reactive/fuente")
                .retrieve()
                .bodyToMono(RespuestaReactiva.class)
                .timeout(timeout)
                .onErrorResume(error ->
                        Mono.just(
                                new RespuestaReactiva(
                                        "La fuente de datos no esta disponible. Se devuelve una respuesta alternativa.",
                                        "Sistema",
                                        LocalDateTime.now()
                                )
                        )
                );
    }
}

