package aoc23.day10;



public class Node {
    private Integer xIndex;
    private Integer yIndex;
    private NodeType type;

    public Node(Integer xIndex, Integer yIndex, NodeType type) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.type = type;
    }

    public Integer getXIndex() {
        return xIndex;
    }

    public Integer getYIndex() {
        return yIndex;
    }

    public NodeType getType() {
        return type;
    }
}
