import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Scanner;

public class MainControlCompras {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Producto> productos = new ArrayList<>();
        HashSet<String> categorias = new HashSet<>();
        HashMap<String, Double> totalPorCategoria = new HashMap<>();

        System.out.println("===== CONTROL DE COMPRAS =====");
        for (int i = 1; i <= 5; i++) {
            System.out.println("\nProducto No. " + i);
            System.out.print("Nombre del producto: ");
            String nombre = scanner.nextLine();
            System.out.print("Categoría: ");
            String categoria = scanner.nextLine();
            System.out.print("Precio unitario: ");
            double precio = scanner.nextDouble();
            System.out.print("Cantidad comprada: ");
            int cantidad = scanner.nextInt();
            scanner.nextLine();
            if (nombre.trim().isEmpty()) {
                System.out.println("Producto no registrado: nombre vacío.");
                continue;
            }
            if (categoria.trim().isEmpty()) {
                System.out.println("Producto no registrado: categoría vacía.");
                continue;
            }
            if (precio <= 0) {
                System.out.println("Producto no registrado: el precio debe ser mayor que cero.");
                continue;
            }
            if (cantidad <= 0) {
                System.out.println("Producto no registrado: la cantidad debe ser mayor que cero.");
                continue;
            }
            Producto producto = new Producto(
                    nombre, categoria, precio, cantidad
            );
            productos.add(producto);
            categorias.add(categoria);
            double subtotal = producto.calcularSubtotal();
            totalPorCategoria.put(
                    categoria,
                    totalPorCategoria.getOrDefault(categoria, 0.0) + subtotal
            );
            System.out.println("Producto registrado correctamente.");
        }

        System.out.println("\n===== RESUMEN DE COMPRAS =====");

        double totalGeneral = 0;

        for (Producto producto : productos) {

            double subtotal = producto.calcularSubtotal();

            System.out.printf(
                    "%s | %s | Q%.2f x %d | Subtotal: Q%.2f%n",
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getPrecioUnitario(),
                    producto.getCantidad(),
                    subtotal
            );

            totalGeneral += subtotal;
        }

        System.out.println("\nCategorías registradas:");
        System.out.println(categorias);

        System.out.println("\nTotal por categoría:");

        for (String categoria : totalPorCategoria.keySet()) {

            System.out.printf(
                    "%s: Q%.2f%n",
                    categoria,
                    totalPorCategoria.get(categoria)
            );
        }

        System.out.println("\nProductos registrados: "
                + productos.size());

        System.out.printf("\nTotal general: Q%.2f%n", totalGeneral);

        if (!productos.isEmpty()) {

            Producto productoMayor = productos.get(0);
            Producto productoMenor = productos.get(0);

            for (Producto producto : productos) {

                if (producto.calcularSubtotal() >
                        productoMayor.calcularSubtotal()) {
                    productoMayor = producto;
                }

                if (producto.calcularSubtotal() <
                        productoMenor.calcularSubtotal()) {
                    productoMenor = producto;
                }
            }

            System.out.println("\nProducto con mayor gasto:");
            System.out.printf("%s - Q%.2f%n",
                    productoMayor.getNombre(),
                    productoMayor.calcularSubtotal());

            System.out.println("\nProducto con menor gasto:");
            System.out.printf("%s - Q%.2f%n",
                    productoMenor.getNombre(),
                    productoMenor.calcularSubtotal());

            String categoriaMayor = "";
            double gastoMayorCategoria = 0;

            for (String categoria : totalPorCategoria.keySet()) {

                double total = totalPorCategoria.get(categoria);

                if (total > gastoMayorCategoria) {
                    gastoMayorCategoria = total;
                    categoriaMayor = categoria;
                }
            }

            System.out.println("\nCategoría con mayor gasto:");
            System.out.printf("%s - Q%.2f%n",
                    categoriaMayor, gastoMayorCategoria);

        } else {
            System.out.println("\nNo hay productos registrados para analizar.");
        }

        System.out.println("\n===== CONSULTA DE CATEGORÍA =====");

        System.out.print("Ingrese una categoría para consultar: ");
        String categoriaBuscar = scanner.nextLine().trim();

        if (totalPorCategoria.containsKey(categoriaBuscar)) {

            double totalGastado = totalPorCategoria.get(categoriaBuscar);

            System.out.printf(
                    "Total gastado en %s: Q%.2f%n",
                    categoriaBuscar,
                    totalGastado
            );

        } else {

            System.out.println(
                    "La categoría ingresada no se encuentra registrada."
            );
        }

        scanner.close();
    }
}
