public class EnvioNacional extends Envio {

    private String departamentoDestino;
    private double distanciaKm;

    public EnvioNacional(String codigo,
                         String destinatario,
                         double pesoKg,
                         String departamentoDestino,
                         double distanciaKm) {

        super(codigo, destinatario, pesoKg);

        this.departamentoDestino = departamentoDestino;
        this.distanciaKm = distanciaKm;
    }

    public String getDepartamentoDestino() {
        return departamentoDestino;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public double calcularCargoDistancia() {
        return distanciaKm * 0.50;
    }

    @Override
    public double calcularCostoFinal() {
        return calcularCostoBase() + calcularCargoDistancia();
    }

    @Override
    public void mostrarResumen(boolean mostrarDesglose) {

        if (mostrarDesglose) {
            System.out.println("\n===== ENVÍO NACIONAL =====");
            System.out.println("Código: " + getCodigo());
            System.out.println("Destinatario: " + getDestinatario());
            System.out.println("Departamento: " + departamentoDestino);
            System.out.printf("Peso: %.2f kg%n", getPesoKg());
            System.out.printf("Distancia: %.2f km%n", distanciaKm);
            System.out.printf("Costo base: Q%.2f%n", calcularCostoBase());
            System.out.printf("Cargo por distancia: Q%.2f%n",
                    calcularCargoDistancia());
            System.out.printf("Costo final: Q%.2f%n",
                    calcularCostoFinal());
        } else {
            mostrarResumen();
        }
    }
}
