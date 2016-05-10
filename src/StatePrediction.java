/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class predicts the network status with given nodes relationship
 *  
 */

public class StatePrediction {
    
    Network network;
    
    public StatePrediction(String file) {
        network = new Network(file);
    }
    
    public void run(int maxRound) {
        System.out.println("Original state");
        System.out.println(network.printNode());
        for (int i = 0; i < maxRound; i ++) {
            network.next();
            if (!network.hasChanged()) {
                System.out.println("Network remains stable.");
                System.out.println(network.printNode());
                break;
            }
            else {
                System.out.println("Loop " + network.getLoop());
                System.out.println(network.printNode());
            }
        }
    }
    
    public static void main(String[] args) {
        // Test Client
        String fileName = args[0];
        StatePrediction sp = new StatePrediction(fileName);
        sp.run(50);
    }

}
