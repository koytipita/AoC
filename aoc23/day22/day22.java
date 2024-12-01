package aoc23.day22;


import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class day22 {
    static Logger logger = Logger.getLogger(day22.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day22/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day22/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day22/example3.txt";
    static final String ACTUAL_FILE_PATH = "day22/input.txt";
    static List<String> lines;
    static List<Brick> bricks = new ArrayList<>();
    static List<Brick> bricksFallen = new ArrayList<>();
    static Set<Brick> bricksSupporting = new HashSet<>();
    static long fallingBrickCount = 0L;

    public static void main(String[] args) {
        lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH,logger);
        parseBricks();
        bricks.forEach(day22::brickFall);
        System.out.println(bricks.size()-bricksSupporting.size());
        Set<Brick> copy = new HashSet<>(bricksSupporting);
        for (Brick brick: copy ) {
            countFallingBricks(brick);
        }
        System.out.println(fallingBrickCount);

    }

    public static void countFallingBricks(Brick brick){
        List<Brick> alternativeBricks = new ArrayList<>();
        for (Brick alternativeBrick : bricksFallen) {
            alternativeBricks.add(alternativeBrick.copy());
        }
        alternativeBricks.remove(brick);
        alternativeBricks.forEach(alternativeBrick -> brickFallSimulation(alternativeBrick,  new ArrayList<>(alternativeBricks)));
    }

    public static void brickFallSimulation(Brick brick, List<Brick> alternativeBricks){
        if (canFallSimulation(brick, alternativeBricks)) fallingBrickCount +=1;
        while (canFallSimulation(brick, alternativeBricks)) {
            int[] firstPoint = brick.getFirstPoint();
            int[] secondPoint = brick.getSecondPoint();
            firstPoint[2] = firstPoint[2] - 1;
            secondPoint[2] = secondPoint[2] - 1;
            brick.setFirstPoint(firstPoint);
            brick.setSecondPoint(secondPoint);
        }
        alternativeBricks.add(brick.copy());
    }

    public static boolean canFallSimulation(Brick brick1,List<Brick> alternativeBricks){
        if (brick1.getFirstPoint()[2] == 1) return false;
        return alternativeBricks.stream()
            .filter(brick2 -> brick2.getSecondPoint()[2] == brick1.getFirstPoint()[2]-1)
            .noneMatch(brick2 ->{
                int brick2FirstX = brick2.getFirstPoint()[0];
                int brick2SecondX = brick2.getSecondPoint()[0];
                int brick2FirstY = brick2.getFirstPoint()[1];
                int brick2SecondY = brick2.getSecondPoint()[1];
                int brick1FirstX = brick1.getFirstPoint()[0];
                int brick1SecondX = brick1.getSecondPoint()[0];
                int brick1FirstY = brick1.getFirstPoint()[1];
                int brick1SecondY = brick1.getSecondPoint()[1];
                boolean horizontalOverlap = brick1FirstX <= brick2SecondX && brick1SecondX >= brick2FirstX;
                boolean verticalOverlap = brick1FirstY <= brick2SecondY && brick1SecondY >= brick2FirstY;
                return horizontalOverlap && verticalOverlap;
            });

    }



    public static boolean canFall(Brick brick1){
        if (brick1.getFirstPoint()[2] == 1) return false;

        bricksFallen.stream()
            .filter(brick2 -> brick2.getSecondPoint()[2] == brick1.getFirstPoint()[2] - 1)
            .filter(brick2 -> {
                int brick2FirstX = brick2.getFirstPoint()[0];
                int brick2SecondX = brick2.getSecondPoint()[0];
                int brick2FirstY = brick2.getFirstPoint()[1];
                int brick2SecondY = brick2.getSecondPoint()[1];
                int brick1FirstX = brick1.getFirstPoint()[0];
                int brick1SecondX = brick1.getSecondPoint()[0];
                int brick1FirstY = brick1.getFirstPoint()[1];
                int brick1SecondY = brick1.getSecondPoint()[1];
                boolean horizontalOverlap = brick1FirstX <= brick2SecondX && brick1SecondX >= brick2FirstX;
                boolean verticalOverlap = brick1FirstY <= brick2SecondY && brick1SecondY >= brick2FirstY;
                return horizontalOverlap && verticalOverlap;
            })
            .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                if (list.size() == 1) {
                    bricksSupporting.addAll(list);
                }
                return list;
            }));

        return bricksFallen.stream()
            .filter(brick2 -> brick2.getSecondPoint()[2] == brick1.getFirstPoint()[2]-1)
            .noneMatch(brick2 ->{
                int brick2FirstX = brick2.getFirstPoint()[0];
                int brick2SecondX = brick2.getSecondPoint()[0];
                int brick2FirstY = brick2.getFirstPoint()[1];
                int brick2SecondY = brick2.getSecondPoint()[1];
                int brick1FirstX = brick1.getFirstPoint()[0];
                int brick1SecondX = brick1.getSecondPoint()[0];
                int brick1FirstY = brick1.getFirstPoint()[1];
                int brick1SecondY = brick1.getSecondPoint()[1];
                boolean horizontalOverlap = brick1FirstX <= brick2SecondX && brick1SecondX >= brick2FirstX;
                boolean verticalOverlap = brick1FirstY <= brick2SecondY && brick1SecondY >= brick2FirstY;
                return horizontalOverlap && verticalOverlap;
            });

    }

    public static void brickFall(Brick brick){
        while(canFall(brick)){
            int []firstPoint = brick.getFirstPoint();
            int []secondPoint = brick.getSecondPoint();
            firstPoint[2] = firstPoint[2]-1;
            secondPoint[2] = secondPoint[2]-1;
            brick.setFirstPoint(firstPoint);
            brick.setSecondPoint(secondPoint);
        }
        bricksFallen.add(brick);
    }

    public static void parseBricks(){
        lines.forEach(line -> {
            int []firstPoint = Arrays.stream(line.split("~")[0].split(",")).mapToInt(Integer::parseInt).toArray();
            int []secondPoint = Arrays.stream(line.split("~")[1].split(",")).mapToInt(Integer::parseInt).toArray();
            bricks.add(new Brick(firstPoint,secondPoint));
        });
        bricks = bricks.stream().sorted((brick1, brick2) -> {
            Integer firstZ = brick1.getSecondPoint()[2];
            Integer secondZ = brick2.getSecondPoint()[2];
            return firstZ.compareTo(secondZ);
        }).toList();
    }
}
