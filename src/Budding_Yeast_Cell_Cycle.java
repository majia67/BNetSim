import java.io.File;

/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class generates the testing data used in Dr. Guanyu Wang's paper
 *  XXX
 *  
 */

public class Budding_Yeast_Cell_Cycle {
    
    private static String fileName = "data" + File.separator + 
                              "Budding_Yeast_Cell_Cycle";
    private static int positive             = 1;
    private static int negative             = -100;
    private static int selfInhibition       = -1;
    private static int on                   = Node.ON;
    private static int off                  = Node.OFF;
    
    public static void main(String[] args) {
        
        String[] nameOfNodes = {"Cln3", "MBF", "SBF", "Cln1,2", "Cdh1", "Swi5", "Cdc20/14", "Clb5,6", "Sic1", "Clb1,2", "Mcm1/SFF"};
        NetworkGenerator ng = new NetworkGenerator(nameOfNodes);
        
        //Set initial activated nodes (default is off)
        ng.setNodeState("Cln3", on);
        ng.setNodeState("Cdh1", on);
        ng.setNodeState("Sic1", on);
        
        //Set relationship
        ng.setRelationship("Cln3", "Cln3", selfInhibition);
        ng.setRelationship("Cln3", "MBF", positive);
        ng.setRelationship("Cln3", "SBF", positive);
        ng.setRelationship("MBF", "Clb5,6", positive);
        ng.setRelationship("SBF", "Cln1,2", positive);
        ng.setRelationship("Cln1,2", "Cln1,2", selfInhibition);
        ng.setRelationship("Cln1,2", "Cdh1", negative);
        ng.setRelationship("Cln1,2", "Sic1", negative);
        ng.setRelationship("Cdh1", "Clb1,2", negative);
        ng.setRelationship("Swi5", "Swi5", selfInhibition);
        ng.setRelationship("Cdc20/14", "Cdh1", positive);
        ng.setRelationship("Cdc20/14", "Swi5", positive);
        ng.setRelationship("Cdc20/14", "Cdc20/14", selfInhibition);
        ng.setRelationship("Cdc20/14", "Clb5,6", negative);
        ng.setRelationship("Cdc20/14", "Sic1", positive);
        ng.setRelationship("Cdc20/14", "Clb1,2", negative);
        ng.setRelationship("Clb5,6", "Cdh1", negative);
        ng.setRelationship("Clb5,6", "Sic1", negative);
        ng.setRelationship("Clb5,6", "Clb1,2", positive);
        ng.setRelationship("Clb5,6", "Mcm1/SFF", positive);
        ng.setRelationship("Sic1", "Clb5,6", negative);
        ng.setRelationship("Sic1", "Clb1,2", negative);
        ng.setRelationship("Clb1,2", "MBF", negative);
        ng.setRelationship("Clb1,2", "SBF", negative);
        ng.setRelationship("Clb1,2", "Cdh1", negative);
        ng.setRelationship("Clb1,2", "Cln3", negative);
        ng.setRelationship("Clb1,2", "Cdc20/14", positive);
        ng.setRelationship("Clb1,2", "Sic1", negative);
        ng.setRelationship("Clb1,2", "Mcm1/SFF", positive);
        ng.setRelationship("Mcm1/SFF", "Swi5", positive);
        ng.setRelationship("Mcm1/SFF", "Cdc20/14", positive);
        ng.setRelationship("Mcm1/SFF", "Clb1,2", positive);
        ng.setRelationship("Mcm1/SFF", "Mcm1/SFF", selfInhibition);
        
        ng.writeFile(fileName, false);
    }

}
