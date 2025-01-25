package aoc24.day8;


import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Day8 {
    private static Logger logger = Logger.getLogger(Day8.class.getName());
    private static final String EXAMPLE1_FILE_PATH = "aoc24/day8/example1.txt";
    private static final String EXAMPLE2_FILE_PATH = "aoc24/day8/example2.txt";
    private static final String EXAMPLE3_FILE_PATH = "aoc24/day8/example3.txt";
    private static final String EXAMPLE4_FILE_PATH = "aoc24/day8/example4.txt";
    private static final String ACTUAL_FILE_PATH = "aoc24/day8/input.txt";

    private enum PointType {
        IDLE,
        ANTENNA,
        ANTINODE
    }

    private class Point {
        private char pointName = '.';
        private PointType pointType;

        public Point(PointType pointType, char pointName) {
            this.pointType = pointType;
            this.pointName = pointName;
        }

        public PointType getPointType() {
            return pointType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return pointName == point.pointName && pointType == point.pointType;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pointName, pointType);
        }
    }

    public static void main(String[] args) {
        Day8 day8 = new Day8();

        part1(day8);
    }

    private static void part1(Day8 day8) {
        List<String> input = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH , logger);
        int verticalSize = input.size();
        int horizontalSize = input.getFirst().length();
        Map<Integer, Point> pointToAntennaMap = new HashMap<>();
        Map<Point, List<Integer>> antennaToPointsMap = new HashMap<>();
        day8.createAntennaMaps(input, pointToAntennaMap, antennaToPointsMap, verticalSize, horizontalSize);
        System.out.println(day8.countAntiNodes(pointToAntennaMap, antennaToPointsMap,
                verticalSize, horizontalSize));
    }

    private void createAntennaMaps (List<String> input,
                                    Map<Integer, Point> pointToAntennaMap,
                                    Map<Point, List<Integer>> antennaToPointsMap,
                                    int verticalSize,
                                    int horizontalSize) {
        IntStream.range(0, verticalSize)
                .forEach(y -> IntStream.range(0, horizontalSize)
                        .forEach(x -> {
                            int index = y * verticalSize + x;
                            char pointName = input.get(y).charAt(x);
                            PointType pointType = (pointName == '.') ? PointType.IDLE : PointType.ANTENNA;
                            Point point = new Point(pointType, pointName);
                            pointToAntennaMap.put(index, point);
                            if (pointType.equals(PointType.ANTENNA)) {
                                List<Integer> points = antennaToPointsMap.getOrDefault(point, new ArrayList<>());
                                points.add(index);
                                antennaToPointsMap.put(point, points);
                            }
                        }));
    }

    private long countAntiNodes( Map<Integer, Point> pointToAntennaMap,
                                 Map<Point, List<Integer>> antennaToPointsMap,
                                 int verticalSize,
                                 int horizontalSize) {

        return antennaToPointsMap.entrySet().stream()
                .mapToLong(pointListEntry -> {
                    List<Integer> indexes = pointListEntry.getValue();
                    long count = 0;
                    int indexesSize = indexes.size();
                    for (int i = 0; i < indexesSize; i++) {
                        for (int j = 0; j < indexesSize; j++) {
                            if (i==j) continue;
                            Point point = createAntiNode(indexes.get(i), indexes.get(j),
                                    verticalSize, horizontalSize, pointToAntennaMap);
                            if (point != null) {
                                count++;
                            }
                        }
                    }
                    return count;
                }).sum();
    }

    private Point createAntiNode (int indexFirst,
                                  int indexMirror,
                                  int verticalSize, int horizontalSize, Map<Integer, Point> pointToAntennaMap) {
        int xFirst = indexFirst % verticalSize;
        int yFirst = indexFirst / verticalSize;

        int xMirror = indexMirror % verticalSize;
        int yMirror = indexMirror / verticalSize;

        int xCandidate = xMirror + ( xMirror - xFirst);
        int yCandidate = yMirror + ( yMirror - yFirst);

        int indexCandidate = yCandidate * verticalSize + xCandidate;

        boolean isCandidateInsideBoundaries = (xCandidate >= 0) && (xCandidate < horizontalSize)  &&
                (yCandidate >= 0) && (yCandidate < verticalSize);

        if (!isCandidateInsideBoundaries) {
            return null;
        }

        Point point = pointToAntennaMap.get(indexCandidate);

        if (point.getPointType().equals(PointType.ANTINODE)) {
            return null;
        }

        Point antiNodePoint = new Point(PointType.ANTINODE, '#');
        pointToAntennaMap.put(indexCandidate, antiNodePoint);
        return antiNodePoint;
    }
}
