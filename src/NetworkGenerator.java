
public class NetworkGenerator {
    
    private NodeList nodes;
    private Relationship relat;
    private int size;
    
    public NetworkGenerator(String[] nameOfNodes) {
        size = nameOfNodes.length;
        nodes = new NodeList(size);
        relat = new Relationship(size);
        
        for (int i = 0; i < size; i++) {
            nodes.set(i, nameOfNodes[i]);
        }
    }
    
    private boolean checkNode(String nameOfNode) {
        if (nodes.get(nameOfNode) == null) {
            System.err.println("Wrong name of node: " + nameOfNode);
            return false;
        }
        return true;
    }
    
    public void setNodeState(String node, int state) {
        assert(checkNode(node));
        nodes.setNodeState(node, state);
    }
    
    public void setMilestone(String node, boolean milestoneTermination) {
        nodes.setMilestone(node, milestoneTermination);
    }
    
    public void setRelationship(String node1, String node2, int relationship) {
        assert(checkNode(node1));
        assert(checkNode(node2));
        int idx1 = nodes.getIndex(node1);
        int idx2 = nodes.getIndex(node2);
        relat.set(idx1, idx2, relationship);
    }
    
    public void setDependency(String node, String... dependencies) {
        assert(checkNode(node));
        for (String nd : dependencies) {
            assert(checkNode(nd));
        }
        nodes.setDependency(node, dependencies);
    }
    
    public void writeFile(String fileName) {
        Network net = new Network(nodes, relat);
        Pajek pj = new Pajek();
        pj.writeFile(fileName, net);
    }
}
