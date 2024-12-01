package aoc23.day17;

import java.util.List;

public class Graph {
    private List<Vertex> vertices;
    private List<List<Edge>> edges;

    public Graph(List<Vertex> vertices, List<List<Edge>> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public Vertex getVertex(Position position,Integer rightCount, Integer straightCount){
        try {
            return vertices.stream().filter(vertex -> vertex.getPosition().equals(position)
                    && vertex.getRightCount().equals(rightCount)
                    && vertex.getStraightCount().equals(straightCount)).findFirst().get();
        }
        catch (Exception e){
            return null;
        }
    }
    public void setVertex(Vertex othVertex,Integer rightCount, Integer straightCount){
        this.vertices = vertices.stream().map(vertex -> {
            if(vertex.getPosition().equals(othVertex.getPosition())
                    && vertex.getRightCount().equals(rightCount)
                    && vertex.getStraightCount().equals(straightCount)){
                return othVertex;
            }
            return vertex;
        }).toList();
    }
    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<List<Edge>> getEdges() {
        return edges;
    }

    public void setEdges(List<List<Edge>> edges) {
        this.edges = edges;
    }
}
