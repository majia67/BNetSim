
public class NetworkGenerator {
    
    private NodeList nodes;
    private Relationship relat;
    
    public NetworkGenerator(int size) {
        nodes = new NodeList(size);
        relat = new Relationship(size);
    }
}
