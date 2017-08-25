import java.io.*;
import java.util.*;

/**
 * Created by AlinaCh on 20.04.2017.
 */
public class Main {

    public static MyGraph<String, Integer> dependencygraph;
    public static Map<String, String> conditions;
    public static List<String> sorted;
    public static int[] values;

    /**
     * solves all the equations of possible
     * by going through sorted vertices
     * @return the value of the 'R' variable
     */
    public static Integer solve() {
        values = new int[sorted.size()];
        for(int i = 0; i < sorted.size(); i++) {
            if(conditions.get(sorted.get(i)).contains("+")) {
                String[] temp = conditions.get(sorted.get(i)).split("\\+");
                values[i] = isVariable(temp[0]) + isVariable(temp[1]);
            } else if (conditions.get(sorted.get(i)).contains("*")) {
                String[] temp = conditions.get(sorted.get(i)).split("\\*");
                values[i] = isVariable(temp[0]) * isVariable(temp[1]);
            } else {
                values[i] = Integer.parseInt(conditions.get(sorted.get(i)));
            }
        }
        return values[sorted.indexOf("R")];
    }

    /**
     * checks whether multiplier/summand has variables or/and number values
     * assigns the value from either the value array or using parser
     * @param s the
     * @return the value
     */
    public static int isVariable(String s) {
        int a;
        if (sorted.contains(s))
            a = values[sorted.indexOf(s)];
        else
            a = Integer.parseInt(s);
        return a;
    }

    /**
     * topological sort of dependency graph
     * @return false if sorting failed, true if the graph become sorted
     * @throws MyException if there're some mistakes with the work of the graph
     */
    public static boolean topologicalSort() throws MyException {
        sorted = new LinkedList<>();
        incomingVertices();
        if (dependencygraph.numEdges() != 0 && !sorted.contains("R")) {
            return false;
        } else
            return true;
    }

    /**
     * performs sorting, by choosing vertices without outgoing edges
     * then adds it to the sorted set and deletes incoming edges, checks whether
     * its adjacent vertices still has outgoing edges, if not adds them to the set
     * of vertices without outgoing edges
     * performs until set of vertices without outgoing edges if empty
     * @throws MyException if there're some mistakes with the work of the graph
     */
    public static void incomingVertices() throws MyException {
        List<String> nooutgoingvertices = noOutgoingVertices();
        while (nooutgoingvertices.size() != 0) {
            String vertex = nooutgoingvertices.remove(0);
            sorted.add(vertex);
            String[] outgoingvertices = (String[])
                    dependencygraph.getIncomingVertices(vertex).toArray(new String[0]);
            for (int i = 0; i < outgoingvertices.length; i++) {
                String v = outgoingvertices[i];
                Edge e = dependencygraph.getEdge(v, vertex);
                dependencygraph.removeEdge(e);
                if (dependencygraph.getOutgoingVertices(v).size() == 0)
                    nooutgoingvertices.add(v);
            }
        }
    }

    /**
     * @return set of vertices without outgoing edges
     * @throws MyException if there're some mistakes with the work of the graph
     */
    public static List<String> noOutgoingVertices() throws MyException {
        Set<String> vertices = dependencygraph.getVertices();
        List<String> result = new LinkedList<>();
        for (String vertex : vertices) {
            if (dependencygraph.getOutgoingVertices(vertex).size() == 0) {
                result.add(vertex);
            }
        }
        return result;
    }

    /**
     * writes answer to the output file
     * @param s
     */
    public static void write(String s) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("output.txt"), "ascii"))) { writer.write(s); }
        catch (IOException ex) { }
    }

    public static void main(String[] args) throws IOException, MyException {
        Reader reader = new Reader();
        dependencygraph = reader.read();
        conditions = reader.getConditions();
        boolean flag = topologicalSort();
        if (flag)
            write(solve().toString());
        else
            write("ERROR");
    }
}
