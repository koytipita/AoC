package aoc24.day4;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 {
    private static Logger logger = Logger.getLogger(Day4.class.getName());
    private static final String EXAMPLE1_FILE_PATH = "aoc24/day4/example1.txt";
    private static final String EXAMPLE2_FILE_PATH = "aoc24/day4/example2.txt";
    private static final String ACTUAL_FILE_PATH =   "aoc24/day4/input.txt";

    public static void main(String[] args) {
        Day4 day4 = new Day4();
        List<String> input = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        logger.info(() -> String.valueOf(day4.countXMAS(input)));
    }

    private long countXMAS(List<String> input){
        long count = 0;
        count += countXMASHorizontally(input);
        count += countXMASVertically(input);
        count += countXMASDiagonallyUpperLeftToLowerRight(input);
        count += countXMASDiagonallyLowerLeftToUpperRight(input);
        return count;
    }

    private long countXMASDiagonallyUpperLeftToLowerRight(List<String> input){
        return IntStream.range(0, input.getFirst().length() - 3).mapToLong(x ->
            IntStream.range(0, input.size() -3).filter(y -> {
                String word = String.valueOf(input.get(y).charAt(x)) +
                        input.get(y + 1).charAt(x + 1) +
                        input.get(y + 2).charAt(x + 2) +
                        input.get(y + 3).charAt(x + 3);
                return word.equals("XMAS") || word.equals("SAMX");
            }).count()
        ).sum();
    }

    private long countXMASDiagonallyLowerLeftToUpperRight(List<String> input){
        return IntStream.range(0, input.getFirst().length() - 3).mapToLong(x ->
                IntStream.range(3, input.size()).filter(y -> {
                    String word = String.valueOf(input.get(y).charAt(x)) +
                            input.get(y - 1).charAt(x + 1) +
                            input.get(y - 2).charAt(x + 2) +
                            input.get(y - 3).charAt(x + 3);
                    return word.equals("XMAS") || word.equals("SAMX");
                }).count()
        ).sum();
    }

    private long countXMASHorizontally(List<String> input){
        return input.stream().mapToLong(line ->
                IntStream.range(0, line.length()-3)
                        .mapToObj(i -> line.substring(i, i+4))
                        .filter(word -> word.equals("XMAS") || word.equals("SAMX"))
                        .count()
        ).sum();
    }

    private long countXMASVertically(List<String> input) {
        return IntStream.range(0, input.getFirst().length())
                .mapToLong(columnIndex -> countWordsInColumn(input, columnIndex))
                .sum();
    }

    private long countWordsInColumn(List<String> input, int columnIndex) {
        return IntStream.range(0, input.size() - 3)
                .filter(rowIndex -> isWordMatch(input, columnIndex, rowIndex))
                .count();
    }

    private boolean isWordMatch(List<String> input, int columnIndex, int rowIndex) {
        String word = IntStream.range(0, 4)
                .mapToObj(offset -> String.valueOf(input.get(rowIndex + offset).charAt(columnIndex)))
                .collect(Collectors.joining());
        return word.equals("XMAS") || word.equals("SAMX");
    }
}
