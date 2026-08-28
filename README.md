# Mini CRM Académico

Aplicación web desarrollada con **Spring Boot** para la gestión de contactos, oportunidades y comunicaciones dentro de un proyecto de CRM académico.

El proyecto utiliza una arquitectura organizada por capas, integrando **Controllers, Services, Repositories y Entities**, además de una interfaz web con **Thymeleaf** y una API REST consumida desde JavaScript.

## Tecnologías utilizadas

* Java
* Spring Boot 4.1.0
* Maven
* Spring MVC / Web
* Spring Data JPA
* Thymeleaf
* Spring Boot DevTools
* Spring Boot Validation
* MySQL
* HTML
* CSS
* JavaScript
* Git y GitHub

## Arquitectura del proyecto

El proyecto está organizado siguiendo una separación de responsabilidades:

```text
Cliente web
    │
    ├── Interfaz HTML + CSS + JavaScript
    │
    ↓
Controller
    │
    ↓
Service
    │
    ↓
Repository
    │
    ↓
Entity
    │
    ↓
MySQL
```

También se utiliza un controlador MVC para la vista dinámica con Thymeleaf:

```text
Navegador
    ↓
ContactoController
    ↓
ContactoService
    ↓
ContactoRepository
    ↓
Contacto
    ↓
contactos.html
```

## Estructura principal

