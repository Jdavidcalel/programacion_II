import java.util.HashMap;

public class MainHashMap {
    public static void main(String[] args) {
        HashMap<String, Double> precioMascota = new HashMap<>();

        precioMascota.put("PET-001", 150.00);
        precioMascota.put("PET-002", 380.00);
        precioMascota.put("PET-003", 380.00);

        System.out.println(precioMascota.get("PET-001"));
    }
}
