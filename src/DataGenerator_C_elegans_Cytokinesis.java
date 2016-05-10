import java.io.File;

/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class generates the testing data used in Dr. Guanyu Wang's paper
 *  XXX
 *  
 */

public class DataGenerator_C_elegans_Cytokinesis {
    
    private static String fileName = "data" + File.separator + 
                                     "C_elegans_Cytokinesis";
    private static int POSI                 = 1;
    private static int NEGA                 = -100;
    private static int SELFINHIB            = -1;
    private static int ON                   = Node.ON;
    private static int OFF                  = Node.OFF;
    
    public static void main(String[] args) {
        NodeList nList = new NodeList();
        Relationship relat = new Relationship();
        
        //Insert node information
        Node nd;
        nList.add(new Node("CYK-4", ON));
        nList.add(new Node("ZEN-4", OFF));
        nList.add(new Node("NOP-1", OFF));
        nList.add(new Node("ECT-2", OFF));
        nList.add(new Node("RGA-3/4", OFF));
        nList.add(new Node("RhoA(GDP)", OFF));
        nList.add(new Node("RhoA(GTP)", OFF));
        nList.add(new Node("ANI-1", OFF));
        nList.add(new Node("F-Actin", OFF));
        nd = new Node("CenSpindlin", OFF);
        nd.setRequires("CYK-4", ON);
        nd.setRequires("ZEN-4", ON);
        nList.add(nd);
        nList.add(new Node("KIF4", OFF));
        nList.add(new Node("PRC1", OFF));
        nList.add(new Node("CenSpinMt", OFF));
        nList.add(new Node("AstralMt", OFF));
        nList.add(new Node("Plk1", OFF));
        nList.add(new Node("Cdk1", OFF));
        nList.add(new Node("ROCK", OFF));
        nList.add(new Node("Profilin", OFF));
        nList.add(new Node("LET-502", OFF));
        nList.add(new Node("MEL-11", OFF));
        nList.add(new Node("NMY-2", OFF));
        nList.add(new Node("NMY-2(P)", OFF));
        nList.add(new Node("MLC-4", OFF));
        nList.add(new Node("MLC-4(P)", OFF));
        nList.add(new Node("CYK-1", OFF));
        nList.add(new Node("ContrRing", OFF, "Milestone", false));
        nList.add(new Node("CleaFurContr", OFF, "Milestone", true));
        

        relat.set("CYK-4", "CenSpindlin", POSI);
        relat.set("ZEN-4", "CenSpindlin", POSI);
        relat.set("NOP-1", "ECT-2", POSI);
        relat.set("ECT-2", "RhoA(GTP)", POSI);
        relat.set("ECT-2", "RhoA(GDP)", NEGA);
        relat.set("RGA-3/4", "RhoA(GDP)", POSI);
        relat.set("RGA-3/4", "RhoA(GTP)", NEGA);
        relat.set("RhoA(GTP)", "ANI-1", POSI);
        relat.set("RhoA(GTP)", "ROCK", POSI);
        relat.set("RhoA(GTP)", "CYK-1", POSI);
        relat.set("ANI-1", "NMY-2", POSI);
        relat.set("ANI-1", "AstralMt", POSI);
        relat.set("F-Actin", "ContrRing", POSI);
        relat.set("F-Actin", "NMY-2", POSI);
        relat.set("F-Actin", "MLC-4", POSI);
        relat.set("CenSpindlin", "ECT-2", POSI);
        relat.set("KIF4", "CenSpinMt", POSI);
        relat.set("PRC1", "KIF4", POSI);
        relat.set("PRC1", "CenSpinMt", POSI);
        relat.set("PRC1", "Plk1", POSI);
        relat.set("CenSpinMt", "CenSpindlin", POSI);
        relat.set("AstralMt", "ANI-1", POSI);
        relat.set("Plk1", "ECT-2", POSI);
        relat.set("Plk1", "KIF4", POSI);
        relat.set("Cdk1", "CYK-4", NEGA);
        relat.set("Cdk1", "PRC1", NEGA);
        relat.set("Cdk1", "ZEN-4", NEGA);
        relat.set("ROCK", "MLC-4(P)", POSI);
        relat.set("ROCK", "MEL-11", NEGA);
        relat.set("Profilin", "F-Actin", POSI);
        relat.set("LET-502", "MEL-11", NEGA);
        relat.set("MEL-11", "MLC-4(P)", NEGA);
        relat.set("NMY-2", "ContrRing", POSI);
        relat.set("NMY-2(P)", "CleaFurContr", POSI);
        relat.set("MLC-4", "ContrRing", POSI);
        relat.set("MLC-4(P)", "NMY-2(P)", POSI);
        relat.set("CYK-1", "ContrRing", POSI);
        relat.set("CYK-1", "RhoA(GTP)", POSI);
        relat.set("CYK-1", "Profilin", POSI);
        relat.set("CYK-1", "CYK-1", SELFINHIB);
        relat.set("ContrRing", "LET-502", POSI);

        Network net = new Network(nList, relat);
        net.writeFile(fileName);
    }

}
