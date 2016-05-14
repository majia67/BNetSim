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
                              "Budding_Yeast_Cell_Cycle";
    private static int positive             = 1;
    private static int negative             = -100;
    private static int selfInhibition       = -1;
    private static int on                   = 1;
    private static int off                  = 0;
    
    public static void main(String[] args) {
        NodeList nList = new NodeList();
        Relationship relat = new Relationship();
        
        //Insert node information
        nList.add(new Node("Cln3", on));
        nList.add(new Node("MBF", off));
        nList.add(new Node("SBF", off));
        nList.add(new Node("Cln1,2", off));
        nList.add(new Node("Cdh1", on));
        nList.add(new Node("Swi5", off));
        nList.add(new Node("Cdc20/14", off));
        nList.add(new Node("Clb5,6", off));
        nList.add(new Node("Sic1", on));
        nList.add(new Node("Clb1,2", off));
        nList.add(new Node("Mcm1/SFF", off));

        relat.set("Cln3", "Cln3", selfInhibition);
        relat.set("Cln3", "MBF", positive);
        relat.set("Cln3", "SBF", positive);
        relat.set("MBF", "Clb5,6", positive);
        relat.set("SBF", "Cln1,2", positive);
        relat.set("Cln1,2", "Cln1,2", selfInhibition);
        relat.set("Cln1,2", "Cdh1", negative);
        relat.set("Cln1,2", "Sic1", negative);
        relat.set("Cdh1", "Clb1,2", negative);
        relat.set("Swi5", "Swi5", selfInhibition);
        relat.set("Cdc20/14", "Cdh1", positive);
        relat.set("Cdc20/14", "Swi5", positive);
        relat.set("Cdc20/14", "Cdc20/14", selfInhibition);
        relat.set("Cdc20/14", "Clb5,6", negative);
        relat.set("Cdc20/14", "Sic1", positive);
        relat.set("Cdc20/14", "Clb1,2", negative);
        relat.set("Clb5,6", "Cdh1", negative);
        relat.set("Clb5,6", "Sic1", negative);
        relat.set("Clb5,6", "Clb1,2", positive);
        relat.set("Clb5,6", "Mcm1/SFF", positive);
        relat.set("Sic1", "Clb5,6", negative);
        relat.set("Sic1", "Clb1,2", negative);
        relat.set("Clb1,2", "MBF", negative);
        relat.set("Clb1,2", "SBF", negative);
        relat.set("Clb1,2", "Cdh1", negative);
        relat.set("Clb1,2", "Cln3", negative);
        relat.set("Clb1,2", "Cdc20/14", positive);
        relat.set("Clb1,2", "Sic1", negative);
        relat.set("Clb1,2", "Mcm1/SFF", positive);
        relat.set("Mcm1/SFF", "Swi5", positive);
        relat.set("Mcm1/SFF", "Cdc20/14", positive);
        relat.set("Mcm1/SFF", "Clb1,2", positive);
        relat.set("Mcm1/SFF", "Mcm1/SFF", selfInhibition);
        
        Network net = new Network(nList, relat);
        Pajek pj = new Pajek();
        pj.writeFile(fileName, net);
    }

}
