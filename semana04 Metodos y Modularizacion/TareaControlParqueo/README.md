# Tarea III - Procedimientos y Funciones en Java

## Control de Parqueo

### Datos del estudiante

**Nombre:** Juan Chiquin  
**Carné:** 9941-21-16056 
**Curso:** Programación en Java  
**Actividad:** Tarea III - Procedimientos y Funciones en Java

---

## Descripción del programa

El programa **Control de Parqueo** fue desarrollado en Java y funciona mediante consola.

Su objetivo es registrar varios vehículos que utilizan un parqueo, calcular el monto que debe pagar cada uno según el tipo de vehículo y las horas estacionadas, y mostrar un resumen general al finalizar la jornada.

El sistema permite registrar:

- Número de placa.
- Tipo de vehículo.
- Cantidad de horas estacionadas.
- Si el conductor perdió o no el ticket.

Las tarifas utilizadas son:

| Tipo de vehículo | Tarifa por hora |
|---|---:|
| Motocicleta | Q5.00 |
| Automóvil | Q8.00 |
| Pickup o camioneta | Q12.00 |

Cuando un vehículo permanece **más de 8 horas**, se aplica un descuento del **15 %** sobre el subtotal.

Si el conductor perdió el ticket, se agrega un recargo de **Q50.00** después de calcular el descuento correspondiente.

---

## Métodos creados

### `obtenerTarifa(int tipoVehiculo)`

Este método devuelve la tarifa por hora correspondiente al tipo de vehículo seleccionado:

- Motocicleta: Q5.00
- Automóvil: Q8.00
- Pickup o camioneta: Q12.00

### `obtenerNombreVehiculo(int tipoVehiculo)`

Este método devuelve el nombre del vehículo según la opción seleccionada por el usuario.

### `calcularDescuento(double subtotal, int horas)`

Este método calcula el descuento del 15 % cuando el vehículo permanece más de 8 horas en el parqueo. Si permanece 8 horas o menos, no aplica descuento.

### `calcularPago(int horas, double tarifa)`

Este método calcula el pago cuando el conductor no perdió el ticket. Calcula el subtotal y aplica el descuento cuando corresponde.

### `calcularPago(int horas, double tarifa, double recargo)`

Este método calcula el pago cuando el conductor perdió el ticket. Calcula el subtotal, aplica el descuento correspondiente y agrega el recargo de Q50.00.

### `mostrarComprobante(...)`

Este es un método `void` que muestra el comprobante individual de cada vehículo con la placa, tipo de vehículo, horas, tarifa, subtotal, descuento, recargo y total a pagar.

---

## Sobrecarga de métodos

La sobrecarga se aplicó en el método `calcularPago`.

Se crearon dos métodos con el mismo nombre, pero con diferente cantidad de parámetros:

```java
public static double calcularPago(int horas, double tarifa)

---

## Casos de prueba

Para comprobar el funcionamiento del programa se realizaron los siguientes casos de prueba:

### Caso 1 - Ejecución normal

Se registró una motocicleta con los siguientes datos:

- Placa: `M111AAA`
- Tipo: Motocicleta
- Horas estacionadas: 4
- Ticket perdido: N

Resultado:

- Tarifa por hora: Q5.00
- Subtotal: Q20.00
- Descuento: Q0.00
- Recargo: Q0.00
- Total a pagar: Q20.00

### Caso 2 - Descuento y ticket perdido

Se registró un automóvil con los siguientes datos:

- Placa: `P123ABC`
- Tipo: Automóvil
- Horas estacionadas: 10
- Ticket perdido: S

Resultado:

- Tarifa por hora: Q8.00
- Subtotal: Q80.00
- Descuento del 15 %: Q12.00
- Recargo por ticket perdido: Q50.00
- Total a pagar: Q118.00

### Caso 3 - Validación de datos incorrectos

Se probaron diferentes datos inválidos para comprobar las validaciones del programa:

- Cantidad de vehículos igual a 0.
- Tipo de vehículo fuera del rango de 1 a 3.
- Cantidad de horas igual a 0.
- Respuesta de ticket diferente de S o N.

El programa rechaza estos datos y solicita nuevamente la información hasta recibir un valor válido.

### Caso 4 - Registro de varios vehículos

Se registraron tres vehículos para comprobar los contadores, acumuladores y el resumen de la jornada:

1. Motocicleta: Q20.00
2. Automóvil con descuento y ticket perdido: Q118.00
3. Pickup o camioneta: Q60.00

Total recaudado: **Q198.00**

El vehículo con el pago más alto fue el automóvil con placa `P123ABC`, con un pago de **Q118.00**.

---

## Reto opcional

No se realizó el reto opcional de control exacto de hora y minutos.

El programa desarrollado corresponde a los requerimientos de la actividad ordinaria y solicita directamente la cantidad de horas estacionadas.

---

## Temas aplicados

Durante el desarrollo del programa se utilizaron:

- Variables y tipos de datos.
- Operadores aritméticos, relacionales y lógicos.
- Entrada de datos mediante `Scanner`.
- Condiciones `if`, `else` y `switch`.
- Ciclo `for`.
- Ciclo `do while`.
- Métodos `static`.
- Métodos con `return`.
- Métodos `void`.
- Parámetros y argumentos.
- Sobrecarga de métodos.
- Contadores y acumuladores.

---

## Herramientas utilizadas

- Java
- IntelliJ IDEA
- Git
- GitHub

---

## Estado del proyecto

El programa registra varios vehículos, valida los datos ingresados, calcula tarifas, descuentos y recargos, genera comprobantes individuales y muestra un resumen general de la jornada.