public class EnvioInternacional extends Envio {

    private String paisDestino;
    private final double CARGO_GESTION = 75.00;

    public EnvioInternacional(String codigo,
                              String destinatario,
                              double pesoKg,
                              String paisDestino) {

        super(codigo, destinatario, pesoKg);

        this.paisDestino = paisDestino;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public double calcularRecargoInternacional() {
        return calcularCostoBase() * 0.12;
    }

    @Override
    public double calcularCostoFinal() {
        return calcularCostoBase()
                + CARGO_GESTION
                + calcularRecargoInternacional();
    }

    @Override
    public void mostrarResumen(boolean mostrarDesglose) {

        if (mostrarDesglose) {
            System.out.println("\n===== ENVÍO INTERNACIONAL =====");
            System.out.println("Código: " + getCodigo());
            System.out.println("Destinatario: " + getDestinatario());
            System.out.println("País de destino: " + paisDestino);
            System.out.printf("Peso: %.2f kg%n", getPesoKg());
            System.out.printf("Costo base: Q%.2f%n", calcularCostoBase());
            System.out.printf("Cargo internacional: Q%.2f%n", CARGO_GESTION);
            System.out.printf("Recargo del 12%%: Q%.2f%n",
                    calcularRecargoInternacional());
            System.out.printf("Costo final: Q%.2f%n",
                    calcularCostoFinal());
        } else {
            mostrarResumen();
        }
    }
}
