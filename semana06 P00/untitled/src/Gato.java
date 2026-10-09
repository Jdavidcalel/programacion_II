public class Gato extends Mascota {

    private boolean esInterior;

    public Gato(
            String codigoPaciente,
            String nombre,
            int edadMeses,
            double pesoKg,
            boolean esInterior) {

        super(codigoPaciente, nombre, edadMeses, pesoKg);
        this.esInterior = esInterior;
    }

    public boolean isEsInterior() {
        return esInterior;
    }

    @Override
    public void emitirSonido() {
        System.out.println(
                getNombre() + ": ¡Miau!");
    }

    @Override
    public double calcularCostoConsulta() {

        if (isEsInterior()) {
            return 90.00;
        } else {
            return 105.00;
        }
    }
}