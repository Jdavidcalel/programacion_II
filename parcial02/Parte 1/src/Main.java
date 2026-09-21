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
                        mostrarVehiculos(vehiculos);
                        break;

                    case 3:
                        buscarVehiculoPorPlaca(
                                scanner,
                                vehiculos
                        );
                        break;

                    case 4:
                        mostrarVehiculoMayorCosto(vehiculos);
                        break;

                    case 5:
                        mostrarTotalGeneral(vehiculos);
                        break;

                    case 6:
                        System.out.println(
                                "Total recaudado por tipo"
                        );
                        break;

                    case 7:
                        System.out.println(
                                "Saliendo del sistema..."
                        );
                        break;

                    default:
                        System.out.println(
                                "Opción inválida."
                        );
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


    // ==========================================
    // REGISTRAR VEHÍCULO
    // ==========================================

    public static void registrarVehiculo(
            Scanner scanner,
            ArrayList<Vehiculo> vehiculos,
            HashSet<String> placasRegistradas) {

        System.out.println(
                "\n===== REGISTRAR VEHÍCULO ====="
        );

        // PLACA
        System.out.print("Ingrese la placa: ");

        String placa =
                scanner.nextLine().trim().toUpperCase();

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
        System.out.print(
                "Ingrese el nombre del propietario: "
        );

        String propietario =
                scanner.nextLine().trim();

        if (propietario.isEmpty()) {

            System.out.println(
                    "Error: el propietario no puede estar vacío."
            );

            return;
        }


        // HORA DE INGRESO
        System.out.print(
                "Ingrese la hora de ingreso: "
        );

        String horaIngreso =
                scanner.nextLine().trim();

        if (horaIngreso.isEmpty()) {

            System.out.println(
                    "Error: la hora de ingreso no puede estar vacía."
            );

            return;
        }


        // TIPO DE VEHÍCULO
        System.out.println(
                "\nTipo de vehículo:"
        );

        System.out.println("1. Automóvil");
        System.out.println("2. Motocicleta");

        System.out.print(
                "Seleccione una opción: "
        );

        String tipo =
                scanner.nextLine();


        // VALIDAR TIPO
        if (!tipo.equals("1")
                && !tipo.equals("2")) {

            System.out.println(
                    "Error: tipo de vehículo inválido."
            );

            return;
        }


        // HORAS UTILIZADAS
        try {

            System.out.print(
                    "Ingrese las horas utilizadas: "
            );

            double horas =
                    Double.parseDouble(
                            scanner.nextLine()
                    );


            // VALIDAR HORAS
            if (horas <= 0) {

                System.out.println(
                        "Error: las horas deben ser mayores que cero."
                );

                return;
            }


            // POLIMORFISMO
            Vehiculo vehiculo;


            if (tipo.equals("1")) {

                vehiculo =
                        new Automovil(
                                placa,
                                propietario,
                                horaIngreso,
                                horas
                        );

            } else {

                vehiculo =
                        new Motocicleta(
                                placa,
                                propietario,
                                horaIngreso,
                                horas
                        );
            }


            // AGREGAR AL ARRAYLIST
            vehiculos.add(vehiculo);


            // AGREGAR PLACA AL HASHSET
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


    // ==========================================
    // MOSTRAR TODOS LOS VEHÍCULOS
    // ==========================================

    public static void mostrarVehiculos(
            ArrayList<Vehiculo> vehiculos) {

        System.out.println(
                "\n===== VEHÍCULOS REGISTRADOS ====="
        );


        if (vehiculos.isEmpty()) {

            System.out.println(
                    "No hay vehículos registrados."
            );

            return;
        }


        for (Vehiculo vehiculo : vehiculos) {

            String tipo;


            if (vehiculo instanceof Automovil) {

                tipo = "Automóvil";

            } else {

                tipo = "Motocicleta";
            }


            System.out.println(
                    "------------------------------"
            );


            System.out.println(
                    "Placa: "
                            + vehiculo.getPlaca()
            );


            System.out.println(
                    "Propietario: "
                            + vehiculo.getPropietario()
            );


            System.out.println(
                    "Tipo: "
                            + tipo
            );


            System.out.println(
                    "Horas utilizadas: "
                            + vehiculo.getHorasUtilizadas()
            );


            System.out.printf(
                    "Costo: Q%.2f%n",
                    vehiculo.calcularCosto()
            );
        }


        System.out.println(
                "------------------------------"
        );
    }


    // ==========================================
    // BUSCAR VEHÍCULO POR PLACA
    // ==========================================

    public static void buscarVehiculoPorPlaca(
            Scanner scanner,
            ArrayList<Vehiculo> vehiculos) {

        System.out.println(
                "\n===== BUSCAR VEHÍCULO ====="
        );


        if (vehiculos.isEmpty()) {

            System.out.println(
                    "No hay vehículos registrados."
            );

            return;
        }


        System.out.print(
                "Ingrese la placa a buscar: "
        );

        String placaBuscada =
                scanner.nextLine()
                        .trim()
                        .toUpperCase();


        if (placaBuscada.isEmpty()) {

            System.out.println(
                    "Error: la placa no puede estar vacía."
            );

            return;
        }


        Vehiculo encontrado = null;


        for (Vehiculo vehiculo : vehiculos) {

            if (vehiculo.getPlaca()
                    .equalsIgnoreCase(
                            placaBuscada
                    )) {

                encontrado = vehiculo;

                break;
            }
        }


        if (encontrado != null) {

            String tipo;


            if (encontrado instanceof Automovil) {

                tipo = "Automóvil";

            } else {

                tipo = "Motocicleta";
            }


            System.out.println(
                    "\nVehículo encontrado:"
            );


            System.out.println(
                    "------------------------------"
            );


            System.out.println(
                    "Placa: "
                            + encontrado.getPlaca()
            );


            System.out.println(
                    "Propietario: "
                            + encontrado.getPropietario()
            );


            System.out.println(
                    "Hora de ingreso: "
                            + encontrado.getHoraIngreso()
            );


            System.out.println(
                    "Tipo: "
                            + tipo
            );


            System.out.println(
                    "Horas utilizadas: "
                            + encontrado.getHorasUtilizadas()
            );


            System.out.printf(
                    "Costo: Q%.2f%n",
                    encontrado.calcularCosto()
            );


        } else {

            System.out.println(
                    "No se encontró ningún vehículo con la placa "
                            + placaBuscada
                            + "."
            );
        }
    }


    // ==========================================
    // MOSTRAR VEHÍCULO CON MAYOR COSTO
    // ==========================================

    public static void mostrarVehiculoMayorCosto(
            ArrayList<Vehiculo> vehiculos) {

        System.out.println(
                "\n===== VEHÍCULO CON MAYOR COSTO ====="
        );


        if (vehiculos.isEmpty()) {

            System.out.println(
                    "No hay vehículos registrados."
            );

            return;
        }


        Vehiculo mayor =
                vehiculos.get(0);


        for (Vehiculo vehiculo : vehiculos) {

            if (vehiculo.calcularCosto()
                    > mayor.calcularCosto()) {

                mayor = vehiculo;
            }
        }


        String tipo;


        if (mayor instanceof Automovil) {

            tipo = "Automóvil";

        } else {

            tipo = "Motocicleta";
        }


        System.out.println(
                "Placa: "
                        + mayor.getPlaca()
        );


        System.out.println(
                "Propietario: "
                        + mayor.getPropietario()
        );


        System.out.println(
                "Hora de ingreso: "
                        + mayor.getHoraIngreso()
        );


        System.out.println(
                "Tipo: "
                        + tipo
        );


        System.out.println(
                "Horas utilizadas: "
                        + mayor.getHorasUtilizadas()
        );


        System.out.printf(
                "Costo: Q%.2f%n",
                mayor.calcularCosto()
        );
    }


    // ==========================================
    // MOSTRAR TOTAL GENERAL RECAUDADO
    // ==========================================

    public static void mostrarTotalGeneral(
            ArrayList<Vehiculo> vehiculos) {

        System.out.println(
                "\n===== TOTAL GENERAL RECAUDADO ====="
        );


        if (vehiculos.isEmpty()) {

            System.out.println(
                    "No hay vehículos registrados."
            );

            return;
        }


        double total = 0;


        for (Vehiculo vehiculo : vehiculos) {

            total += vehiculo.calcularCosto();
        }


        System.out.printf(
                "Total recaudado: Q%.2f%n",
                total
        );
    }
}