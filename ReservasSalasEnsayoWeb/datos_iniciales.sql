
-- =============================================
-- PROYECTO: RESERVAS DE SALAS DE ENSAYO
-- ARCHIVO: datos_iniciales.sql
-- PostgreSQL
-- =============================================

BEGIN;

-- =============================================
-- 1. SALAS DE ENSAYO
-- =============================================

INSERT INTO salas
(nombre, capacidad, tarifa_hora, estado)
SELECT
    v.nombre,
    v.capacidad,
    v.tarifa_hora,
    v.estado
FROM (
         VALUES
             ('Sala Rock', 6, 75.00, 'ACTIVA'),
             ('Sala Acustica', 4, 50.00, 'ACTIVA'),
             ('Sala Profesional', 10, 125.00, 'ACTIVA'),
             ('Sala Premium', 8, 100.00, 'ACTIVA'),
             ('Sala Jazz Premium', 7, 95.00, 'INACTIVA')
     ) AS v(nombre, capacidad, tarifa_hora, estado)
WHERE NOT EXISTS (
    SELECT 1
    FROM salas s
    WHERE s.nombre = v.nombre
);

-- =============================================
-- 2. DIEZ RESERVAS DE EJEMPLO
-- =============================================

WITH datos_reservas AS (
    SELECT *
    FROM (
             VALUES
                 ('Sala Rock', 'Banda Los Rockeros',
                  DATE '2026-10-30', TIME '14:00',
                  TIME '17:00', 225.00, 'ACTIVA'),

                 ('Sala Acustica', 'Cliente Prueba Actualizacion',
                  DATE '2026-12-15', TIME '10:00',
                  TIME '12:00', 100.00, 'ACTIVA'),

                 ('Sala Profesional', 'Prueba de Cancelación',
                  DATE '2026-11-25', TIME '09:00',
                  TIME '10:00', 125.00, 'CANCELADA'),

                 ('Sala Rock', 'Grupo Sonido Chapin',
                  DATE '2026-12-01', TIME '09:00',
                  TIME '11:00', 150.00, 'ACTIVA'),

                 ('Sala Acustica', 'Banda Ritmo Latino',
                  DATE '2026-12-02', TIME '10:00',
                  TIME '12:00', 100.00, 'ACTIVA'),

                 ('Sala Profesional', 'Orquesta Juvenil',
                  DATE '2026-12-03', TIME '13:00',
                  TIME '15:00', 250.00, 'ACTIVA'),

                 ('Sala Premium', 'Grupo Melodia',
                  DATE '2026-12-04', TIME '14:00',
                  TIME '16:00', 200.00, 'ACTIVA'),

                 ('Sala Rock', 'Banda Rock Nacional',
                  DATE '2026-12-05', TIME '15:00',
                  TIME '18:00', 225.00, 'ACTIVA'),

                 ('Sala Acustica', 'Grupo Acustico GT',
                  DATE '2026-12-06', TIME '08:00',
                  TIME '10:00', 100.00, 'ACTIVA'),

                 ('Sala Profesional', 'Musicos Independientes',
                  DATE '2026-12-07', TIME '16:00',
                  TIME '18:00', 250.00, 'ACTIVA')

         ) AS v(
                nombre_sala,
                cliente,
                fecha,
                hora_inicio,
                hora_fin,
                costo_total,
                estado
        )
)
INSERT INTO reservas (
    id_sala,
    cliente,
    fecha,
    hora_inicio,
    hora_fin,
    costo_total,
    estado
)
SELECT
    s.id_sala,
    d.cliente,
    d.fecha,
    d.hora_inicio,
    d.hora_fin,
    d.costo_total,
    d.estado
FROM datos_reservas d
         JOIN salas s
              ON s.nombre = d.nombre_sala
WHERE NOT EXISTS (
    SELECT 1
    FROM reservas r
    WHERE r.id_sala = s.id_sala
      AND r.cliente = d.cliente
      AND r.fecha = d.fecha
      AND r.hora_inicio = d.hora_inicio
      AND r.hora_fin = d.hora_fin
);

COMMIT;

-- =============================================
-- 3. VERIFICACION
-- =============================================

SELECT
    (SELECT COUNT(*) FROM salas) AS total_salas,
    (SELECT COUNT(*) FROM reservas) AS total_reservas;

SELECT
    estado,
    COUNT(*) AS cantidad
FROM reservas
GROUP BY estado
ORDER BY estado;
