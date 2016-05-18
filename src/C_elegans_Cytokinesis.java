import java.io.File;

/*
 *  Undergraduate Project: State Prediction
 *  CopyRight: Yicong Tao, 2016
 *  ------
 *  This Class generates the testing data used in Dr. Guanyu Wang's paper
 *  XXX
 *  
 */

public class C_elegans_Cytokinesis {
    
    private static String fileName = "data" + File.separator + 
                                     "C_elegans_Cytokinesis";
    private static int POSI                 = 1;
    private static int NEGA                 = -100;
    private static int SELFINHIB            = -1;
    private static int ON                   = Node.ON;
    private static int OFF                  = Node.OFF;
    
    public static void main(String[] args) {
        
        String[] nameOfNodes = {"CYK-4", "ZEN-4", "NOP-1", "ECT-2", "RGA-3/4", "RhoA(GDP)", "RhoA(GTP)",
                "ANI-1(Cortex)", "ANI-1(CenSpin)", "F-Actin", "CenSpindlin", "KIF-4", "PRC-1", "CenSpinMt",
                "AstralMt", "PLK-1", "CDK-1", "ROCK", "Profilin", "LET-502", "MEL-11", "NMY-2", "NMY-2(P)",
                "MLC-4", "MLC-4(P)", "CYK-1", "AnaOnset", "ContrRing", "CleaFurContr"};
        
        NetworkGenerator ng = new NetworkGenerator(nameOfNodes);
        
        //Set initial activated nodes (default is off)
        ng.setNodeState("RGA-3/4", ON);
        ng.setNodeState("RhoA(GDP)", ON);
        ng.setNodeState("ANI-1(Cortex)", ON);
        ng.setNodeState("CDK-1", ON);
        ng.setNodeState("MEL-11", ON);
        ng.setNodeState("AnaOnset", ON);
        
        //Set milestone
        ng.setMilestone("AnaOnset", false);
        ng.setMilestone("ContrRing", false);
        ng.setMilestone("CleaFurContr", true);
        
        //Set dependencies
        ng.setDependency("CenSpindlin", "CYK-4", "ZEN-4");
        ng.setDependency("PLK-1", "CenSpindlin");
        ng.setDependency("ROCK", "ContrRing");
        ng.setDependency("LET-502", "ContrRing");
        ng.setDependency("MEL-11", "ContrRing");
        ng.setDependency("NMY-2(P)", "NMY-2");
        ng.setDependency("MLC-4", "NMY-2");
        ng.setDependency("MLC-4(P)", "MLC-4");
        ng.setDependency("ContrRing", "MLC-4", "NMY-2", "F-Actin", "CYK-1");
        ng.setDependency("CleaFurContr", "ContrRing");
        
        //Set relationship
        ng.setRelationship("CYK-4", "CenSpindlin", POSI);
        ng.setRelationship("ZEN-4", "CenSpindlin", POSI);
        ng.setRelationship("NOP-1", "ECT-2", POSI);
        ng.setRelationship("ECT-2", "RhoA(GTP)", POSI);
        ng.setRelationship("ECT-2", "RhoA(GDP)", NEGA);
        ng.setRelationship("ECT-2", "RGA-3/4", NEGA);
        ng.setRelationship("RGA-3/4", "RhoA(GDP)", POSI);
        ng.setRelationship("RGA-3/4", "RhoA(GTP)", NEGA);
        ng.setRelationship("RhoA(GTP)", "ANI-1(Cortex)", POSI);
        ng.setRelationship("RhoA(GTP)", "ANI-1(CenSpin)", POSI);
        ng.setRelationship("RhoA(GTP)", "ROCK", POSI);
        ng.setRelationship("RhoA(GTP)", "CYK-1", POSI);
        ng.setRelationship("ANI-1(Cortex)", "ContrRing", NEGA);
        ng.setRelationship("ANI-1(CenSpin)", "NMY-2", POSI);
        ng.setRelationship("ANI-1(CenSpin)", "MLC-4", POSI);
        ng.setRelationship("F-Actin", "ContrRing", POSI);
        ng.setRelationship("F-Actin", "NMY-2", POSI);
        ng.setRelationship("F-Actin", "MLC-4", POSI);
        ng.setRelationship("CenSpindlin", "ECT-2", POSI);
        ng.setRelationship("KIF-4", "CenSpinMt", POSI);
        ng.setRelationship("KIF-4", "AstralMt", POSI);
        ng.setRelationship("PRC-1", "KIF-4", POSI);
        ng.setRelationship("PRC-1", "PLK-1", POSI);
        ng.setRelationship("PRC-1", "CenSpinMt", POSI);
        ng.setRelationship("PRC-1", "AstralMt", POSI);
        ng.setRelationship("CenSpinMt", "CYK-4", POSI);
        ng.setRelationship("CenSpinMt", "ZEN-4", POSI);
        ng.setRelationship("AstralMt", "ANI-1(CenSpin)", POSI);
        ng.setRelationship("AstralMt", "ANI-1(Cortex)", NEGA);
        ng.setRelationship("PLK-1", "ECT-2", POSI);
        ng.setRelationship("PLK-1", "KIF-4", NEGA);
        ng.setRelationship("CDK-1", "CYK-4", NEGA);
        ng.setRelationship("CDK-1", "PRC-1", NEGA);
        ng.setRelationship("CDK-1", "ZEN-4", NEGA);
        ng.setRelationship("ROCK", "MLC-4(P)", POSI);
        ng.setRelationship("ROCK", "MEL-11", NEGA);
        ng.setRelationship("Profilin", "F-Actin", POSI);
        ng.setRelationship("LET-502", "MEL-11", NEGA);
        ng.setRelationship("MEL-11", "MLC-4(P)", NEGA);
        ng.setRelationship("NMY-2", "ContrRing", POSI);
        ng.setRelationship("NMY-2(P)", "CleaFurContr", POSI);
        ng.setRelationship("MLC-4", "ContrRing", POSI);
        ng.setRelationship("MLC-4(P)", "NMY-2(P)", POSI);
        ng.setRelationship("CYK-1", "ContrRing", POSI);
        ng.setRelationship("CYK-1", "RhoA(GTP)", POSI);
        ng.setRelationship("CYK-1", "Profilin", POSI);
        ng.setRelationship("CYK-1", "CYK-1", SELFINHIB);
        ng.setRelationship("AnaOnset", "PRC-1", POSI);
        ng.setRelationship("AnaOnset", "PLK-1", POSI);
        ng.setRelationship("AnaOnset", "CDK-1", NEGA);
        ng.setRelationship("ContrRing", "LET-502", POSI);

        ng.writeFile(fileName);
    }

}
