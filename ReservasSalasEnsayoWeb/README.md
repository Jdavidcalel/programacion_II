
# PF-28 — Sistema de Reservas de Salas de Ensayo

## Información académica

**Universidad:** Universidad Mariano Gálvez de Guatemala  
**Carrera:** Ingeniería en Sistemas  
**Curso:** Programación II  
**Estudiante:** Juan David Chiquin Calel  
**Carné:** 9941-21-16056  
**Proyecto:** PF-28 — Sistema de Reservas de Salas de Ensayo

---

## 1. Descripción del problema

Los establecimientos que ofrecen salas de ensayo musical
necesitan administrar los espacios disponibles, los horarios
de utilización y las reservas realizadas por sus clientes.

Cuando estos procesos se realizan manualmente, pueden
presentarse inconvenientes como:

- Reservas duplicadas para una misma sala y horario.
- Dificultad para consultar la disponibilidad.
- Errores en el cálculo del costo del servicio.
- Falta de control sobre las reservas canceladas.
- Dificultad para consultar el historial.
- Pérdida de tiempo en la administración de horarios.

Para solucionar estos problemas se desarrolló el
**Sistema de Reservas de Salas de Ensayo**, una aplicación
web que permite gestionar salas y reservas mediante
una interfaz de usuario y una API REST.

El sistema utiliza Java, Spring Boot y PostgreSQL
para almacenar información y aplicar reglas de negocio.

## 2. Objetivos

### Objetivo general

Desarrollar una aplicación web que permita administrar
salas de ensayo musical y sus reservas, aplicando
programación orientada a objetos, persistencia de datos,
arquitectura por capas y servicios REST.

### Objetivos específicos

- Registrar y administrar salas de ensayo.
- Consultar la disponibilidad por fecha y horario.
- Registrar, consultar y modificar reservas.
- Evitar reservas con horarios traslapados.
- Calcular automáticamente el costo de las reservas.
- Validar fechas y horarios.
- Cancelar reservas conservando su historial.
- Implementar manejo de errores HTTP.
- Realizar pruebas automatizadas y pruebas de API.

## 3. Tecnologías utilizadas

### Backend

- Java 25
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Data JPA
- Jakarta Validation
- Maven Wrapper

### Base de datos

- PostgreSQL
- pgAdmin 4

### Frontend

- HTML5
- CSS3
- JavaScript

### Herramientas

- IntelliJ IDEA
- Git
- GitHub
- Postman
- JUnit
- Mockito

## 4. Arquitectura del sistema

El proyecto utiliza una arquitectura por capas
para separar las responsabilidades del sistema.

### Estructura general

```text
ReservasSalasEnsayoWeb/
|
|-- src/
|   |-- main/
|   |   |-- java/
|   |   |   `-- com/umg/reservas/
|   |   |       |-- controller/
|   |   |       |-- dto/
|   |   |       |-- entity/
|   |   |       |-- exception/
|   |   |       |-- repository/
|   |   |       `-- service/
|   |   |
|   |   `-- resources/
|   |       |-- static/
|   |       `-- application.properties
|   |
|   `-- test/
|       `-- java/com/umg/reservas/service/
|           `-- ReservaServiceTest.java
|
|-- .mvn/
|-- pom.xml
|-- mvnw
|-- mvnw.cmd
|-- datos_iniciales.sql
|-- PF28_ReservasSalasEnsayo_Postman.json
`-- README.md
```

### Descripción de las capas

**entity:** contiene las entidades que representan
las tablas de PostgreSQL.

**repository:** permite consultar y almacenar
información mediante Spring Data JPA.

**service:** contiene la lógica del negocio,
validaciones y cálculos.

**controller:** expone los endpoints REST para
interactuar con el sistema.

**dto:** define objetos para recibir datos de
las solicitudes.

**exception:** centraliza el manejo de errores
y las respuestas HTTP.

**static:** contiene los recursos de la interfaz web.

## 5. Funcionalidades

### Administración de salas

1. Consultar todas las salas.
2. Consultar una sala por identificador.
3. Registrar nuevas salas.
4. Actualizar información de salas.
5. Desactivar salas.
6. Consultar salas activas.
7. Buscar salas por capacidad.
8. Consultar disponibilidad por fecha y horario.

