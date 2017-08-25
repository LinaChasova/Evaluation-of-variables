/**
 * Created by AlinaCh on 02.04.2017.
 * This is the generic implementation of the weighted edge
 */
public class Edge<K, Item extends Comparable> {

    public K[] endVertices;
    public Item weight;

    /**
     * initializing of the edge
     * @param v1 origin vertex
     * @param v2 sink vertex
     * @param weight of the edge
     */
    Edge(K v1, K v2, Item weight) {
        endVertices = (K[]) new Object[2];
        endVertices[0] = v1;
        endVertices[1] = v2;
        this.weight = weight;
    }
}
