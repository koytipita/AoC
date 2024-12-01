package aoc24.day23;

import java.util.List;

public class Node {
    private char sign;
    private int[] position;
    private List<Edge> edgeList;

    public Node(char sign, int[] position, List<Edge> edgeList) {
        this.sign = sign;
        this.position = position;
        this.edgeList = edgeList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public char getSign() {
        return sign;
    }

    public void setSign(char sign) {
        this.sign = sign;
    }
}
