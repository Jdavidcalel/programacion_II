
-- =============================================
-- PF-28: SISTEMA DE RESERVAS DE SALAS DE ENSAYO
-- Base de datos: reservas_ensayo
-- PostgreSQL
-- =============================================

-- Ejecutar en la base de datos reservas_ensayo.
-- Este script crea la estructura, pero no
-- elimina ni modifica los registros existentes.

BEGIN;

-- =============================================
-- 1. EXTENSION NECESARIA
-- =============================================

CREATE EXTENSION IF NOT EXISTS btree_gist;

-- =============================================
-- 2. TABLA DE SALAS
-- =============================================

CREATE TABLE IF NOT EXISTS salas (
                                     id_sala INTEGER GENERATED ALWAYS AS IDENTITY
                                     PRIMARY KEY,

                                     nombre VARCHAR(100) NOT NULL,

    capacidad INTEGER NOT NULL
    CHECK (capacidad > 0),

    tarifa_hora NUMERIC(10,2) NOT NULL
    CHECK (tarifa_hora > 0),

    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA'
    CHECK (estado IN ('ACTIVA', 'INACTIVA'))
    );

-- =============================================
-- 3. TABLA DE RESERVAS
-- =============================================

CREATE TABLE IF NOT EXISTS reservas (
                                        id_reserva INTEGER GENERATED ALWAYS AS IDENTITY
                                        PRIMARY KEY,

                                        id_sala INTEGER NOT NULL
                                        REFERENCES salas(id_sala),

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

-- =============================================
-- 4. IMPEDIR RESERVAS ACTIVAS TRASLAPADAS
-- =============================================

-- PostgreSQL no admite ADD CONSTRAINT
-- IF NOT EXISTS directamente.
-- Por eso comprobamos el catalogo del sistema.

DO $$
BEGIN

    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'reservas_sin_traslapes'
          AND conrelid = 'reservas'::regclass
    ) THEN

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

END IF;

END
$$;

COMMIT;

-- =============================================
-- 5. CONSULTAS DE VERIFICACION
-- =============================================

SELECT
    table_name
FROM information_schema.tables
WHERE table_schema = 'public'
  AND table_name IN ('salas', 'reservas')
ORDER BY table_name;

SELECT
    conname AS nombre_restriccion
FROM pg_constraint
WHERE conrelid = 'reservas'::regclass
ORDER BY conname;
