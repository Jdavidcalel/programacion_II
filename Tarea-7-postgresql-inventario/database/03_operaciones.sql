-- =====================================================
-- TAREA VII - POSTGRESQL INVENTARIO
-- Archivo: 03_operaciones.sql
-- Descripcion: Consultas, actualizaciones y eliminacion
-- =====================================================


-- =====================================================
-- CONSULTA 1
-- Mostrar todos los productos
-- =====================================================

SELECT codigo, nombre, categoria, precio, stock
FROM producto
ORDER BY codigo;


-- =====================================================
-- CONSULTA 2
-- Buscar productos de una categoria especifica
-- =====================================================

SELECT codigo, nombre, categoria, precio, stock
FROM producto
WHERE categoria = 'Tecnologia';


-- =====================================================
-- CONSULTA 3
-- Ordenar productos del precio mas alto al mas bajo
-- =====================================================

SELECT codigo, nombre, categoria, precio, stock
FROM producto
ORDER BY precio DESC;


-- =====================================================
-- CONSULTA 4
-- Mostrar productos con tres unidades o menos
-- =====================================================

SELECT codigo, nombre, categoria, precio, stock
FROM producto
WHERE stock <= 3;


-- =====================================================
-- CONSULTA 5
-- Mostrar productos activos que tengan existencias
-- =====================================================

SELECT codigo, nombre, categoria, precio, stock
FROM producto
WHERE activo = TRUE
  AND stock > 0;


-- =====================================================
-- CONSULTA 6
-- Buscar un producto mediante su codigo
-- =====================================================

SELECT codigo, nombre, categoria, precio, stock
FROM producto
WHERE codigo = 'PROD-006';


-- =====================================================
-- SIMULACION DE UNA VENTA
-- =====================================================

-- Comprobar stock ANTES de la venta
SELECT codigo, nombre, stock
FROM producto
WHERE codigo = 'PROD-001';


-- Vender 2 unidades
UPDATE producto
SET stock = stock - 2
WHERE codigo = 'PROD-001';


-- Comprobar stock DESPUES de la venta
SELECT codigo, nombre, stock
FROM producto
WHERE codigo = 'PROD-001';


-- =====================================================
-- PRODUCTOS AGOTADOS
-- =====================================================

-- Verificar los registros antes del UPDATE
SELECT codigo, nombre, stock, activo
FROM producto
WHERE stock = 0;


-- Cambiar productos agotados a inactivos
UPDATE producto
SET activo = FALSE
WHERE stock = 0;


-- Comprobar productos inactivos
SELECT codigo, nombre, stock, activo
FROM producto
WHERE activo = FALSE;


-- =====================================================
-- ELIMINACION DEL PRODUCTO TEMPORAL
-- =====================================================

-- Comprobar que TEMP-001 existe antes del DELETE
SELECT codigo, nombre, categoria, precio, stock
FROM producto
WHERE codigo = 'TEMP-001';


-- Eliminar unicamente TEMP-001
DELETE FROM producto
WHERE codigo = 'TEMP-001';


-- Comprobar que TEMP-001 ya no existe
SELECT codigo, nombre, categoria, precio, stock
FROM producto
WHERE codigo = 'TEMP-001';