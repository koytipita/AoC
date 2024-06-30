package day25;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class day25 {
    static Logger logger = Logger.getLogger(day25.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day25/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day25/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day25/example3.txt";
    static final String ACTUAL_FILE_PATH = "day25/input.txt";
    static List<String> lines;
    static Random random = new Random();

    public static void main(String[] args) {
        lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        Map<String, Map<String, Integer>> G = new HashMap<>();
        for (String l : lines) {
            String[] parts = l.split(": ");
            String lhs = parts[0];
            String[] rhs = parts[1].split(" ");
            G.putIfAbsent(lhs, new HashMap<>());
            for (String r : rhs) {
                G.get(lhs).put(r, 1);
                G.putIfAbsent(r, new HashMap<>());
                G.get(r).put(lhs, 1);
            }
        }

        Graph2 graph = new Graph2(G);
        String s = graph.getG().keySet().iterator().next();
        for (String t : graph.getG().keySet()) {
            if (!s.equals(t) && graph.minCut(s, t) == 3) {
                break;
            }
        }
        System.out.println(graph.solve());
    }
    public static Graph createGraph(){
        Graph mainGraph = new Graph(new ArrayList<>(), 0);
        lines.forEach(line -> {
            Node node1 = new Node(1,line.split(": ")[0]);
            List<Node> nodeList = Arrays.stream(line.split(": ")[1]
                .split(" "))
                .map(str -> new Node(1,str))
                .toList();
            List<Edge> edgeList = new ArrayList<>();
            nodeList.forEach(node -> edgeList.add(new Edge(node1, node)));
            edgeList.forEach(edge -> mainGraph.addEdge(edge));
        });
        Set<String> nodeNameSet = mainGraph.getEdgeList().stream().
            flatMap(edge -> Stream.of(edge.getNode1().getName(),edge.getNode2().getName()))
            .collect(Collectors.toSet());
        mainGraph.setVerticeCount(nodeNameSet.size());
        return mainGraph;
    }
    public static List<Edge> kargerMinCut(Graph graph)
    {
        while (graph.getVerticeCount() > 2){
            int randomInt = (random.nextInt(0,3500)) % graph.getEdgeList().size();
            Edge randomEdge = graph.getEdgeList().get(randomInt);
            graph.cutEdge(randomEdge);
        }
        //System.out.print(graph.getEdgeList().size()+ " \n");
        if (graph.getEdgeList().size()==3) {
            return graph.getEdgeList();
        }
        return null;
    }
    
}
