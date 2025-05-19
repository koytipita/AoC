package aoc24.day10;

import utils.FileParseUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Day10 {
    private static Logger logger = Logger.getLogger(Day10.class.getName());
    private static final String EXAMPLE1_FILE_PATH = "aoc24/day10/example1.txt";
    private static final String EXAMPLE2_FILE_PATH = "aoc24/day10/example2.txt";
    private static final String EXAMPLE3_FILE_PATH = "aoc24/day10/example3.txt";
    private static final String EXAMPLE4_FILE_PATH = "aoc24/day10/example4.txt";
    private static final String ACTUAL_FILE_PATH = "aoc24/day10/input.txt";

    public static void main(String[] args) {
        Day10 day10 = new Day10();

        List<String> input = FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);

        logger.info(String.valueOf(day10.solution(input)));

    }

    private long solution(List<String> input) {
        int [][] topographicMap = getTopographicMap(input);

        List<int[]> zeroLocations = getZeroLocations(topographicMap);

        long score = 0L;

        for (int[] zeroLocation : zeroLocations) {
            Set<int[]> checkingLocations = new HashSet<>();
            checkingLocations.add(zeroLocation);
            for (int i = 0; i < 9; i++) {
                Set<int[]> newCheckingLocations = new HashSet<>();
                for (int[] checkingLocation : checkingLocations) {
                    newCheckingLocations.addAll(getFourNeighborsOfNumber(checkingLocation, topographicMap));
                }
                checkingLocations = newCheckingLocations;
            }
            score += checkingLocations.stream()
                    .map(ints -> Objects.hash(ints[0], ints[1], ints[2]))
                    .distinct()
                    .count();
        }

        return score;
    }

    private List<int[]> getZeroLocations(int[][] matrix) {
        return IntStream.range(0, matrix.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, matrix[i].length)
                        .filter(j -> matrix[i][j] == 0)
                        .mapToObj(j -> new int[]{0, j, i}))
                .toList();
    }


    private List<int[]> getFourNeighborsOfNumber(int[] numberLocation, int[][] matrix){
        List<int[]> numberLocations = new ArrayList<>();
        if(checkLocation(new int[]{numberLocation[0] + 1, numberLocation[1], numberLocation[2] - 1}, matrix)){
            numberLocations.add(new int[]{numberLocation[0] + 1, numberLocation[1], numberLocation[2] - 1});
        }
        if(checkLocation( new int[]{numberLocation[0] + 1, numberLocation[1], numberLocation[2] + 1}, matrix)){
            numberLocations.add(new int[]{numberLocation[0] + 1, numberLocation[1], numberLocation[2] + 1});
        }
        if(checkLocation(new int[]{numberLocation[0] + 1, numberLocation[1] - 1, numberLocation[2]}, matrix)){
            numberLocations.add(new int[]{numberLocation[0] + 1, numberLocation[1] - 1, numberLocation[2]});
        }
        if(checkLocation(new int[]{numberLocation[0] + 1, numberLocation[1] + 1, numberLocation[2]}, matrix)){
            numberLocations.add(new int[]{numberLocation[0] + 1, numberLocation[1] + 1, numberLocation[2]});
        }
        return numberLocations;
    }

    private boolean checkLocation(int[] numberLocations, int[][] matrix) {
        if (numberLocations[2] < 0 || numberLocations[2] >= matrix.length || numberLocations[1] < 0 || numberLocations[1] >= matrix[0].length)
            return false;
        return matrix[numberLocations[2]][numberLocations[1]] == numberLocations[0];
    }

    private int [][] getTopographicMap(List<String> input) {
        int height = input.size();
        return input.stream()
                .map(str->str.chars()
                        .map(i-> i - 48).
                        toArray())
                .toArray(size -> new int[height][]);
    }


}
