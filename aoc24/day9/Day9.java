package aoc24.day9;

import utils.FileParseUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day9 {
    private static Logger logger = Logger.getLogger(Day9.class.getName());
    private static final String EXAMPLE1_FILE_PATH = "aoc24/day9/example1.txt";
    private static final String EXAMPLE2_FILE_PATH = "aoc24/day9/example2.txt";
    private static final String EXAMPLE3_FILE_PATH = "aoc24/day9/example3.txt";
    private static final String EXAMPLE4_FILE_PATH = "aoc24/day9/example4.txt";
    private static final String ACTUAL_FILE_PATH = "aoc24/day9/input.txt";

    public static void main(String[] args) {
        Day9 day9 = new Day9();

        String input = FileParseUtil.readStringFromFile(ACTUAL_FILE_PATH, logger);

        logger.info(String.valueOf(day9.solution(input)));

    }

    private long solution (String input) {
        List<Integer> blockList = getFirstLookWithEmptyBlocks(input);

        ArrayList<Integer> blocks = new ArrayList<>(blockList);

        logger.info(blocks::toString);

        int emptyIndex = blocks.indexOf(-1);

        int backIndex = blocks.size() - 1;

        while (emptyIndex < backIndex && backIndex > 0) {
            int backElement = blocks.get(backIndex);
            if (backElement != -1) {
                blocks.set(emptyIndex, backElement);
                blocks.set(backIndex, -1);
                while (emptyIndex < backIndex && blocks.get(emptyIndex) != -1){
                    emptyIndex += 1;
                }
            }
            backIndex--;
        }

        logger.info(blocks::toString);
        logger.info(String.valueOf(computeChecksum(blocks)));

        return 0L;
    }

    private List<Integer> getFirstLookWithEmptyBlocks (String input) {
        return IntStream.range(0, input.length())
                .flatMap(i -> {
                    int[] numbers = new int[Character.getNumericValue(input.charAt(i))];
                    if (i % 2 == 0) {
                        Arrays.fill(numbers, i/2);
                        return Arrays.stream(numbers);
                    }
                    Arrays.fill(numbers, -1);
                    return Arrays.stream(numbers);
                }).boxed().toList();
    }

    private long computeChecksum(List<Integer> blocks){
        return LongStream.range(0, blocks.size())
                .filter(i -> blocks.get((int) i) != -1L)
                .map(i -> i * blocks.get((int) i))
                .sum();
    }

}
