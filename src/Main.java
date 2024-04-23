import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Search search = new Search("Z6", "Z3", "C:\\Users\\MoQing\\Desktop\\RomaniaMap_Java\\RomaniaMap_Java\\NEUMapInfo.txt");

        System.out.println(search.getPosition(41.76230687785468, 123.42029469582734).name);
    }
}