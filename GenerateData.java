import java.io.*;
import java.util.*;
 public class GenerateData {

    private static final String[] palabras = {
        "Dragon", "Empire", "Quest", "Galaxy", "Legends", "Warrior", "Shadow", "Knight", "Blade", "Infinity"
    };

    private static final String[] categorias = {
        "Accion", "Aventura", "Estrategia", "RPG", "Deportes", "Simulacion"
    };


    public static ArrayList<Game> generateGames(int n) {
        ArrayList<Game> games = new ArrayList<>(n);
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            String name = generarNombre(rand);
            String category = categorias[rand.nextInt(categorias.length)];
            int price = rand.nextInt(70001); // 0 a 70000
            int quality = rand.nextInt(101); // 0 a 100

            games.add(new Game(name, price, category, quality));
        }

        return games;
    }

    private static String generarNombre(Random rand) {
        String palabra1 = palabras[rand.nextInt(palabras.length)];
        String palabra2 = palabras[rand.nextInt(palabras.length)];
        while (palabra1.equals(palabra2)) {
            palabra2 = palabras[rand.nextInt(palabras.length)];
        }
        return palabra1 + palabra2;
    }


    public static void guardarEnArchivo(ArrayList<Game> games, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Nombre,Precio,Categoria,Calidad");
            writer.newLine();
            for (Game g : games) {
                writer.write(g.getName() + "," + g.getPrice() + "," + g.getCategory() + "," + g.getQuality());
                writer.newLine();
            }
            System.out.println(" Datos guardados en: " + filename);
        } catch (IOException e) {
            System.err.println(" Error al guardar: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        int[] tamanos = {100, 10_000, 1_000_000};
        for (int tamano : tamanos) {
            System.out.println("Generando " + tamano + " juegos...");
            ArrayList<Game> juegos = generateGames(tamano);
            String filename = "games_" + tamano + ".csv";
            guardarEnArchivo(juegos, filename);
        }
    }
}
