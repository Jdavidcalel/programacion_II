public class Main {

    public static void main(String[] args) {

        Perro perro1 = new Perro(
                "VET-001",
                "Max",
                36,
                30.5,
                "Labrador");

        Gato gato1 = new Gato(
                "VET-002",
                "Michi",
                24,
                4.5,
                true);

        Ave ave1 = new Ave(
                "VET-003",
                "Piolín",
                12,
                0.8,
                "Exótica");

        Mascota[] mascotas = {
                perro1,
                gato1,
                ave1
        };

        for (Mascota mascota : mascotas) {

            System.out.println("---");

            System.out.println(
                    "Código: " +
                            mascota.getCodigoPaciente());

            System.out.println(
                    mascota.mostrarInformacion());

            mascota.emitirSonido();

            double costo =
                    mascota.calcularCostoConsulta();

            System.out.println(
                    "Costo de consulta: Q" + costo);
        }
    }
}

