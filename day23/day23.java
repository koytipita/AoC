package day23;

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
        map = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH,logger);
        //memoryDp.put(Arrays.toString(new int[]{1,0}), 0);
        traverseLongestPathPartTwo();

        //createNodesAndEdges();
        //Node startNode = nodeList.stream().filter(node -> node.getSign() == 'S').findAny().orElseThrow();
        //longestPath(startNode);
    }

    public static Integer traverseLongestPathPartTwo() {
        Stack<NodeState> stack = new Stack<>();
        stack.push(new NodeState(new int[]{1,0}, 0, "DOWN", new HashSet<>(), new HashMap<>()));

        int longestPath = Integer.MIN_VALUE;

        while (!stack.isEmpty()) {
            NodeState currentState = stack.pop();
            int[] position = currentState.position;
            int distance = currentState.distance;
            String from = currentState.from;
            Set<String> visitedPositions = currentState.visitedPositions;
            Map<String,Integer> memoryDp = currentState.memoryDp;

            char currentChar;
            try {
                currentChar = map.get(position[1]).charAt(position[0]);
            } catch (Exception e) {
                continue;
            }

            String positionKey = Arrays.toString(position);
            if (memoryDp.containsKey(positionKey) && memoryDp.get(positionKey) >= distance) {
                continue;
            }

            if (visitedPositions.contains(positionKey) || currentChar == '#') {
                memoryDp.put(positionKey, Integer.MIN_VALUE);
                continue;
            }

            visitedPositions.add(positionKey);
            memoryDp.put(positionKey, distance);


            if (position[0] == map.getFirst().length() - 2 && position[1] == map.size() - 1) {
                longestPath = Math.max(longestPath, distance);
                System.out.println(longestPath);
                //continue;
            }

            int[] leftPosition = Arrays.copyOf(position, 2);
            leftPosition[0] -= 1;
            int[] rightPosition = Arrays.copyOf(position, 2);
            rightPosition[0] += 1;
            int[] upPosition = Arrays.copyOf(position, 2);
            upPosition[1] -= 1;
            int[] downPosition = Arrays.copyOf(position, 2);
            downPosition[1] += 1;

            if (!from.equals("LEFT"))
                stack.push(new NodeState(rightPosition, distance + 1, "RIGHT", new HashSet<>(visitedPositions), new HashMap<>(memoryDp)));
            if (!from.equals("UP"))
                stack.push(new NodeState(downPosition, distance + 1, "DOWN", new HashSet<>(visitedPositions), new HashMap<>(memoryDp)));
            if (!from.equals("RIGHT"))
                stack.push(new NodeState(leftPosition, distance + 1, "LEFT", new HashSet<>(visitedPositions), new HashMap<>(memoryDp)));
            if (!from.equals("DOWN"))
                stack.push(new NodeState(upPosition, distance + 1, "UP", new HashSet<>(visitedPositions), new HashMap<>(memoryDp)));
        }

        return longestPath;
    }

    private static class NodeState {
        int[] position;
        int distance;
        String from;
        Set<String> visitedPositions;
        Map<String,Integer> memoryDp;


        NodeState(int[] position, int distance, String from, Set<String> visitedPositions, Map<String,Integer> memoryDp) {
            this.position = position;
            this.distance = distance;
            this.from = from;
            this.visitedPositions = visitedPositions;
            this.memoryDp = memoryDp;
        }
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
                System.out.print(Arrays.toString(node.getPosition()) + ": "+dist.get(node)+ " ");
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
            //System.out.println(" node1: "+Arrays.toString(node1.getPosition())
            //        +" node2: " + Arrays.toString(node2.getPosition()));
        }
        if (currentChar == '.'){
            try {
                Node node2 = nodeList.stream()
                        .filter(node -> Arrays.equals(node.getPosition(), position))
                        .findAny().orElseThrow();
                node1.getEdgeList().add(new Edge(node1, node2, distance));
                //System.out.println(" node1: "+Arrays.toString(node1.getPosition())
                //        +" node2: " + Arrays.toString(node2.getPosition()));
            }
            catch (Exception ignored){
            }
            int[] leftPosition = Arrays.copyOf(position,2);
            leftPosition[0] -= 1;
            int[] rightPosition = Arrays.copyOf(position,2);
            rightPosition[0] += 1;
            int[] upPosition = Arrays.copyOf(position,2);
            upPosition[1] -= 1;
            int[] downPosition = Arrays.copyOf(position,2);
            downPosition[1] += 1;
            if (!from.equals("LEFT"))
                findEdges(rightPosition,node1,distance+1,"RIGHT");
            if (!from.equals("UP"))
                findEdges(downPosition,node1,distance+1,"DOWN");
            if (node1.getEdgeList().isEmpty()){
                if (!from.equals("RIGHT"))
                    findEdges(leftPosition,node1,distance+1,"LEFT");
                if (!from.equals("DOWN"))
                    findEdges(upPosition,node1,distance+1,"UP");
            }
        }

    }
}
