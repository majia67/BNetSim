import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

class Attractor {
    
    int count;
    
    public Attractor() {
        count = 0;
    }
    
}

public class NetworkAnalysis {
    
    private int size;
    private String[] nodeName;
    private int[] net;
    
    public NetworkAnalysis(String fileName) {
        readFile(fileName);
    }
    
    public void readFile(String fileName) {
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
            
            //Read vertices
            sc.next();
            size = sc.nextInt();
            sc.nextLine();
            
            nodeName = new String[size];
            for (int i = 0; i < size; i++) {
                sc.nextInt();
                nodeName[i] = sc.next();
                sc.nextLine();
            }
            
            //Read network
            sc.nextLine();
            net = new int[size];
            for (int i = 0; i < size; i++) {
                int na = sc.nextInt() - 1;
                int nb = sc.nextInt() - 1;
                net[na] = nb;
                sc.nextLine();
            }
            
            sc.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }        
    }
    
    public void analyze() {
        boolean[] visited = new boolean[size];
        HashMap<Integer, Attractor> attr = new HashMap<Integer, Attractor>();
        
        for (int i = 0; i < size; i++) {
            if (visited[i]) { continue; }
            int j = i;
            int pathLength = 1;
            while (net[j] != j) {
                visited[j] = true;
                j = net[j];
                if (!visited[j]) { pathLength++; }
            }
            if (!attr.containsKey(j)) {
                attr.put(j, new Attractor());
            }
            attr.get(j).count += pathLength;
            visited[j] = true;
        }
        
        for (Entry<Integer, Attractor> a : attr.entrySet()) {
            int idx = a.getKey();
            int count = a.getValue().count;
            System.out.println(nodeName[idx] + " : " + count);
        }
    }
    
    public static void main(String[] args) {
        String fileName = args[0];
        NetworkAnalysis nana = new NetworkAnalysis(fileName);
        nana.analyze();
    }

}
