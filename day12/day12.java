package day12;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class day12 {
    static Logger logger = Logger.getLogger(day12.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day12/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day12/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day12/example3.txt";
    static final String ACTUAL_FILE_PATH = "day12/input.txt";
    static List<Row> rows = new ArrayList<>();
    static long combinationCount = 0;

    public static void parseRows(List<String> lines){
        rows = lines.stream().map(line -> {
            List<Node> nodeList = new ArrayList<>();
            List<Integer> contiguousDamagedCounts = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                if(line.charAt(i) == '?'){
                    nodeList.add(new Node(NodeType.UNKNOWN,i));
                }
                if(line.charAt(i) == '#'){
                    nodeList.add(new Node(NodeType.DAMAGED,i));
                }
                if(line.charAt(i) == '.'){
                    nodeList.add(new Node(NodeType.OPERATIONAL,i));
                }
                if(line.charAt(i) != '?' && line.charAt(i) != '.' && line.charAt(i) != '#'){
                    contiguousDamagedCounts = Arrays.stream(line.substring(i+1).split(",")).map(Integer::parseInt).toList();
                    break;
                }
            }
            return new Row(nodeList,contiguousDamagedCounts);
        }).toList();
    }

    public static boolean validateCombinations(Row row){
        List<Integer> contiguousDamageds = row.getContiguousDamagedCounts();
        List<Node> nodeList = row.getNodes();
        Integer count = 0;
        Integer index = 0;
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).getNodeType().equals(NodeType.OPERATIONAL)){
                if(count != 0){
                    if(index >= contiguousDamageds.size()){
                        return false;
                    }
                    if(!count.equals(contiguousDamageds.get(index))){
                        return false;
                    }
                    count =0;
                    index+=1;
                }
            }
            if (nodeList.get(i).getNodeType().equals(NodeType.DAMAGED)){
                count+=1;
                if(i == nodeList.size()-1){
                    if(index >= contiguousDamageds.size()){
                        return false;
                    }
                    if(!count.equals(contiguousDamageds.get(index))){
                        return false;
                    }
                    count =0;
                    index+=1;
                }
            }
        }
        if(index < contiguousDamageds.size()){
            return false;
        }
        return true;
    }
    public static Set<Row> populateRow(Row row){
        Set<Row> rowsGenerated = new HashSet<>();
        rowsGenerated.add(row);
        List<Node> unknownNodes = row.getNodes().stream().filter(node -> node.getNodeType().equals(NodeType.UNKNOWN)).toList();
        for (int i = 0; i < unknownNodes.size(); i++) {
            Set<Row> rowsGeneratedBefore = rowsGenerated;
            Integer index = i;
            rowsGenerated = rowsGeneratedBefore.stream().flatMap(tempRow -> {
                Row tempRow1 = new Row(tempRow);
                Row tempRow2 = new Row(tempRow);
                tempRow1.getNodes().get(unknownNodes.get(index).getxIndex()).setNodeType(NodeType.OPERATIONAL);
                tempRow2.getNodes().get(unknownNodes.get(index).getxIndex()).setNodeType(NodeType.DAMAGED);
                return Stream.of(tempRow1,tempRow2);
            }).collect(Collectors.toSet());
        }
        return rowsGenerated;
    }
    public static void main(String[] args) {
        List<String> lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        parseRows(lines);
        for (Row row:rows) {
            Set<Row> populatedRows = populateRow(row);
            combinationCount += populatedRows.stream().filter(day12::validateCombinations).count();
        }
        logger.log(Level.INFO, String.valueOf(combinationCount));
    }
}
