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

## Avance actual — hasta el paso 7

- [x] Creación del proyecto Maven `ReservasSalasEnsayo`.
- [x] Creación de la base de datos `reservas_ensayo`.
- [x] Creación de las tablas `salas` y `reservas` con relación mediante clave foránea.
- [x] Registro de cuatro salas de prueba.
- [x] Incorporación de la dependencia JDBC en `pom.xml`.
- [x] Creación de `ConexionBD.java` para abrir conexiones a PostgreSQL.
- [x] Creación y ejecución de `PruebaConexion.java`.
- [x] Consulta exitosa de las salas almacenadas desde Java.
- [ ] Implementación de las clases de modelo y la lógica de negocio.
- [ ] Validación de traslapes y cálculo automático de costos.
- [ ] Menú interactivo y operaciones completas de reservas.
- [ ] Pruebas integrales y entrega final.

## Estructura actual

```text
ReservasSalasEnsayo/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── com/
                └── umg/
                    └── reservas/
                        ├── PruebaConexion.java
                        └── conexion/
                            └── ConexionBD.java
```

## Base de datos

**Nombre:** `reservas_ensayo`

**Tabla `salas`:** `id_sala`, `nombre`, `capacidad`, `tarifa_hora`, `estado`.

**Tabla `reservas`:** `id_reserva`, `id_sala`, `cliente`, `fecha`, `hora_inicio`, `hora_fin`, `costo_total`, `estado`.

La tabla `reservas` contiene una clave foránea a `salas` y una restricción que exige `hora_fin > hora_inicio`. La validación de traslapes está pendiente.

### Salas de prueba

| Sala | Capacidad | Tarifa por hora |
|---|---:|---:|
| Sala Rock | 6 | Q75.00 |
| Sala Acustica | 4 | Q50.00 |
| Sala Profesional | 10 | Q125.00 |
| Sala Premium | 8 | Q100.00 |

## Prueba de conexión realizada

Se ejecutó `PruebaConexion.java` desde IntelliJ IDEA. La aplicación utilizó JDBC para consultar los registros de la tabla `salas` y mostró la información recuperada desde PostgreSQL en la consola.

### Configuración local

La clase `ConexionBD.java` utiliza una conexión JDBC hacia una instancia local de PostgreSQL, con una URL de la forma:

```text
jdbc:postgresql://localhost:5432/reservas_ensayo
```

El puerto y las credenciales deben ajustarse al entorno donde se ejecute el proyecto.

**Seguridad:** no subir contraseñas reales a GitHub. Antes de publicar el repositorio, retirar las credenciales del código fuente y utilizar variables de entorno o un archivo de configuración excluido de Git.

## Próximos pasos

1. Crear la clase `Sala` y la clase `Reserva` aplicando POO.
2. Implementar acceso a datos y servicios de reservas.
3. Agregar controles de disponibilidad, validaciones y cálculo de tarifas.
4. Desarrollar el menú de consola.
5. Ejecutar pruebas funcionales y documentar resultados.

## Modalidad

Se desarrolla inicialmente como aplicación de consola. Si el docente requiere interfaz gráfica, se podrá incorporar posteriormente, separando la presentación de la lógica de negocio y el acceso a datos.
