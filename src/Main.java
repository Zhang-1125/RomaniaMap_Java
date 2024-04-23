import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Search search = new Search("W7", "M1", "C:\\Users\\MoQing\\Desktop\\RomaniaMap_Java\\RomaniaMap_Java\\Romania.txt");
        search.AStar();

    }
}