### Administración de reservas

1. Registrar nuevas reservas.
2. Consultar todas las reservas.
3. Consultar una reserva por identificador.
4. Buscar reservas por cliente.
5. Filtrar reservas por fecha.
6. Actualizar reservas existentes.
7. Recalcular costos al modificar una reserva.
8. Evitar reservas con horarios traslapados.
9. Validar fechas y horarios.
10. Cancelar reservas conservando su historial.
11. Consultar el indicador de reservas activas.

## 6. Base de datos PostgreSQL

### Nombre de la base de datos

`reservas_ensayo`

### Tablas principales

**salas**

Almacena información sobre las salas de ensayo:

- Identificador de sala.
- Nombre.
- Capacidad.
- Tarifa por hora.
- Estado.

**reservas**

Almacena las reservas realizadas:

- Identificador de reserva.
- Sala asociada.
- Cliente.
- Fecha.
- Hora de inicio.
- Hora de finalización.
- Costo total.
- Estado.

### Datos iniciales

El script de datos iniciales contiene:

| Información | Cantidad |
|---|---:|
| Salas registradas | 5 |
| Reservas registradas | 10 |
| Reservas activas | 9 |
| Reservas canceladas | 1 |

Estas cantidades corresponden al conjunto inicial.
Pueden aumentar o cambiar cuando se utiliza la API.

### Configuración de PostgreSQL

1. Abrir pgAdmin 4.
2. Conectarse al servidor PostgreSQL.
3. Crear una base de datos llamada `reservas_ensayo`.
4. Ejecutar el script SQL de creación de tablas y restricciones.
5. Ejecutar el archivo `datos_iniciales.sql`.
6. Verificar los registros creados.

Consulta de verificación:

```sql
SELECT COUNT(*) AS total_salas
FROM salas;

SELECT COUNT(*) AS total_reservas
FROM reservas;

SELECT estado, COUNT(*) AS cantidad
FROM reservas
GROUP BY estado;
```

### Prevención de traslapes

La base de datos utiliza la extensión `btree_gist`
y una restricción de exclusión para impedir
reservas activas con horarios superpuestos
en una misma sala.

Esta restricción complementa las validaciones
realizadas desde el backend.

Los scripts SQL están preparados para ejecutarse
nuevamente sin duplicar los datos iniciales.

## 7. Reglas principales del negocio

1. No se permite reservar una sala inexistente.
2. No se permite reservar una sala inactiva.
3. No se permiten nuevas reservas en fechas pasadas.
4. No se permiten horarios de inicio que ya pasaron.
5. La hora de finalización debe ser posterior al inicio.
6. No se permiten reservas activas traslapadas.
7. El costo depende de la duración y tarifa por hora.
8. Al actualizar una reserva se recalcula el costo.
9. Al actualizar una reserva se verifica la disponibilidad.
10. No se permite modificar una reserva cancelada.
11. La cancelación cambia el estado a `CANCELADA`.
12. No se permite cancelar dos veces una reserva.
13. Las reservas canceladas permanecen registradas.

La aplicación utiliza la zona horaria:

`America/Guatemala`

## 8. Requisitos para ejecutar el proyecto

Se necesita:

- JDK 25.
- PostgreSQL.
- Base de datos `reservas_ensayo`.
- Maven Wrapper incluido en el proyecto.
- IntelliJ IDEA u otro IDE compatible.
- Navegador web.
- Postman para realizar pruebas de API (opcional).

También debe estar disponible el puerto `8080`.

## 9. Configuración del backend

### 9.1. Conexión con PostgreSQL

El archivo de configuración se encuentra en:

`src/main/resources/application.properties`

Configuración:

```properties
spring.application.name=ReservasSalasEnsayoWeb

spring.datasource.url=jdbc:postgresql://localhost:5432/reservas_ensayo

spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=validate

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```

Las credenciales se configuran mediante
variables de entorno para evitar publicar
contraseñas en el repositorio.

### 9.2. Configurar las variables de entorno

