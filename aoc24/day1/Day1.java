package aoc24.day1;


import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day1 {
    static Logger logger = Logger.getLogger(Day1.class.getName());
    static final String EXAMPLE1_FILE_PATH = "aoc24/day1/example1.txt";
    static final String ACTUAL_FILE_PATH = "aoc24/day1/input.txt";

    private int calculateDifferenceBetweenLists(List<String> input){
        List<Integer> leftList = input.stream().
                map(line -> Integer.valueOf(line.split(" {3}")[0]))
                .sorted()
                .toList();
        List<Integer> rightList = input.stream().
                map(line -> Integer.valueOf(line.split(" {3}")[1]))
                .sorted()
                .toList();

        return IntStream.range(0, leftList.size())
                .map(i -> Math.abs(leftList.get(i) - rightList.get(i)))
                .sum();
    }

    private long calculateSimilarityScore(List<String> input){
        List<Integer> leftList = input.stream().
                map(line -> Integer.valueOf(line.split(" {3}")[0]))
                .sorted()
                .toList();
        Map<Integer, Long> rightMap = input.stream().
                map(line -> Integer.valueOf(line.split(" {3}")[1]))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return leftList.stream()
                .mapToLong(i -> i * rightMap.getOrDefault(i, 0L))
                .sum();
    }



    public static void main(String[] args) {
        Day1 day1 = new Day1();
        List<String> input = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        logger.log(Level.INFO, () -> String.valueOf(day1.calculateDifferenceBetweenLists(input)));
        logger.log(Level.INFO, () -> String.valueOf(day1.calculateSimilarityScore(input)));
    }
}
