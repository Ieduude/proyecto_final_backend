# Sistema de Alquiler de Herramientas 🔧

## Descripción

Este proyecto consiste en una API REST desarrollada con Spring Boot y PostgreSQL para administrar el alquiler de herramientas. Permite registrar herramientas, consultar su disponibilidad, gestionar alquileres y devoluciones, calcular costos y consultar los alquileres atrasados.

También incluye DTO, validaciones y manejo de excepciones para controlar los datos recibidos y devolver respuestas HTTP adecuadas.

## Tecnologías utilizadas

* Java 25
* Spring Boot
* Spring Data JPA
* PostgreSQL 18
* Maven
* Postman
* IntelliJ IDEA

## Funcionalidades

* Registrar, consultar, actualizar y eliminar herramientas.
* Consultar las herramientas disponibles.
* Registrar alquileres y calcular su costo estimado.
* Consultar alquileres activos y atrasados.
* Registrar la devolución de herramientas.
* Validar los datos mediante DTO.
* Manejar excepciones y responder con códigos HTTP como `400`, `404` y `409`.

## Endpoints principales

### Herramientas

| Método | Endpoint                    | Descripción                        |
| ------ | --------------------------- | ---------------------------------- |
| GET    | `/herramientas`             | Listar todas las herramientas      |
| GET    | `/herramientas/disponibles` | Consultar herramientas disponibles |
| GET    | `/herramientas/{id}`        | Buscar una herramienta por ID      |
| POST   | `/herramientas`             | Registrar una herramienta          |
| PUT    | `/herramientas/{id}`        | Actualizar una herramienta         |
| DELETE | `/herramientas/{id}`        | Eliminar una herramienta           |

### Alquileres

| Método | Endpoint                      | Descripción                                        |
| ------ | ----------------------------- | -------------------------------------------------- |
| GET    | `/alquileres`                 | Listar todos los alquileres                        |
| GET    | `/alquileres/{id}`            | Consultar un alquiler por ID                       |
| GET    | `/alquileres/activos`         | Consultar alquileres activos                       |
| GET    | `/alquileres/atrasados`       | Consultar alquileres atrasados                     |
| POST   | `/alquileres`                 | Registrar un alquiler                              |
| PUT    | `/alquileres/{id}/devolucion` | Registrar la devolución                            |
| DELETE | `/alquileres/{id}`            | Eliminar un alquiler cuando las reglas lo permiten |

## Configuración

1. Clonar o descargar el repositorio.
2. Abrir el proyecto en IntelliJ IDEA.
3. Crear la base de datos `alquiler_herramientas` en PostgreSQL.
4. Configurar las credenciales de PostgreSQL en `application.properties`.
5. Ejecutar la clase principal `AlquilerHerramientasApplication`.
6. Probar los endpoints desde Postman usando `http://localhost:8080`.

## Estructura del proyecto

* `controller`: recibe las solicitudes HTTP.
* `dto`: contiene los objetos utilizados para recibir los datos de las herramientas.
* `entity`: contiene las entidades del sistema.
* `repository`: permite interactuar con la base de datos.
* `service`: contiene la lógica del sistema.
* `exception`: contiene las excepciones personalizadas y el manejador global de errores.

## Autor

Ángel Ivo Altavilla Castillo

Proyecto académico desarrollado como práctica de Spring Boot, API REST y bases de datos relacionales.
