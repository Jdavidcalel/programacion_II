import java.util.Scanner;

public class ControlParqueo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int cantidadVehiculos;

        System.out.println("==================================");
        System.out.println("     SISTEMA DE CONTROL DE PARQUEO");
        System.out.println("==================================");

        do {
            System.out.print("Ingrese la cantidad de vehículos a registrar: ");
            cantidadVehiculos = scanner.nextInt();

            if (cantidadVehiculos <= 0) {
                System.out.println("Error: la cantidad debe ser mayor que cero.");
            }

        } while (cantidadVehiculos <= 0);

        int cantidadMotos = 0;
        int cantidadAutomoviles = 0;
        int cantidadPickups = 0;
        int cantidadTicketsPerdidos = 0;

        double totalRecaudado = 0;

        double pagoMasAlto = 0;
        String placaPagoMasAlto = "";
        String tipoPagoMasAlto = "";

        for (int i = 1; i <= cantidadVehiculos; i++) {

            System.out.println();
            System.out.println("========== VEHÍCULO " + i + " ==========");

            System.out.print("Ingrese número de placa: ");
            String placa = scanner.next();

            int tipoVehiculo;

            do {
                System.out.println("Tipo de vehículo:");
                System.out.println("1. Motocicleta");
                System.out.println("2. Automóvil");
                System.out.println("3. Pickup o camioneta");
                System.out.print("Seleccione una opción: ");

                tipoVehiculo = scanner.nextInt();

                if (tipoVehiculo < 1 || tipoVehiculo > 3) {
                    System.out.println("Error: debe ingresar una opción entre 1 y 3.");
                }

            } while (tipoVehiculo < 1 || tipoVehiculo > 3);

            double tarifa = obtenerTarifa(tipoVehiculo);
            String nombreVehiculo = obtenerNombreVehiculo(tipoVehiculo);

            if (tipoVehiculo == 1) {
                cantidadMotos++;
            } else if (tipoVehiculo == 2) {
                cantidadAutomoviles++;
            } else {
                cantidadPickups++;
            }
            int horas;

            do {
                System.out.print("Ingrese cantidad de horas estacionadas: ");
                horas = scanner.nextInt();

                if (horas <= 0) {
                    System.out.println("Error: las horas deben ser mayores que cero.");
                }

            } while (horas <= 0);

            String ticketPerdido;

            do {
                System.out.print("¿Perdió el ticket? (S/N): ");
                ticketPerdido = scanner.next();

                if (!ticketPerdido.equalsIgnoreCase("S")
                        && !ticketPerdido.equalsIgnoreCase("N")) {

                    System.out.println("Error: debe ingresar únicamente S o N.");
                }

            } while (!ticketPerdido.equalsIgnoreCase("S")
                    && !ticketPerdido.equalsIgnoreCase("N"));

            double subtotal = horas * tarifa;
            double descuento = calcularDescuento(subtotal, horas);
            double recargo = 0;
            double totalPagar;

            if (ticketPerdido.equalsIgnoreCase("S")) {

                recargo = 50.00;
                cantidadTicketsPerdidos++;
                totalPagar = calcularPago(horas, tarifa, recargo);

            } else {

                totalPagar = calcularPago(horas, tarifa);
            }

            totalRecaudado += totalPagar;

            if (totalPagar > pagoMasAlto) {

                pagoMasAlto = totalPagar;
                placaPagoMasAlto = placa;
                tipoPagoMasAlto = nombreVehiculo;
            }

            mostrarComprobante(
                    placa,
                    nombreVehiculo,
                    horas,
                    tarifa,
                    subtotal,
                    descuento,
                    recargo,
                    totalPagar
            );
        }

        System.out.println();
        System.out.println("========== RESUMEN DE LA JORNADA ==========");
        System.out.println("Cantidad de motocicletas: " + cantidadMotos);
        System.out.println("Cantidad de automóviles: " + cantidadAutomoviles);
        System.out.println("Cantidad de pickups o camionetas: " + cantidadPickups);
        System.out.println("Cantidad de tickets perdidos: " + cantidadTicketsPerdidos);
        System.out.printf("Total de dinero recaudado: Q%.2f%n", totalRecaudado);

        System.out.println();
        System.out.println("Vehículo con el pago más alto:");
        System.out.println("Placa: " + placaPagoMasAlto);
        System.out.println("Tipo: " + tipoPagoMasAlto);
        System.out.printf("Pago: Q%.2f%n", pagoMasAlto);
        System.out.println("============================================");

        scanner.close();
    }

    public static double obtenerTarifa(int tipoVehiculo) {
        switch (tipoVehiculo) {
            case 1:
                return 5.00;
            case 2:
                return 8.00;
            case 3:
                return 12.00;
            default:
                return 0;
        }
    }

    public static String obtenerNombreVehiculo(int tipoVehiculo) {
        switch (tipoVehiculo) {
            case 1:
                return "Motocicleta";
            case 2:
                return "Automóvil";
            case 3:
                return "Pickup o camioneta";
            default:
                return "Desconocido";
        }
    }

    public static double calcularDescuento(double subtotal, int horas) {
        if (horas > 8) {
            return subtotal * 0.15;
        } else {
            return 0;
        }
    }

    public static double calcularPago(int horas, double tarifa) {
        double subtotal = horas * tarifa;
        double descuento = calcularDescuento(subtotal, horas);
        return subtotal - descuento;
    }

    public static double calcularPago(
            int horas,
            double tarifa,
            double recargo) {

        double subtotal = horas * tarifa;
        double descuento = calcularDescuento(subtotal, horas);
        return subtotal - descuento + recargo;
    }

    public static void mostrarComprobante(
            String placa,
            String nombreVehiculo,
            int horas,
            double tarifa,
            double subtotal,
            double descuento,
            double recargo,
            double totalPagar) {

        System.out.println();
        System.out.println("========== COMPROBANTE ==========");
        System.out.println("Placa: " + placa);
        System.out.println("Tipo: " + nombreVehiculo);
        System.out.println("Horas estacionado: " + horas);
        System.out.printf("Tarifa por hora: Q%.2f%n", tarifa);
        System.out.printf("Subtotal: Q%.2f%n", subtotal);
        System.out.printf("Descuento: Q%.2f%n", descuento);
        System.out.printf("Recargo por ticket perdido: Q%.2f%n", recargo);
        System.out.printf("TOTAL: Q%.2f%n", totalPagar);
        System.out.println("=================================");
    }
}