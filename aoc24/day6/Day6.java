package aoc24.day6;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Day6 {
    private static Logger logger = Logger.getLogger(Day6.class.getName());
    private static final String EXAMPLE1_FILE_PATH = "aoc24/day6/example1.txt";
    private static final String EXAMPLE2_FILE_PATH = "aoc24/day6/example2.txt";
    private static final String EXAMPLE3_FILE_PATH = "aoc24/day6/example3.txt";
    private static final String ACTUAL_FILE_PATH = "aoc24/day6/input.txt";
    private static int[] point;

    public static void main(String[] args) {
        Day6 day6 = new Day6();

        List<String> input = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);

        logger.info(() -> String.valueOf(day6.countTravelledDistinctPositions(input)));
    }

    private String intArrToString(int[] arr){
        return String.format("%s:%s",arr[0], arr[1]);
    }

    private long countTravelledDistinctPositions(List<String> input) {
        Map<Integer, List<Integer>> horizontalObstacleMap = new HashMap<>();
        Map<Integer, List<Integer>> verticalObstacleMap = new HashMap<>();

        point = getGuardStartLocation(input);
        initializeMaps(horizontalObstacleMap,verticalObstacleMap,input);
        Set<String> travelledPoints = new LinkedHashSet<>(Collections.singleton(intArrToString(point)));

        while (true){
            if (travelNorth(verticalObstacleMap, travelledPoints)) break;
            if (travelEast(horizontalObstacleMap, travelledPoints, input.getFirst().length())) break;
            if (travelSouth(verticalObstacleMap, travelledPoints, input.size())) break;
            if (travelWest(horizontalObstacleMap, travelledPoints)) break;
        }

        return travelledPoints.size();
    }

    private boolean travelNorth(Map<Integer, List<Integer>> verticalObstacleMap,
                                Set<String> travelledPoints){
        List<Integer> obstaclesList = verticalObstacleMap.get(point[0]);

        int[] startPoint = point;
        int pointStopped = obstaclesList.stream()
                .filter(ind -> ind < startPoint[1])
                .max(Comparator.naturalOrder())
                .orElse(-1);

        if (pointStopped != -1){
            List<String> notTravelledPoints = IntStream.range(pointStopped+1, startPoint[1])
                    .mapToObj(y -> new int[]{startPoint[0], y})
                    .map(this::intArrToString)
                    .filter(currPoint -> !travelledPoints.contains(currPoint))
                    .toList();
            travelledPoints.addAll(notTravelledPoints);
            point = new int[]{startPoint[0], pointStopped+1};
            return false;
        }

        List<String> notTravelledPoints = IntStream.range(0, startPoint[1])
                .mapToObj(y -> new int[]{startPoint[0], y})
                .map(this::intArrToString)
                .filter(currPoint -> !travelledPoints.contains(currPoint))
                .toList();
        travelledPoints.addAll(notTravelledPoints);
        return true;
    }

    private boolean travelEast(Map<Integer, List<Integer>> horizontalObstacleMap,
                                Set<String> travelledPoints,
                               int length){
        List<Integer> obstaclesList = horizontalObstacleMap.get(point[1]);

        int[] startPoint = point;
        int pointStopped = obstaclesList.stream()
                .filter(ind -> ind > startPoint[0])
                .findFirst().orElse(-1);

        if (pointStopped != -1){
            List<String> notTravelledPoints = IntStream.range(startPoint[0], pointStopped)
                    .mapToObj(x -> new int[]{x, startPoint[1]})
                    .map(this::intArrToString)
                    .filter(currPoint -> !travelledPoints.contains(currPoint))
                    .toList();
            travelledPoints.addAll(notTravelledPoints);
            point = new int[]{pointStopped-1, startPoint[1]};
            return false;
        }

        List<String> notTravelledPoints = IntStream.range(startPoint[0], length)
                .mapToObj(x -> new int[]{x, startPoint[1]})
                .map(this::intArrToString)
                .filter(currPoint -> !travelledPoints.contains(currPoint))
                .toList();
        travelledPoints.addAll(notTravelledPoints);
        return true;
    }

    private boolean travelSouth(Map<Integer, List<Integer>> verticalObstacleMap,
                                Set<String> travelledPoints,
                                int length){
        List<Integer> obstaclesList = verticalObstacleMap.get(point[0]);

        int[] startPoint = point;
        int pointStopped = obstaclesList.stream()
                .filter(ind -> ind > startPoint[1])
                .findFirst().orElse(-1);

        if (pointStopped != -1){
            List<String> notTravelledPoints = IntStream.range(startPoint[1], pointStopped)
                    .mapToObj(y -> new int[]{startPoint[0], y})
                    .map(this::intArrToString)
                    .filter(currPoint -> !travelledPoints.contains(currPoint))
                    .toList();
            travelledPoints.addAll(notTravelledPoints);
            point = new int[]{startPoint[0], pointStopped-1};
            return false;
        }

        List<String> notTravelledPoints = IntStream.range(startPoint[1], length)
                .mapToObj(y -> new int[]{startPoint[0], y})
                .map(this::intArrToString)
                .filter(currPoint -> !travelledPoints.contains(currPoint))
                .toList();
        travelledPoints.addAll(notTravelledPoints);
        return true;
    }

    private boolean travelWest(Map<Integer, List<Integer>> horizontalObstacleMap,
                               Set<String> travelledPoints){
        List<Integer> obstaclesList = horizontalObstacleMap.get(point[1]);

        int[] startPoint = point;
        int pointStopped = obstaclesList.stream()
                .filter(ind -> ind < startPoint[0])
                .max(Comparator.naturalOrder())
                .orElse(-1);

        if (pointStopped != -1){
            List<String> notTravelledPoints = IntStream.range(pointStopped+1, startPoint[0])
                    .mapToObj(x -> new int[]{x, startPoint[1]})
                    .map(this::intArrToString)
                    .filter(currPoint -> !travelledPoints.contains(currPoint))
                    .toList();
            travelledPoints.addAll(notTravelledPoints);
            point = new int[]{pointStopped+1, startPoint[1]};
            return false;
        }

        List<String> notTravelledPoints = IntStream.range(0, startPoint[0])
                .mapToObj(x -> new int[]{x, startPoint[1]})
                .map(this::intArrToString)
                .filter(currPoint -> !travelledPoints.contains(currPoint))
                .toList();
        travelledPoints.addAll(notTravelledPoints);
        return true;
    }

    private static int[] getGuardStartLocation(List<String> input){
        point = new int[]{-1,-1};
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.getFirst().length(); j++) {
                if (input.get(j).charAt(i) == '^'){
                    point[0] = i;
                    point[1] = j;
                    return point;
                }
            }
        }
        return point;
    }

    private void initializeMaps(Map<Integer, List<Integer>> horizontalObstacleMap, Map<Integer, List<Integer>> verticalObstacleMap, List<String> input) {
        IntStream.range(0, input.size())
                .forEach(j -> IntStream.range(0, input.getFirst().length())
                        .forEach(i -> {
                            if (input.get(j).charAt(i) == '#'){
                                horizontalObstacleMap.putIfAbsent(j, new ArrayList<>());
                                horizontalObstacleMap.get(j).add(i);
                                verticalObstacleMap.putIfAbsent(i, new ArrayList<>());
                                verticalObstacleMap.get(i).add(j);
                            }
                        }));
    }

}
