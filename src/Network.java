
/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class defines the boolean network structure used in the main project
 *  
 */

public class Network {
    
    private NodeList nodes;
    private Relationship relat;
    private boolean hasChanged;
    private boolean hasTerminated;
    private boolean hasReachedMilestone;
    private boolean[] isMilestone;
    private int sizeOfNetwork;
    
    public Network(NodeList nodeList, Relationship relationship) {
        nodes = nodeList;
        relat = relationship;
        System.err.println(relat);
        hasChanged = false;
        hasTerminated = false;
        sizeOfNetwork = nodes.size();
        isMilestone = new boolean[sizeOfNetwork];
        for (int i = 0; i < sizeOfNetwork; i++) {
            if (nodes.get(i).type.equals("Milestone")) {
                isMilestone[i] = true;
            }
        }
    }
    
    public void next() {
        /*
         * Calculate the next state of the network nodes according to the given
         * network relation. Use the state transition equation described in
         * Dr. Guanyu Wang's paper 
        */
        hasChanged = false;
        hasReachedMilestone = false;
        hasTerminated = false;
        int[] oldState = nodes.getNodeStates();
        
        for (int i = 0; i < sizeOfNetwork; i++) {
            Node s = nodes.get(i);
            //Skip activated milestone
            if (isMilestone[i] && oldState[i] == Node.ON) {
                continue;
            }
            //Calculate Boolean function value of node s
            int score = 0;
            for (int j = 0; j < nodes.size(); j++) {
                if (oldState[j] == Node.ON && 
                        relat.get(j, i) != 0) {
                    score += relat.get(j, i);
                }
            }

            //Change the node state according to the score. 
            //If the score equals to 0, the node state will not change.
            if (score > 0) {
                if (s.depends != null) {
                    //Check requirements before setting node state
                    boolean meetDependencies = true;
                    for (int j : s.depends) {
                        if (oldState[j] != Node.ON) {
                            meetDependencies = false;
                            break;
                        }
                    }
                    if (meetDependencies) {
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
            if (s.state != oldState[i]) {
                hasChanged = true;
                //Check if Milestone is activated at the first time
                if (isMilestone[i] && s.state == Node.ON) {
                    hasReachedMilestone = true;
                    //Check if Milestone with termination is activated
                    if (s.milestoneTermination)
                        hasTerminated = true;
                }
            }
            
        }
        
    }
    
    public int size() {
        return nodes.size();
    }
    
    public Node getNode(int index) {
        return nodes.get(index);
    }
    
    public NodeList getNodeList() {
        return nodes;
    }
    
    public Relationship getRelationship() {
        return relat;
    }
    
    public boolean hasChanged() {
        return hasChanged;
    }
    
    public boolean hasTerminated() {
        return hasTerminated;
    }
    
    public boolean hasReachedMilestone() {
        return hasReachedMilestone;
    }
    
    public int[] getNodeStates() {
        return nodes.getNodeStates();
    }
    
    public void setNodeStates(int[] states) {
        nodes.setNodeStates(states);
    }
    
    public int getMilestoneNodesNum() {
        return nodes.getMilestoneNodesNum();
    }
    
    public int[] getMilestoneNodesIndex(boolean isTerminatedMilestone) {
        return nodes.getMilestoneNodesIndex(isTerminatedMilestone);
    }
    
    public String printNode() {
        return nodes.toString();
    }
    
    public String printState() {
        return nodes.printState();
    }
    
    public String printRelationship() {
        return relat.toString();
    }
    
    public String getNodeStateString() {
        return nodes.getNodeStateString();
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
