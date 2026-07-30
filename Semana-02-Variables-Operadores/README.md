# Semana 2 - Calculadora de edad

## Nombre de la actividad

Calculadora de edad aproximada utilizando variables y operadores en Java.

## Descripción del programa

Este programa de consola solicita el nombre completo, carné, año de nacimiento y año actual del usuario.

Con los datos ingresados, calcula la edad aproximada, la edad aproximada en meses y determina si la persona es mayor de edad mediante una variable de tipo boolean.

La edad se considera aproximada porque solamente se utilizan el año de nacimiento y el año actual.

## Datos solicitados

- Nombre completo.
- Carné.
- Año de nacimiento.
- Año actual.

## Cálculos realizados

La edad aproximada se calcula restando el año de nacimiento al año actual:

```java
edadAproximada = anioActual - anioNacimiento;