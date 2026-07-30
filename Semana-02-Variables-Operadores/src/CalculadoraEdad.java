import java.util.Scanner;

public class CalculadoraEdad {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        String nombreCompleto;
        String carne;
        int anioNacimiento;
        int anioActual;
        int edadAproximada;
        int edadEnMeses;
        boolean esMayorDeEdad;

        System.out.println("===== CALCULADORA DE EDAD =====");

        System.out.print("Ingrese su nombre completo: ");
        nombreCompleto = entrada.nextLine();

        System.out.print("Ingrese su carné: ");
        carne = entrada.nextLine();

        System.out.print("Ingrese su año de nacimiento: ");
        anioNacimiento = entrada.nextInt();

        System.out.print("Ingrese el año actual: ");
        anioActual = entrada.nextInt();

        // Calcula una edad aproximada
        edadAproximada = anioActual - anioNacimiento;

        // Convierte la edad aproximada de años a meses
        edadEnMeses = edadAproximada * 12;

        esMayorDeEdad = edadAproximada >= 18;

        System.out.println("\n----- RESULTADO -----");
        System.out.println("Nombre: " + nombreCompleto);
        System.out.println("Carné: " + carne);
        System.out.println("Edad aproximada: " + edadAproximada + " años");
        System.out.println("Edad aproximada en meses: " + edadEnMeses + " meses");
        System.out.println("¿Es mayor de edad?: " + esMayorDeEdad);
        System.out.println("\nLa edad mostrada es aproximada, el cálculo utiliza únicamente los años.");

        entrada.close();
    }
}