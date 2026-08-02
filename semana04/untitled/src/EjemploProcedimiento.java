public class EjemploProcedimiento {
    public static void main(String[] args) {

        mostrarSeparador();
        saludar("David");
        mostrarSeparador();
    }
    public static void saludar(String nombre) {
        System.out.println("Bienvenido/a, " + nombre);
    }

    public static void mostrarSeparador() {
        System.out.println("-------------------------");
    }
}