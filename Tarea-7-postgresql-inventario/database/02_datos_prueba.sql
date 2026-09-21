-- =====================================================
-- TAREA VII - POSTGRESQL INVENTARIO
-- Archivo: 02_datos_prueba.sql
-- Descripcion: Datos de prueba y comprobacion
-- de restricciones
-- =====================================================


-- =====================================================
-- 1. INSERTAR 7 PRODUCTOS
-- =====================================================

INSERT INTO producto
    (codigo, nombre, categoria, precio, stock, activo)
VALUES
    ('PROD-001', 'Teclado USB', 'Tecnologia', 125.50, 10, TRUE),
    ('PROD-002', 'Mouse inalambrico', 'Tecnologia', 89.99, 8, TRUE),
    ('PROD-003', 'Cuaderno universitario', 'Papeleria', 25.50, 15, TRUE),
    ('PROD-004', 'Lapicero azul', 'Papeleria', 3.75, 30, TRUE),
    ('PROD-005', 'Botella de agua', 'Hogar', 45.00, 3, TRUE),
    ('PROD-006', 'Lampara de escritorio', 'Hogar', 175.00, 6, TRUE),
    ('PROD-007', 'Audifonos', 'Tecnologia', 210.00, 0, TRUE);


-- =====================================================
-- 2. PRODUCTO 8
-- Se omite activo para comprobar DEFAULT TRUE
-- =====================================================

INSERT INTO producto
    (codigo, nombre, categoria, precio, stock)
VALUES
    ('PROD-008', 'Mochila escolar', 'Escolar', 150.00, 2);


-- Comprobar los 8 productos
SELECT id, codigo, nombre, categoria, precio, stock, activo
FROM producto
ORDER BY id;


-- =====================================================
-- 3. PRODUCTO TEMPORAL
-- Utilizado posteriormente para comprobar DELETE
-- =====================================================

INSERT INTO producto
    (codigo, nombre, categoria, precio, stock)
VALUES
    ('TEMP-001', 'Producto temporal', 'Prueba', 10.00, 1);


-- =====================================================
-- 4. ERROR INTENCIONAL: CODIGO DUPLICADO
-- PostgreSQL debe rechazar este INSERT por UNIQUE
-- =====================================================

INSERT INTO producto
    (codigo, nombre, categoria, precio, stock)
VALUES
    ('PROD-001', 'Producto duplicado', 'Prueba', 50.00, 5);


-- CORRECCION DEL CODIGO DUPLICADO
INSERT INTO producto
    (codigo, nombre, categoria, precio, stock)
VALUES
    ('PROD-009', 'Producto de prueba', 'Prueba', 50.00, 5);


-- =====================================================
-- 5. ERROR INTENCIONAL: STOCK NEGATIVO
-- PostgreSQL debe rechazar este INSERT por CHECK
-- =====================================================

INSERT INTO producto
    (codigo, nombre, categoria, precio, stock)
VALUES
    ('PROD-010', 'Producto stock negativo', 'Prueba', 75.00, -5);


-- CORRECCION DEL STOCK NEGATIVO
INSERT INTO producto
    (codigo, nombre, categoria, precio, stock)
VALUES
    ('PROD-010', 'Producto stock corregido', 'Prueba', 75.00, 5);