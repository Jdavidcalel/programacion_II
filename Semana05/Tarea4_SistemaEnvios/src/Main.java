import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n==========================");
            System.out.println("     SISTEMA DE ENVÍOS");
            System.out.println("==========================");
            System.out.println("1. Registrar envío nacional");
            System.out.println("2. Registrar envío internacional");
            System.out.println("3. Salir");

            opcion = leerEntero(scanner, "Seleccione una opción: ");

            switch (opcion) {

                case 1:
                    registrarEnvioNacional(scanner);
                    break;

                case 2:
                    registrarEnvioInternacional(scanner);
                    break;

                case 3:
                    System.out.println("\nGracias por utilizar el sistema.");
                    break;

                default:
                    System.out.println("\nOpción inválida. Intente nuevamente.");
            }

        } while (opcion != 3);

        scanner.close();
    }

    public static void registrarEnvioNacional(Scanner scanner) {

        System.out.println("\n--- REGISTRO DE ENVÍO NACIONAL ---");

        String codigo = leerTextoNoVacio(
                scanner,
                "Código del envío: "
        );

        String destinatario = leerTextoNoVacio(
                scanner,
                "Nombre del destinatario: "
        );

        double peso = leerDoublePositivo(
                scanner,
                "Peso del paquete en kg: "
        );

        String departamento = leerTextoNoVacio(
                scanner,
                "Departamento de destino: "
        );

        double distancia = leerDoublePositivo(
                scanner,
                "Distancia en kilómetros: "
        );

        Envio envio = new EnvioNacional(
                codigo,
                destinatario,
                peso,
                departamento,
                distancia
        );

        envio.mostrarResumen(true);
    }

    public static void registrarEnvioInternacional(Scanner scanner) {

        System.out.println("\n--- REGISTRO DE ENVÍO INTERNACIONAL ---");

        String codigo = leerTextoNoVacio(
                scanner,
                "Código del envío: "
        );

        String destinatario = leerTextoNoVacio(
                scanner,
                "Nombre del destinatario: "
        );

        double peso = leerDoublePositivo(
                scanner,
                "Peso del paquete en kg: "
        );

        String pais = leerTextoNoVacio(
                scanner,
                "País de destino: "
        );

        Envio envio = new EnvioInternacional(
                codigo,
                destinatario,
                peso,
                pais
        );

        envio.mostrarResumen(true);
    }

    public static String leerTextoNoVacio(
            Scanner scanner,
            String mensaje) {

        String texto;

        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println(
                        "El dato no puede estar vacío."
                );
            }

        } while (texto.isEmpty());

        return texto;
    }

    public static double leerDoublePositivo(
            Scanner scanner,
            String mensaje) {

        double valor = 0;
        boolean valido = false;

        while (!valido) {

            System.out.print(mensaje);

            String entrada = scanner.nextLine();

            try {
                valor = Double.parseDouble(entrada);

                if (valor > 0) {
                    valido = true;
                } else {
                    System.out.println(
                            "El valor debe ser mayor que cero."
                    );
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "Entrada inválida. Ingrese un número."
                );
            }
        }

        return valor;
    }

    public static int leerEntero(
            Scanner scanner,
            String mensaje) {

        int valor = 0;
        boolean valido = false;

        while (!valido) {

            System.out.print(mensaje);

            String entrada = scanner.nextLine();

            try {
                valor = Integer.parseInt(entrada);
                valido = true;

            } catch (NumberFormatException e) {
                System.out.println(
                        "Entrada inválida. Ingrese un número entero."
                );
            }
        }

        return valor;
    }
}