package aoc24.day5;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Day5 {
    private static Logger logger = Logger.getLogger(Day5.class.getName());
    private static final String EXAMPLE1_FILE_PATH = "aoc24/day5/example1.txt";
    private static final String EXAMPLE2_FILE_PATH = "aoc24/day5/example2.txt";
    private static final String EXAMPLE3_FILE_PATH = "aoc24/day5/example3.txt";
    private static final String ACTUAL_FILE_PATH = "aoc24/day5/input.txt";

    public static void main(String[] args) {
        Day5 day5 = new Day5();
        List<String> input = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        logger.info(() -> String.valueOf(day5.sumOfMiddleNumbersOfCorrectlyOrderedUpdates(input)));
        //logger.info(() -> String.valueOf(day5.countXMAS2(input)));
    }

    private long sumOfMiddleNumbersOfCorrectlyOrderedUpdates(List<String> input){
        List<int[]> rules = input.subList(0, input.indexOf("")).stream()
                .map(line -> new int[]{Integer.parseInt(line.split("\\|")[0]),
                        Integer.parseInt(line.split("\\|")[1])}
                ).toList();
        List<List<Integer>> updates = input.subList(input.indexOf("") + 1, input.size()).stream()
                .map(line -> Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).boxed().toList()
                ).toList();

        return updates.stream()
                .filter(update -> rules.stream().noneMatch(rule -> isAfter(rule[0], rule[1], update)))
                .mapToLong(update -> update.get(update.size()/2))
                .sum();
    }

    private boolean isAfter(int a, int b, List<Integer> update){
        if (update.contains(a) && update.contains(b)){
            return update.indexOf(a) > update.indexOf(b);
        }
        return false;
    }
}
