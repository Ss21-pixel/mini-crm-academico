# Mini CRM Académico

## Descripción del proyecto

Mini CRM Académico es una aplicación web desarrollada con Spring Boot que permite gestionar contactos, oportunidades y comunicaciones dentro de un entorno académico.

El proyecto implementa operaciones CRUD (Crear, Consultar, Actualizar y Eliminar), utilizando Java, Spring Boot, Spring MVC, Spring Data JPA, MySQL y tecnologías web.

## Objetivo general

Desarrollar una aplicación web con una API REST que permita administrar la información de contactos, oportunidades y comunicaciones, aplicando estándares y buenas prácticas de desarrollo de software.

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.1.1
- Spring MVC
- Spring Data JPA
- Spring Validation
- MySQL
- HTML5
- CSS3
- JavaScript
- Thymeleaf
- Maven
- Git y GitHub
- Postman

## Funcionalidades principales

- Gestión de contactos.
- Gestión de oportunidades.
- Registro y consulta de comunicaciones.
- Consulta y búsqueda de registros.
- Edición y eliminación de información.
- Validación de datos.
- API REST para las operaciones CRUD.
- Manejo centralizado de excepciones y errores HTTP.

---

# Semana 13 - API CRUD con Spring

## Descripción

Durante la Semana 13 se implementó una API REST para administrar contactos mediante operaciones CRUD, utilizando Spring Boot y controladores REST.

La API permite intercambiar información en formato JSON y realizar solicitudes HTTP.

## URL base

```text
http://localhost:8080/api/contactos
```

## Endpoints implementados

| Método HTTP | Endpoint | Funcionalidad |
|---|---|---|
| GET | `/api/contactos` | Listar contactos |
| GET | `/api/contactos/{id}` | Consultar contacto por ID |
| POST | `/api/contactos` | Registrar contacto |
| PUT | `/api/contactos/{id}` | Actualizar contacto |
| DELETE | `/api/contactos/{id}` | Eliminar contacto |

## Ejemplo de registro de contacto

Método: POST

```json
{
  "nombre": "Carlos",
  "apellido": "Rodriguez",
  "email": "carlos.prueba13@gmail.com",
  "telefono": "3001234567",
  "programa": "Tecnología en Desarrollo de Software",
  "estado": "ACTIVO"
}
```

---

# Semana 14 - API REST con estándares y buenas prácticas

## Descripción

En la Semana 14 se continuó trabajando sobre la API REST desarrollada en la Semana 13, mejorando su organización, validaciones, respuestas HTTP y manejo de errores.

Se conservaron las funcionalidades existentes y se trabajó en los tres módulos principales del sistema:

- Contactos.
- Oportunidades.
- Comunicaciones.

## Objetivos de la Semana 14

- Aplicar buenas prácticas en el diseño de una API REST.
- Organizar los controladores y servicios.
- Utilizar correctamente los métodos HTTP.
- Implementar respuestas HTTP apropiadas.
- Validar los datos recibidos.
- Manejar errores mediante excepciones.
- Comprobar las operaciones utilizando Postman.

## Endpoints de la API REST

### 1. Contactos

URL base:

```text
http://localhost:8080/api/contactos
```

| Método HTTP | Endpoint | Descripción |
|---|---|---|
| GET | `/api/contactos` | Consultar contactos |
| GET | `/api/contactos/{id}` | Consultar contacto por ID |
| POST | `/api/contactos` | Crear contacto |
| PUT | `/api/contactos/{id}` | Actualizar contacto |
| DELETE | `/api/contactos/{id}` | Eliminar contacto |

### 2. Oportunidades

URL base:

```text
http://localhost:8080/api/oportunidades
```

| Método HTTP | Endpoint | Descripción |
|---|---|---|
| GET | `/api/oportunidades` | Consultar oportunidades |
| GET | `/api/oportunidades/{id}` | Consultar oportunidad por ID |
| POST | `/api/oportunidades` | Crear oportunidad |
| PUT | `/api/oportunidades/{id}` | Actualizar oportunidad |
| DELETE | `/api/oportunidades/{id}` | Eliminar oportunidad |

### 3. Comunicaciones

URL base:

```text
http://localhost:8080/api/comunicaciones
```

| Método HTTP | Endpoint | Descripción |
|---|---|---|
| GET | `/api/comunicaciones` | Consultar comunicaciones |
| GET | `/api/comunicaciones/{id}` | Consultar comunicación por ID |
| POST | `/api/comunicaciones` | Crear comunicación |
| PUT | `/api/comunicaciones/{id}` | Actualizar comunicación |
| DELETE | `/api/comunicaciones/{id}` | Eliminar comunicación |

