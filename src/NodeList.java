/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class defines the default Node structure used in the main project
 *  
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;

public class NodeList implements Iterable<Node>{
    
    private HashMap<String, Integer> indexTable;
    private HashSet<Integer> milestoneNodes;
    private Node[] nodes;
    
    public NodeList(int size) {
        indexTable = new HashMap<String, Integer>();
        nodes = new Node[size];
        milestoneNodes = new HashSet<Integer>();
    }
    
    public int getIndex(String name) {
        if (indexTable.containsKey(name)) {
            return indexTable.get(name);
        }
        return -1;
    }
    
    public Node get(int index) {
        return nodes[index];
    }
    
    public Node get(String name) {
        return get(getIndex(name));
    }

    public void set(int index, Node node) {
        if (nodes[index] != null) {
            indexTable.remove(nodes[index].name);
            if (nodes[index].type.equals("Milestone")) {
                milestoneNodes.remove(index);
            }
        }
        nodes[index] = node;
        indexTable.put(node.name, index);
        if (node.type.equals("Milestone")) {
            milestoneNodes.add(index);
        }
    }
    
    public void set(String name, Node node) {
        set(getIndex(name), node);
    }
    
    public int[] getNodeStates() {
        int[] states = new int[size()];
        for (int i = 0; i < size(); i++) {
            states[i] = nodes[i].state;
        }
        return states;
    }
    
    public void setNodeState(int index, int state) {
        nodes[index].state = state;
    }
    
    public void setNodeStates(int[] states) {
        for (int i = 0; i < size(); i++) {
            nodes[i].state = states[i];
        }
    }
    
    public Node[] getNodeList() {
        return nodes;
    }
    
    public int size() {
        return nodes.length;
    }
    
    public boolean contains(String name) {
        return indexTable.containsKey(name);
    }
    
    
    public int getMilestoneNodesNum() {
        return milestoneNodes.size();
    }
    
    public int[] getMilestoneNodesIndex(boolean isTerminatedMilestone) {
        Integer[] tmp = milestoneNodes.toArray(new Integer[0]);
        int[] rlt;
        if (!isTerminatedMilestone) {
            rlt = new int[tmp.length];
            for (int i = 0; i < tmp.length; i++) {
                rlt[i] = tmp[i].intValue();
            }
        }
        else {
            int count = 0;
            for (int s : tmp) {
                if (nodes[s].milestoneTermination) { count ++; }
            }
            rlt = new int[count];
            int i = 0;
            for (int s : tmp) {
                if (nodes[s].milestoneTermination) {
                    rlt[i++] = s;
                }
            }
        }
        return rlt;
    }
    
    @Override
    public Iterator<Node> iterator() {
        return new Iterator<Node>() {
            private int idx = 0;
            @Override
            public boolean hasNext() {
                return idx < size();
            }

            @Override
            public Node next() {
                return nodes[idx++];
            }
            
            @Override
            public void remove() {
                throw new java.lang.UnsupportedOperationException();
            }
        };
    }
    
    public String printState() {
        String result = new String();
        String[] pattern = new String[nodes.length];
        int i;
        
        //Prepare pattern array
        for (i = 0; i < nodes.length; i++) {
            pattern[i] = "%" + Integer.toString(nodes[i].name.length() + 2) + "s";
        }
        
        //Third line: node state
        for (i = 0; i < nodes.length; i++) {
            result += String.format(pattern[i], nodes[i].state);
        }
        
        return result;
    }
    
    public String getNodeStateString() {
        String result = new String();
        for (Node s : nodes) {
            result += Integer.toString(s.state);
        }
        return result;
    }
    
    public String toString() {
        String result = new String();
        String[] pattern = new String[size()];
        Node[] nList = getNodeList();
        int i;
        
        //Prepare pattern array
        for (i = 0; i < size(); i++) {
            pattern[i] = "%" + Integer.toString(nList[i].name.length() + 2) + "s";
        }
        
        //First line: node name
        for (i = 0; i < size(); i++) {
            result += String.format(pattern[i], nList[i].name);         
        }
        result += System.lineSeparator();
        
        //Second line: node type
        for (i = 0; i < size(); i++) {
            result += String.format(pattern[i], nList[i].type.charAt(0));
        }
        result += System.lineSeparator();
        
        //Third line: node state
        for (i = 0; i < size(); i++) {
            result += String.format(pattern[i], nList[i].state);
        }
        result += System.lineSeparator();
        
        return result;
    }

}
