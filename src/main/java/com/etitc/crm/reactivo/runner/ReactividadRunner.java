
package com.etitc.crm.reactivo.runner;

import com.etitc.crm.reactivo.modelo.ContactoReactivo;
import com.etitc.crm.reactivo.service.ContactoReactivoService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import org.reactivestreams.Subscription;

@Component
public class ReactividadRunner implements CommandLineRunner {

    private final ContactoReactivoService servicio;

    public ReactividadRunner(ContactoReactivoService servicio) {
        this.servicio = servicio;
    }

    @Override
    public void run(String... args) {

        System.out.println("\n==========================================");
        System.out.println(" DEMOSTRACION REACTIVA - SEMANA 15");
        System.out.println(" PROYECTO MINI CRM ACADEMICO");
        System.out.println("==========================================");

        demostrarFlux();
        demostrarMono();
        demostrarError();
        demostrarControlDemanda();

        System.out.println("\n==========================================");
        System.out.println(" FIN DE LAS PRUEBAS REACTIVAS");
        System.out.println("==========================================");
    }

    // 1. FLUX: procesamiento de contactos activos.
    private void demostrarFlux() {

        System.out.println("\n--- 1. FLUX Y OPERADORES ---");

        servicio.obtenerResumenActivos()
                .subscribe(

                    // onNext: recibe cada registro.
                    resumen -> System.out.println(
                        "[onNext] Contacto procesado: " + resumen
                    ),

                    // onError: recibe errores.
                    error -> System.out.println(
                        "[onError] Error: " + error.getMessage()
                    ),

                    // onComplete: finaliza el flujo.
                    () -> System.out.println(
                        "[onComplete] Procesamiento finalizado correctamente."
                    )
                );
    }

    // 2. MONO: resultado unico y conteo.
    private void demostrarMono() {

        System.out.println("\n--- 2. MONO Y COUNT ---");

        // Obtener un solo contacto activo.
        servicio.obtenerPrimerActivo()
                .subscribe(
                    contacto -> System.out.println(
                        "[Mono] Primer contacto activo: "
                        + contacto.getNombre()
                    ),
                    error -> System.out.println(
                        "[Mono Error] " + error.getMessage()
                    ),
                    () -> System.out.println(
                        "[Mono] Consulta finalizada."
                    )
                );

        // Contar todos los contactos activos.
        servicio.contarActivos()
                .subscribe(total -> System.out.println(
                    "[Mono + count] Total de contactos activos: " + total
                ));

        // Caso sin registros activos.
        servicio.contarActivosPorPrograma("Programa inexistente")
                .subscribe(total -> {

                    System.out.println(
                        "[Caso sin registros] Total encontrado: " + total
                    );

                    if (total == 0) {
                        System.out.println(
                            "No existen contactos activos para ese programa."
                        );
                    }
                });
    }

    // 3. DEMOSTRACION DEL ERROR.
    private void demostrarError() {

        System.out.println("\n--- 3. MANEJO DE ONERROR ---");

        Flux.<String>error(
            new IllegalStateException(
                "Error simulado para demostrar el manejo de errores."
            )
        ).subscribe(

            dato -> System.out.println("[onNext] " + dato),

            error -> System.out.println(
                "[onError] Error controlado: " + error.getMessage()
            ),

            () -> System.out.println(
                "[onComplete] Flujo finalizado."
            )
        );
    }

    // 4. CONTROL DE DEMANDA CON BASESUBSCRIBER.
    private void demostrarControlDemanda() {

        System.out.println("\n--- 4. CONTROL DE DEMANDA ---");

        servicio.flujoParaDemanda()
                .subscribe(new BaseSubscriber<ContactoReactivo>() {

                    private int recibidos = 0;

                    // Se ejecuta al iniciar la suscripcion.
                    @Override
                    protected void hookOnSubscribe(
                            Subscription subscription) {

                        System.out.println(
                            "[BaseSubscriber] Suscripcion iniciada."
                        );

                        System.out.println(
                            "[request(1)] Solicitando un elemento."
                        );

                        request(1);
                    }

                    // Recibir cada elemento individualmente.
                    @Override
                    protected void hookOnNext(ContactoReactivo contacto) {

                        recibidos++;

                        System.out.println(
                            "[Elemento " + recibidos + "] "
                            + contacto.getNombre()
                            + " - " + contacto.getEstado()
                        );

                        if (recibidos == 2) {

                            System.out.println(
                                "[cancel()] Cancelando la suscripcion."
                            );

                            cancel();

                        } else {

                            System.out.println(
                                "[request(1)] Solicitando el siguiente elemento."
                            );

                            request(1);
                        }
                    }

                    @Override
                    protected void hookOnCancel() {

                        System.out.println(
                            "[CANCELADO] No se recibiran mas contactos."
                        );

                        System.out.println(
                            "[RESULTADO] Elementos recibidos: " + recibidos
                        );
                    }

                    @Override
                    protected void hookOnComplete() {

                        System.out.println(
                            "[BaseSubscriber] Flujo completado."
                        );
                    }

                    @Override
                    protected void hookOnError(Throwable error) {

                        System.out.println(
                            "[BaseSubscriber] Error: " + error.getMessage()
                        );
                    }
                });
    }
}
