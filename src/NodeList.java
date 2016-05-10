/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class defines the default Node structure used in the main project
 *  
 */
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class NodeList implements Iterable<Node>{
    
    private Hashtable<String, Integer> indexTable;
    private ArrayList<Node> nodeList;
    
    public NodeList() {
        indexTable = new Hashtable<String, Integer>();
        nodeList = new ArrayList<Node>();
        nodeList.add(null);     //Set starting index from 1
    }
    
    public Node get(String name) {
        return nodeList.get(indexTable.get(name));
    }
    
    public Node get(int index) {
        return nodeList.get(index);
    }

    public int getIndex(String name) {
        if (indexTable.containsKey(name)) {
            return indexTable.get(name);
        }
        return -1;
    }
    
    public int getNodeState(String name) {
        return nodeList.get(indexTable.get(name)).state;
    }
    
    public int getNodeState(int index) {
        return nodeList.get(index).state;
    }
    
    public boolean add(Node node) {
        if (indexTable.get(node.name) != null) {
            System.err.println("Error: adding duplicate node!");
            return false;
        }        
        nodeList.add(node);
        indexTable.put(node.name, size());
        return true;
    }
    
    public Node[] getNodeList() {
        return nodeList.toArray(new Node[0]);
    }
    
    public String[] getNameList() {
        String[] nameList = new String[nodeList.size()];
        nameList[0] = null;
        for (int i = 1; i <= size(); i++) {
            nameList[i] = nodeList.get(i).name;
        }
        return nameList;
    }
    
    public int size() {
        return nodeList.size()-1;
    }
    
    public boolean contains(String name) {
        return indexTable.containsKey(name);
    }
    
    @Override
    public Iterator<Node> iterator() {
        return new Iterator<Node>() {
            private int index = 1;
            @Override
            public boolean hasNext() {
                return index < nodeList.size();
            }

            @Override
            public Node next() {
                return nodeList.get(index++);
            }
            
        };
    }
    
    public String toString() {
        String result = new String();
        String[] pattern = new String[nodeList.size()];
        Node[] nList = getNodeList();
        int i;
        
        //Prepare pattern array
        for (i = 1; i <= size(); i++) {
            pattern[i] = "%" + Integer.toString(nList[i].name.length() + 2) + "s";
        }
        
        //First line: node name
        for (i = 1; i <= size(); i++) {
            result += String.format(pattern[i], nList[i].name);         
        }
        result += System.lineSeparator();
        
        //Second line: node type
        for (i = 1; i <= size(); i++) {
            result += String.format(pattern[i], nList[i].type.charAt(0));
        }
        result += System.lineSeparator();
        
        //Third line: node state
        for (i = 1; i <= size(); i++) {
            result += String.format(pattern[i], nList[i].state);
        }
        result += System.lineSeparator();
        
        return result;
    }
    
    public NodeList clone() {
        //Deep copy of the Node object
        NodeList copy = new NodeList();
        
        for (Node s : getNodeList()) {
            if (s != null) {
                copy.add(s.clone());
            }
        }
        
        return copy;
    }
}
