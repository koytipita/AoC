package aoc24.day2;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day2 {
    private static Logger logger = Logger.getLogger(Day2.class.getName());
    private static final String EXAMPLE1_FILE_PATH = "aoc24/day2/example1.txt";
    private static final String ACTUAL_FILE_PATH = "aoc24/day2/input.txt";

    private long countSafes(List<String> input){
        return input.stream()
                .map(line ->
                        Arrays.stream(line.split(" "))
                                .mapToInt(Integer::valueOf)
                                .boxed()
                                .toList()
                )
                .filter(Day2::isMonotonic)
                .count();
    }

    private long countSafesWithOneTolerance(List<String> input){
        return input.stream()
                .map(line ->
                        Arrays.stream(line.split(" "))
                                .mapToInt(Integer::valueOf)
                                .boxed()
                                .toList()
                )
                .filter(Day2::isMonotonicWithOneTolerance)
                .count();
    }

    private static boolean isMonotonicWithOneTolerance(List<Integer> list) {
        if (list.size() <= 1) return true;

        if (isMonotonic(list)) return true;

        List<List<Integer>> alternativeLists = IntStream.range(0, list.size())
                .mapToObj(i -> IntStream.range(0, list.size())
                        .filter(j -> j != i)
                        .map(list::get)
                        .boxed().toList())
                .toList();

        return alternativeLists.stream().anyMatch(Day2::isMonotonic);
    }

    private static boolean isMonotonic(List<Integer> list) {
        if (list.size() <= 1) return true;

        boolean increasing = IntStream.range(0, list.size() - 1)
                .allMatch(i -> (list.get(i) < list.get(i + 1))
                        && (list.get(i + 1) - list.get(i) >= 1)
                        && (list.get(i + 1) - list.get(i) <= 3));

        boolean decreasing = IntStream.range(0, list.size() - 1)
                .allMatch(i -> (list.get(i) > list.get(i + 1))
                        && (list.get(i) - list.get(i + 1) >= 1)
                        && (list.get(i) - list.get(i + 1) <= 3));

        return increasing || decreasing;
    }

    public static void main(String[] args) {
        Day2 day2 = new Day2();
        List<String> input = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        logger.log(Level.INFO, () -> String.valueOf(day2.countSafes(input)));
        logger.log(Level.INFO, () -> String.valueOf(day2.countSafesWithOneTolerance(input)));
    }
}
