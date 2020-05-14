import java.util.ArrayList;

public class Node {
    /*
    id:the stop_id;
    name:the stop_name;
    latitude:the latitude of stop;
    longitude: the longitude of stop;
    next:the next stop;
     */

    public String id;
    public String name;
    public double latitude;
    public double longitude;
    public ArrayList<Node> next;


    public Node(String id,String name) {
        this.id = id;
        this.name = name;
        latitude = 0;
        longitude = 0;
        next = new ArrayList<>();
    }

}
