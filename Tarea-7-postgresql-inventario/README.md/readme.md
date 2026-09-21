# Tarea VII - PostgreSQL Inventario

## Descripción

Este proyecto consiste en el diseño e implementación de una base de datos en PostgreSQL para llevar el control básico del inventario de una tienda.

La base de datos permite almacenar información de los productos, incluyendo código, nombre, categoría, precio, cantidad disponible y estado.

Durante el desarrollo se aplicaron diferentes conceptos de bases de datos, entre ellos:

- Creación de bases de datos y tablas.
- Llaves primarias.
- Identificadores automáticos.
- Restricciones NOT NULL, UNIQUE y CHECK.
- Valores predeterminados mediante DEFAULT.
- Inserción de registros.
- Consultas mediante SELECT.
- Filtros utilizando WHERE.
- Ordenamiento mediante ORDER BY.
- Actualización de información mediante UPDATE.
- Eliminación de registros mediante DELETE.
- Validación de datos incorrectos.

---

## Base de datos

La base de datos utilizada en el proyecto se llama:

`control_inventario`

La tabla principal se llama:

`producto`

---

## Requisitos

Para ejecutar este proyecto se necesita:

- PostgreSQL.
- pgAdmin 4.
- Un servidor PostgreSQL en funcionamiento.
- Acceso al Query Tool de pgAdmin.

---

## Estructura del proyecto

```text
tarea-07-postgresql-inventario/
├── database/
│   ├── 01_creacion.sql
│   ├── 02_datos_prueba.sql
│   └── 03_operaciones.sql
├── evidencias/
│   └── Evidencias_PostgreSQL_NombreApellido.docx
└── README.md