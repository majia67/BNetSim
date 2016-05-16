import java.util.HashMap;

public class RobustnessTest {

    private Network net;
    private HashMap<String, String> result;
    private int[] states;
    private int sizeOfNetwork;
    private int[] reasonOfStop;
    //    private long[] loop_counter;
    private int progress;
    private int totalTestNum;
    private int onePercent;

    private static final int MAXROUND = 20;
    private static final String[] STOP_REASON = 
        {"Remains Stable", "Repeat Same Road", "Terminated", "Exceed Max Round"};

    public RobustnessTest(String file) {
        Pajek pj = new Pajek();
        net = pj.readFile(file);
        sizeOfNetwork = net.size();
        progress = 0;
        totalTestNum = (int) Math.pow(2, net.size() - net.getMilestoneNodesNum());
        onePercent = totalTestNum / 100;

        result = new HashMap<String, String>(totalTestNum);
        states = net.getNodeStates();

        reasonOfStop = new int[STOP_REASON.length];
        //        loop_counter = new long[MAXROUND+1];
        
        System.out.println("Total test cases: " + totalTestNum);
    }

    public void generator(int index) {

        if (index == sizeOfNetwork) {

            //Run simulation
            net.setNodeStates(states);
            int ros = run();

            //Record reason of stop
            reasonOfStop[ros] += 1;

            //            //Record loop number
            //            loop_counter[network.getLoop()] += 1;

            //Report progress
            progress += 1;
            if (progress % onePercent == 0) {
                System.out.println("Complete " + (progress / onePercent) + "% (" + progress + " test cases passed)");
            }

        }
        else {
            if (net.getNode(index).type.equals("Milestone")) {
                generator(index + 1);
            }
            else {
                states[index] = 0;
                generator(index + 1);
                states[index] = 1;
                generator(index + 1);
            }
        }
    }

    public int run() {
        String oldState = net.getNodeStateString();
        for (int i = 0; i < MAXROUND; i++) {
            
            if (result.containsKey(oldState)) {
                return 1;
            }
            
            net.next();

            //Record simulation result
            String newState = net.getNodeStateString();
            result.put(oldState, newState);
            oldState = newState;

            if (!net.hasChanged()) {
                return 0;
            }
            else if(net.hasTerminated()) {
                return 2;
            }
        }
        return 3;
    }
    
    public void writeResult(String fileName) {

        System.out.println();
        System.out.println("===Write Report===");
        System.out.println();

        System.out.println("Reason of Stop:");
        for (int i = 0; i < STOP_REASON.length; i++) {
            System.out.println(STOP_REASON[i] + ": " + reasonOfStop[i] + " time(s).");
        }
        System.out.println();

        //        System.out.println("Loop Counter:");
        //        for (int i = 1; i <= MAXROUND; i++) {
        //            System.out.println(i + ": " + loop_counter[i] + " time(s).");
        //        }
        //        System.out.println();

        System.out.println("Export Pajek network file...");
        Pajek pj = new Pajek();
        pj.writeRobustnessTestResult(fileName, result, net.getMilestoneNodesIndex(true));
        System.out.println("Complete!");

    }

    public static void main(String[] args) {
        // Test Client
        String fileName = args[0];
        RobustnessTest rt = new RobustnessTest(fileName);

        System.out.println();
        System.out.println("===Enumeration of all possible states of the network===");
        System.out.println();

        rt.generator(0);
        rt.writeResult(fileName + ".result");
    }

}
