public class Perro extends Mascota {

    private String raza;

    public Perro(
            String codigoPaciente,
            String nombre,
            int edadMeses,
            double pesoKg,
            String raza) {

        super(codigoPaciente, nombre, edadMeses, pesoKg);
        this.raza = raza;
    }

    public String getRaza() {
        return raza;
    }

    @Override
    public void emitirSonido() {
        System.out.println(
                getNombre() + ": ¡Guau!");
    }

    @Override
    public double calcularCostoConsulta() {

        if (getPesoKg() > 25) {
            return 125.00;
        } else {
            return 100.00;
        }
    }
}