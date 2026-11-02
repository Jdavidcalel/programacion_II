
# PF-28 — Sistema de Reservas de Salas de Ensayo

## Descripción

Aplicación web desarrollada con Java y Spring Boot para administrar
salas de ensayo musical y sus respectivas reservas.

El sistema permite consultar salas, registrar reservas, calcular
automáticamente el costo, evitar horarios traslapados y cancelar
reservas sin eliminar su historial.

## Tecnologías utilizadas

- Java 25
- Spring Boot
- Spring Web
- Spring Data JPA
- Jakarta Validation
- Maven
- PostgreSQL
- HTML5
- CSS3
- JavaScript
- IntelliJ IDEA
- pgAdmin 4

## Arquitectura

El proyecto utiliza una arquitectura por capas:

- entity: entidades persistentes de PostgreSQL.
- repository: acceso a datos mediante Spring Data JPA.
- service: lógica y reglas de negocio.
- controller: endpoints REST.
- dto: validación de solicitudes.
- exception: manejo centralizado de errores.
- static: interfaz web HTML, CSS y JavaScript.

## Funcionalidades

1. Consultar salas de ensayo.
2. Registrar nuevas salas.
3. Actualizar información de salas.
4. Desactivar salas.
5. Registrar reservas.
6. Consultar reservas.
7. Buscar reservas por cliente.
8. Filtrar reservas por fecha.
9. Calcular automáticamente el costo según la tarifa por hora.
10. Evitar reservas con horarios traslapados.
11. Validar fechas y horarios.
12. Cancelar reservas conservando su historial.
13. Consultar el indicador de reservas activas.

## Base de datos

Nombre: reservas_ensayo

Tablas principales:

- salas
- reservas

Datos de prueba:

- 5 salas registradas.
- 10 reservas registradas.
- 9 reservas activas.
- 1 reserva cancelada.

## Reglas de negocio

- No se permite reservar una sala inactiva.
- La fecha de una nueva reserva no puede ser anterior al día actual.
- La hora de finalización debe ser posterior a la hora de inicio.
- No se permiten reservas activas traslapadas en una misma sala.
- El costo se calcula automáticamente según la duración y tarifa.
- La cancelación cambia el estado a CANCELADA.

## Endpoints REST

### Salas

GET /api/salas
GET /api/salas/{id}
GET /api/salas/activas
GET /api/salas/capacidad?minimo=4
POST /api/salas
PUT /api/salas/{id}
DELETE /api/salas/{id}

### Reservas

GET /api/reservas
GET /api/reservas/{id}
GET /api/reservas/fecha?fecha=2026-11-15
GET /api/reservas/cliente?nombre=Banda
POST /api/reservas
PATCH /api/reservas/{id}/cancelar
GET /api/reservas/indicadores/activas

## Configuración

1. Crear la base de datos PostgreSQL reservas_ensayo.
2. Ejecutar los scripts SQL correspondientes.
3. Configurar las variables de entorno DB_USERNAME y DB_PASSWORD.
4. Abrir el proyecto en IntelliJ IDEA.
5. Ejecutar el proyecto con Maven:

   .\mvnw.cmd spring-boot:run

6. Abrir la aplicación en:

   http://localhost:8080/

## Pruebas realizadas

- Registro correcto de salas.
- Actualización y desactivación de salas.
- Registro de reservas.
- Validación de campos obligatorios.
- Rechazo de fechas anteriores.
- Prevención de reservas traslapadas.
- Cálculo automático de costos.
- Cancelación lógica de reservas.
- Búsqueda de reservas por cliente.

## Proyecto académico

Universidad Mariano Gálvez de Guatemala

Curso: Programación II

Proyecto: PF-28 — Sistema de Reservas de Salas de Ensayo
