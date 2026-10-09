public class Circulo extends Figura {

    private double radio;
    public Circulo(double radio) {
        super("Círculo");
        if (radio <= 0) {
            throw new IllegalArgumentException(
                    "El radio debe ser mayor que cero."
            );
        }
        this.radio = radio;
    }
    @Override
    public double calcularArea() {
        return Math.PI * radio * radio;
    }
}

