package day23;

import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class day23 {
    static Logger logger = Logger.getLogger(day23.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day23/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day23/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day23/example3.txt";
    static final String ACTUAL_FILE_PATH = "day23/input.txt";
    static List<String> map;
    static List<Node> nodeList = new ArrayList<>();
    public static void main(String[] args) {
        map = utils.FileParseUtil.readLinesFromFile(EXAMPLE1_FILE_PATH,logger);
        createNodesAndEdges();
        Node startNode = nodeList.stream().filter(node -> node.getSign() == 'S').findAny().orElseThrow();
        longestPath(startNode);
    }

    public static void topologicalSortUtil(Node node1, Map<Node, Boolean> visited,
                             Stack<Node> stack)
    {
        // Mark the current node as visited
        visited.put(node1,true);

        // Recur for all the vertices adjacent to this vertex
        for (int i = 0; i<node1.getEdgeList().size(); i++) {
            Node node2 = node1.getEdgeList().get(i).getNode2();
            if (Boolean.FALSE.equals(visited.get(node2)))
                topologicalSortUtil(node2, visited, stack);
        }

        // Push current vertex to stack which stores topological
        // sort
        stack.push(node1);
    }
    public static void longestPath(Node source)
    {
        Stack<Node> stack = new Stack<>();
        int nodesSize = nodeList.size();
        Map<Node, Integer> dist = new HashMap<>(nodesSize);

        // Mark all the vertices as not visited
        Map<Node, Boolean> visited = new HashMap<>(nodesSize);
        for (Node node : nodeList)
            visited.put(node, false);

        // Call the recursive helper function to store Topological
        // Sort starting from all vertices one by one
        if (Boolean.FALSE.equals(visited.get(source)))
            topologicalSortUtil(source, visited, stack);

        // Initialize distances to all vertices as infinite and
        // distance to source as 0
        for (Node node : nodeList)
            dist.put(node, Integer.MIN_VALUE);

        dist.put(source, 0);

        // Process vertices in topological order
        while (!stack.isEmpty())
        {

            // Get the next vertex from topological order
            Node u = stack.peek();
            stack.pop();

            // Update distances of all adjacent vertices ;
            if (dist.get(u) != Integer.MIN_VALUE)
            {
                for (Edge edge : u.getEdgeList())
                {
                    Node node = edge.getNode2();
                    if (dist.get(node) < dist.get(u) + edge.getDistance())
                        dist.put(node,dist.get(u) + edge.getDistance());
                }
            }
        }

        // Print the calculated longest distances
        for (Node node: nodeList)
            if(dist.get(node) == Integer.MIN_VALUE)
                System.out.print("INF ");
            else
                System.out.print(dist.get(node) + " ");
    }


    public static void createNodesAndEdges(){
        nodeList.add(new Node('S', new int[]{1,0}, new ArrayList<>()));
        IntStream.range(0,map.size()).forEach(index -> {
            for (int i = 0; i < map.get(index).length(); i++) {
                char tempChar = map.get(index).charAt(i);
                if (tempChar == '>' || tempChar == 'v'){
                    nodeList.add(new Node(tempChar, new int[]{i,index}, new ArrayList<>()));
                }
            }
        });
        nodeList.add(new Node('E', new int[]{map.getFirst().length()-2,map.size()-1}, new ArrayList<>()));

        nodeList.forEach(node -> {
            char sign = node.getSign();
            int[] position = Arrays.copyOf(node.getPosition(),2);
            switch (sign){
                case '>' :
                    position[0] += 1;
                    findEdges(position, node, 1,"RIGHT");
                break;
                case 'v', 'S':
                    position[1] += 1;
                    findEdges(position, node, 1,"DOWN");
                    break;
                case 'E':
                    //position[1] -= 1;
                    //findEdges(position, node, 1,"UP");
                    break;
                default:
                    throw new IllegalStateException("Error");
            }
        });
    }

    public static void findEdges(int[] position, Node node1, int distance, String from){
        char currentChar;
        try {
            currentChar = map.get(position[1]).charAt(position[0]);
        }
        catch (Exception e){
            return;
        }

        if (currentChar == '#'){
            return;
        }
        if (currentChar == '>' || currentChar == 'v' || currentChar == 'S' || currentChar == 'E'){
            Node node2 = nodeList.stream()
                    .filter(node -> Arrays.equals(node.getPosition(), position))
                        .findAny().orElseThrow();
            node1.getEdgeList().add(new Edge(node1, node2, distance));
        }
        if (currentChar == '.'){
            int[] leftPosition = Arrays.copyOf(position,2);
            leftPosition[0] -= 1;
            int[] rightPosition = Arrays.copyOf(position,2);
            rightPosition[0] += 1;
            int[] upPosition = Arrays.copyOf(position,2);
            upPosition[1] -= 1;
            int[] downPosition = Arrays.copyOf(position,2);
            downPosition[1] += 1;
            if (!from.equals("RIGHT"))
                findEdges(leftPosition,node1,distance+1,"LEFT");
            if (!from.equals("LEFT"))
                findEdges(rightPosition,node1,distance+1,"RIGHT");
            if (!from.equals("DOWN"))
                findEdges(upPosition,node1,distance+1,"UP");
            if (!from.equals("UP"))
                findEdges(downPosition,node1,distance+1,"DOWN");
        }

    }
}
