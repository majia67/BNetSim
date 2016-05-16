//2-dim matrix, >0: positive; <0: negative; 
//0: no relationship between the two nodes

public class Relationship{
    
    private int[][] relat;
    
    public Relationship(int size) {
        relat = new int[size][size];
    }
    
    public int get(int s, int t) {
        return relat[s][t];
    }
    
    public void set(int s, int t, int effect) {
        relat[s][t] = effect;
    }

}
