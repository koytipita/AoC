package day17;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class day17 {

    static Logger logger = Logger.getLogger(day17_dp_trial.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day17/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day17/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day17/example3.txt";
    static final String ACTUAL_FILE_PATH = "day17/input.txt";
    static Graph graph;
    static List<String> lines;
    static List<Vertex> queue = new ArrayList<>();

    public static List<List<Edge>> addEdges(){
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
    public static List<Vertex> addVertices(){
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
    public static void createGraph(){
        List<Vertex> vertices = addVertices();
        graph = new Graph(vertices,new ArrayList<>());
        List<List<Edge>> edges = addEdges();
        graph.setEdges(edges);
    }

    public static void dijkstraPartOne(){
        Vertex startVertex = graph.getVertex(new Position(0,0),0,0);
        startVertex.setDistance(0);
        graph.setVertex(startVertex,0,0);
        queue.addAll(graph.getVertices());

        while (!queue.isEmpty()){
            Integer minDistance = queue.stream()
                    .map(Vertex::getDistance)
                    .min(Integer::compare).get();
            //if (minDistance.equals(Integer.MAX_VALUE)){
            //    break;
            //}
            //logger.log(Level.INFO,String.valueOf(minDistance));
            Vertex tempVertex = queue.stream()
                    .filter(vertex -> Objects.equals(vertex.getDistance(), minDistance))
                    .findFirst().get();
            /*if (tempVertex.getPosition().getY().equals(lines.size()-1) && tempVertex.getPosition().getX().equals(lines.getFirst().length()-1)){
                break;
            }*/

            queue.remove(tempVertex);

            Position tempPosition = tempVertex.getPosition();
            int tempVertexRightCount = tempVertex.getRightCount();
            int tempVertexStraightCount = tempVertex.getStraightCount();// 0 0 0 1 0 2 0 3 1 0 2 0 3 0
            int indexToBeAdded = tempVertexRightCount < 1 ? tempVertexStraightCount : tempVertexRightCount + 3;
            List<Edge> tempEdges = graph.getEdges().get(tempPosition.getY()*lines.getFirst().length()*7+ tempPosition.getX()*7+indexToBeAdded)
                    .stream().filter(edge ->
                        queue.stream().anyMatch(vertex -> vertex.getPosition().equals(edge.getVertex().getPosition())
                                && vertex.getStraightCount().equals(edge.getVertex().getStraightCount())
                                && vertex.getRightCount().equals(edge.getVertex().getRightCount())
                        )
                    ).toList();
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
        lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        createGraph();
        dijkstraPartOne();
        Integer shorthestPath1 = graph.getVertex(new Position(lines.size()-1,lines.getFirst().length()-1),0,1).getDistance();
        Integer shorthestPath2 = graph.getVertex(new Position(lines.size()-1,lines.getFirst().length()-1),0,2).getDistance();
        Integer shorthestPath3 = graph.getVertex(new Position(lines.size()-1,lines.getFirst().length()-1),0,3).getDistance();
        Integer shorthestPath4 = graph.getVertex(new Position(lines.size()-1,lines.getFirst().length()-1),1,0).getDistance();
        Integer shorthestPath5 = graph.getVertex(new Position(lines.size()-1,lines.getFirst().length()-1),2,0).getDistance();
        Integer shorthestPath6 = graph.getVertex(new Position(lines.size()-1,lines.getFirst().length()-1),3,0).getDistance();
        Vertex vertex = graph.getVertex(new Position(lines.size()-1,lines.getFirst().length()-1),3,0);
        while (vertex != null){
            System.out.println(String.valueOf(vertex.getValue()) + " " + String.valueOf(vertex.getPosition().getY()) + " " + String.valueOf(vertex.getPosition().getX()));
            vertex = vertex.getPrevious();
        }
        System.out.println(String.valueOf(shorthestPath1) + " " +
                String.valueOf(shorthestPath2) + " " +
                String.valueOf(shorthestPath3) + " " +
                String.valueOf(shorthestPath4) + " " +
                String.valueOf(shorthestPath5) + " " +
                String.valueOf(shorthestPath6) + " ");
        int i = 6;

    }
}
