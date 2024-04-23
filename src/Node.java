import java.util.HashMap;

public class Node {
    public Node(String name, int neighborNum, double latitude, double longitude){
        this.name = name;
        this.neighborNum = neighborNum;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Node(String name, double latitude, double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double latitude;       // 纬度
    public double longitude;      // 经度

    public String name;
    public double h;
    public int neighborNum;

    public HashMap<Integer, String> nextState = new HashMap<Integer, String>();

    double rad(double d){
        return d * 3.1415926535897932384626433832795 / 180.0;
    }
    public double getDistanceBetween(Node node){
        double a;
        double b;
        double radLat1 = rad(this.latitude);
        double radLat2 = rad(node.latitude);
        a = radLat1 - radLat2;
        b = rad(this.longitude) - rad(node.longitude);
        return 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))) * 6378137.0;
    }

    public double getH(Node node){
        return this.getDistanceBetween(node);
    }
}
