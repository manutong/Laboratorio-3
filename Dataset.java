import java.io.*;
import java.util.*;

public class Dataset {
    private ArrayList<Game> data;
    private String sortedByAttribute;

    public Dataset(ArrayList<Game> data) {
        this.data = data;
        this.sortedByAttribute = "";
    }

    public ArrayList<Game> getData() {
        return data;
    }

    public ArrayList<Game> getGamesByPrice(int price) {
        ArrayList<Game> result = new ArrayList<>();

        if (sortedByAttribute.equals("price")) {
            int left = 0;
            int right = data.size() - 1;

            while (left <= right) {
                int mid = (left + right) / 2;
                Game midGame = data.get(mid);

                if (midGame.getPrice() == price) {
                    int i = mid;

                    // Izquierda
                    while (i >= 0 && data.get(i).getPrice() == price) {
                        i--;
                    }
                    i++;
                    while (i < data.size() && data.get(i).getPrice() == price) {
                        result.add(data.get(i));
                        i++;
                    }
                    break;
                } else if (midGame.getPrice() < price) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        } else {
            for (Game game : data) {
                if (game.getPrice() == price) {
                    result.add(game);
                }
            }
        }

        return result;
    }

    public ArrayList<Game> getGamesByPriceRange(int lowerPrice, int higherPrice) {
        ArrayList<Game> result = new ArrayList<>();

        if (sortedByAttribute.equals("price")) {
            int left = 0;
            int right = data.size() - 1;
            int FirstGame = -1;

            while (left <= right) {
                int mid = (left + right) / 2;
                if (data.get(mid).getPrice() >= lowerPrice) {
                    FirstGame = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            if (FirstGame != -1) {
                for (int i = FirstGame; i < data.size(); i++) {
                    int price = data.get(i).getPrice();
                    if (price > higherPrice) {
                        break;
                    }
                    if (price >= lowerPrice && price <= higherPrice) {
                        result.add(data.get(i));
                    }
                }
            }
        } else {
            for (Game game : data) {
                int price = game.getPrice();
                if (price >= lowerPrice && price <= higherPrice) {
                    result.add(game);
                }
            }
        }

        return result;
    }

    public ArrayList<Game> getGamesByCategory(String category) {
        ArrayList<Game> result = new ArrayList<>();
        if (sortedByAttribute.equals("category")) {
            int left = 0;
            int right = data.size() - 1;

            while (left <= right) {
                int mid = (left + right) / 2;
                Game midGame = data.get(mid);
                int comparison = midGame.getCategory().compareTo(category);

                if (comparison == 0) {
                    int i = mid;

                    while (i >= 0 && data.get(i).getCategory().equals(category)) {
                        i--;
                    }
                    i++;

                    while (i < data.size() && data.get(i).getCategory().equals(category)) {
                        result.add(data.get(i));
                        i++;
                    }
                    break;
                } else if (comparison < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        } else {
            for (Game game : data) {
                if (game.getCategory().equals(category)) {
                    result.add(game);
                }
            }
        }

        return result;
    }

    public ArrayList<Game> getGamesByQuality(int quality) {
        ArrayList<Game> result = new ArrayList<>();

        if (sortedByAttribute.equals("quality")) {
            int left = 0;
            int right = data.size() - 1;

            while (left <= right) {
                int mid = (left + right) / 2;
                Game midGame = data.get(mid);

                if (midGame.getQuality() == quality) {
                    int i = mid;

                    while (i >= 0 && data.get(i).getQuality() == quality) {
                        i--;
                    }
                    i++;

                    while (i < data.size() && data.get(i).getQuality() == quality) {
                        result.add(data.get(i));
                        i++;
                    }
                    break;
                } else if (midGame.getQuality() < quality) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        } else {
            for (Game game : data) {
                if (game.getQuality() == quality) {
                    result.add(game);
                }
            }
        }

        return result;
    }

    public void sortByAlgorithm(String algorithm, String attribute) {
        Comparator<Game> comparator;

        switch (attribute) {
            case "category":
                comparator = Comparator.comparing(Game::getCategory);
                break;
            case "quality":
                comparator = Comparator.comparingInt(Game::getQuality);
                break;
            case "price":
            default:
                comparator = Comparator.comparingInt(Game::getPrice);
                attribute = "price";
                break;
        }

        switch (algorithm) {
            case "bubbleSort":
                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < data.size() - i - 1; j++) {
                        if (comparator.compare(data.get(j), data.get(j + 1)) > 0) {
                            Collections.swap(data, j, j + 1);
                        }
                    }
                }
                break;
            case "insertionSort":
                for (int i = 1; i < data.size(); i++) {
                    Game key = data.get(i);
                    int j = i - 1;
                    while (j >= 0 && comparator.compare(data.get(j), key) > 0) {
                        data.set(j + 1, data.get(j));
                        j--;
                    }
                    data.set(j + 1, key);
                }
                break;
            case "selectionSort":
                for (int i = 0; i < data.size(); i++) {
                    int minIdx = i;
                    for (int j = i + 1; j < data.size(); j++) {
                        if (comparator.compare(data.get(j), data.get(minIdx)) < 0) {
                            minIdx = j;
                        }
                    }
                    Collections.swap(data, i, minIdx);
                }
                break;
            case "mergeSort":
                data = mergeSort(data, comparator);
                break;
            case "quickSort":
                quickSort(0, data.size() - 1, comparator);
                break;
            default:
                Collections.sort(data, comparator);
        }

        sortedByAttribute = attribute;
    }

    private ArrayList<Game> mergeSort(ArrayList<Game> list, Comparator<Game> comparator) {
        if (list.size() <= 1) return list;

        int mid = list.size() / 2;
        ArrayList<Game> left = mergeSort(new ArrayList<>(list.subList(0, mid)), comparator);
        ArrayList<Game> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())), comparator);

        ArrayList<Game> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));
        return merged;
    }

    private void quickSort(int low, int high, Comparator<Game> comparator) {
        if (low < high) {
            int pivotIndex = partition(low, high, comparator);
            quickSort(low, pivotIndex - 1, comparator);
            quickSort(pivotIndex + 1, high, comparator);
        }
    }

    private int partition(int low, int high, Comparator<Game> comparator) {
        Game pivot = data.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(data.get(j), pivot) <= 0) {
                i++;
                Collections.swap(data, i, j);
            }
        }
        Collections.swap(data, i + 1, high);
        return i + 1;
    }

    public static Dataset loadFromFile(String filename) {
        ArrayList<Game> games = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) continue;

                String name = parts[0].trim();
                int price = Integer.parseInt(parts[1].trim());
                String category = parts[2].trim();
                int quality = Integer.parseInt(parts[3].trim());

                games.add(new Game(name, price, category, quality));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error leyendo archivo: " + filename);
            e.printStackTrace();
        }

        return new Dataset(games);
    }
