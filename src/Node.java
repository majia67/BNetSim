
public class Node {
    String name;
    int state;
    String type;
    
    public Node() {
        this("", 0);
    }
    
    public Node(String na, int st) {
        this(na, st, "Vertice");
    }
    
    public Node(String na, int st, String ty) {
        name = na;
        state = st;
        type = ty;
    }
    
    public Node clone() {
        Node cl = new Node(name, state, type);
        return cl;
    }
}
