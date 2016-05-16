
public class Node {
    
    //General definition
    String name;
    String type;
    int state;
    
    //Definition for Dependency
    int[] depends;
    
    //Definition for Milestone
    boolean milestoneTermination;
    
    //Definition for constants
    public static final int ON = 1;
    public static final int OFF = 0;
    
    public Node(String name) {
        this(name, 0);
    }
    
    public Node(String name, int state) {
        this.name = name;
        this.state = state;
        type = "Vertex";
    }
    
    public void setDependency(int[] dependencies) {
        depends = dependencies;
    }
    
    public void setMilestone(boolean milestoneTermination) {
        type = "Milestone";
        this.milestoneTermination = milestoneTermination; 
    }
    
    public Node clone() {
        Node cl = new Node(name, state);
        if (type.equals("Milestone")) {
            cl.setMilestone(milestoneTermination);
        }
        if (depends != null) {
            cl.setDependency(depends.clone());
        }
        return cl;
    }
}
