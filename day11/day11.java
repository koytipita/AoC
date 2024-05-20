package day11;

import day11.NodeType;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;

public class day11 {

    static Logger logger = Logger.getLogger(day11.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day11/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day11/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day11/example3.txt";
    static final String ACTUAL_FILE_PATH = "day11/input.txt";
    static List<List<Node>> nodeMap = new ArrayList<>();
    static List<Integer> shortestPaths = new ArrayList<>();
    static Integer sum = 0;
    public static void parseNodeMap(List<String> lines){
        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            List<Node> nodeRow = new ArrayList<>();
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#')
                    nodeRow.add(new Node(NodeType.GALAXY,j,i));
                else
                    nodeRow.add(new Node(NodeType.EMPTYSPACE,j,i));
            }
            nodeMap.add(nodeRow);
        }
    }

    public static List<Integer> getEmptyHorizontalLineIndexes(List<List<Node>> nodeMap){
        return IntStream.range(0, nodeMap.size()).filter(yIndex ->{
            List<Node> nodeRow = nodeMap.get(yIndex);
            return nodeRow.stream().allMatch(node -> node.getNodeType().equals(NodeType.EMPTYSPACE));
        }).boxed().toList();
    }

    public static List<Integer> getEmptyVerticalLineIndexes(List<List<Node>> nodeMap){
        return IntStream.range(0, nodeMap.getFirst().size()).filter(xIndex -> nodeMap.stream()
                .flatMap(Collection::stream)
                .filter(node -> node.getxIndex().equals(xIndex))
                .allMatch(node -> node.getNodeType().equals(NodeType.EMPTYSPACE))).boxed().toList();
    }

    public static void expandHorizontally(List<List<Node>> nodeMap, List<Integer> emptyHorizontalLineIndexes){
        IntStream.range(0,emptyHorizontalLineIndexes.size()).forEach(increment -> {
            Integer yIndex = emptyHorizontalLineIndexes.get(increment);
            List<Node> emptyHorizontalRow = new ArrayList<>();
            for (int i = 0; i < nodeMap.getFirst().size(); i++) {
                emptyHorizontalRow.add(new Node(NodeType.EMPTYSPACE,i,yIndex+increment));
            }
            nodeMap.add(yIndex+increment,emptyHorizontalRow);
        });
    }

    public static void expandVertically(List<List<Node>> nodeMap, List<Integer> emptyVerticalLineIndexes){
        IntStream.range(0, emptyVerticalLineIndexes.size()).forEach(increment ->{
            Integer xIndex = emptyVerticalLineIndexes.get(increment);
            for (int i = 0; i < nodeMap.size(); i++) {
                nodeMap.get(i).add(xIndex+increment,new Node(NodeType.EMPTYSPACE,xIndex+increment,i));
            }
        });
    }

    public static void printImage(List<List<Node>> nodeMap){
        nodeMap.forEach(nodes -> {
            for (int i = 0; i < nodes.size(); i++) {
                if(nodes.get(i).getNodeType().equals(NodeType.EMPTYSPACE))
                    System.out.print(".");
                else
                    System.out.print("#");
            }
            System.out.print("\n");
        });
    }

    public static List<List<Node>> getGalaxyPairs(List<List<Node>> nodeMap){
        List<Node> galaxyList = new ArrayList<Node>(nodeMap.stream()
                .flatMap(Collection::stream)
                .filter(node -> node.getNodeType().equals(NodeType.GALAXY)).toList());
        List <Node> updatedGalaxyList = updateGalaxyList(galaxyList);
        return IntStream.range(0, galaxyList.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, updatedGalaxyList.size())
                        .mapToObj(j -> List.of(updatedGalaxyList.get(i), updatedGalaxyList.get(j))))
                .toList();
    }

    public static List<Node> updateGalaxyList(List<Node> galaxyList){
        for (Node galaxy:galaxyList) {
            for (int i = 0; i < nodeMap.size(); i++) {
                for (int j = 0; j < nodeMap.getFirst().size(); j++) {
                    if(nodeMap.get(i).get(j) == galaxy){
                        galaxy.setxIndex(j);
                        galaxy.setyIndex(i);
                    }
                }
            }
        }
        return galaxyList;

    }

    public static void calculateShortestPaths(List<List<Node>>galaxyPairs){
        for (int i = 0; i < galaxyPairs.size(); i++) {
            List<Node> galaxyPair = galaxyPairs.get(i);
            Integer shortestPath = abs(galaxyPair.getFirst().getxIndex()-galaxyPair.getLast().getxIndex())
                    + abs(galaxyPair.getFirst().getyIndex()-galaxyPair.getLast().getyIndex());
            shortestPaths.add(shortestPath);
            sum += shortestPath;
        }
    }


    public static void main(String[] args) {
        List<String> lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        parseNodeMap(lines);
        List<Integer> emptyHorizontalLineIndexes = getEmptyHorizontalLineIndexes(nodeMap);
        List<Integer> emptyVerticalLineIndexes = getEmptyVerticalLineIndexes(nodeMap);
        expandHorizontally(nodeMap,emptyHorizontalLineIndexes);
        expandVertically(nodeMap,emptyVerticalLineIndexes);
        List<List<Node>> galaxyPairs =getGalaxyPairs(nodeMap);
        calculateShortestPaths(galaxyPairs);
        System.out.println(sum);



    }

}

