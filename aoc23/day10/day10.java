package aoc23.day10;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class day10 {

    static Logger logger = Logger.getLogger(day10.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day10/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day10/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day10/example3.txt";
    static final String ACTUAL_FILE_PATH = "day10/input.txt";
    static int startXPosition = 0;
    static int startYPosition= 0;
    static Integer currXIndex;
    static Integer currYIndex;
    static Integer nodeCount = 0;
    static List<Node> nodeMap = new ArrayList<>();
    
    static List<Node> visitedNodes = new ArrayList<>();

    static double interiorArea = 0.0;
    
    public static boolean hasEast(Node node,Integer currXIndex,Integer currYIndex){
        return node.getXIndex() == currXIndex+1
                && Objects.equals(node.getYIndex(), currYIndex)
                && (node.getType().equals(NodeType.HORIZONTAL)
                || node.getType().equals(NodeType.SOUTHWESTBEND)
                || node.getType().equals(NodeType.NORTHWESTBEND));
    }
    
    public static boolean hasWest(Node node,Integer currXIndex,Integer currYIndex){
        return node.getXIndex() == currXIndex-1
                && Objects.equals(node.getYIndex(), currYIndex)
                && (node.getType().equals(NodeType.HORIZONTAL)
                || node.getType().equals(NodeType.NORTHEASTBEND)
                || node.getType().equals(NodeType.SOUTHEASTBEND));
    }

    public static boolean hasNorth(Node node,Integer currXIndex,Integer currYIndex){
        return Objects.equals(node.getXIndex(), currXIndex)
                && node.getYIndex() == currYIndex-1
                && (node.getType().equals(NodeType.VERTICAL)
                || node.getType().equals(NodeType.SOUTHWESTBEND)
                || node.getType().equals(NodeType.SOUTHEASTBEND));
    }

    public static boolean hasSouth(Node node,Integer currXIndex,Integer currYIndex){
        return Objects.equals(node.getXIndex(), currXIndex)
                && Objects.equals(node.getYIndex(), currYIndex+1)
                && (node.getType().equals(NodeType.VERTICAL)
                || node.getType().equals(NodeType.NORTHEASTBEND)
                || node.getType().equals(NodeType.NORTHWESTBEND));
    }

    public static Node setNode(Integer xIndex, Integer yIndex, Character character){
        switch (character){
            case 'S':
                startXPosition = xIndex;
                startYPosition = yIndex;
                return new Node(xIndex,yIndex,NodeType.START);
            case '.':
                return new Node(xIndex,yIndex,NodeType.GROUND);
            case '|':
                return new Node(xIndex,yIndex,NodeType.VERTICAL);
            case '-':
                return new Node(xIndex,yIndex,NodeType.HORIZONTAL);
            case 'L':
                return new Node(xIndex,yIndex,NodeType.NORTHEASTBEND);
            case 'J':
                return new Node(xIndex,yIndex,NodeType.NORTHWESTBEND);
            case '7':
                return new Node(xIndex,yIndex,NodeType.SOUTHWESTBEND);
            case 'F':
                return new Node(xIndex,yIndex,NodeType.SOUTHEASTBEND);
            default:
                logger.log(Level.WARNING,"parse error");
                break;
        }
        return null;
    }


    public static void traverse(String initialDirection) {
        while (true) {
            Optional<Node> currNodeOp = Optional.empty();
            switch (initialDirection) {
                case "EAST":
                    currNodeOp = nodeMap.stream().filter(node -> node.getXIndex().equals(currXIndex + 1) && node.getYIndex().equals(currYIndex)).findFirst();
                    if (!currNodeOp.isPresent()) return;
                    currXIndex += 1;
                    break;
                case "WEST":
                    currNodeOp = nodeMap.stream().filter(node -> node.getXIndex().equals(currXIndex - 1) && node.getYIndex().equals(currYIndex)).findFirst();
                    if (!currNodeOp.isPresent()) return;
                    currXIndex -= 1;
                    break;
                case "NORTH":
                    currNodeOp = nodeMap.stream().filter(node -> node.getXIndex().equals(currXIndex) && node.getYIndex().equals(currYIndex - 1)).findFirst();
                    if (!currNodeOp.isPresent()) return;
                    currYIndex -= 1;
                    break;
                case "SOUTH":
                    currNodeOp = nodeMap.stream().filter(node -> node.getXIndex().equals(currXIndex) && node.getYIndex().equals(currYIndex + 1)).findFirst();
                    if (!currNodeOp.isPresent()) return;
                    currYIndex += 1;
                    break;
                default:
                    return;
            }

            Node currNode = currNodeOp.orElse(null);
            visitedNodes.add(currNode);
            nodeCount += 1;

            switch (currNode.getType()) {
                case NodeType.HORIZONTAL:
                    if (initialDirection.equals("EAST") || initialDirection.equals("WEST")) continue;
                    break;
                case NodeType.VERTICAL:
                    if (initialDirection.equals("NORTH") || initialDirection.equals("SOUTH")) continue;
                    break;
                case NodeType.SOUTHWESTBEND:
                    if (initialDirection.equals("EAST")) {
                        initialDirection = "SOUTH";
                    } else if (initialDirection.equals("NORTH")) {
                        initialDirection = "WEST";
                    }
                    break;
                case NodeType.SOUTHEASTBEND:
                    if (initialDirection.equals("WEST")) {
                        initialDirection = "SOUTH";
                    } else if (initialDirection.equals("NORTH")) {
                        initialDirection = "EAST";
                    }
                    break;
                case NodeType.NORTHWESTBEND:
                    if (initialDirection.equals("EAST")) {
                        initialDirection = "NORTH";
                    } else if (initialDirection.equals("SOUTH")) {
                        initialDirection = "WEST";
                    }
                    break;
                case NodeType.NORTHEASTBEND:
                    if (initialDirection.equals("WEST")) {
                        initialDirection = "NORTH";
                    } else if (initialDirection.equals("SOUTH")) {
                        initialDirection = "EAST";
                    }
                    break;
                default:
                    return;
            }
        }
    }

    public static void traverseLoop() throws Exception {
        Optional<Node> currNodeOp = nodeMap.stream().filter(node -> node.getType().equals(NodeType.START)).findFirst();
        Node currNode = currNodeOp.orElse(null);
        if(currNode == null){
            logger.log(Level.WARNING,"start node not found");
            throw new Exception();
        }
        currXIndex = currNode.getXIndex();
        currYIndex = currNode.getYIndex();
        
        boolean hasEast = nodeMap.stream().anyMatch(node -> hasEast(node,currXIndex,currYIndex));
        boolean hasWest = nodeMap.stream().anyMatch(node -> hasWest(node,currXIndex,currYIndex));
        boolean hasNorth = nodeMap.stream().anyMatch(node -> hasNorth(node,currXIndex,currYIndex));
        boolean hasSouth = nodeMap.stream().anyMatch(node -> hasSouth(node,currXIndex,currYIndex));

        while (true){
            nodeCount+=1;
            visitedNodes.add(currNode);
            if(hasEast){
                traverse("EAST");
                break;
            }
            if(hasWest){
                traverse("WEST");
                break;
            }
            if(hasNorth){
                traverse("NORTH");
                break;
            }
            if(hasSouth){
                traverse("SOUTH");
                break;
            }
        }
    }

    public static void calculateInteriorArea(){
        visitedNodes.add(visitedNodes.getFirst());
        int n = visitedNodes.size();

        double sum1 = IntStream.range(0, n-1)
                .mapToDouble(i -> visitedNodes.get(i).getXIndex() * visitedNodes.get((i + 1)).getYIndex())
                .sum();

        double sum2 = IntStream.range(0, n-1)
                .mapToDouble(i -> visitedNodes.get(i).getYIndex()* visitedNodes.get((i + 1)).getXIndex())
                .sum();

        interiorArea =0.5 * abs(sum1 - sum2);
        visitedNodes.remove(visitedNodes.size()-1);
    }

    public static void main(String[] args) {
        List<String> lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                nodeMap.add(setNode(j,i,line.charAt(j)));
            }
        }
        try {
            traverseLoop();
            calculateInteriorArea();
        }
        catch (Exception e){
            logger.log(Level.WARNING,e.getMessage());
        }
        System.out.println(nodeCount%2 == 0 ? nodeCount/2 : (nodeCount-1)/2);
        System.out.println(Double.valueOf(interiorArea - 0.5 * visitedNodes.size() + 1).intValue()+1);
    }
}
