package aoc23.day23;

public class Edge {
    private Node node1;
    private Node node2;
    private Integer distance;

    public Edge(Node node1, Node node2, Integer distance) {
        this.node1 = node1;
        this.node2 = node2;
        this.distance = distance;
    }

    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public Node getNode2() {
        return node2;
    }

    public void setNode2(Node node2) {
        this.node2 = node2;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
