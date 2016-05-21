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
        network = Pajek.readFile(file);
    }
    
    public void run(int maxRound) {
        System.out.println("Original state");
        System.out.println(network.printNode());
        for (int i = 0; i < maxRound; i ++) {
            network.next();
            //System.out.println("Loop " + network.getLoop());
            System.out.println(network.printState());
            if (!network.hasChanged()) {
                System.out.println("Simulation remains stable.");
                break;
            }
            if(network.hasTerminated()) {
                System.out.println("Simulation is terminated.");
                break;
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
