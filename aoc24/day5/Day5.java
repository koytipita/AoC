package aoc24.day5;

import java.util.*;
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
        List<int[]> rules = input.subList(0, input.indexOf("")).stream()
                .map(line -> new int[]{Integer.parseInt(line.split("\\|")[0]),
                        Integer.parseInt(line.split("\\|")[1])}
                ).toList();
        List<List<Integer>> updates = input.subList(input.indexOf("") + 1, input.size()).stream()
                .map(line -> Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).boxed().toList()
                ).toList();
        logger.info(() -> String.valueOf(day5.sumOfMiddleNumbersOfCorrectlyOrderedUpdates(rules, updates)));
        logger.info(() -> String.valueOf(day5.sumOfMiddleNumbersOfIncorrectlyOrderedUpdates(rules, updates)));
    }

    private long sumOfMiddleNumbersOfIncorrectlyOrderedUpdates(List<int[]> rules, List<List<Integer>> updates) {
        long sum = 0;

        List<List<Integer>> incorrectlyOrderedUpdates =
                updates.stream()
                        .filter(update -> !isCorrectlyOrdered(rules, update))
                        .toList();

        Map<Integer, List<Integer>> ruleNumbersViolationGraph = getRuleNumbersViolationGraph(rules);

        for (List<Integer> update: incorrectlyOrderedUpdates){
            while (true){
                update = swapAndGetNewUpdate(update, ruleNumbersViolationGraph);
                if (checkOrdered(update, ruleNumbersViolationGraph)){
                    sum += update.get(update.size()/2);
                    break;
                }
            }
        }

        return sum;
    }

    private Map<Integer, List<Integer>> getRuleNumbersViolationGraph(List<int[]> rules){
        Map<Integer, List<Integer>> ruleNumbersViolationGraph = new HashMap<>();

        rules.forEach(rule -> {
                    ruleNumbersViolationGraph.putIfAbsent(rule[1], new ArrayList<>());
                    ruleNumbersViolationGraph.get(rule[1]).add(rule[0]);
                });

        return ruleNumbersViolationGraph;
    }

    private List<Integer> swapAndGetNewUpdate(List<Integer> update, Map<Integer, List<Integer>> ruleNumbersViolationGraph){
        List<Integer> newUpdate = new ArrayList<>(update);
        for (int i = 0; i < update.size(); i++) {
            for (int j = i+1; j < update.size(); j++) {
                if (ruleNumbersViolationGraph.getOrDefault(update.get(i), new ArrayList<>()).contains(update.get(j))){
                    Collections.swap(newUpdate, i, j);
                }
            }
        }
        return newUpdate;
    }

    private boolean checkOrdered(List<Integer> update, Map<Integer, List<Integer>> ruleNumbersViolationGraph){
        for (int i = 0; i < update.size(); i++) {
            for (int j = i+1; j < update.size(); j++) {
                if (ruleNumbersViolationGraph.getOrDefault(update.get(i), new ArrayList<>()).contains(update.get(j))){
                    return false;
                }
            }
        }
        return true;
    }

    private long sumOfMiddleNumbersOfCorrectlyOrderedUpdates(List<int[]> rules,
                                                             List<List<Integer>> updates){
        return updates.stream()
                .filter(update -> isCorrectlyOrdered(rules, update))
                .mapToLong(update -> update.get(update.size()/2))
                .sum();
    }

    private boolean isCorrectlyOrdered(List<int[]> rules, List<Integer> update) {
        return rules.stream().noneMatch(rule -> isNotCompliantWithRule(rule, update));
    }

    private boolean isNotCompliantWithRule(int[] rule, List<Integer> update){
        if (update.contains(rule[0]) && update.contains(rule[1])){
            return update.indexOf(rule[0]) > update.indexOf(rule[1]);
        }
        return false;
    }
}
