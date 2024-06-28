package day25;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {
    private List<Edge> edgeList = new ArrayList<>();
    private Integer verticeCount;

    public Graph(List<Edge> edgeList, Integer verticeCount) {
        this.edgeList = edgeList;
        this.verticeCount = verticeCount;
    }

    public void cutEdge(Edge edge){
        Node node1 = edge.getNode1();
        Node node2 = edge.getNode2();
        node1.setCount(node1.getCount()+ node2.getCount());

        verticeCount -= 1;
        edgeList.remove(edge);
        edgeList.stream().filter(edge1 -> edge1.getNode1().getName().equals(node2.getName()))
            .forEach(edge1-> {
                int index = edgeList.indexOf(edge1);
                edge1.setNode1(node1);
                edgeList.set(index, edge1);
            });
        edgeList.stream().filter(edge1 -> edge1.getNode2().getName().equals(node2.getName()))
            .forEach(edge1->{
                int index = edgeList.indexOf(edge1);
                edge1.setNode2(node1);
                edgeList.set(index, edge1);
            });
        List<Edge> selfLoops = edgeList.stream().filter(edge1 -> edge1.getNode1().getName().equals(edge1.getNode2().getName())).toList();
        selfLoops.forEach(selfLoop -> edgeList.remove(selfLoop));
        node1.setName(node1.getName()+ " " + node2.getName());
    }

    public boolean addEdge(Edge edge){
        String str1 = edge.getNode1().getName();
        String str2 = edge.getNode2().getName();
        if(edgeList.stream().anyMatch(edge1 ->
            (edge1.getNode1().getName().equals(str1) && edge1.getNode2().getName().equals(str2)
            || edge1.getNode1().getName().equals(str2) && edge1.getNode2().getName().equals(str1)))){
            return false;
        }
        edgeList.add(edge);
        return true;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public Integer getVerticeCount() {
        return verticeCount;
    }

    public void setVerticeCount(Integer verticeCount) {
        this.verticeCount = verticeCount;
    }
}
