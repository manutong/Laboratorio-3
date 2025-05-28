import java.io.*;
import java.util.*;
class Game{
    private String name;
    private String category;
    private int price;
    private int quality;

public Game(String name, int price, String category, int quality)   {
   
    this.name = name;
    this.category = category;
    this.price = price;
    this.quality = quality;
}
 public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int getQuality() {
        return quality;
    }
   public String toString() {
        return name + " | $" + price + " | " + category + " | Quality: " + quality;
    }
public static void main(String[] args) {
        Game juego = new Game("Fornite", 0, "Accion", 99);
        System.out.println("Juego creado:");
        System.out.println(juego);
    }
}
