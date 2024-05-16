package day8;

import day7.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Character.getNumericValue;
import static java.lang.Character.isDigit;

public class day8 {
    static Logger logger = Logger.getLogger(day8.class.getName());


    public static Node executeOneLine(List<Node> nodeList, char[] directions, Node currNode){
        for (char direction: directions) {
            if (direction == 'L'){
                String leftNodeId = currNode.getLeftNodeId();
                currNode = nodeList.stream().filter(node -> node.getNodeId().equals(leftNodeId)).toList().get(0);
            }
            if (direction == 'R'){
                String rightNodeId = currNode.getRightNodeId();
                currNode = nodeList.stream().filter(node -> node.getNodeId().equals(rightNodeId)).toList().get(0);
            }
        }
        return currNode;
    }

    public static void main(String[] args) {

        String filePath = "day8/input.txt"; // Replace with the actual file path
        List<String> lines = utils.FileParseUtil.readLinesFromFile(filePath, logger);
        char[] directions  = lines.get(0).toCharArray();
        List<Node> nodeList = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");

        IntStream.range(1,lines.size()).forEach(index -> {
            Matcher matcher = pattern.matcher(lines.get(index));
            if (matcher.matches()) {
                String nodeId = matcher.group(1); // VVX
                String leftNodeId = matcher.group(2); // VRH
                String rightNodeId = matcher.group(3); // RBM
                nodeList.add(new Node(nodeId,leftNodeId,rightNodeId));
            }
        });

        int countToReachZ = 0;
        Node currNode = nodeList.stream().filter(node -> node.getNodeId().equals("AAA")).toList().get(0);
        currNode = executeOneLine(nodeList,directions,currNode);
        countToReachZ += 1;

        while (!currNode.getNodeId().equals("ZZZ")){
            currNode = executeOneLine(nodeList,directions,currNode);
            countToReachZ += 1;
        }
        System.out.println(countToReachZ*directions.length);



    }
}