```text
mini-crm-academico/
│
├── .mvn/
│   └── wrapper/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── etitc/
│   │   │           └── crm/
│   │   │               ├── MiniCrmAcademicoApplication.java
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   ├── ComunicacionController.java
│   │   │               │   ├── ContactoController.java
│   │   │               │   ├── ContactoRestController.java
│   │   │               │   ├── OportunidadController.java
│   │   │               │   └── SaludoController.java
│   │   │               │
│   │   │               ├── entity/
│   │   │               │   ├── Comunicacion.java
│   │   │               │   ├── Contacto.java
│   │   │               │   └── Oportunidad.java
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   ├── ComunicacionRepository.java
│   │   │               │   ├── ContactoRepository.java
│   │   │               │   └── OportunidadRepository.java
│   │   │               │
│   │   │               └── service/
│   │   │                   ├── ComunicacionService.java
│   │   │                   ├── ContactoService.java
│   │   │                   └── OportunidadService.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   └── index.html
│   │       │
│   │       ├── templates/
│   │       │   └── contactos.html
│   │       │
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── etitc/
│                   └── crm/
│                       └── MiniCrmAcademicoApplicationTests.java
│
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Entidades del proyecto

El proyecto cuenta actualmente con las siguientes entidades:

### Contacto

La entidad `Contacto` contiene:

* `id`
* `nombre`
* `apellido`
* `email`
* `telefono`
* `programa`
* `estado`

Está mapeada a la tabla `contactos` de MySQL.

### Oportunidad

El proyecto también contiene la entidad `Oportunidad`, utilizada para representar oportunidades relacionadas con los contactos.

### Comunicación

La entidad `Comunicacion` representa las comunicaciones asociadas al proyecto.

## Servicios

Actualmente existen los siguientes servicios:

* `ContactoService`
* `OportunidadService`
* `ComunicacionService`

Estos servicios utilizan los respectivos repositories para trabajar con la información almacenada.

## Repositories

El proyecto utiliza Spring Data JPA con:

* `ContactoRepository`
* `OportunidadRepository`
* `ComunicacionRepository`

Estos repositories permiten trabajar con la persistencia de las entidades.

## Interfaz web

El archivo principal de la interfaz se encuentra en:

```text
src/main/resources/static/index.html
```

La página utiliza:

* HTML
* CSS
* JavaScript
* `fetch()`

El JavaScript realiza solicitudes a la API REST mediante rutas bajo `/api`.

Entre las solicitudes implementadas actualmente se encuentran:

```text
/api/contactos
/api/oportunidades
/api/comunicaciones
```

También existen operaciones de creación, actualización, consulta y eliminación mediante las solicitudes HTTP correspondientes que están implementadas en el JavaScript del proyecto.

## Thymeleaf

Para la implementación de Spring MVC se creó la plantilla:

```text
src/main/resources/templates/contactos.html
```

Esta vista utiliza Thymeleaf para mostrar dinámicamente los contactos enviados desde `ContactoController`.

Entre las expresiones utilizadas se encuentran:

```html
th:each
```

y:

```html
th:text
```

La ruta correspondiente es:

```text
/contactos
```

## API REST

El proyecto cuenta con un controlador REST para contactos:

```text
ContactoRestController.java
```

La ruta base es:

```text
/api/contactos
```

También existe:

```text
SaludoController.java
```

con la ruta:

```text
/api/saludo
```

La respuesta actual de esta ruta es:

```text
Mini CRM Académico - Spring Boot funcionando
```

## Rutas principales

Con la aplicación ejecutándose en el puerto 8080 se pueden probar las siguientes rutas:

### Página principal

```text
http://localhost:8080/
```

### Vista MVC de contactos

```text
http://localhost:8080/contactos
```

### API de saludo

```text
http://localhost:8080/api/saludo
```

### API de contactos

```text
http://localhost:8080/api/contactos
```

## Configuración

La configuración principal se encuentra en:

```text
src/main/resources/application.properties
```

Actualmente incluye:

```properties
spring.application.name=mini-crm-academico
server.port=8080
spring.profiles.active=dev
logging.level.com.etitc.crm=DEBUG
```

También contiene la configuración de conexión con MySQL, JPA y Thymeleaf.

## Base de datos

El proyecto utiliza MySQL con la base de datos:

```text
mini_crm_academico
```

La aplicación se conecta actualmente mediante:

```text
localhost:3306
```

Spring Data JPA se utiliza para la persistencia de las entidades.

## Maven

El proyecto utiliza Maven y cuenta con Maven Wrapper:

```text
mvnw
mvnw.cmd
.mvn/
```

Para compilar el proyecto:

```powershell
.\mvnw.cmd clean package
```

Para ejecutar la aplicación:

```powershell
.\mvnw.cmd spring-boot:run
```

## Verificación del proyecto

La aplicación fue comprobada ejecutando:

```powershell
.\mvnw.cmd clean package
```

El proceso finalizó correctamente con:

```text
BUILD SUCCESS
```

También se verificó el inicio de Spring Boot en el puerto:

```text
8080
```

La aplicación fue ejecutada con Java:

```text
Java 21.0.2
```

mientras que el proyecto está configurado para compilar con:

```text
Java 17
```

También se verificó la conexión con MySQL y la detección de los repositories de Spring Data JPA.

## Control de versiones

El proyecto utiliza Git y está publicado en GitHub.

Repositorio:

```text
https://github.com/Ss21-pixel/mini-crm-academico
```

## Avances registrados

El repositorio contiene commits relacionados con los avances y recuperación de evidencias de las primeras semanas del proyecto.

Entre ellos:

```text
feat: proyecto inicial mini crm academico
feat: implementacion spring mvc y thymeleaf semana 4
feat: recuperar evidencia semana 1 spring boot y api rest
feat: recuperar evidencia semana 2 spring boot
feat: recuperar evidencia semana 3 configuracion spring boot
```

## Estado actual

Actualmente el proyecto cuenta con:

* Spring Boot configurado y ejecutable.
* Maven y Maven Wrapper.
* Spring MVC.
* Spring Data JPA.
* MySQL.
* Entities.
* Repositories.
* Services.
* Controllers.
* API REST.
* Interfaz web estática con HTML, CSS y JavaScript.
* Vista dinámica con Thymeleaf.
* Configuración mediante `application.properties`.
* Repositorio Git y GitHub.

## Objetivo del proyecto

El proyecto busca servir como una base de CRM académico para gestionar información relacionada con:

```text
Contactos
Oportunidades
Comunicaciones
```

La aplicación se encuentra en desarrollo acumulativo y continuará incorporando funcionalidades durante las siguientes etapas del proyecto.
