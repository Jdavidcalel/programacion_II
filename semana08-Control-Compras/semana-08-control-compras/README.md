
# Tarea 6 - Colecciones y Excepciones

## Descripción

Aplicación de consola desarrollada en Java que permite registrar
y analizar las compras realizadas por una familia durante una semana.

El programa utiliza programación orientada a objetos, colecciones,
ciclos y estructuras condicionales.

## Objetivo

Aplicar los conocimientos de Java mediante el uso de ArrayList,
HashSet y HashMap para organizar y procesar información.

## Tecnologías utilizadas

- Java
- IntelliJ IDEA
- Programación Orientada a Objetos (POO)
- ArrayList
- HashSet
- HashMap

## Funcionalidades

1. Registrar productos con nombre, categoría, precio y cantidad.
2. Validar los datos antes de registrar cada producto.
3. Calcular el subtotal de cada producto.
4. Almacenar productos mediante ArrayList.
5. Registrar categorías sin duplicados mediante HashSet.
6. Acumular gastos por categoría utilizando HashMap.
7. Calcular el total general de las compras.
8. Identificar el producto con mayor gasto.
9. Identificar el producto con menor gasto.
10. Identificar la categoría con mayor gasto.
11. Consultar el total gastado en una categoría.

## Estructura del proyecto

semana-08-control-compras/
- src/
    - Producto.java
    - MainControlCompras.java
- evidencias/
    - Evidencias_Control_Compras_NombreApellido.docx
- README.md
- .gitignore

## Ejecución

1. Abrir el proyecto en IntelliJ IDEA.
2. Verificar que Java JDK esté configurado.
3. Abrir la clase MainControlCompras.java.
4. Ejecutar el método main().
5. Ingresar los datos solicitados.
6. Revisar el resumen de compras.
7. Consultar una categoría.

## Validaciones

El programa no permite registrar productos cuando:

- El nombre está vacío.
- La categoría está vacía.
- El precio unitario es menor o igual a cero.
- La cantidad es menor o igual a cero.

## Pruebas realizadas

- Registro de cinco productos válidos.
- Registro de categorías sin duplicados.
- Validación de precio inválido.
- Validación de cantidad inválida.
- Consulta de categoría existente.
- Consulta de categoría inexistente.
- Comprobación manual de los cálculos.

## Resultados de ejemplo

Productos registrados: 5

Total general: Q107.50

Producto con mayor gasto: Leche - Q25.00

Producto con menor gasto: Pan - Q16.00

Categoría con mayor gasto: Lacteos - Q45.00

## Evidencias

El documento de evidencias se encuentra en la carpeta evidencias.

Incluye capturas del código, pruebas de funcionamiento,
cálculos manuales, análisis previo y reflexión final.

## Autor

Estudiante de Ingeniería en Sistemas

Universidad Mariano Gálvez de Guatemala
