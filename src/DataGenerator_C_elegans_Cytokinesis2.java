import java.io.File;

/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class generates the testing data used in Dr. Guanyu Wang's paper
 *  XXX
 *  
 */

public class DataGenerator_C_elegans_Cytokinesis2 {
    
    private static String fileName = "data" + File.separator + 
                                     "C_elegans_Cytokinesis";
    private static int POSI                 = 1;
    private static int NEGA                 = -100;
    private static int SELFINHIB            = -1;
    private static int ON                   = Node.ON;
    private static int OFF                  = Node.OFF;
    
    public static void main(String[] args) {
        NodeList nList = new NodeList();
        RelationshipMap relat = new RelationshipMap();
        Node nd;
        
        //Insert node information
        nList.add(new Node("CYK-4", OFF));
        nList.add(new Node("ZEN-4", OFF));
        nList.add(new Node("NOP-1", OFF));
        nList.add(new Node("ECT-2", OFF));
        nList.add(new Node("RGA-3/4", ON));
        nList.add(new Node("RhoA(GDP)", ON));
        nList.add(new Node("RhoA(GTP)", OFF));
        nList.add(new Node("ANI-1(Cortex)", ON));
        nList.add(new Node("ANI-1(CenSpin)", OFF));
        nList.add(new Node("F-Actin", OFF));
        nd = new Node("CenSpindlin", OFF);
        nd.setRequires("CYK-4", ON);
        nd.setRequires("ZEN-4", ON);
        nList.add(nd);
        nList.add(new Node("KIF-4", OFF));
        nList.add(new Node("PRC-1", OFF));
        nList.add(new Node("CenSpinMt", OFF));
        nList.add(new Node("AstralMt", OFF));
        nd = new Node("PLK-1", OFF);
        nd.setRequires("CenSpindlin", ON);
        nList.add(nd);
        nList.add(new Node("CDK-1", ON));
        
        nd = new Node("ROCK", OFF);
        nd.setRequires("ContrRing", ON);
        nList.add(nd);
        
        nList.add(new Node("Profilin", OFF));
        
        nd = new Node("LET-502", OFF);
        nd.setRequires("ContrRing", ON);
        nList.add(nd);
        
        nd = new Node("MEL-11", ON);
        nd.setRequires("ContrRing", ON);
        nList.add(nd);
        
        nList.add(new Node("NMY-2", OFF));
        
        nd = new Node("NMY-2(P)", OFF);
        nd.setRequires("NMY-2", ON);
        nList.add(nd);
        
        nd = new Node("MLC-4", OFF);
        nd.setRequires("NMY-2", ON);
        nList.add(nd);
        
        nd = new Node("MLC-4(P)", OFF);
        nd.setRequires("MLC-4", ON);
        nList.add(nd);
        
        nList.add(new Node("CYK-1", OFF));
        nList.add(new Node("AnaOnset", ON, "Milestone", false));
        
        nd = new Node("ContrRing", OFF, "Milestone", false);
        nd.setRequires("MLC-4", ON);
        nd.setRequires("NMY-2", ON);
        nd.setRequires("F-Actin", ON);
        nd.setRequires("CYK-1", ON);
        nList.add(nd);
        
        nd = new Node("CleaFurContr", OFF, "Milestone", true);
        nd.setRequires("ContrRing", ON);
        nList.add(nd);        

        relat.set("CYK-4", "CenSpindlin", POSI);
        relat.set("ZEN-4", "CenSpindlin", POSI);
        relat.set("NOP-1", "ECT-2", POSI);
        relat.set("ECT-2", "RhoA(GTP)", POSI);
        relat.set("ECT-2", "RhoA(GDP)", NEGA);
        relat.set("ECT-2", "RGA-3/4", NEGA);
        relat.set("RGA-3/4", "RhoA(GDP)", POSI);
        relat.set("RGA-3/4", "RhoA(GTP)", NEGA);
        relat.set("RhoA(GTP)", "ANI-1(Cortex)", POSI);
        relat.set("RhoA(GTP)", "ANI-1(CenSpin)", POSI);
        relat.set("RhoA(GTP)", "ROCK", POSI);
        relat.set("RhoA(GTP)", "CYK-1", POSI);
        relat.set("ANI-1(Cortex)", "ContrRing", NEGA);
        relat.set("ANI-1(CenSpin)", "NMY-2", POSI);
        relat.set("ANI-1(CenSpin)", "MLC-4", POSI);
        relat.set("F-Actin", "ContrRing", POSI);
        relat.set("F-Actin", "NMY-2", POSI);
        relat.set("F-Actin", "MLC-4", POSI);
        relat.set("CenSpindlin", "ECT-2", POSI);
        relat.set("KIF-4", "CenSpinMt", POSI);
        relat.set("KIF-4", "AstralMt", POSI);
        relat.set("PRC-1", "KIF-4", POSI);
        relat.set("PRC-1", "PLK-1", POSI);
        relat.set("PRC-1", "CenSpinMt", POSI);
        relat.set("PRC-1", "AstralMt", POSI);
        relat.set("CenSpinMt", "CYK-4", POSI);
        relat.set("CenSpinMt", "ZEN-4", POSI);
        relat.set("AstralMt", "ANI-1(CenSpin)", POSI);
        relat.set("AstralMt", "ANI-1(Cortex)", NEGA);
        relat.set("PLK-1", "ECT-2", POSI);
        relat.set("PLK-1", "KIF-4", NEGA);
        relat.set("CDK-1", "CYK-4", NEGA);
        relat.set("CDK-1", "PRC-1", NEGA);
        relat.set("CDK-1", "ZEN-4", NEGA);
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
        relat.set("AnaOnset", "PRC-1", POSI);
        relat.set("AnaOnset", "PLK-1", POSI);
        relat.set("AnaOnset", "CDK-1", NEGA);
        relat.set("ContrRing", "LET-502", POSI);

        Network net = new Network(nList, relat);
        Pajek pj = new Pajek();
        pj.writeFile(fileName, net);
    }

}
