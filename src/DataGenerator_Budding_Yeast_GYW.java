import java.io.File;

/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class generates the testing data used in Dr. Guanyu Wang's paper
 *  XXX
 *  
 */

public class DataGenerator_Budding_Yeast_GYW {
    
    private static String fileName = "data" + File.separator + 
                              "Budding_Yeast_Cell_Cycle.txt";
    private static int positive             = 1;
    private static int negative             = -100;
    private static int selfInhibition       = -1;
    private static int on                   = 1;
    private static int off                  = 0;
    
    public static void main(String[] args) {
        Network net = new Network();
        
        //Insert node information
        net.setNode("Cln3", on);
        net.setNode("MBF", off);
        net.setNode("SBF", off);
        net.setNode("Cln1,2", off);
        net.setNode("Cdh1", on);
        net.setNode("Swi5", off);
        net.setNode("Cdc20/14", off);
        net.setNode("Clb5,6", off);
        net.setNode("Sic1", on);
        net.setNode("Clb1,2", off);
        net.setNode("Mcm1/SFF", off);

        //Insert relationship information
        try {
            net.setRelationship("Cln3", "Cln3", selfInhibition);
            net.setRelationship("Cln3", "MBF", positive);
            net.setRelationship("Cln3", "SBF", positive);
            net.setRelationship("MBF", "Clb5,6", positive);
            net.setRelationship("SBF", "Cln1,2", positive);
            net.setRelationship("Cln1,2", "Cln1,2", selfInhibition);
            net.setRelationship("Cln1,2", "Cdh1", negative);
            net.setRelationship("Cln1,2", "Sic1", negative);
            net.setRelationship("Cdh1", "Clb1,2", negative);
            net.setRelationship("Swi5", "Swi5", selfInhibition);
            net.setRelationship("Cdc20/14", "Cdh1", positive);
            net.setRelationship("Cdc20/14", "Swi5", positive);
            net.setRelationship("Cdc20/14", "Cdc20/14", selfInhibition);
            net.setRelationship("Cdc20/14", "Clb5,6", negative);
            net.setRelationship("Cdc20/14", "Sic1", positive);
            net.setRelationship("Cdc20/14", "Clb1,2", negative);
            net.setRelationship("Clb5,6", "Cdh1", negative);
            net.setRelationship("Clb5,6", "Sic1", negative);
            net.setRelationship("Clb5,6", "Clb1,2", positive);
            net.setRelationship("Clb5,6", "Mcm1/SFF", positive);
            net.setRelationship("Sic1", "Clb5,6", negative);
            net.setRelationship("Sic1", "Clb1,2", negative);
            net.setRelationship("Clb1,2", "MBF", negative);
            net.setRelationship("Clb1,2", "SBF", negative);
            net.setRelationship("Clb1,2", "Cdh1", negative);
            net.setRelationship("Clb1,2", "Cln3", negative);
            net.setRelationship("Clb1,2", "Cdc20/14", positive);
            net.setRelationship("Clb1,2", "Sic1", negative);
            net.setRelationship("Clb1,2", "Mcm1/SFF", positive);
            net.setRelationship("Mcm1/SFF", "Swi5", positive);
            net.setRelationship("Mcm1/SFF", "Cdc20/14", positive);
            net.setRelationship("Mcm1/SFF", "Clb1,2", positive);
            net.setRelationship("Mcm1/SFF", "Mcm1/SFF", selfInhibition);
        } catch (InvalidRelationshipException e) {
            e.printStackTrace();
        }
        
        net.writeFile(fileName);
    }

}
