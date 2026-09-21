import java.util.ArrayList;

public class MainArrayList {

    public static void main(String[] args) {

        ArrayList<String> estudiantes = new ArrayList<>();

        estudiantes.add("Ana");
        estudiantes.add("Benito");
        estudiantes.add("Daniel");

        estudiantes.remove(0);

        System.out.println(estudiantes.get(0));

        for (String estudiante : estudiantes) {
            System.out.println(estudiante);
        }

        estudiantes.add("Juan");
        estudiantes.add("David");

        estudiantes.clear();

        System.out.println("Total estudiantes: " + estudiantes.size());
    }
}