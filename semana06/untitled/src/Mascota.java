public abstract class Mascota {

    private String nombre;
    private int edadMeses;
    private double pesoKg;

    public Mascota(
            String nombre,
            int edadMeses,
            double pesoKg) {

        this.nombre = nombre;
        this.edadMeses = edadMeses;

        if (pesoKg > 0) {
            this.pesoKg = pesoKg;
        } else {
            this.pesoKg = 0.1;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdadMeses() {
        return edadMeses;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void actualizarPeso(double nuevoPeso) {
        if (nuevoPeso > 0) {
            this.pesoKg = nuevoPeso;
        } else {
            System.out.println(
                    "El peso no es válido.");
        }
    }

    public void cumplirMes() {
        edadMeses++;
    }

    public String mostrarInformacion() {
        return nombre + " | " +
                edadMeses + " meses | " +
                pesoKg + " kg";
    }

    public abstract void emitirSonido();
    public abstract double calcularCostoConsulta();
}
