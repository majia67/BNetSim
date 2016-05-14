import java.util.Map.Entry;

/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class defines the boolean network structure used in the main project
 *  
 */

public class Network {
    
    private NodeList nodeList;
    private Relationship relationship;
    private int loop; 
    private boolean hasChanged;
    private boolean hasTerminated;
    
    private Network() {
        loop = 0;
        hasChanged = false;
        hasTerminated = false;
    }
    
    public Network(NodeList nList, Relationship relat) {
        this();
        nodeList = nList.clone();
        relationship = relat.clone();
        
        //Verify the network is constructed correctly
        for (Node s : nodeList) {
            if (relat.getNodeList(s.name) != null) {
                for (String t : relat.getNodeList(s.name)) {
                    if (!nList.contains(t)) {
                        System.err.println("Invalid relationship: " + "Node " + t
                                + " is not set in the vertices.");
                    }
                }
            }
        }
    }
    
    public boolean next() {
        /*
         * Calculate the next state of the network nodes according to the given
         * network relation. Use the state transition equation described in
         * Dr. Guanyu Wang's paper XXX
        */
        loop ++;
        hasChanged = false;
        boolean reachMilestone = false;
        NodeList oldNode = nodeList.clone();
        
        for (Node s : nodeList) {
            //Skip activated milestone
            if (s.type.equals("Milestone") && s.state == Node.ON) {
                continue;
            }
            //Calculate Boolean function value of node s
            int score = 0;
            for (Node t : nodeList) {
                if (oldNode.get(t.name).state == Node.ON && 
                        relationship.get(t.name, s.name) != null) {
                    score += relationship.get(t.name, s.name);
                }
            }

            //Change the node state according to the score. 
            //If the score equals to 0, the node state will not change.
            if (score > 0) {
                if (s.requires != null) {
                    //Check requirements before setting node state
                    boolean meetRequirements = true;
                    for (Entry<String, Integer> t : s.requires.entrySet()) {
                        if (oldNode.get(t.getKey()).state != t.getValue().intValue()) {
                            meetRequirements = false;
                            break;
                        }
                    }
                    if (meetRequirements) {
                        if (s.name == "ROCK") {
                            System.err.println(oldNode.get("MLC-4").state);
                        }
                        s.state = Node.ON;
                    }
                }
                else {
                    s.state = Node.ON;
                }
            }
            else if (score < 0) {
                s.state = Node.OFF;
            }
            
            //Check if node states are changed in this round
            if (s.state != oldNode.get(s.name).state) {
                hasChanged = true;
            }
            
            //Check if Milestone with termination is activated
            if (s.type.equals("Milestone") && s.state == Node.ON) {
                reachMilestone = true;
                if (s.milestone_termination)
                    hasTerminated = true;
            }
        }
        
        return reachMilestone;
    }
    
    public NodeList getNodeList() {
        return nodeList.clone();
    }
    
    public Relationship getRelationship() {
        return relationship.clone();
    }
    
    public int getLoop() {
        return loop;
    }
    
    public boolean hasChanged() {
        return hasChanged;
    }
    
    public boolean hasTerminated() {
        return hasTerminated;
    }
    
    public String printNode() {
        return nodeList.toString();
    }
    
    public String printState() {
        String result = new String();
        Node[] nList = nodeList.getNodeList();
        String[] pattern = new String[nList.length];
        int i;
        
        //Prepare pattern array
        for (i = 1; i <= nodeList.size(); i++) {
            pattern[i] = "%" + Integer.toString(nList[i].name.length() + 2) + "s";
        }
        
        //Third line: node state
        for (i = 1; i <= nodeList.size(); i++) {
            result += String.format(pattern[i], nList[i].state);
        }
        
        return result;
    }
    
    public String printRelationship() {
        return relationship.toString();
    }
    
    public String getNodeStateString() {
        String result = new String();
        for (Node s : nodeList) {
            result += Integer.toString(s.state);
        }
        return result;
    }
    
    public String toString() {
        String result = new String();
        
        result += "Node Information:" + System.lineSeparator();
        result += printNode();
        
        result += "Node Relationship:" + System.lineSeparator();
        result += printRelationship();
        
        return result;
    }
    
}
