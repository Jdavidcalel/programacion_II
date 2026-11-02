
-- =============================================
-- PF-28: SISTEMA DE RESERVAS DE SALAS DE ENSAYO
-- Base de datos: reservas_ensayo
-- PostgreSQL
-- =============================================

-- Ejecutar en una base de datos vacía llamada
-- reservas_ensayo.

CREATE EXTENSION IF NOT EXISTS btree_gist;

-- TABLA DE SALAS

CREATE TABLE IF NOT EXISTS salas (
                                     id_sala INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                     nombre VARCHAR(100) NOT NULL,
    capacidad INTEGER NOT NULL CHECK (capacidad > 0),
    tarifa_hora NUMERIC(10,2) NOT NULL CHECK (tarifa_hora > 0),
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA'
    CHECK (estado IN ('ACTIVA', 'INACTIVA'))
    );

-- TABLA DE RESERVAS

CREATE TABLE IF NOT EXISTS reservas (
                                        id_reserva INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                        id_sala INTEGER NOT NULL REFERENCES salas(id_sala),
    cliente VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    costo_total NUMERIC(10,2) NOT NULL
    CHECK (costo_total >= 0),
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA'
    CHECK (estado IN ('ACTIVA', 'CANCELADA')),
    CONSTRAINT chk_horario
    CHECK (hora_fin > hora_inicio)
    );

-- IMPEDIR RESERVAS ACTIVAS TRASLAPADAS

ALTER TABLE reservas
    ADD CONSTRAINT reservas_sin_traslapes
    EXCLUDE USING gist (
    id_sala WITH =,
    tsrange(
        fecha + hora_inicio,
        fecha + hora_fin,
        '[)'
    ) WITH &&
)
WHERE (estado = 'ACTIVA');

-- SALAS INICIALES

INSERT INTO salas
(nombre, capacidad, tarifa_hora, estado)
VALUES
    ('Sala Rock', 6, 75.00, 'ACTIVA'),
    ('Sala Acustica', 4, 50.00, 'ACTIVA'),
    ('Sala Profesional', 10, 125.00, 'ACTIVA'),
    ('Sala Premium', 8, 100.00, 'ACTIVA');

-- CONSULTAS DE VERIFICACION

SELECT * FROM salas;
SELECT * FROM reservas;
