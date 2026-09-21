import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Vehiculo> vehiculos = new ArrayList<>();
        HashSet<String> placasRegistradas = new HashSet<>();
        HashMap<String, Double> totalesPorTipo = new HashMap<>();

        int opcion;

        do {
            System.out.println("\n========== ESTACIONAMIENTO ==========");
            System.out.println("1. Registrar vehículo");
            System.out.println("2. Mostrar todos los vehículos");
            System.out.println("3. Buscar vehículo por placa");
            System.out.println("4. Mostrar vehículo con mayor costo");
            System.out.println("5. Mostrar total general recaudado");
            System.out.println("6. Mostrar total recaudado por tipo");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {

                    case 1:
                        registrarVehiculo(
                                scanner,
                                vehiculos,
                                placasRegistradas
                        );
                        break;

                    case 2:
                        System.out.println("Mostrar vehículos");
                        break;

                    case 3:
                        System.out.println("Buscar vehículo");
                        break;

                    case 4:
                        System.out.println("Vehículo con mayor costo");
                        break;

                    case 5:
                        System.out.println("Total general recaudado");
                        break;

                    case 6:
                        System.out.println("Total recaudado por tipo");
                        break;

                    case 7:
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opción inválida.");
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: debe ingresar un número."
                );

                opcion = 0;
            }

        } while (opcion != 7);

        scanner.close();
    }


    public static void registrarVehiculo(
            Scanner scanner,
            ArrayList<Vehiculo> vehiculos,
            HashSet<String> placasRegistradas) {

        System.out.println("\n===== REGISTRAR VEHÍCULO =====");

        // PLACA
        System.out.print("Ingrese la placa: ");
        String placa = scanner.nextLine().trim().toUpperCase();

        if (placa.isEmpty()) {
            System.out.println(
                    "Error: la placa no puede estar vacía."
            );
            return;
        }

        // VALIDAR PLACA DUPLICADA
        if (placasRegistradas.contains(placa)) {
            System.out.println(
                    "Error: la placa ya está registrada."
            );
            return;
        }

        // PROPIETARIO
        System.out.print("Ingrese el nombre del propietario: ");
        String propietario = scanner.nextLine().trim();

        if (propietario.isEmpty()) {
            System.out.println(
                    "Error: el propietario no puede estar vacío."
            );
            return;
        }

        // HORA DE INGRESO
        System.out.print("Ingrese la hora de ingreso: ");
        String horaIngreso = scanner.nextLine().trim();

        if (horaIngreso.isEmpty()) {
            System.out.println(
                    "Error: la hora de ingreso no puede estar vacía."
            );
            return;
        }

        // TIPO DE VEHÍCULO
        System.out.println("\nTipo de vehículo:");
        System.out.println("1. Automóvil");
        System.out.println("2. Motocicleta");
        System.out.print("Seleccione una opción: ");

        String tipo = scanner.nextLine();

        if (!tipo.equals("1") && !tipo.equals("2")) {
            System.out.println(
                    "Error: tipo de vehículo inválido."
            );
            return;
        }

        // HORAS UTILIZADAS
        try {

            System.out.print("Ingrese las horas utilizadas: ");

            double horas =
                    Double.parseDouble(scanner.nextLine());

            if (horas <= 0) {
                System.out.println(
                        "Error: las horas deben ser mayores que cero."
                );
                return;
            }

            // POLIMORFISMO
            Vehiculo vehiculo;

            if (tipo.equals("1")) {

                vehiculo = new Automovil(
                        placa,
                        propietario,
                        horaIngreso,
                        horas
                );

            } else {

                vehiculo = new Motocicleta(
                        placa,
                        propietario,
                        horaIngreso,
                        horas
                );
            }

            // GUARDAR EN LAS COLECCIONES
            vehiculos.add(vehiculo);
            placasRegistradas.add(placa);

            System.out.println(
                    "\nVehículo registrado correctamente."
            );

            System.out.printf(
                    "Costo: Q%.2f%n",
                    vehiculo.calcularCosto()
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Error: las horas deben ingresarse como un número."
            );

        } finally {

            System.out.println(
                    "Proceso de registro finalizado."
            );
        }
    }
}