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
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Scanner;

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
    
    public Network(String file) {
        this();
        this.readFile(file);
    }
    
    public Network(NodeList nList, Relationship relat) {
        this();
        nodeList = nList;
        relationship = relat;
        
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
    
    private void readFileHelper(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
        String line = null;
        String mode = null;
        String modePattern = "\\*(\\w+)";
        while (sc.hasNext()) {
            line = sc.findInLine(modePattern);
            if (line != null) { 
                mode = line;
                sc.nextLine();
                continue;
            }
            switch (mode) {
            case "*Vertices":
                Node s = new Node();
                sc.nextInt();
                s.name = sc.next();
                nodeList.add(s);
                break;
            case "*Arcs":
                Node na = nodeList.get(sc.nextInt());
                Node nb = nodeList.get(sc.nextInt());
                int relat = sc.nextInt();
                relationship.set(na.name, nb.name, relat);
                break;
            case "*States":
                na = nodeList.get(sc.nextInt());
                na.state = sc.nextInt();
                break;
            case "*Types":
                na = nodeList.get(sc.nextInt());
                String type = sc.next();
                na.type = type;
                break;
            case "*Requirements":
                na = nodeList.get(sc.nextInt());
                nb = nodeList.get(sc.nextInt());
                int state = sc.nextInt();
                na.setRequires(nb.name, state);
                break;
            case "*Milestone":
                na = nodeList.get(sc.nextInt());
                na.milestone_termination = sc.nextBoolean();
                break;
            }
            sc.nextLine();
        }
        sc.close();
    }
    
    private void readFile(String prefix) {

        nodeList = new NodeList();
        relationship = new Relationship();
        
        try {
            
            readFileHelper(prefix + ".net");
            readFileHelper(prefix + ".bns");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Write the network structure into file with compliance to Pajek format
     */
    public void writeFile(String prefix) {
        try {

            String[] vertices = nodeList.getNameList();
            
            //=== Write .net Network file ===
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(prefix + ".net"));
            
            //Write Vertices
            writer.write("*Vertices " + Integer.toString(nodeList.size()));
            writer.newLine();
            for (int i = 1; i <= nodeList.size(); i++) {
                writer.write(i + "\t" + vertices[i]);
                writer.newLine();
            }
            
            //Write Arcs
            writer.write("*Arcs");
            writer.newLine();
            for (int i = 1; i <= nodeList.size(); i++) {
                if (relationship.getNodeList(vertices[i]) == null) { continue; }
                for (int j = 1; j <= nodeList.size(); j++) {
                    if (relationship.get(vertices[i], vertices[j]) != null) {
                        writer.write(i + "\t" + j + "\t" + relationship.get(vertices[i], vertices[j]));
                        writer.newLine();                        
                    }
                }
            }
            
            writer.close();

            //=== Write .bns BNetSim file ===
            writer = new BufferedWriter(
                    new FileWriter(prefix + ".bns"));
            
            //Write States
            writer.write("*States");
            writer.newLine();
            for (int i = 1; i <= nodeList.size(); i++) {
                writer.write(i + "\t" + nodeList.get(i).state);
                writer.newLine();
            }
            
            //Write Types
            writer.write("*Types");
            writer.newLine();
            for (int i = 1; i <= nodeList.size(); i++) {
                writer.write(i + "\t" + nodeList.get(i).type);
                writer.newLine();
            }
            
            //Write Requirements
            writer.write("*Requirements");
            writer.newLine();
            for (int i = 1; i <= nodeList.size(); i++) {
                Hashtable<String, Integer> req = nodeList.get(i).requires;
                if (req != null) {
                    for (String s : req.keySet()) {
                        writer.write(i + "\t" + (nodeList.getIndex(s)) + "\t" + req.get(s));
                        writer.newLine();
                    }
                }
            }
            
            //Write Milestone
            writer.write("*Milestone");
            writer.newLine();
            for (int i = 1; i <= nodeList.size(); i++) {
                if (nodeList.get(i).type == "Milestone") {
                    writer.write(i + "\t" + nodeList.get(i).milestone_termination);
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
            if (s.type.equals("Milestone") && s.milestone_termination && s.state == Node.ON) {
                hasTerminated = true;
            }
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
    
    public boolean hasTerminated() {
        return hasTerminated;
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
