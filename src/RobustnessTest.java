import java.util.Arrays;

public class RobustnessTest {

    private Network net;
    private int[] result;
    private int[] states;
    private boolean[] isMilestone;
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

        net = Pajek.readFile(file);
        sizeOfNetwork = net.size();
        progress = 0;
        totalTestNum = totalTestNum(net);
        onePercent = totalTestNum / 100;
        
        result = new int[totalTestNum];
        for (int i = 0; i < result.length; i++) {
            result[i] = -1;
        }
        
        states = net.getNodeStates();
        
        isMilestone = new boolean[sizeOfNetwork];
        for (int i = 0; i < sizeOfNetwork; i++) {
            if (net.getNode(i).type.equals("Milestone")) {
                isMilestone[i] = true;
            }
        }
        
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
//            if (isMilestone[index]) {
//                generator(index + 1);
//            }
//            else {
                states[index] = 0;
                generator(index + 1);
                states[index] = 1;
                generator(index + 1);
//            }
        }
    }
    
    public static int b2d(String binaryString) {
        return Integer.parseInt(binaryString, 2);
    }
    
    public static String d2b(int decimal, int length) {
        String binString = Integer.toBinaryString(decimal);
        char[] padArray = new char[length - binString.length()];
        Arrays.fill(padArray, '0');
        String padString = new String(padArray);
        return padString + binString;
    }
    
    public static int totalTestNum(Network net) {
        return (int) Math.pow(2, net.size());
    }
    
    public int run() {
        String oldState = net.getNodeStateString();
        for (int i = 0; i < MAXROUND; i++) {
            
            if (result[b2d(oldState)] != -1) {
                return 1;
            }
            
            net.next();

            //Record simulation result
            String newState = net.getNodeStateString();
            result[b2d(oldState)] = b2d(newState);
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
        Pajek.writeRobustnessTestResult(fileName, net, result);
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
