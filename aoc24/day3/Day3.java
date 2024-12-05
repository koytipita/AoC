package aoc24.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    private static Logger logger = Logger.getLogger(Day3.class.getName());
    private static final String EXAMPLE1_FILE_PATH = "aoc24/day3/example1.txt";
    private static final String EXAMPLE2_FILE_PATH = "aoc24/day3/example2.txt";
    private static final String ACTUAL_FILE_PATH = "aoc24/day3/input.txt";

    private long getResult(String str){
        Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
        Matcher matcher = pattern.matcher(str);
        List<String> matches = new ArrayList<>();
        while (matcher.find()){
            matches.add(matcher.group());
        }
        return calculateCorruptedMultipleSum(matches);
    }

    private long getResultWithSwitches(String str){
        String mulRegex = "mul\\(\\d+,\\d+\\)";
        String controlRegex = "do\\(\\)|don't\\(\\)";

        Pattern mulPattern = Pattern.compile(mulRegex);
        Pattern controlPattern = Pattern.compile(controlRegex);

        Matcher mulMatcher = mulPattern.matcher(str);
        Matcher controlMatcher = controlPattern.matcher(str);

        List<String> results = new ArrayList<>();
        boolean canAdd = true;

        TreeMap<Integer, String> matchMap = new TreeMap<>();

        while (mulMatcher.find()) {
            matchMap.put(mulMatcher.start(), "mul:" + mulMatcher.group());
        }

        while (controlMatcher.find()) {
            matchMap.put(controlMatcher.start(), controlMatcher.group());
        }

        for (Map.Entry<Integer, String> entry : matchMap.entrySet()) {
            String match = entry.getValue();
            if (match.equals("do()")) {
                canAdd = true;
            } else if (match.equals("don't()")) {
                canAdd = false;
            } else if (match.startsWith("mul:") && canAdd) {
                results.add(match.substring(4));
            }
        }
        return calculateCorruptedMultipleSum(results);

    }

    private long calculateCorruptedMultipleSum(List<String> multiplicationsList){
         return multiplicationsList.stream()
                 .mapToLong(str ->
                         Long.parseLong(str.substring(str.indexOf('(')+1,str.indexOf(',')))
                         * Long.parseLong(str.substring(str.indexOf(',')+1,str.indexOf(')')))
                 )
                 .sum();
    }


    public static void main(String[] args) {
        Day3 day3 = new Day3();
        try {
            String input = new String(Files.readAllBytes(Path.of(ACTUAL_FILE_PATH)));
            logger.log(Level.INFO, () -> String.valueOf(day3.getResult(input)));
            logger.log(Level.INFO, () -> String.valueOf(day3.getResultWithSwitches(input)));

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
