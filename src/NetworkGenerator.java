import java.util.HashSet;

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
    
    public void writeFile(String filePrefix, boolean writePartition) {
        Network net = new Network(nodes, relat);
        Pajek pj = new Pajek(net);
        pj.writeNetwork(filePrefix);
        pj.writeBNetSimFile(filePrefix);
        if (writePartition) {
            pj.writePartition(filePrefix);
        }
    }
    
    public void writeDependencyNetwork(String filePrefix, boolean writePartition) {
        HashSet<Node> ndSet = new HashSet<Node>();

        for (Node nd : nodes.getNodeList()) {
            if (nd.depends != null) {
                ndSet.add(nd);
                for (int i : nd.depends) {
                    ndSet.add(nodes.get(i));
                }
            }
        }
        
        int size = ndSet.size();
        Node[] ndList = ndSet.toArray(new Node[0]);
        NodeList nl = new NodeList(ndList);
        Relationship rlt = new Relationship(size);
        
        for (int i = 0; i < ndList.length; i++) {
            if (ndList[i].depends != null) {
                for (int j : ndList[i].depends) {
                    int tj = nl.getIndex(nodes.get(j).name);
                    rlt.set(tj, i, 1);
                }
            }
        }
        
        filePrefix += "_Dependency";
        Pajek pj = new Pajek(new Network(nl, rlt));
        pj.writeNetwork(filePrefix);
        if (writePartition) {
            pj.writePartition(filePrefix);
        }
    }
}
