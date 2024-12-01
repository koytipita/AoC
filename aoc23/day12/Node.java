package aoc23.day12;

public class Node {
    private NodeType nodeType;
    private Integer xIndex;

    public Node(NodeType nodeType, Integer xIndex) {
        this.nodeType = nodeType;
        this.xIndex = xIndex;
    }

    public Node(Node other) {
        this.nodeType = other.nodeType;
        this.xIndex = other.xIndex;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getxIndex() {
        return xIndex;
    }

    public void setxIndex(Integer xIndex) {
        this.xIndex = xIndex;
    }
}
