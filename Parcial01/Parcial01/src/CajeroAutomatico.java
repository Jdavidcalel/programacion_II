import java.util.Scanner;

public class CajeroAutomatico {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String nombre;
        int pin;
        int opcion;
        int tipoDeposito;

        double saldo = 1000.00;
        double cantidad;

        String[] movimientos = new String[100];
        int contadorMovimientos = 0;

        System.out.println("===== CAJERO AUTOMÁTICO =====");

        System.out.print("Ingrese su nombre: ");
        nombre = scanner.nextLine();

        System.out.print("Ingrese su PIN: ");
        pin = scanner.nextInt();

        if (pin == 1234) {

            System.out.println("Acceso autorizado.");
            System.out.println("Bienvenido " + nombre);
            System.out.println("Su saldo inicial es: Q" + saldo);

            do {

                mostrarMenu();

                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {

                    case 1:

                        consultarSaldo(saldo);
                        break;

                    case 2:

                        System.out.println("\n===== TIPO DE DEPÓSITO =====");
                        System.out.println("1. Depósito normal");
                        System.out.println("2. Depósito con bono");
                        System.out.print("Seleccione una opción: ");
                        tipoDeposito = scanner.nextInt();

                        System.out.print("Ingrese la cantidad a depositar: Q");
                        cantidad = scanner.nextDouble();

                        if (cantidad > 0) {

                            if (tipoDeposito == 1) {

                                saldo = depositar(saldo, cantidad);

                                movimientos[contadorMovimientos] =
                                        "Depósito: Q" + cantidad;

                                contadorMovimientos++;

                                System.out.println(
                                        "Depósito normal realizado correctamente."
                                );

                                System.out.println(
                                        "Nuevo saldo: Q" + saldo
                                );

                            } else if (tipoDeposito == 2) {

                                double bono = 50.00;

                                saldo = depositar(
                                        saldo,
                                        cantidad,
                                        bono
                                );

                                movimientos[contadorMovimientos] =
                                        "Depósito con bono: Q"
                                                + cantidad
                                                + " + bono Q"
                                                + bono;

                                contadorMovimientos++;

                                System.out.println(
                                        "Depósito con bono realizado correctamente."
                                );

                                System.out.println(
                                        "Bono aplicado: Q" + bono
                                );

                                System.out.println(
                                        "Nuevo saldo: Q" + saldo
                                );

                            } else {

                                System.out.println(
                                        "Tipo de depósito no válido."
                                );
                            }

                        } else {

                            System.out.println(
                                    "La cantidad debe ser mayor que cero."
                            );
                        }

                        break;

                    case 3:

                        System.out.print(
                                "Ingrese la cantidad a retirar: Q"
                        );

                        cantidad = scanner.nextDouble();

                        if (cantidad <= 0) {

                            System.out.println(
                                    "La cantidad debe ser mayor que cero."
                            );

                        } else if (cantidad > saldo) {

                            System.out.println(
                                    "Fondos insuficientes."
                            );

                            System.out.println(
                                    "Su saldo disponible es: Q" + saldo
                            );

                        } else {

                            saldo = retirar(saldo, cantidad);

                            movimientos[contadorMovimientos] =
                                    "Retiro: Q" + cantidad;

                            contadorMovimientos++;

                            System.out.println(
                                    "Retiro realizado correctamente."
                            );

                            System.out.println(
                                    "Nuevo saldo: Q" + saldo
                            );
                        }

                        break;

                    case 4:

                        mostrarMovimientos(
                                movimientos,
                                contadorMovimientos
                        );

                        break;

                    case 5:

                        System.out.println(
                                "Gracias por utilizar el cajero."
                        );

                        break;

                    default:

                        System.out.println(
                                "Opción no válida."
                        );
                }

            } while (opcion != 5);

        } else {

            System.out.println("PIN incorrecto.");
        }

        scanner.close();
    }

    public static void mostrarMenu() {

        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Depositar dinero");
        System.out.println("3. Retirar dinero");
        System.out.println("4. Mostrar movimientos");
        System.out.println("5. Salir");
    }

    public static void consultarSaldo(double saldo) {

        System.out.println(
                "Su saldo actual es: Q" + saldo
        );
    }

    public static double depositar(
            double saldo,
            double cantidad
    ) {

        double nuevoSaldo = saldo + cantidad;

        return nuevoSaldo;
    }

    public static double depositar(
            double saldo,
            double cantidad,
            double bono
    ) {

        double nuevoSaldo =
                saldo + cantidad + bono;

        return nuevoSaldo;
    }
    public static double retirar(
            double saldo,
            double cantidad
    ) {

        double nuevoSaldo =
                saldo - cantidad;

        return nuevoSaldo;
    }
    public static void mostrarMovimientos(
            String[] movimientos,
            int cantidad
    ) {

        System.out.println(
                "\n===== MOVIMIENTOS ====="
        );

        if (cantidad == 0) {

            System.out.println(
                    "No se han realizado movimientos."
            );

        } else {

            for (int i = 0; i < cantidad; i++) {

                System.out.println(
                        (i + 1)
                                + ". "
                                + movimientos[i]
                );
            }
        }
    }
}