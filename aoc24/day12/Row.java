package aoc24.day12;

import java.util.List;
import java.util.stream.Collectors;

public class Row {
    private List<Node> nodes;
    private List<Integer> contiguousDamagedCounts;

    public Row(List<Node> nodes, List<Integer> contiguousDamagedCounts) {
        this.nodes = nodes;
        this.contiguousDamagedCounts = contiguousDamagedCounts;
    }

    public Row(Row other) {
        this.nodes = other.nodes.stream()
                .map(Node::new)
                .collect(Collectors.toList());
        this.contiguousDamagedCounts = other.contiguousDamagedCounts;
    }
    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Integer> getContiguousDamagedCounts() {
        return contiguousDamagedCounts;
    }

    public void setContiguousDamagedCounts(List<Integer> contiguousDamagedCounts) {
        this.contiguousDamagedCounts = contiguousDamagedCounts;
    }
}
