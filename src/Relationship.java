import java.util.HashMap;

//2-dim matrix, >0: positive; <0: negative; 
//0: no relationship between the two nodes

public class Relationship{
    
    private HashMap<String, HashMap<String, Integer>> relat;
    
    public Relationship() {
        relat = new HashMap<String, HashMap<String, Integer>>();
    }
    
    public Integer get(String s, String t) {
        if (relat.get(s) != null)
            return relat.get(s).get(t);
        else
            return null;
    }
    
    public void set(String s, String t, Integer effect) {
        if (!relat.containsKey(s)) {
            relat.put(s, new HashMap<String, Integer>());
        }
        relat.get(s).put(t, effect);
    }

    public String[] getNodeList() {
        return relat.keySet().toArray(new String[0]);
    }
    
    public String[] getNodeList(String s) {
        if (relat.get(s) != null)
            return relat.get(s).keySet().toArray(new String[0]);
        else
            return null;
    }

    public int size() {
        int numOfRelat = 0;
        for (String s : getNodeList()) {
            numOfRelat += relat.get(s).size();
        }
        return numOfRelat;
    }
    
    public String toString() {
        String result = new String();
        
        for (String s : getNodeList()) {
            result += s + ":" + System.lineSeparator();
            for (String t : getNodeList(s)) {
                result += "    " + t + " " + get(s, t);
                result += System.lineSeparator();
            }
        }
        
        return result;
    }
    
    public Relationship clone() {
        //Deep copy of the Relationship object
        Relationship copy = new Relationship();
        
        for (String s : getNodeList()) {
            for (String t : getNodeList(s)) {
                copy.set(s, t, get(s, t));
            }
        }
        
        return copy;
    }
}
