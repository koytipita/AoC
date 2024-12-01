package aoc23.day18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class day18part2 {
    static Logger logger = Logger.getLogger(day18part2.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day18/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day18/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day18/example3.txt";
    static final String ACTUAL_FILE_PATH = "day18/input.txt";
    static List<String> lines;
    static List<Position> edges = new ArrayList<>();
    static String direction;

    public static Position convertPlanToPosition(String line){
        List<String> command = new ArrayList<>(Arrays.asList(line.split(" ")));
        Position digPosition = new Position(0L,0L, direction);
        String hexValue = command.getLast().substring(2,command.getLast().length()-2);
        int value = utils.MathUtil.hexadecimalToDecimal(hexValue);
        String directionNum = command.getLast().substring(command.getLast().length()-2,command.getLast().length()-1);
        digPosition.addValue(value,directionNum);
        return digPosition;
    }

    public static Position convertPlanToPositionPart1(String line){
        List<String> command = new ArrayList<>(Arrays.asList(line.split(" ")));
        Position digPosition = new Position(0L,0L, direction);
        int value = Integer.parseInt(command.get(1));
        switch (command.getFirst()){
            case "R" -> direction = "0";
            case "D" -> direction = "1";
            case "L" -> direction = "2";
            case "U" -> direction = "3";
            default -> throw new IllegalStateException("Unexpected value: " + command.getFirst());
        }
        digPosition.addValue(value,direction);
        return digPosition;
    }

    public static void insertEdges(List<Position> relativePositionList){
        Position currentPosition = new Position(0L,0L, direction);
        edges.add(currentPosition);
        for (Position relativePosition : relativePositionList) {
            //direction = currentPosition.getDirection();
            currentPosition = currentPosition.addRelativePosition(relativePosition);
            edges.add(currentPosition);
        }
    }

    public static long calculatePoints(){
        int n = edges.size();


        double sum1 = IntStream.range(0, n-1)
                .mapToDouble(i -> edges.get(i).getX() * edges.get(i + 1).getY())
                .sum();

        double sum2 = IntStream.range(0, n-1)
                .mapToDouble(i -> edges.get(i).getY() * edges.get(i + 1).getX())
                .sum();
        long boundaryPoints = IntStream.range(0,n-1)
                .mapToLong(i -> max(abs(edges.get(i).getX()-edges.get(i+1).getX()),abs(edges.get(i).getY()-edges.get(i+1).getY())))
                .sum();

        double area = Math.abs((sum1 - sum2) / 2.0);
        return (long) (area - (boundaryPoints / 2.0) + 1) + boundaryPoints;
    }
    

    public static void main(String[] args) {
        lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        List<Position> relativePositionsList = lines.stream().map(day18part2::convertPlanToPosition).toList();
        insertEdges(relativePositionsList);
        System.out.println(calculatePoints());
    }
}
