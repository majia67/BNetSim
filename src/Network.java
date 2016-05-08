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
    
    private NodeList nodeList;
    private Relationship relationship;
    private int loop; 
    private boolean hasChanged;
    
    public Network() {
        loop = 0;
        hasChanged = false;
    }
    
    public Network(String file) {
        this();
        this.readFile(file);
    }
    
    public Network(NodeList nList, Relationship relat) {
        nodeList = nList;
        relationship = relat;
        
        //Verify the network is constructed correctly
        for (String s : nList.getNameList()) {
            for (String t : relat.getNodeList(s)) {
                if (!nList.contains(t)) {
                    System.err.println("Invalid relationship: " + "Node " + t
                            + " is not set in the vertices.");
                }
            }
        }
    }
    
    public void readFile(String file) {
        nodeList = new NodeList();
        relationship = new Relationship();
        
        //Check if node and relationship container is not empty
        if (nodeList.size() != 0 || relationship.size() != 0) {
            System.err.println("Warning! The network is not empty. "
                    + "Reading from new files may overwrite existing "
                    + "node and relationship information!");
        }
        
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
            
            int totalNode = sc.nextInt();
            
            for (int i = 0; i < totalNode; i++) {
                Node s = new Node();
                s.name = sc.next();
                s.state = sc.nextInt();
                nodeList.set(s);
            }
            
            while (sc.hasNext()) {
                String s = sc.next();
                String t = sc.next();
                int effect = sc.nextInt();
                relationship.set(s, t, effect);
            }
            
            sc.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Write the network structure into file with compliance to Pajek format
     */
    public void writeFile(String prefix) {
        try {
            
            //=== Write .net Network file ===
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(prefix + ".net"));
            
            //Write Vertices
            writer.write("*Vertices " + Integer.toString(nodeList.size()));
            writer.newLine();
            String[] vertices = nodeList.getNameList();
            for (int i = 0; i < vertices.length; i++) {
                writer.write((i+1) + "\t" + vertices[i]);
                writer.newLine();
            }
            
            //Write Arcs
            writer.write("*Arcs");
            writer.newLine();
            for (int i = 0; i < vertices.length; i++) {
                if (relationship.getNodeList(vertices[i]) == null) { continue; }
                for (int j = 0; j < vertices.length; j++) {
                    if (relationship.get(vertices[i], vertices[j]) != null) {
                        writer.write((i+1) + "\t" + (j+1) + "\t" + relationship.get(vertices[i], vertices[j]));
                        writer.newLine();                        
                    }
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
        NodeList oldNode = nodeList.clone();
        
        for (Node s : nodeList) {
            int score = 0;
            for (Node t : nodeList) {
                if (oldNode.get(t.name).state == NodeList.ON && 
                        relationship.get(t.name, s.name) != null) {
                    score += relationship.get(t.name, s.name);
                }
            }

            //Change the node state according to the score. 
            //If the score equals to 0, the node state will not change.
            if (score > 0) {
                s.state = NodeList.ON;                
            }
            else if (score < 0) {
                s.state = NodeList.OFF;
            }
            
            //Check if node states are changed in this round
            if (s.state != oldNode.get(s.name).state)
                hasChanged = true;
        }
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
    
    public String printNode() {
        return nodeList.toString();
    }
    
    public String printRelationship() {
        return relationship.toString();
    }
    
    public String toString() {
        String result = new String();
        
        result += "Node Information:" + System.lineSeparator();
        result += printNode();
        
        result += "Node Relationship:" + System.lineSeparator();
        result += printRelationship();
        
        return result;
    }
    
    public static void main(String[] args) {
        //Unit Test
//        Network net = new Network();
//        net.writeFile("data/test.txt");
    }
}
