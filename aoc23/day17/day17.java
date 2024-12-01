package aoc23.day17;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class day17 {

    static Logger logger = Logger.getLogger(day17_dp_trial.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day17/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day17/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day17/example3.txt";
    static final String ACTUAL_FILE_PATH = "day17/input.txt";
    static Graph graph;
    static List<String> lines;
    static List<Vertex> queue = new ArrayList<>();

    public static List<List<Edge>> addEdgesPartOne(){
        List<List<Edge>> edges = new ArrayList<>();
        List<Vertex> vertices = graph.getVertices();

        for (Vertex vertex: vertices){
            List<Edge> edgesOfVertex = new ArrayList<>();
            int j = vertex.getPosition().getX();
            int i = vertex.getPosition().getY();
            int rightCount = vertex.getRightCount();
            int straightCount = vertex.getStraightCount();
            if(j != lines.getFirst().length()-1 && straightCount<3) edgesOfVertex.add(new Edge(graph.getVertex(new Position(j+1,i),0,straightCount+1)));
            if(i != lines.size()-1 && rightCount <3) edgesOfVertex.add(new Edge(graph.getVertex(new Position(j,i+1),rightCount+1,0)));
            if(j != 0 && straightCount< 1) edgesOfVertex.add(new Edge(graph.getVertex(new Position(j-1,i),0,0)));
            if(i != 0 && rightCount< 1) edgesOfVertex.add(new Edge(graph.getVertex(new Position(j,i-1),0,0)));
            edges.add(edgesOfVertex);
        }
        return edges;
    }

    public static List<List<Edge>> addEdgesPartTwo(){
        List<List<Edge>> edges = new ArrayList<>();
        List<Vertex> vertices = graph.getVertices();

        for (Vertex vertex: vertices){
            List<Edge> edgesOfVertex = new ArrayList<>();
            int x = vertex.getPosition().getX();
            int y = vertex.getPosition().getY();
            int rightCount = vertex.getRightCount();
            int straightCount = vertex.getStraightCount();
            if(x != lines.getFirst().length()-1
                    && ((straightCount>0 &&straightCount < 10) || (straightCount == 0 && rightCount > 3))
                    && !((x == lines.getFirst().length()-1 && straightCount<4)
                        || (x == lines.getFirst().length()-2 && straightCount<3)
                        || (x == lines.getFirst().length()-3 && straightCount<2))
            ) edgesOfVertex.add(new Edge(graph.getVertex(new Position(x+1,y),0,straightCount+1)));
            if(y != lines.size()-1
                    && ((rightCount>0 &&rightCount < 10) || (rightCount == 0 && straightCount > 3))
                    && !((y == lines.size()-1 && rightCount<4)
                        || (y == lines.size()-2 && rightCount<3)
                        || (y == lines.size()-3 && rightCount<2))
            ) edgesOfVertex.add(new Edge(graph.getVertex(new Position(x,y+1),rightCount+1,0)));
            edges.add(edgesOfVertex);
        }
        return edges;
    }
    public static List<Vertex> addVerticesPartOne(){
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                Integer value = Integer.valueOf(line.substring(j,j+1));
                Position position = new Position(j,i);
                for (int rightCount = 0; rightCount < 4; rightCount++) {
                    for (int straightCount = 0; straightCount < 4; straightCount++) {
                        if (rightCount>0 && straightCount>0)
                            break;
                        vertices.add(new Vertex(position,value,rightCount,straightCount));
                    }
                }
            }
        }
        return vertices;
    }

    public static List<Vertex> addVerticesPartTwo(){
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                Integer value = Integer.valueOf(line.substring(j,j+1));
                Position position = new Position(j,i);
                for (int rightCount = 0; rightCount < 11; rightCount++) {
                    for (int straightCount = 0; straightCount < 11; straightCount++) {
                        if (rightCount>0 && straightCount>0)
                            break;
                        vertices.add(new Vertex(position,value,rightCount,straightCount));
                    }
                }
            }
        }
        return vertices;
    }
    public static void createGraphPartOne(){
        List<Vertex> vertices = addVerticesPartOne();
        graph = new Graph(vertices,new ArrayList<>());
        List<List<Edge>> edges = addEdgesPartOne();
        graph.setEdges(edges);
    }
    public static void createGraphPartTwo(){
        List<Vertex> vertices = addVerticesPartTwo();
        graph = new Graph(vertices,new ArrayList<>());
        List<List<Edge>> edges = addEdgesPartTwo();
        graph.setEdges(edges);
    }

    public static void dijkstra(boolean partOne){
        if (partOne){
            Vertex startVertex = graph.getVertex(new Position(0,0),0,0);
            startVertex.setDistance(0);
            graph.setVertex(startVertex,0,0);
        }
        else {
            Vertex startVertex1 = graph.getVertex(new Position(1, 0), 0, 1);
            startVertex1.setDistance(startVertex1.getValue());
            graph.setVertex(startVertex1, 0, 1);
            Vertex startVertex2 = graph.getVertex(new Position(0, 1), 1, 0);
            startVertex2.setDistance(startVertex2.getValue());
            graph.setVertex(startVertex2, 1, 0);
        }
        queue.addAll(graph.getVertices());

        while (!queue.isEmpty()){
            Integer minDistance = queue.stream()
                    .map(Vertex::getDistance)
                    .min(Integer::compare).get();
            if (minDistance.equals(Integer.MAX_VALUE)){
                break;
            }
            Vertex tempVertex = queue.stream()
                    .filter(vertex -> Objects.equals(vertex.getDistance(), minDistance))
                    .findFirst().get();
            if (tempVertex.getPosition().getY().equals(lines.size()-1) && tempVertex.getPosition().getX().equals(lines.getFirst().length()-1)){
                break;
            }

            queue.remove(tempVertex);

            Position tempPosition = tempVertex.getPosition();
            int tempVertexRightCount = tempVertex.getRightCount();
            int tempVertexStraightCount = tempVertex.getStraightCount();// 0 0 0 1 0 2 0 3 1 0 2 0 3 0
            List<Edge> tempEdges = null;
            if (partOne){
                int indexToBeAdded = tempVertexRightCount < 1 ? tempVertexStraightCount : tempVertexRightCount + 3;
                tempEdges = graph.getEdges().get(tempPosition.getY()*lines.getFirst().length()*7+ tempPosition.getX()*7+indexToBeAdded)
                        .stream().filter(edge ->
                                queue.stream().anyMatch(vertex -> vertex.getPosition().equals(edge.getVertex().getPosition())
                                        && vertex.getStraightCount().equals(edge.getVertex().getStraightCount())
                                        && vertex.getRightCount().equals(edge.getVertex().getRightCount())
                                )
                        ).toList();
            }
            else {
                int indexToBeAdded = tempVertexRightCount < 1 ? tempVertexStraightCount : tempVertexRightCount + 10;
                tempEdges = graph.getEdges().get(tempPosition.getY()*lines.getFirst().length()*21+ tempPosition.getX()*21+indexToBeAdded)
                        .stream().filter(edge ->
                                queue.stream().anyMatch(vertex -> vertex.getPosition().equals(edge.getVertex().getPosition())
                                        && vertex.getStraightCount().equals(edge.getVertex().getStraightCount())
                                        && vertex.getRightCount().equals(edge.getVertex().getRightCount())
                                )
                        ).toList();
            }

            for (Edge edge: tempEdges) {
                Vertex neighborVertex = edge.getVertex();
                Integer tempDist = tempVertex.getDistance() + neighborVertex.getValue();
                if (tempDist < neighborVertex.getDistance()){
                    int rightCount = neighborVertex.getRightCount();
                    int straightCount = neighborVertex.getStraightCount();
                    neighborVertex.setDistance(tempDist);
                    neighborVertex.setPrevious(tempVertex);
                    graph.setVertex(neighborVertex,rightCount,straightCount);
                }
            }
        }
    }

    public static void main(String[] args) {
        lines = utils.FileParseUtil.readLinesFromFile(EXAMPLE1_FILE_PATH, logger);
        createGraphPartTwo();
        int minDistance1 = Integer.MAX_VALUE;
        dijkstra(false);
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i>0 && j>0)
                    break;
                int indexToBeAdded = i < 1 ? j : i + 10;
                if (graph.getVertices().get((lines.size()-1)*lines.getFirst().length()*21+ (lines.getFirst().length()-1)*21+indexToBeAdded) != null){
                    Integer shortestPath = graph.getVertices().get((lines.size()-1)*lines.getFirst().length()*21+ (lines.getFirst().length()-1)*21+indexToBeAdded).getDistance();
                    if (shortestPath<minDistance1){
                        minDistance1 = shortestPath;
                    }
                }
            }
        }
        System.out.println(minDistance1);



    }
}
