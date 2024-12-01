package aoc23.day11;

public class Node {
    private NodeType nodeType;
    private Integer xIndex;
    private Integer yIndex;


    public Node(NodeType nodeType, Integer xIndex, Integer yIndex) {
        this.nodeType = nodeType;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public Integer getxIndex() {
        return xIndex;
    }

    public void setxIndex(Integer xIndex) {
        this.xIndex = xIndex;
    }

    public Integer getyIndex() {
        return yIndex;
    }

    public void setyIndex(Integer yIndex) {
        this.yIndex = yIndex;
    }
}