## Métodos HTTP y códigos de respuesta

La API utiliza los siguientes códigos de estado HTTP:

| Código | Nombre | Descripción |
|---|---|---|
| 200 | OK | Operación realizada correctamente |
| 201 | Created | Registro creado correctamente |
| 204 | No Content | Registro eliminado correctamente |
| 400 | Bad Request | Datos inválidos o solicitud incorrecta |
| 404 | Not Found | Registro no encontrado |
| 409 | Conflict | Conflicto con datos relacionados |
| 500 | Internal Server Error | Error interno del servidor |

## Validaciones implementadas

Se incorporaron validaciones para garantizar la integridad de la información enviada a la API.

Entre ellas:

- Campos obligatorios.
- Validación del formato del correo electrónico.
- Restricciones de longitud en los campos.
- Validación de información recibida.
- Prevención de correos electrónicos duplicados en contactos.
- Control de registros inexistentes.
- Manejo de conflictos relacionados con registros asociados.

## Organización y buenas prácticas

Para mejorar la estructura del proyecto se utilizaron diferentes componentes:

- **Controladores REST:** reciben y procesan las solicitudes HTTP.
- **Servicios:** contienen la lógica de negocio.
- **DTO:** permiten organizar la información de entrada y salida de la API.
- **Validaciones:** verifican que los datos cumplan las condiciones establecidas.
- **GlobalExceptionHandler:** centraliza el manejo de excepciones y respuestas de error.
- **Repositorios:** permiten acceder a la información almacenada en la base de datos.

## Pruebas realizadas con Postman

Se utilizó Postman para comprobar el funcionamiento de los endpoints de los tres módulos.

Se realizaron las siguientes pruebas:

1. GET: consultar todos los registros.
2. GET por ID: consultar un registro específico.
3. POST: crear un nuevo registro.
4. PUT: actualizar un registro existente.
5. DELETE: eliminar un registro.
6. Consulta de registros inexistentes.
7. Envío de información inválida.
8. Validación de correos electrónicos duplicados.
9. Comprobación de conflictos por relaciones entre registros.

## Resultados de las pruebas

| Prueba | Resultado esperado | Estado |
|---|---|---|
| Consultar registros | 200 OK | Correcto |
| Crear registro | 201 Created | Correcto |
| Actualizar registro | 200 OK | Correcto |
| Eliminar registro | 204 No Content | Correcto |
| Consultar ID inexistente | 404 Not Found | Correcto |
| Enviar datos inválidos | 400 Bad Request | Correcto |
| Registrar correo duplicado | 400 Bad Request | Correcto |
| Eliminar registro relacionado | 409 Conflict | Correcto |

## Colección de Postman

Se creó una colección denominada:

**Semana 14 - API REST Mini CRM Académico**

Esta colección contiene las solicitudes necesarias para probar los endpoints de contactos, oportunidades y comunicaciones.

La colección se encuentra dentro del repositorio en:

```text
postman/collections/
```

## Cómo ejecutar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/Ss21-pixel/mini-crm-academico.git
```

### 2. Ingresar a la carpeta

```bash
cd mini-crm-academico
```

### 3. Configurar la conexión a MySQL

Revisar los datos de conexión en los archivos de configuración del proyecto.

### 4. Ejecutar la aplicación

En PowerShell utilizar:

```powershell
.\mvnw.cmd spring-boot:run
```

### 5. Abrir la aplicación

```text
http://localhost:8080/
```

### 6. Probar la API REST

Contactos:

```text
http://localhost:8080/api/contactos
```

Oportunidades:

```text
http://localhost:8080/api/oportunidades
```

Comunicaciones:

```text
http://localhost:8080/api/comunicaciones
```

## Repositorio GitHub

Proyecto académico individual desarrollado como parte de la asignatura Programación IV.

Repositorio:

https://github.com/Ss21-pixel/mini-crm-academico

## Conclusión

Durante las semanas 13 y 14 se desarrolló y mejoró una API REST para el sistema Mini CRM Académico. Se implementaron operaciones CRUD para contactos, oportunidades y comunicaciones, aplicando métodos HTTP, códigos de respuesta, validaciones y manejo centralizado de excepciones.

Las pruebas realizadas con Postman permitieron verificar el funcionamiento de los endpoints y comprobar las respuestas de la API ante solicitudes correctas e incorrectas.

El proyecto permite aplicar los conocimientos de Spring Boot, arquitectura MVC, persistencia de datos, desarrollo de servicios REST y buenas prácticas de programación.
