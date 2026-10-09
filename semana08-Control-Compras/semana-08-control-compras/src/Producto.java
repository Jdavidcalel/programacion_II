public class Producto {
    private String nombre;
    private String categoria;
    private double precioUnitario;
    private int cantidad;
    public Producto(String nombre, String categoria,
                    double precioUnitario, int cantidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCategoria() {
        return categoria;
    }
    public double getPrecioUnitario() {
        return precioUnitario;
    }
    public int getCantidad() {
        return cantidad;
    }
    public double calcularSubtotal() {
        return precioUnitario * cantidad;
    }
}