Abrir PowerShell dentro de la carpeta del proyecto.

Ejecutar:

```powershell
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD=Read-Host "Contraseña de PostgreSQL"
```

Ingresar la contraseña de PostgreSQL cuando se solicite.

### 9.3. Iniciar el backend

Ejecutar:

```powershell
.\mvnw.cmd spring-boot:run
```

Esperar a que Spring Boot termine de iniciar.

El backend estará disponible en:

`http://localhost:8080`

Para comprobar la conexión con la API:

`http://localhost:8080/api/salas`

## 10. Instrucciones para abrir el frontend

La interfaz web utiliza HTML5, CSS3 y JavaScript.

Los recursos estáticos del proyecto se encuentran en:

`src/main/resources/static/`

Spring Boot puede servir estos archivos directamente,
sin necesidad de iniciar un servidor frontend separado.

Para abrir la aplicación:

1. Iniciar PostgreSQL.
2. Verificar que la base de datos exista.
3. Configurar las variables de entorno.
4. Ejecutar Spring Boot.
5. Abrir un navegador web.
6. Ingresar a:

`http://localhost:8080/`

**Nota:** para acceder desde la ruta principal `/`,
debe existir un archivo `index.html` dentro de
`src/main/resources/static/`.

## 11. Endpoints REST

Dirección base:

`http://localhost:8080`

### 11.1. Endpoints de salas

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/salas` | Listar salas |
| GET | `/api/salas/{id}` | Consultar sala |
| GET | `/api/salas/activas` | Listar salas activas |
| GET | `/api/salas/capacidad?minimo=4` | Buscar por capacidad |
| GET | `/api/salas/disponibles` | Consultar disponibilidad |
| POST | `/api/salas` | Registrar sala |
| PUT | `/api/salas/{id}` | Actualizar sala |
| DELETE | `/api/salas/{id}` | Desactivar sala |

### Consultar disponibilidad

Ejemplo:

```http
GET /api/salas/disponibles?fecha=2026-12-15&horaInicio=10:00&horaFin=12:00
```

El sistema devuelve las salas activas que
no tengan reservas superpuestas en el horario.

### 11.2. Endpoints de reservas

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/reservas` | Listar reservas |
| GET | `/api/reservas/{id}` | Consultar reserva |
| GET | `/api/reservas/fecha?fecha=2026-12-15` | Filtrar por fecha |
| GET | `/api/reservas/cliente?nombre=Banda` | Buscar por cliente |
| POST | `/api/reservas` | Registrar reserva |
| PUT | `/api/reservas/{id}` | Actualizar reserva |
| PATCH | `/api/reservas/{id}/cancelar` | Cancelar reserva |
| GET | `/api/reservas/indicadores/activas` | Contar reservas activas |

### Ejemplo de registro

Método:

`POST /api/reservas`

Body:

```json
{
  "idSala": 1,
  "cliente": "Banda de Prueba",
  "fecha": "2026-12-22",
  "horaInicio": "10:00",
  "horaFin": "12:00"
}
```

El sistema calcula automáticamente el costo
y registra la reserva como `ACTIVA`.

### Ejemplo de actualización

Método:

`PUT /api/reservas/{id}`

Body:

```json
{
  "idSala": 1,
  "cliente": "Banda de Prueba Actualizada",
  "fecha": "2026-12-22",
  "horaInicio": "10:00",
  "horaFin": "13:00"
}
```

Al actualizar se vuelve a comprobar la disponibilidad
y se recalcula el costo total.

### Ejemplo de cancelación

Método:

`PATCH /api/reservas/{id}/cancelar`

No requiere Body.

La reserva conserva su información y cambia
su estado a `CANCELADA`.

## 12. Manejo de errores HTTP

El sistema utiliza un manejador centralizado
de excepciones.

| Código | Significado | Ejemplo |
|---|---|---|
| 400 | Bad Request | Horario inválido |
| 404 | Not Found | Reserva inexistente |
| 409 | Conflict | Reserva ya cancelada |
| 500 | Internal Server Error | Error interno |

### Validaciones implementadas

