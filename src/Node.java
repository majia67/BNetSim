import java.util.HashMap;
import java.util.Map.Entry;

public class Node {
    
    //General definition
    String name;
    int state;
    String type;
    
    //Definition for Requirements
    HashMap<String, Integer> requires;
    
    //Definition for Milestone
    boolean milestone_termination;
    
    public static final int ON = 1;
    public static final int OFF = 0;
    
    public Node() {
        this("");
    }
    
    public Node(String name) {
        this(name, 0);
    }
    
    public Node(String name, int state) {
        this(name, state, "Vertex");
    }
    
    public Node(String name, int state, String type) {
        this(name, state, type, false);
    }
    
    public Node(String name, int state, String type, boolean milestone_termination) {
        this.name = name;
        this.state = state;
        this.type = type;
        this.milestone_termination = milestone_termination;
    }
    
    public boolean setRequires(String na, Integer st) {
        if (na != null && st != null) {
            if (requires == null) { requires = new HashMap<String, Integer>(); }
            requires.put(na, st);
            return true;
        }
        return false;
    }
    
    public Node clone() {
        Node cl = new Node(name, state, type, milestone_termination);
        if (this.requires != null) {
            for (Entry<String, Integer> t : this.requires.entrySet()) {
                cl.setRequires(t.getKey(), t.getValue());
            }
        }
        return cl;
    }
}
