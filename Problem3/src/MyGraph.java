import java.util.*;

/**
 * Created by AlinaCh on 02.04.2017.
 * This is the generic implementation of the graph, using adjacency map,
 * and specific weighted Edge class with specific Node class.
 * It may be directed or undirected
 */
public class MyGraph<K, Item extends Comparable> {

    private Map<K, Node> adjacencymap;
    private List<Edge> edges;
    private boolean directed;

    /**
     * the initializing of the graph, either directed or undirected
     * @param directed whether the graph is directed
     */
    MyGraph(boolean directed) {
        adjacencymap = new HashMap<>();
        edges = new LinkedList<>();
        this.directed = directed;
    }

    /**
     * @return whether the graph is directed
     */
    public boolean isDirected() {
        return this.directed;
    }

    /**
     * @return number of vertices in the graph
     */
    public int numVertices() {
        return adjacencymap.size();
    }

    /**
     * @return the set of all vertices og the graph
     */
    public Set getVertices() {
        return adjacencymap.keySet();
    }

    /**
     * @return the number of edges in the graph
     */
    public int numEdges() {
        return edges.size();
    }

    /**
     * @return the list of all edges of the graph
     */
    public List getEdges() {
        return edges;
    }

    /**
     * @param u vertex
     * @param v vertex
     * @return the edge between vertices or null, if vertices are not connected
     * @throws MyException if graph doesn't contain either or both vertices
     */
    public Edge getEdge(K u, K v) throws MyException {
        if (containsVertex(u) && containsVertex(v)) {
            return (Edge) adjacencymap.get(u).outgoingedges.get(v);
        } else
            throw new MyException("There's no such vertices");
    }

    /**
     * @param v vertex
     * @return the incident vertices of the given vertex
     * if graph is directed returns both incoming and outgoing edges
     * @throws MyException if graph does not contain vertex
     */
    public Collection<Edge> incidentEdges(K v) throws MyException {
        if (containsVertex(v)) {
            Collection<Edge> result = new LinkedList<>();
            result = adjacencymap.get(v).outgoingedges.values();
            if (this.directed) {
                result.addAll(adjacencymap.get(v).incomingedges.values());
            }
            return result;
        } else
            throw new MyException("There's no such vertex");
    }

    /**
     * @param v vertex
     * @return the set of sink vertices of the given source vertex
     * @throws MyException if graph does not contain vertex
     */
    public Set getOutgoingVertices(K v) throws MyException {
        if (containsVertex(v)) {
            return adjacencymap.get(v).outgoingedges.keySet();
        } else
            throw new MyException("There's no such vertex");
    }

    /**
     * @param v vertex
     * @return the set of source vertices of the given sink vertex
     * @throws MyException if graph does not contain vertex
     */
    public Set getIncomingVertices(K v) throws MyException {
        if (containsVertex(v)) {
            return adjacencymap.get(v).incomingedges.keySet();
        } else
            throw new MyException("There's no such vertex");
    }

    /**
     * @param v vertex
     * @return the degree of the given vertex
     * @throws MyException if graph does not contain vertex
     */
    public int getDegree(K v) throws MyException {
        if (containsVertex(v)) {
            return adjacencymap.get(v).incomingedges.size()
                    + adjacencymap.get(v).outgoingedges.size();
        } else
            throw new MyException("There's no such vertex");
    }

    /**
     * adds vertex to the graph
     * @param v vertex
     */
    public void insertVertex(K v) {
        if (!containsVertex(v)) {
            adjacencymap.put(v, new Node(directed));
        }
    }

    /**
     * adds weighted edge from vertex u to vertex v
     * for undirected graph add the same edge from v to u
     * @param u origin vertex
     * @param v sink vertex
     * @param weight of the edge
     * @throws MyException if there's already edge between u and v
     */
    public void insertEdge(K u, K v, Item weight) throws MyException {
        if (getEdge(u, v) == null) {
            Edge edge = new Edge(u, v, weight);
            adjacencymap.get(u).outgoingedges.put(v, edge);
            adjacencymap.get(v).incomingedges.put(u, edge);
            edges.add(edge);
        }
    }

    /**
     * deletes vertex from the graph with all adjacent edges
     * @param v vertex
     * @throws MyException if graph does not contain vertex
     */
    public void removeVertex(K v) throws MyException {
        if (containsVertex(v)) {
            Collection<Edge> incident = incidentEdges(v);
            for (Edge e : incident) {
                edges.remove(e);
            }
        } else
            throw new MyException("Such vertex does not exist!");
    }

    /**
     * deletes edge
     * @param e edge
     * @throws MyException if graph does not contain edge
     */
    public void removeEdge(Edge e) throws MyException {
        K u = (K) e.endVertices[0];
        K v = (K) e.endVertices[1];
        if (containsVertex(u) && containsVertex(v)) {
            adjacencymap.get(u).outgoingedges.remove(v);
            adjacencymap.get(v).incomingedges.remove(u);
            edges.remove(e);
        } else
            throw new MyException("Such edge does not exist!");
    }

    /**
     * @param u origin vertex
     * @param v sink vertex
     * @return whether edge between given vertices exists
     * @throws MyException if graph does not contain vertices
     */
    public boolean hasEdge(K u, K v) throws MyException {
        Edge temp = getEdge(u, v);
        return temp != null;
    }

    /**
     * @param v vertex
     * @return whether graph contains vertex
     */
    private boolean containsVertex(K v) {
        return adjacencymap.containsKey(v);
    }
}
