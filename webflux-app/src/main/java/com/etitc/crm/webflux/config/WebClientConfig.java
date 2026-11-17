package com.etitc.crm.webflux.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(
            @Value("${webflux.fuente.base-url}") String baseUrl) {

        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public Duration timeoutCliente(
            @Value("${webflux.cliente.timeout}") Duration timeout) {

        return timeout;
    }
}