- Campos obligatorios.
- Fecha y horarios válidos.
- Horarios no anteriores al momento actual.
- Sala existente y activa.
- Prevención de reservas traslapadas.
- Rechazo de modificaciones a reservas canceladas.
- Rechazo de cancelaciones repetidas.

Los conflictos de integridad detectados
por PostgreSQL también son gestionados
por el manejador de errores.

## 13. Pruebas automatizadas

El proyecto utiliza JUnit y Mockito
para verificar la lógica del servicio de reservas.

Archivo:

`src/test/java/com/umg/reservas/service/ReservaServiceTest.java`

### Casos de prueba

1. Cálculo correcto del costo.
2. Rechazo de horarios ocupados.
3. Cancelación de reserva activa.
4. Rechazo de una segunda cancelación.
5. Rechazo de fechas pasadas.
6. Rechazo de horarios pasados del día actual.
7. Actualización y recálculo del costo.

### Ejecutar las pruebas

Desde PowerShell:

```powershell
.\mvnw.cmd clean test "-Dtest=ReservaServiceTest"
```

Resultado esperado:

```text
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Estas pruebas utilizan Mockito para simular
los repositorios y no modifican la base de datos.

## 14. Pruebas de API con Postman

Se incluye una colección de Postman para
verificar los endpoints del sistema.

Archivo:

`PF28_ReservasSalasEnsayo_Postman.json`

### Importar la colección

1. Abrir Postman.
2. Seleccionar `Import`.
3. Buscar el archivo JSON.
4. Importar la colección.
5. Verificar la variable `baseUrl`.

Valor de `baseUrl`:

`http://localhost:8080`

### Solicitudes incluidas

| No. | Método | Prueba |
|---|---|---|
| 01 | GET | Listar todas las salas |
| 02 | GET | Consultar disponibilidad |
| 03 | POST | Registrar nueva reserva |
| 04 | PUT | Actualizar reserva |
| 05 | PATCH | Cancelar reserva |
| 06 | POST | Error 400 - Horario inválido |
| 07 | GET | Error 404 - Reserva inexistente |
| 08 | PATCH | Error 409 - Reserva ya cancelada |

### Resultados esperados

- `200 OK`: consultas y actualizaciones exitosas.
- `201 Created`: registro correcto de reservas.
- `400 Bad Request`: datos inválidos.
- `404 Not Found`: recurso inexistente.
- `409 Conflict`: operación no permitida.

**Importante:** las pruebas de registro y modificación
utilizan datos de ejemplo. Al repetirlas puede ser
necesario cambiar la fecha, el horario o el ID.

## 15. Verificación del funcionamiento

Para comprobar el proyecto:

1. Crear una base de datos PostgreSQL vacía.
2. Ejecutar el script de estructura.
3. Ejecutar `datos_iniciales.sql`.
4. Verificar las 5 salas y 10 reservas iniciales.
5. Configurar las variables de entorno.
6. Iniciar Spring Boot.
7. Consultar las salas mediante la API.
8. Consultar disponibilidad por fecha y horario.
9. Registrar una reserva.
10. Actualizarla y comprobar el nuevo costo.
11. Cancelar la reserva.
12. Verificar los errores HTTP 400, 404 y 409.
13. Ejecutar las pruebas automatizadas.
14. Comprobar el funcionamiento del frontend.

## 16. Proyecto académico

**Universidad:** Universidad Mariano Gálvez de Guatemala

**Carrera:** Ingeniería en Sistemas

**Curso:** Programación II

**Estudiante:** Juan David Chiquin Calel

**Carné:** 9941-21-16056

**Proyecto:** PF-28 — Sistema de Reservas de Salas de Ensayo

### Objetivo académico

Aplicar los conocimientos adquiridos durante
el curso de Programación II mediante el desarrollo
de una aplicación web con base de datos.

El proyecto integra:

- Programación orientada a objetos.
- Arquitectura por capas.
- Persistencia con PostgreSQL.
- Servicios REST.
- Validaciones y reglas de negocio.
- Manejo de excepciones.
- Pruebas automatizadas.
- Pruebas de API.
- Control de versiones con Git y GitHub.
