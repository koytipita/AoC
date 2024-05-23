package day12;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class day12part12 {

    static Logger logger = Logger.getLogger(day12part12.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day12/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day12/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day12/example3.txt";
    static final String ACTUAL_FILE_PATH = "day12/input.txt";

    static List<LineAndCounts> rows = new ArrayList<>();
    static Map<String,Long> memoryDp = new HashMap<>();
    static long combinationCount =0L;

    public static void parseRows(List<String> lines){
        rows = lines.stream().map(line -> {
            String rowString = "";
            List<Long> contiguousDamagedCounts = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                if(line.charAt(i) != '?' && line.charAt(i) != '.' && line.charAt(i) != '#'){
                    rowString = line.substring(0,i);
                    contiguousDamagedCounts = Arrays.stream(line.substring(i+1).split(",")).map(Long::parseLong).toList();
                    break;
                }
            }
            return new LineAndCounts(rowString,contiguousDamagedCounts);
        }).toList();
    }

    public static void putMemoryDp(String lineSoFar, List<Long> damageCountsSoFar, Long result){
        String damageCounts = damageCountsSoFar.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
        memoryDp.put(lineSoFar + damageCounts,result);
    }
    public static long calculateArrangements(String line,List<Long> damageCounts){
        long result = 0L;
        if (line.isEmpty()){
            return damageCounts.isEmpty() ? 1 : 0 ;
        }
        if(damageCounts.isEmpty()){
            return line.contains("#") ? 0 : 1;
        }

        if(memoryDp.get(line+
                damageCounts.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining())) != null){
            return memoryDp.get(line+
                    damageCounts.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining()));
        }
        if (line.charAt(0) == '.' || line.charAt(0) == '?'){
            result += calculateArrangements(line.substring(1),damageCounts);
        }
        if (line.charAt(0) == '#' || line.charAt(0) == '?'){
            int damage =Math.toIntExact(damageCounts.getFirst());
            if(damage <= line.length() //if nums[0] <= len(cfg)
                    // and "." not in cfg[:nums[0]]
                    // and (nums[0] == len(cfg) or cfg[nums[0]] != "#")
            && !((line.substring(0, damage)).contains("."))
            &&  (damage == line.length() || line.charAt(damage) != '#'))
            {
            try {
            result += calculateArrangements(line.substring(damage+1),damageCounts.subList(1, damageCounts.size()));
            }
            catch (Exception e){
                result += calculateArrangements("",damageCounts.subList(1, damageCounts.size()));
            }
            }
        }
        putMemoryDp(line, damageCounts,result);
        return result;
    }

    public static LineAndCounts unfoldRow(LineAndCounts row, Integer times){
        String line = row.getLine();
        String unfoldedLine = row.getLine();
        List<Long> numberList = row.getNumberList();
        List<Long> unfoldedNumbers = new ArrayList<>(numberList);
        for (int i = 0; i < times-1; i++) {
            unfoldedLine = unfoldedLine.concat("?"+line);
            unfoldedNumbers.addAll(numberList);
        }
        return new LineAndCounts(unfoldedLine,unfoldedNumbers);
    }

    public static void main(String[] args) {
        List<String> lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        parseRows(lines);
        rows.forEach(row -> {
            memoryDp = new HashMap<>();
            //Integer combCountOld = combinationCount;
            row = unfoldRow(row,5);
            combinationCount += calculateArrangements(row.getLine(), row.getNumberList());
            //System.out.print(combinationCount-combCountOld);
            //System.out.print(" ");
        });
        logger.log(Level.INFO, String.valueOf(combinationCount));
    }

}
