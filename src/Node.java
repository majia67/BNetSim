/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class defines the default Node structure used in the main project
 *  
 */
import java.util.Hashtable;
import java.util.Iterator;

public class Node implements Iterable<String>{
    
    public static final int ON = 1;
    public static final int OFF = 0;
    
    private Hashtable<String, Integer> node;
    private String[] nodeList;
    
    public Node() {
        node = new Hashtable<String, Integer>();
        nodeList = new String[0];
    }
    
    public Integer get(String name) {
        return node.get(name);
    }
    
    public Integer put(String name, Integer state) {
        Integer result = node.put(name, state);
        refreshNodeList();
        return result; 
    }
    
    public String[] getNodeList() {
        return nodeList;
    }

    private void refreshNodeList() {
        nodeList = node.keySet().toArray(new String[0]);
    }
    
    public int size() {
        return node.size();
    }
    
    public Integer remove(String name) {
        return node.remove(name);
    }
    
    public boolean contains(String name) {
        return node.containsKey(name);
    }
    
    @Override
    public Iterator<String> iterator() {
        return node.keySet().iterator();
    }
    
    public String toString() {
        String result = new String();
        
        for (String s : getNodeList())
            result += String.format("%10s", s);
        result += System.lineSeparator();

        for (String s : getNodeList())
            result += String.format("%10s", node.get(s));
        result += System.lineSeparator();
        
        return result;
    }
    
    public Node clone() {
        //Deep copy of the Node object
        Node copy = new Node();
        
        for (String s : getNodeList()) {
            Integer i = new Integer(get(s).intValue());
            copy.put(s, i);
        }
        
        return copy;
    }
}
