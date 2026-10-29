# PF-28 — Sistema de Reservas de Salas de Ensayo

Proyecto final del curso de Programación II. Aplicación de consola en Java para administrar salas de ensayo y sus reservas.

## Objetivo

Desarrollar un sistema que permita gestionar salas y reservas de bandas o artistas, controlar conflictos de horarios y calcular el costo de uso de cada sala.

## Requisitos funcionales del proyecto

- Registrar y consultar salas de ensayo.
- Registrar reservas asociadas a una sala.
- Validar que la hora final sea posterior a la inicial.
- Evitar reservas activas que se traslapen para una misma sala.
- Calcular el costo según la duración y la tarifa por hora.
- Consultar reservas por fecha, salas disponibles, reservas por cliente y el total esperado por reserva.

> **Estado:** los requisitos anteriores representan el alcance previsto. Aún no están todos implementados.

## Tecnologías

- Java 25 (JDK)
- IntelliJ IDEA
- Maven
- PostgreSQL 18
- pgAdmin 4
- PostgreSQL JDBC Driver 42.7.7

## 7. Estructura del proyecto

El proyecto utiliza paquetes para organizar las clases según su responsabilidad.

```text
ReservasSalasEnsayo/
│
├── pom.xml
├── README.md
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── umg/
        │           └── reservas/
        │               │
        │               ├── conexion/
        │               │   └── ConexionBD.java
        │               │
        │               ├── modelo/
        │               │   ├── Sala.java
        │               │   └── Reserva.java
        │               │
        │               ├── dao/
        │               │   ├── SalaDAO.java
        │               │   └── ReservaDAO.java
        │               │
        │               ├── PruebaConexion.java
        │               ├── PruebaSalaDAO.java
        │               ├── PruebaReservaDAO.java
        │               └── PruebaConsultasReserva.java
        │
        └── resources/
```

---

## 8. Programación orientada a objetos

Se implementaron clases para representar las entidades principales del sistema.

### Clase Sala.java

Representa una sala de ensayo.

Contiene información como:

- Identificador de la sala.
- Nombre.
- Capacidad.
- Tarifa por hora.
- Estado.

Se implementaron constructores, métodos de acceso y un método para mostrar información.

### Clase Reserva.java

Representa una reserva realizada por un cliente.

Contiene:

- Identificador de reserva.
- Identificador de sala.
- Cliente.
- Fecha.
- Hora de inicio.
- Hora de finalización.
- Costo total.
- Estado.

También incorpora la lógica para calcular el costo de una reserva.

---

## 9. Conexión con PostgreSQL

Se implementó la clase:

`ConexionBD.java`

Su función es establecer la conexión entre Java y PostgreSQL utilizando JDBC.

La conexión utiliza una URL con la siguiente estructura:

```text
jdbc:postgresql://localhost:5432/reservas_ensayo
```

La conexión fue comprobada mediante la clase `PruebaConexion.java`.

## 10. Implementación del patrón DAO

Se implementó el patrón DAO (Data Access Object) para separar las operaciones de base de datos de las clases de modelo.

### SalaDAO.java

Permite:

- Consultar todas las salas registradas.
- Buscar una sala por su identificador.
- Recuperar la información almacenada en PostgreSQL.

### ReservaDAO.java

Actualmente contiene los siguientes métodos:

| Método | Funcionalidad |
|---|---|
| listarReservas() | Consultar todas las reservas |
| registrarReserva() | Registrar una nueva reserva |
| buscarPorFecha() | Consultar reservas por fecha |
| buscarPorCliente() | Buscar reservas por cliente |
| convertirReserva() | Convertir registros SQL en objetos Java |

El método `convertirReserva()` permite reutilizar código y evitar duplicaciones en las consultas.

---

## 11. Validación de horarios

Se implementaron validaciones para controlar los horarios de las reservas.

Las reglas principales son:

- La fecha no puede ser anterior a la actual.
- La hora final debe ser posterior a la inicial.
- No se permiten reservas activas traslapadas para la misma sala.
- Las reservas deben realizarse sobre salas existentes y activas.

### Restricción en PostgreSQL

Se habilitó la extensión:

`btree_gist`

Posteriormente, se configuró una restricción de exclusión para impedir conflictos entre reservas activas.

La restricción se identifica como:

