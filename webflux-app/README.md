# Semana 16 - Implementación de Spring WebFlux

## 1. Descripción del proyecto

Este módulo implementa una API REST reactiva utilizando Spring WebFlux como complemento del proyecto Mini CRM Académico.

El objetivo es aplicar programación reactiva mediante Mono y Flux, transmisión de eventos en tiempo real mediante Server-Sent Events (SSE) y consumo de servicios HTTP mediante WebClient.

El módulo es independiente de la aplicación Spring MVC original y utiliza el puerto 8081.

## 2. Tecnologías utilizadas

- Java 21.
- Spring Boot 4.1.1.
- Spring WebFlux.
- Project Reactor.
- Mono y Flux.
- WebClient.
- Server-Sent Events (SSE).
- Maven.

## 3. Arquitectura del módulo

La organización del código es la siguiente:

```text
webflux-app/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/etitc/crm/webflux/
    │   │   ├── MiniCrmWebFluxApplication.java
    │   │   ├── config/
    │   │   │   └── WebClientConfig.java
    │   │   ├── controller/
    │   │   │   └── ReactivoController.java
    │   │   ├── modelo/
    │   │   │   └── RespuestaReactiva.java
    │   │   └── service/
    │   │       ├── ReactivoService.java
    │   │       └── WebClientService.java
    │   └── resources/
    │       └── application.properties
    └── test/
```

## 4. Endpoints disponibles

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/v1/reactive/bienvenida/{usuario}` | Devuelve una respuesta reactiva Mono. |
| GET | `/api/v1/reactive/eventos` | Emite cinco eventos mediante Flux y SSE. |
| GET | `/api/v1/reactive/fuente` | Simula un servicio HTTP de origen. |
| GET | `/api/v1/reactive/consultar-fuente` | Consume el servicio mediante WebClient. |

## 5. Programación reactiva

### Mono

Se utiliza Mono para representar una operación que produce cero o un resultado.

### Flux

Se utiliza Flux para representar una secuencia de datos. En este proyecto genera cinco eventos progresivos mediante `Flux.interval()`, `map()` y `take()`.

### Server-Sent Events

El endpoint `/api/v1/reactive/eventos` utiliza el formato `text/event-stream` para transmitir eventos al cliente progresivamente.

### WebClient

WebClient permite consumir otro endpoint HTTP de forma reactiva.

Se implementan los siguientes métodos:

- `retrieve()`: obtiene la respuesta HTTP.
- `bodyToMono()`: convierte el cuerpo de la respuesta en Mono.
- `timeout()`: establece un tiempo máximo de espera.
- `onErrorResume()`: proporciona una respuesta alternativa cuando ocurre un error.

## 6. Configuración

Archivo `application.properties`:

```properties
spring.application.name=mini-crm-webflux
server.port=8081
webflux.fuente.base-url=http://localhost:8081
webflux.cliente.timeout=3s
```

La dirección de la fuente HTTP es configurable.

## 7. Ejecución del proyecto

Desde la carpeta raíz del repositorio ejecutar:

```powershell
.\mvnw.cmd -f .\webflux-app\pom.xml spring-boot:run
```

La aplicación estará disponible en:

`http://localhost:8081`

## 8. Pruebas realizadas

Se verificaron los siguientes endpoints:

1. Bienvenida reactiva mediante Mono.
2. Fuente HTTP.
3. Consumo de la fuente mediante WebClient.
4. Emisión de cinco eventos progresivos mediante Flux y SSE.
5. Respuesta alternativa cuando la fuente HTTP no está disponible.

Para probar los eventos desde PowerShell:

```powershell
curl.exe -N http://localhost:8081/api/v1/reactive/eventos
```

## 9. Manejo de errores

Se comprobó el funcionamiento de `onErrorResume()` modificando temporalmente la dirección de la fuente HTTP a:

`http://localhost:9999`

Al consultar `/api/v1/reactive/consultar-fuente`, el sistema devolvió una respuesta alternativa indicando que la fuente no estaba disponible.

Posteriormente se restauró la configuración original.

## 10. Diferencias entre Spring MVC y Spring WebFlux

| Característica | Spring MVC | Spring WebFlux |
|---|---|---|
| Modelo | Imperativo | Reactivo |
| Procesamiento | Generalmente basado en bloqueo | No bloqueante |
| Servidor habitual | Tomcat | Reactor Netty |
| Manejo de datos | Objetos tradicionales | Mono y Flux |
| Comunicación | HTTP tradicional | HTTP y flujos reactivos |
| Cliente HTTP | RestClient o RestTemplate | WebClient |

## 11. Conclusión

La implementación permitió integrar un módulo independiente de Spring WebFlux al Mini CRM Académico, aplicando programación reactiva, transmisión de eventos SSE, consumo HTTP mediante WebClient y manejo de errores.

Se mantuvo la aplicación original Spring MVC sin reemplazar sus funcionalidades ni su CRUD.