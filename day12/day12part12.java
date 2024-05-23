package day12;

import utils.StringDiff;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class day12part12 {

    static Logger logger = Logger.getLogger(day12.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day12/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day12/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day12/example3.txt";
    static final String ACTUAL_FILE_PATH = "day12/input.txt";

    static List<LineAndCounts> rows = new ArrayList<>();
    static Map<LineAndCounts,Boolean> memoryDp = new HashMap<>();
    static Integer combinationCount =0;

    public static void parseRows(List<String> lines){
        rows = lines.stream().map(line -> {
            String rowString = "";
            List<Integer> contiguousDamagedCounts = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                if(line.charAt(i) != '?' && line.charAt(i) != '.' && line.charAt(i) != '#'){
                    rowString = line.substring(0,i);
                    contiguousDamagedCounts = Arrays.stream(line.substring(i+1).split(",")).map(Integer::parseInt).toList();
                    break;
                }
            }
            return new LineAndCounts(rowString,contiguousDamagedCounts);
        }).toList();
    }

    public static List<Integer> diffIntegerList(List<Integer> firstList, List<Integer> currList){
        for (Integer currInteger: currList) {
            firstList.remove(currInteger);
        }
        return firstList;
    }

    public static void putMemoryDpFalse(String lineSoFar, List<Integer> damageCountsSoFar){
        memoryDp.put(new LineAndCounts(lineSoFar,damageCountsSoFar),false);
    }
    public static void calculateArrangements(String firstLine, List<Integer> firstDamageCounts, String line,List<Integer> damageCounts ,Integer currDamageCount,Character from){
        if(memoryDp.get(new LineAndCounts(StringDiff.StringDiff(firstLine,line),damageCounts)) != null){
            return;
        }
        if (line.isEmpty() && from == '#'){
            if(damageCounts.size() != 1 || !currDamageCount.equals(damageCounts.getFirst())){
                putMemoryDpFalse(StringDiff.StringDiff(firstLine,line),diffIntegerList(firstDamageCounts,damageCounts));
                return;
            }
            else{
                combinationCount +=1;
                return;
            }
        }
        if (line.isEmpty() && from == '.'){
            if(currDamageCount.equals(0) && (!damageCounts.isEmpty() || !currDamageCount.equals(0))){
                putMemoryDpFalse(StringDiff.StringDiff(firstLine,line),diffIntegerList(firstDamageCounts,damageCounts));
                return;
            }
            if(currDamageCount > 0 && (damageCounts.size() != 1 || !currDamageCount.equals(damageCounts.getFirst()))){
                putMemoryDpFalse(StringDiff.StringDiff(firstLine,line),diffIntegerList(firstDamageCounts,damageCounts));
                return;
            }
            else{
                combinationCount +=1;
                return;
            }
        }
        if(from == '#' && (damageCounts.isEmpty() || currDamageCount>damageCounts.getFirst())){
            putMemoryDpFalse(StringDiff.StringDiff(firstLine,line),diffIntegerList(firstDamageCounts,damageCounts));
            return;
        }
        if(from == '.' && currDamageCount > 0 && (!currDamageCount.equals(damageCounts.getFirst()))) {
            putMemoryDpFalse(StringDiff.StringDiff(firstLine, line), diffIntegerList(firstDamageCounts, damageCounts));
            return;
        }
        if(from == '.' && currDamageCount > 0 && currDamageCount.equals(damageCounts.getFirst())){
            currDamageCount = 0;
            damageCounts = damageCounts.subList(1,damageCounts.size());
        }

        if (line.charAt(0) == '?') { ///...###..## 2,3,1
            String damagedPlacedLine = "#"+line.substring(1);
            String operationalPlacedLine = "."+line.substring(1);
            calculateArrangements(firstLine, firstDamageCounts, damagedPlacedLine,damageCounts,currDamageCount,'#' );
            calculateArrangements(firstLine, firstDamageCounts, operationalPlacedLine,damageCounts,currDamageCount, '.');
        }

        if (line.charAt(0) == '#'){
            calculateArrangements(firstLine, firstDamageCounts, line.substring(1),damageCounts,currDamageCount+1,'#' );
        }
        if (line.charAt(0) == '.'){
            calculateArrangements(firstLine, firstDamageCounts, line.substring(1),damageCounts,currDamageCount,'.');
        }
    }


    public static void main(String[] args) {
        List<String> lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        parseRows(lines);
        rows.forEach(row -> {
            //Integer combCountOld = combinationCount;

            calculateArrangements(row.getLine(), row.getNumberList(), row.getLine(), row.getNumberList(),0,'S');
            //System.out.print(combinationCount-combCountOld);
            //System.out.print(" ");
        });
        logger.log(Level.INFO, String.valueOf(combinationCount));
    }

}
