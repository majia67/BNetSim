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
    public static String MILESTONE_SHAPE = "diamond";
    public static String ARCS_POSI_PATTERN = "Solid";
    public static String ARCS_POSI_COLOR = "OliveGreen";
    public static String ARCS_NEGA_PATTERN = "Solid";
    public static String ARCS_NEGA_COLOR = "Red";

    private NodeList nodes;
    private Relationship relat;
    private int vertexNumber = 0; 

    private void readFileHelper(String file) throws FileNotFoundException {
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
                    nodes = new NodeList(vertexNumber);
                    relat = new Relationship(vertexNumber);
                }
                sc.nextLine();
                continue;
            }
            switch (mode) {
            case "*Vertices":
                int idx = sc.nextInt() - 1;
                Node s = new Node(sc.next());
                nodes.set(idx, s);
                break;
            case "*Arcs":
                int na = sc.nextInt() - 1;
                int nb = sc.nextInt() - 1;
                int effect = sc.nextInt();
                relat.set(na, nb, effect);
                break;
            case "*States":
                s = nodes.get(sc.nextInt() - 1);
                s.state = sc.nextInt();
                break;
            case "*Types":
                s = nodes.get(sc.nextInt() - 1);
                String type = sc.next();
                s.type = type;
                break;
            case "*Dependencies":
                String tmp = sc.next();
                String[] rlt = tmp.split(" ");
                int[] dpd = new int[rlt.length - 1];
                for (int i = 0; i < dpd.length; i++) {
                    dpd[i] = Integer.parseInt(rlt[i+1]) - 1;
                }
                s = nodes.get(Integer.parseInt(rlt[0]) - 1);
                s.setDependency(dpd);
                break;
            case "*Milestones":
                s = nodes.get(sc.nextInt() - 1);
                s.milestoneTermination = sc.nextBoolean();
                break;
            }
            sc.nextLine();
        }
        sc.close();
    }

    public Network readFile(String filePrefix) {

        try {

            readFileHelper(filePrefix + ".net");
            readFileHelper(filePrefix + ".bns");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new Network(nodes, relat);
    }
    
    /*
     * Write the network structure into file with compliance to Pajek format
     */
    public void writeFile(String filePrefix, Network network) {
        try {

            NodeList nodeList = network.getNodeList();
            Relationship relationship = network.getRelationship();
            Node[] vertices = nodeList.getNodeList();

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

            //=== Write .bns BNetSim file ===
            writer = new BufferedWriter(
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

            //=== Write .clu Partitions file ===
            writer = new BufferedWriter(
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

    public void writeRobustnessTestResult(String filePrefix, HashMap<String, String> resultMap, int[] terminatedMilestone) {

        String[] result = resultMap.keySet().toArray(new String[0]);
        HashMap<String, Integer> index = new HashMap<String, Integer>();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePrefix + ".net"));
            //Write Vertices
            writer.write("*Vertices " + Integer.toString(result.length));
            writer.newLine();
            for (int i = 0; i < result.length; i++) {
                writer.write((i+1) + " " + result[i]);
                for (int s : terminatedMilestone) {
                    if (result[i].charAt(s) == '1') {
                        writer.write(" "+ MILESTONE_SHAPE);
                        break;
                    }
                }
                writer.newLine();
                index.put(result[i], i);
            }

            //Write Arcs
            writer.write("*Arcs");
            writer.newLine();
            for (int i = 0; i < result.length; i++) {
                writer.write((i+1) + " " + index.get(resultMap.get(result[i])) + " 1");
                writer.newLine();    
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
