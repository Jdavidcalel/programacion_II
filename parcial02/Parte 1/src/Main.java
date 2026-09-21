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
                        System.out.println("Registrar vehículo");
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
                System.out.println("Error: debe ingresar un número.");
                opcion = 0;
            }

        } while (opcion != 7);

        scanner.close();
    }
}
