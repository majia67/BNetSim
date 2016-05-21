import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Pajek {

    //Definitions for PAJEK file
    public static String VERTEX_SHAPE = "ellipse";
    public static String VERTEX_COLOR = "LightGreen";
    public static int VERTEX_SIZE = 1;
    public static String MILESTONE_SHAPE = "diamond";
    public static String MILESTONE_COLOR = "Red";
    public static int MILESTONE_SIZE = 10;
    public static String ARCS_POSI_PATTERN = "Solid";
    public static String ARCS_POSI_COLOR = "OliveGreen";
    public static String ARCS_NEGA_PATTERN = "Solid";
    public static String ARCS_NEGA_COLOR = "Red";

    private static NodeList nodeList;
    private static Relationship relationship;
    private static Node[] vertices;
    private static int vertexNumber = 0; 
    
    public Pajek(Network network) {
        nodeList = network.getNodeList();
        relationship = network.getRelationship();
        vertices = nodeList.getNodeList();
    }
    
    private static void readFileHelper(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
        String line = null;
        String mode = null;
        String modePattern = "\\*(\\w+)";
        while (sc.hasNext()) {
            line = sc.findInLine(modePattern);
            if (line != null) { 
                mode = line;
                if (mode.equals("*Vertices")) {
                    vertexNumber = sc.nextInt();
                    nodeList = new NodeList(vertexNumber);
                    relationship = new Relationship(vertexNumber);
                }
                sc.nextLine();
                continue;
            }
            switch (mode) {
            case "*Vertices":
                int idx = sc.nextInt() - 1;
                Node s = new Node(sc.next());
                nodeList.set(idx, s);
                sc.nextLine();
                break;
            case "*Arcs":
                int na = sc.nextInt() - 1;
                int nb = sc.nextInt() - 1;
                int effect = sc.nextInt();
                relationship.set(na, nb, effect);
                sc.nextLine();
                break;
            case "*States":
                s = nodeList.get(sc.nextInt() - 1);
                s.state = sc.nextInt();
                sc.nextLine();
                break;
            case "*Types":
                s = nodeList.get(sc.nextInt() - 1);
                String type = sc.next();
                s.type = type;
                sc.nextLine();
                break;
            case "*Dependencies":
                String tmp = sc.nextLine();
                String[] rlt = tmp.split(" ");
                int[] dpd = new int[rlt.length - 1];
                for (int i = 0; i < dpd.length; i++) {
                    dpd[i] = Integer.parseInt(rlt[i+1]) - 1;
                }
                s = nodeList.get(Integer.parseInt(rlt[0]) - 1);
                s.setDependency(dpd);
                break;
            case "*Milestones":
                s = nodeList.get(sc.nextInt() - 1);
                s.milestoneTermination = sc.nextBoolean();
                sc.nextLine();
                break;
            }
        }
        sc.close();
    }

    public static Network readFile(String filePrefix) {

        try {

            readFileHelper(filePrefix + ".net");
            readFileHelper(filePrefix + ".bns");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new Network(nodeList, relationship);
    }
    
    /*
     * Write the network structure into file with compliance to Pajek format
     */
    public void writeNetwork(String filePrefix) {
        try {


            //=== Write .net Network file ===
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(filePrefix + ".net"));

            //Write Vertices
            writer.write("*Vertices " + Integer.toString(vertices.length));
            writer.newLine();
            for (int i = 0; i < vertices.length; i++) {
                writer.write((i+1) + " " + vertices[i].name + " ");
                switch (vertices[i].type) {
                case "Vertex":
                    writer.write(VERTEX_SHAPE);
                    break;
                case "Milestone":
                    writer.write(MILESTONE_SHAPE);
                    break;
                }
                writer.newLine();
            }

            //Write Arcs
            writer.write("*Arcs");
            writer.newLine();
            for (int i = 0; i < vertices.length; i++) {
                for (int j = 0; j < vertices.length; j++) {
                    int relat = relationship.get(i, j);
                    if (relat != 0) {
                        writer.write((i+1) + " " + (j+1) + " " + relat);
                        if (relat < 0) {
                            writer.write(" c " + ARCS_NEGA_COLOR + " p " + ARCS_NEGA_PATTERN);
                        }
                        else {
                            writer.write(" c " + ARCS_POSI_COLOR + " p " + ARCS_POSI_PATTERN);
                        }
                        writer.newLine();                        
                    }
                }
            }

            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeBNetSimFile(String filePrefix) {
        try {

            //=== Write .bns BNetSim file ===
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(filePrefix + ".bns"));

            //Write States
            writer.write("*States");
            writer.newLine();
            for (int i = 0; i < vertices.length; i++) {
                writer.write((i+1) + " " + vertices[i].state);
                writer.newLine();
            }

            //Write Types
            writer.write("*Types");
            writer.newLine();
            for (int i = 0; i < vertices.length; i++) {
                writer.write((i+1) + " " + vertices[i].type);
                writer.newLine();
            }

            //Write Requirements
            writer.write("*Dependencies");
            writer.newLine();
            for (int i = 0; i < vertices.length; i++) {
                int[] dpd = vertices[i].depends;
                if (dpd != null) {
                    writer.write(Integer.toString(i+1));
                    for (int j : dpd) {
                        writer.write(" " + (j+1));
                    }
                    writer.newLine();
                }
            }

            //Write Milestone
            writer.write("*Milestones");
            writer.newLine();
            for (int i = 0; i < vertices.length; i++) {
                if (vertices[i].type.equals("Milestone")) {
                    writer.write((i+1) + " " + vertices[i].milestoneTermination);
                    writer.newLine();
                }
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    public void writePartition(String filePrefix) {
        try {
            
            //=== Write .clu Partitions file ===
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(filePrefix + ".clu"));

            //Write Partitions
            writer.write("*Vertices " + vertices.length);
            writer.newLine();
            HashMap<String, Integer> partitions = new HashMap<String, Integer>();
            for (Node s : nodeList) {
                if (partitions.containsKey(s.type)) {
                    writer.write(partitions.get(s.type) + "");
                }
                else {
                    writer.write(partitions.size() + "");
                    partitions.put(s.type, partitions.size());
                }
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }              
    }
    
    public static void writeRobustnessTestResult(String filePrefix, Network net, int[] result) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePrefix + ".net"));
            int sizeOfNetwork = net.size();
            //Write Vertices
            writer.write("*Vertices " + RobustnessTest.totalTestNum(net));
            writer.newLine();
            for (int i = 0; i < result.length; i++) {
                writer.write((i+1) + " " + RobustnessTest.d2b(i, sizeOfNetwork));
                if (result[i] == i) {
                    writer.write(" "+ MILESTONE_SHAPE + " s_size " + MILESTONE_SIZE + " ic " + MILESTONE_COLOR);
                }
                else {
                    writer.write(" "+ VERTEX_SHAPE + " s_size " + VERTEX_SIZE + " ic " + VERTEX_COLOR);
                }
                writer.newLine();
            }

            //Write Arcs
            writer.write("*Arcs");
            writer.newLine();
            for (int i = 0; i < result.length; i++) {
                writer.write((i+1) + " " + (result[i]+1) + " 1");
                writer.newLine();    
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
