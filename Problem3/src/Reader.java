import java.io.*;
import java.util.*;

/**
 * Created by AlinaCh on 20.04.2017.
 * Class helper for creating the dependency graph,
 * parsing the condition of each variable
 */
public class Reader {

    private MyGraph<String, Integer> dependencygraph;
    private List<String> variables;
    private String condition;
    private StreamTokenizer check;
    private Map<String, String> conditions;

    /**
     * reads the input file line by line
     * @return the dependency graph
     * @throws IOException if there're some mistakes with the parser
     * @throws MyException if there're some mistakes with the work of the graph
     */
    public MyGraph<String, Integer> read() throws IOException, MyException {
        Scanner sc = new Scanner(new File("input.txt"));
        String path = "";
        dependencygraph = new MyGraph<>(true);
        conditions = new HashMap<>();
        while (sc.hasNextLine()) {
            path = sc.nextLine();
            if (!path.equals(""))
                parse(path);
        }
        return dependencygraph;
    }

    /**
     * @return the conditions of each variable
     */
    public Map<String, String> getConditions() {
        return conditions;
    }

    /**
     * parser of the line
     * @param path the equation
     * @throws IOException if there're some mistakes with the parser
     * @throws MyException if there're some mistakes with the work of the graph
     */
    private void parse(String path) throws IOException, MyException {
        variables = new LinkedList<>();
        condition = path.split("=")[1];
        StringReader sequence = new StringReader(path);
        check = new StreamTokenizer(sequence);
        checkToken();
        addToGraph();
    }

    /**
     * adds the information from the line to the graph and condition hash map
     * the first variable is the source vertex with 0-2 outgoing edges
     * @throws MyException
     */
    private void addToGraph() throws MyException {
        dependencygraph.insertVertex(variables.get(0));
        conditions.put(variables.get(0), condition);
        if (variables.size() == 2) {
            dependencygraph.insertVertex(variables.get(1));
            dependencygraph.insertEdge(variables.get(0), variables.get(1), 0);
        } else if (variables.size() == 3) {
            dependencygraph.insertVertex(variables.get(0));
            for (int i = 1; i < 3; i++) {
                dependencygraph.insertVertex(variables.get(i));
                dependencygraph.insertEdge(variables.get(0), variables.get(i), 0);
            }
        }
    }

    /**
     * takes out all variables from the line
     * the first variable is the dependent one
     * @throws IOException if there're some mistakes with the parser
     */
    private void checkToken() throws IOException {
        boolean end = false;
        while (!end) {
            int token = check.nextToken();
            switch (token) {
                case StreamTokenizer.TT_EOF:    end = true;
                    break;
                case StreamTokenizer.TT_WORD:   variables.add(check.sval);
                    break;
            }
        }
    }
}
