
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        // Datos del estudiante
        System.out.println("Estudiante: Juan David Chiquin Calel");
        System.out.println("Carné: 9941-21-1056");
        System.out.println("Semana 3 — Condiciones y ciclos");

        // Menú principal
        do {
            System.out.println("\n========= DESAFÍOS LÓGICOS =========");
            System.out.println("1. Generar una secuencia");
            System.out.println("2. Realizar un conteo regresivo");
            System.out.println("3. Analizar números");
            System.out.println("4. Dibujar una pirámide");
            System.out.println("5. Validar palabra secreta");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            // Validación de entrada numérica
            if (!scanner.hasNextInt()) {
                System.out.println("Error: ingrese una opción numérica.");
                scanner.nextLine();
                continue;
            }

            opcion = scanner.nextInt();

            // Evaluar la opción seleccionada
            switch (opcion) {
                case 1:
                    // Opción 1: Generar una secuencia
                    System.out.println("\n--- GENERAR UNA SECUENCIA ---");

                    System.out.print("Ingrese el número inicial: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Error: debe ingresar un número entero.");
                        scanner.nextLine();
                        break;
                    }
                    int inicio = scanner.nextInt();

                    System.out.print("Ingrese el número final: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Error: debe ingresar un número entero.");
                        scanner.nextLine();
                        break;
                    }
                    int fin = scanner.nextInt();

                    System.out.print("Ingrese el incremento: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Error: debe ingresar un número entero.");
                        scanner.nextLine();
                        break;
                    }
                    int incremento = scanner.nextInt();

                    // Validaciones de los números ingresados
                    if (incremento <= 0) {
                        System.out.println("Error: el incremento debe ser mayor que cero.");

                    } else if (fin <= inicio) {
                        System.out.println("Error: el número final debe ser mayor que el inicial.");

                    } else {
                        System.out.println("\nResultado:");

                        // Generar secuencia utilizando for
                        for (long i = inicio; i <= fin; i += incremento) {
                            System.out.print(i + " ");
                        }

                        System.out.println();
                    }

                    break;

                case 2:
                    // Opción 2: Realizar un conteo regresivo
                    System.out.println("\n--- CONTEO REGRESIVO ---");

                    int numeroRegresivo = 0;
                    boolean numeroValido = false;

                    // Solicitar un número válido entre 10 y 50
                    while (!numeroValido) {

                        System.out.print("Ingrese un número entre 10 y 50: ");

                        // Validar que el dato sea un número entero
                        if (!scanner.hasNextInt()) {
                            System.out.println("Error: debe ingresar un número entero.");
                            scanner.nextLine();
                            continue;
                        }

                        numeroRegresivo = scanner.nextInt();

                        if (numeroRegresivo >= 10 && numeroRegresivo <= 50) {
                            numeroValido = true;
                        } else {
                            System.out.println("Error: el número debe estar entre 10 y 50.");
                        }
                    }

                    // Realizar el conteo regresivo
                    System.out.println("\nResultado:");

                    while (numeroRegresivo >= 0) {
                        System.out.print(numeroRegresivo + " ");
                        numeroRegresivo--;
                    }

                    System.out.println("\n¡Despegue!");

                    break;


                case 3:
                    // Opción 3: Analizar números
                    System.out.println("\n--- ANALIZAR NÚMEROS ---");

                    int positivos = 0;
                    int negativos = 0;
                    int ignorados = 0;
                    long suma = 0;

                    // Solicitar números hasta que el usuario ingrese 0
                    while (true) {

                        System.out.print("Ingrese un número: ");

                        // Validar que se ingrese un número entero
                        if (!scanner.hasNextInt()) {
                            System.out.println("Error: ingrese un número entero válido.");
                            scanner.nextLine();
                            continue;
                        }

                        int numero = scanner.nextInt();

                        // El número 0 finaliza el ingreso
                        if (numero == 0) {
                            break;
                        }

                        // Contar números positivos y negativos
                        if (numero > 0) {
                            positivos++;
                        } else {
                            negativos++;
                        }

                        // Ignorar múltiplos de 5
                        if (numero % 5 == 0) {
                            ignorados++;
                            System.out.println("El número " + numero + " fue ignorado.");
                            continue;
                        }

                        // Sumar únicamente los números válidos
                        suma += numero;
                    }

                    // Mostrar los resultados
                    System.out.println("\nResultado:");
                    System.out.println("Positivos: " + positivos);
                    System.out.println("Negativos: " + negativos);
                    System.out.println("Suma válida: " + suma);
                    System.out.println("Números ignorados: " + ignorados);

                    break;


                case 4:
                    // Opción 4: Dibujar una pirámide
                    System.out.println("\n--- DIBUJAR UNA PIRÁMIDE ---");

                    System.out.print("Ingrese la altura (3 a 10): ");

                    // Validar que se ingrese un número entero
                    if (!scanner.hasNextInt()) {
                        System.out.println("Error: debe ingresar un número entero.");
                        scanner.nextLine();
                        break;
                    }

                    int altura = scanner.nextInt();

                    // Validar la altura permitida
                    if (altura < 3 || altura > 10) {
                        System.out.println("Error: la altura debe estar entre 3 y 10.");
                    } else {

                        System.out.println("\nResultado:");

                        // Ciclo principal: controla las filas
                        for (int fila = 1; fila <= altura; fila++) {

                            // Ciclo para imprimir espacios
                            for (int espacio = 1; espacio <= altura - fila; espacio++) {
                                System.out.print(" ");
                            }

                            // Ciclo para imprimir asteriscos
                            for (int estrella = 1; estrella <= (2 * fila - 1); estrella++) {
                                System.out.print("*");
                            }

                            // Salto de línea al finalizar cada fila
                            System.out.println();
                        }
                    }

                    break;


                case 5:
                    // Opción 5: Validar palabra secreta
                    System.out.println("\n--- VALIDAR PALABRA SECRETA ---");

                    // Limpiar el salto de línea pendiente del Scanner
                    scanner.nextLine();

                    String palabra;

                    // Solicitar la palabra hasta que sea correcta
                    do {
                        System.out.print("Ingrese la palabra secreta: ");
                        palabra = scanner.nextLine().trim();

                        // Comparar sin distinguir mayúsculas y minúsculas
                        if (palabra.equalsIgnoreCase("Guatemala")) {
                            System.out.println("Palabra correcta.");
                        } else {
                            System.out.println("Palabra incorrecta. Intente nuevamente.");
                        }

                    } while (!palabra.equalsIgnoreCase("Guatemala"));

                    break;

                case 6:
                    System.out.println("Programa finalizado correctamente.");
                    break;

                default:
                    System.out.println("Opción inexistente. Intente nuevamente.");
                    break;
            }

        } while (opcion != 6);

        scanner.close();
    }
}
