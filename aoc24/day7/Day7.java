package aoc24.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Day7 {
    private static Logger logger = Logger.getLogger(Day7.class.getName());
    private static final String EXAMPLE1_FILE_PATH = "aoc24/day7/example1.txt";
    private static final String EXAMPLE2_FILE_PATH = "aoc24/day7/example2.txt";
    private static final String EXAMPLE3_FILE_PATH = "aoc24/day7/example3.txt";
    private static final String ACTUAL_FILE_PATH = "aoc24/day7/input.txt";

    public static void main(String[] args) {
        Day7 day7 = new Day7();

        List<String> input = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);

        logger.info(() -> String.valueOf(day7.getCalibrationResultPart1(input)));
        logger.info(() -> String.valueOf(day7.getCalibrationResultPart2(input)));
    }

    private long getCalibrationResultPart1(List<String> input){
        return input.stream()
                .filter(this::checkLineCalibratedPart1)
                .mapToLong(line -> Long.parseLong(line.split(":")[0]))
                .sum();
    }

    private long getCalibrationResultPart2(List<String> input){
        return input.stream()
                .filter(this::checkLineCalibratedPart2)
                .mapToLong(line -> Long.parseLong(line.split(":")[0]))
                .sum();
    }

    private boolean checkLineCalibratedPart1(String line){
        long testValue = Long.parseLong(line.split(":")[0]);
        List<Long> vals = Arrays.stream(line.split(":")[1].split(" "))
                .skip(1)
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();

        List<Long> allPossibleValues = new ArrayList<>();
        allPossibleValues.add(vals.getFirst());
        for (int i = 1; i < vals.size(); i++) {
            int finalI = i;
            List<Long> temporaryValuesMultiplicated = allPossibleValues.stream()
                    .map(val -> val * vals.get(finalI))
                    .toList();
            List<Long> temporaryValuesSummed = allPossibleValues.stream()
                    .map(val -> val + vals.get(finalI))
                    .toList();
            allPossibleValues.clear();
            allPossibleValues.addAll(temporaryValuesSummed);
            allPossibleValues.addAll(temporaryValuesMultiplicated);
        }
        return allPossibleValues.stream().anyMatch(val -> val.equals(testValue));
    }

    private boolean checkLineCalibratedPart2(String line){
        long testValue = Long.parseLong(line.split(":")[0]);
        List<Long> vals = Arrays.stream(line.split(":")[1].split(" "))
                .skip(1)
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();

        List<Long> allPossibleValues = new ArrayList<>();
        allPossibleValues.add(vals.getFirst());
        for (int i = 1; i < vals.size(); i++) {
            int finalI = i;
            List<Long> temporaryValuesMultiplied = allPossibleValues.stream()
                    .map(val -> val * vals.get(finalI))
                    .toList();
            List<Long> temporaryValuesSummed = allPossibleValues.stream()
                    .map(val -> val + vals.get(finalI))
                    .toList();
            List<Long> temporaryValuesConcatenated = allPossibleValues.stream()
                    .map(val -> (long) (val * Math.pow(10, String.valueOf(vals.get(finalI)).length()) + vals.get(finalI)))
                    .toList();
            allPossibleValues.clear();
            allPossibleValues.addAll(temporaryValuesSummed);
            allPossibleValues.addAll(temporaryValuesMultiplied);
            allPossibleValues.addAll(temporaryValuesConcatenated);
        }
        return allPossibleValues.stream().anyMatch(val -> val.equals(testValue));
    }

}
