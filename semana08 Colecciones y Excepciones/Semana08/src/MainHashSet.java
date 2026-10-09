import java.util.HashSet;

public class MainHashSet {
    public static void main(String[] args) {
        HashSet<String> correos = new HashSet<>();

        System.out.println(correos.add("dalvareze@miumg.edu.gt"));
        System.out.println(correos.add("jvaldez@miumg.edu.gt"));
        System.out.println(correos.add("dalvareze@miumg.edu.gt"));

        for (String correo: correos) {
            System.out.println(correo);
        }

    }
}
