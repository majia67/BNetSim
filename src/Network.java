/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class defines the boolean network structure used in the main project
 *  
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Network {
    
    private Node node;
    private Relationship relat;
    private int loop; 
    private boolean hasChanged;
    
    public Network() {
        node = new Node();
        relat = new Relationship();
        loop = 0;
        hasChanged = false;
    }
    
    public Network(String file) {
        this();
        this.readFile(file);
    }

    public void readFile(String file) {
        //Check if node and relationship container is not empty
        if (node.size() != 0 || relat.size() != 0) {
            System.err.println("Warning! The network is not empty. "
                    + "Reading from new files may overwrite existing "
                    + "node and relationship information!");
        }
        
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
            
            int totalNode = sc.nextInt();
            
            for (int i = 0; i < totalNode; i++) {
                String nid = sc.next();
                int state = sc.nextInt();
                node.put(nid, state);
            }
            
            while (sc.hasNext()) {
                String s = sc.next();
                String t = sc.next();
                int effect = sc.nextInt();
                relat.put(s, t, effect);
            }
            
            sc.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String file) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(file));

            //Write total number of nodes
            writer.write(Integer.toString(node.size()));
            writer.newLine();
            
            //Write node's name and its initial state
            for (String nd : node) {
                writer.write(nd + " " + node.get(nd));
                writer.newLine();
            }
           
            //Write interaction network
            for (String s : node) {
                for (String t : relat.getNodeList(s)) {
                    writer.write(s + " " + t + " " + relat.get(s, t));
                    writer.newLine();
                }
            }

            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void next() {
        /*
         * Calculate the next state of the network nodes according to the given
         * network relation. Use the state transition equation described in
         * Dr. Guanyu Wang's paper XXX
        */
        loop ++;
        hasChanged = false;
        Node oldNode = node.clone();
        
        for (String s : getNodeList()) {
            int score = 0;
            for (String t : node) {
                if (oldNode.get(t) == Node.ON && relat.get(t, s) != null) {
                    score += relat.get(t, s);
                }
            }

            //Change the node state according to the score. 
            //If the score equals to 0, the node state will not change.
            if (score > 0) {
                node.put(s, Node.ON);                
            }
            else if (score < 0) {
                node.put(s, Node.OFF);
            }
            
            //Check if node states are changed in this round
            if (!node.get(s).equals(oldNode.get(s)))
                hasChanged = true;
        }
    }
    
    public String[] getNodeList() {
        return node.getNodeList();
    }
    
    public int getNodeState(String name) {
        return node.get(name);
    }
    
    public void setNode(String name, Integer state) {
        node.put(name, state);
    }
    
    public int getRelationship(String s, String t) {
        return relat.get(s, t);
    }
    
    public void setRelationship(String s, String t, int effect) throws InvalidRelationshipException {
        if (node.contains(s) && node.contains(t)) {
            relat.put(s, t, effect);
        }
        else {
            throw new InvalidRelationshipException("The specified node doesn't exist in the current network.");
        }
    }
    
    public int getLoop() {
        return loop;
    }
    
    public boolean hasChanged() {
        return hasChanged;
    }
    
    public String printNode() {
        return node.toString();
    }
    
    public String printRelationship() {
        return relat.toString();
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
