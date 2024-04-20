import java.util.HashMap;

public class Node {
    public Node(String name, int h, int neighborNum){
        this.name = name;
        this.h = h;
        this.neighborNum = neighborNum;
    }

    public static class neighbor {
        public neighbor(String name, int distance)
        {
            this.Name = name;
            this.Distance = distance;
        }
        public String Name;
        public int Distance;
    }

    public String name;
    public int h;
    public int neighborNum;

    public HashMap<Integer, neighbor> nextState = new HashMap<Integer, neighbor>();
}