`reservas_sin_traslapes`

Esta validación protege la integridad de la información directamente desde PostgreSQL.

---

## 12. Cálculo automático de costos

El costo de una reserva se calcula utilizando la tarifa por hora de la sala y el tiempo reservado.

### Fórmula

Costo total = Duración de la reserva × Tarifa por hora

### Ejemplo

Sala seleccionada: Sala Rock

Tarifa por hora: Q75.00

Horario reservado: 14:00 a 17:00

Duración: 3 horas

Costo total:

3 × Q75.00 = Q225.00

El cálculo se realiza desde Java y el resultado se almacena en PostgreSQL.

---

## 13. Registro de reservas

Se implementó el método:

`registrarReserva()`

Este método realiza las siguientes operaciones:

1. Valida el nombre del cliente.
2. Comprueba la fecha.
3. Valida las horas de inicio y finalización.
4. Verifica que la sala exista.
5. Comprueba que la sala esté activa.
6. Calcula el costo total.
7. Registra la información en PostgreSQL.
8. Controla los errores por conflictos de horarios.

### Primera reserva registrada

Se realizó una prueba con los siguientes datos:

| Campo | Valor |
|---|---|
| ID de reserva | 1 |
| Sala | Sala Rock |
| Cliente | Banda Los Rockeros |
| Fecha | 2026-10-30 |
| Hora de inicio | 14:00 |
| Hora de finalización | 17:00 |
| Costo total | Q225.00 |
| Estado | ACTIVA |

La reserva fue almacenada correctamente en PostgreSQL y verificada desde pgAdmin 4.

---

## 14. Consultas por fecha

Se implementó el método:

`buscarPorFecha()`

Permite recuperar las reservas correspondientes a una fecha específica.

### Prueba realizada

Fecha consultada: 2026-10-30

Resultado:

- Total de reservas encontradas: 1.
- Cliente: Banda Los Rockeros.
- Sala: Sala Rock.
- Horario: 14:00 a 17:00.
- Costo: Q225.00.

La consulta fue ejecutada exitosamente desde IntelliJ IDEA.

---

## 15. Consultas por cliente

Se implementó el método:

`buscarPorCliente()`

Permite buscar reservas mediante el nombre del cliente.

Se utiliza el operador `ILIKE` de PostgreSQL para realizar búsquedas sin distinguir mayúsculas y minúsculas.

### Prueba realizada

Texto buscado:

`Rockeros`

Resultado:

- Total de reservas encontradas: 1.
- Cliente: Banda Los Rockeros.
- Fecha: 2026-10-30.
- Costo total: Q225.00.
- Estado: ACTIVA.

La consulta fue ejecutada correctamente.

---

## 16. Pruebas realizadas

Durante el desarrollo se realizaron las siguientes pruebas:

| Prueba | Resultado |
|---|---|
| Conexión JDBC con PostgreSQL | Exitosa |
| Consulta de salas registradas | Exitosa |
| Búsqueda de sala por ID | Implementada |
| Registro de reserva | Exitoso |
| Almacenamiento del costo total | Exitoso |
| Configuración de restricción de traslapes | Realizada |
| Consulta de reservas por fecha | Exitosa |
| Consulta de reservas por cliente | Exitosa |

La validación de conflictos de horarios requiere completar su comprobación funcional y documentar el resultado.

---

## 17. Avance del proyecto

### Funcionalidades completadas

- [x] Creación del proyecto Maven.
- [x] Configuración de Java 25.
- [x] Creación de la base de datos PostgreSQL.
- [x] Creación de las tablas y sus relaciones.
- [x] Registro de cuatro salas de ensayo.
- [x] Configuración de JDBC.
- [x] Prueba de conexión con PostgreSQL.
- [x] Implementación de la clase Sala.
- [x] Implementación de la clase Reserva.
- [x] Implementación de SalaDAO.
- [x] Implementación de ReservaDAO.
- [x] Consulta de salas.
- [x] Registro de reservas.
- [x] Cálculo automático de costos.
- [x] Configuración de restricción de horarios.
- [x] Consulta de reservas por fecha.
- [x] Consulta de reservas por cliente.
- [x] Verificación de registros en pgAdmin 4.

## 18. por el momento aqui llego, mañana tratare la manera de avanzar un poco mas.
.