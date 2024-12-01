package aoc24.day9;

import aoc24.day8.day8;
import utils.MathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class day9 {
    static Logger logger = Logger.getLogger(day9.class.getName());
    static final String EXAMPLE_FILE_PATH = "day9/example.txt";
    static final String ACTUAL_FILE_PATH = "day9/input.txt";
    static int count = 0;

    public static boolean checkExtrapolatingOver(List<Integer> numbers){
        return numbers.stream().allMatch(a -> a == 0);
    }

    public static List<List<Integer>> computeDifferences(List<Integer> numbers){
        List<List<Integer>> allDifferencesList = new ArrayList<>();
        List<Integer> currNumbersList = numbers;
        while (!checkExtrapolatingOver(currNumbersList)){
            List<Integer> tempNumbersList = new ArrayList<>();
            allDifferencesList.add(currNumbersList);
            for (int i = 0; i < currNumbersList.size()-1; i++) {
                tempNumbersList.add(currNumbersList.get(i+1)- currNumbersList.get(i));
            }
            currNumbersList = tempNumbersList;

        }
        allDifferencesList.add(currNumbersList);
        return allDifferencesList;
    }

    public static void extrapolate(List<List<Integer>> numbers){
        Integer lowerRowNumbersLast = numbers.getLast().getLast();
        for (int i = numbers.size()-2 ; i >=0 ; i--) {  // check boundaries
            Integer upperRowNumbersLast = numbers.get(i).getLast();
            lowerRowNumbersLast = upperRowNumbersLast + lowerRowNumbersLast;
        }
        count += lowerRowNumbersLast;
    }

    public static void extrapolateBack(List<List<Integer>> numbers){
        Integer lowerRowNumbersFirst = numbers.getLast().getFirst();
        for (int i = numbers.size()-2 ; i >=0 ; i--) {  // check boundaries
            Integer upperRowNumbersFirst = numbers.get(i).getFirst();
            lowerRowNumbersFirst = upperRowNumbersFirst - lowerRowNumbersFirst;
        }
        count += lowerRowNumbersFirst;
    }



    public static void main(String[] args) {
        List<String> lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            List<Integer> numbers = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).boxed().toList();
            List<List<Integer>> allDifferences = computeDifferences(numbers);
            extrapolateBack(allDifferences);
        }
        System.out.println(count);


    }
}
