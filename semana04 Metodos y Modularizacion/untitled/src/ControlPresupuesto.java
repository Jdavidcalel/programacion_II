import java.util.Scanner;

public class ControlPresupuesto {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese su presupuesto semanal: Q");
        double presupuesto = scanner.nextDouble();

        System.out.print("Gasto de alimentación: Q");
        double alimentacion = scanner.nextDouble();

        System.out.print("Gasto de transporte: Q");
        double transporte = scanner.nextDouble();

        System.out.print("Otros gastos: Q");
        double otros = scanner.nextDouble();

        double totalGastos = calcularTotalGastos(
                alimentacion,
                transporte,
                otros
        );

        double saldo = calcularSaldo(
                presupuesto,
                totalGastos
        );

        String estado = obtenerEstado(saldo);

        mostrarResumen(
                nombre,
                presupuesto,
                totalGastos,
                saldo,
                estado
        );

        scanner.close();
    }

    public static double calcularTotalGastos(double alimentacion,
                                             double transporte,
                                             double otros) {

        double totalGastos = alimentacion + transporte + otros;

        return totalGastos;
    }

    public static double calcularSaldo(double presupuesto,
                                       double totalGastos) {

        double saldo = presupuesto - totalGastos;

        return saldo;
    }

    public static String obtenerEstado(double saldo) {

        String estado;

        if (saldo > 0) {
            estado = "Dentro del presupuesto";
        } else if (saldo == 0) {
            estado = "Presupuesto agotado";
        } else {
            estado = "Excedió el presupuesto";
        }

        return estado;
    }

    public static void mostrarResumen(String nombre,
                                      double presupuesto,
                                      double totalGastos,
                                      double saldo,
                                      String estado) {

        System.out.println("\n--- RESUMEN SEMANAL ---");
        System.out.println("Nombre: " + nombre);
        System.out.println("Presupuesto: Q" + presupuesto);
        System.out.println("Total gastado: Q" + totalGastos);
        System.out.println("Saldo: Q" + saldo);
        System.out.println("Estado: " + estado);
    }
}