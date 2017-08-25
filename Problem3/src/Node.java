import java.util.*;

/**
 * Created by AlinaCh on 17.04.2017.
 * Helping class Node, which contains incoming edges and outgoing edges of the vertes
 * works as value in the adjacency map for each vertex
 * if the graph is undirected incoming edges are the same as outgoing
 */
public class Node<K> {

    public Map<K, Edge> incomingedges;
    public Map<K, Edge> outgoingedges;

    /**
     * initializing of the Node of vertex
     * @param directed whether the graph is directed
     */
    Node(boolean directed) {
        outgoingedges = new HashMap<>();
        if (directed) {
            incomingedges = new HashMap<>();
        } else
            incomingedges = outgoingedges;
    }
}
