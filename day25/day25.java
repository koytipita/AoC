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
        lines = utils.FileParseUtil.readLinesFromFile(EXAMPLE1_FILE_PATH, logger);
        createGraph();
        List<Edge> cutEdges = kargerMinCut(createGraph());
        while (cutEdges == null){
            cutEdges = kargerMinCut(createGraph());
        }
        System.out.println(cutEdges.getFirst().getNode1().getCount()
            * cutEdges.getFirst().getNode2().getCount());

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
