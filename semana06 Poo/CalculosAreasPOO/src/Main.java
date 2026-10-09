public class Main {

    public static void main(String[] args) {
        // Arreglo
        Figura[] figuras = {
                new Circulo(5),
                new Rectangulo(8, 4),
                new Triangulo(10, 6)
        };
        System.out.println("============David===================");
        System.out.println("   CALCULO DE AREAS CON POO");
        System.out.println("============Calel===================");
        // Figuras
        for (Figura figura : figuras) {
            figura.mostrarInformacion();
            System.out.printf(
                    "Area: %.2f%n",
                    figura.calcularArea()
            );

            System.out.println("--------------------------------");
        }
        System.out.println("Programa finalizado correctamente.");
    }
}
