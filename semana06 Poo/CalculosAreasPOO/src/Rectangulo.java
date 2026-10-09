public class Rectangulo extends Figura {

    private double base;
    private double altura;
    public Rectangulo(double base, double altura) {
        super("Rectángulo");
        if (base <= 0 || altura <= 0) {
            throw new IllegalArgumentException(
                    "La base y la altura deben ser mayores que cero."
            );
        }
        this.base = base;
        this.altura = altura;
    }
    @Override
    public double calcularArea() {
        return base * altura;
    }
}
