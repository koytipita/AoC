package aoc24.day8;


public class Node {
    private String nodeId;
    private String leftNodeId;
    private String rightNodeId;

    public Node(String nodeId, String leftNodeId, String rightNodeId) {
        this.nodeId = nodeId;
        this.leftNodeId = leftNodeId;
        this.rightNodeId = rightNodeId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getLeftNodeId() {
        return leftNodeId;
    }

    public void setLeftNodeId(String leftNodeId) {
        this.leftNodeId = leftNodeId;
    }

    public String getRightNodeId() {
        return rightNodeId;
    }

    public void setRightNodeId(String rightNodeId) {
        this.rightNodeId = rightNodeId;
    }
}