public void countingSortByQuality() {
    int maxQuality = 100;
    int[] count = new int[maxQuality + 1];
      for (Game g : data) {
        count[g.getQuality()]++;
    }
      for (int i = 1; i <= maxQuality; i++) {
        count[i] += count[i - 1];
    }
    ArrayList<Game> sorted = new ArrayList<>(data.size());
      for (int i = 0; i < data.size(); i++) {
        sorted.add(null);
    }
      for (int i = data.size() - 1; i >= 0; i--) {
        Game g = data.get(i);
        int quality = g.getQuality();
        int pos = count[quality] - 1;
        sorted.set(pos, g);
        count[quality]--;
    }

    data = sorted;

    sortedByAttribute = "quality";
}
    public static void main(String[] args) {
        ArrayList<Game> games = new ArrayList<>();
        games.add(new Game("Halo", 50000, "Shooter", 90));
        games.add(new Game("Minecraft", 30000, "Sandbox", 95));
        games.add(new Game("The Witcher", 45000, "RPG", 93));
        games.add(new Game("Stardew Valley", 25000, "Simulacion", 88));

        Dataset dataset = new Dataset(games);
        dataset.sortByAlgorithm("mergeSort", "price");

        System.out.println("\nJuegos ordenados por precio:");
        for (Game g : dataset.getData()) {
            System.out.println(g);
        }

        System.out.println("\nJuegos con precio entre 25000 y 45000:");
        for (Game g : dataset.getGamesByPriceRange(25000, 45000)) {
            System.out.println(g);
        }

        System.out.println("\nJuegos de la categoria RPG:");
        for (Game g : dataset.getGamesByCategory("RPG")) {
            System.out.println(g);
        }
    }
}
