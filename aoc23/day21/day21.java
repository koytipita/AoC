package aoc23.day21;

import java.util.*;
import java.util.logging.Logger;

import static java.lang.Math.floor;
import static java.lang.Math.floorMod;

public class day21 {
    static Logger logger = Logger.getLogger(day21.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day21/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day21/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day21/example3.txt";
    static final String ACTUAL_FILE_PATH = "day21/input.txt";
    static List<String> map;
    static Map<Position, Position> visitedPlots = new HashMap<>();
    static Integer sizeX;
    static Integer sizeY;
    private static final int OFFSET = 3;

    public static void main(String[] args) {
        map = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        sizeX = map.getFirst().length();
        sizeY = map.size();
        List<Integer> xs = new ArrayList<>();
        int []stepLefts = new int[]{65,65+131,65+131*2}; //65,65+131,65+131*2 , 7,25,59
        for (int stepLeft : stepLefts){
            Position startPosition = getStartPosition(stepLeft);
            greedyPath(startPosition.getX(), startPosition.getY(), stepLeft);
            System.out.println(stepLeft + " " + visitedPlots.size());
            xs.add(visitedPlots.size());
            visitedPlots.clear();
        }
        System.out.println(quad(xs, (long) floor((26501365 -65) / 131.0)));
    }

    public static long quad(List<Integer> numbers , long n){
        long a = (long) floor((numbers.get(2) - (2L * numbers.get(1)) + numbers.get(0)) / 2.0);
        long b = numbers.get(1) - numbers.get(0) - a;
        long c = numbers.get(0);
        return (a * n * n) + (b * n) + c;
    }

    public static int encode(int x, int y) {
        int shiftedX = x + OFFSET;
        int shiftedY = y + OFFSET;
        return shiftedX * 10 + shiftedY; // Combine the values uniquely
    }

    public static boolean checkPathValid(int x, int y, Position move){
        int moveX = move.getX();
        int moveY = move.getY();
        List<Position> stations = new ArrayList<>();
        int encoded = encode(moveX, moveY);
        switch (encoded) {
            case 23: // (-1, 0)
                if( map.get(floorMod(y,sizeY)).charAt(floorMod(x-1,sizeX)) == '#'){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, move.getStepLeft()-1));
                break;
            case 32: // (0, -1)
                if(map.get(floorMod(y-1,sizeY)).charAt(floorMod(x,sizeX)) == '#'){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, move.getStepLeft()-1));
                break;
            case 34: // (0, 1)
                if(map.get(floorMod(y+1,sizeY)).charAt(floorMod(x,sizeX)) == '#'){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, move.getStepLeft()-1));
                break;
            case 43: // (1, 0)
                if(map.get(floorMod(y,sizeY)).charAt(floorMod(x+1,sizeX)) == '#'){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, move.getStepLeft()-1));
                break;
            default:
                throw new IllegalStateException();
        }
        if ( (move.getStepLeft() -1) %2 == 0) {
            for (Position station : stations) {
                visitedPlots.put(station, station);
            }
        }
        return !stations.isEmpty();
    }

    public static boolean visitedPlotsContains(Position position){
        if (visitedPlots.containsKey(position)) {
            Position existingPosition = visitedPlots.get(position);
            if (existingPosition.getStepLeft() < position.getStepLeft()) {
                visitedPlots.put(position, position);
                return false;
            }
            return true;
        }
        return false;
    }

    public static void greedyPath(int x, int y, int stepLeft){
        if (stepLeft==0){
            return;
        }
        if(!visitedPlotsContains(new Position(x-1,y, stepLeft-1))
            && checkPathValid(x, y, new Position(-1,0, stepLeft)))
            greedyPath(x-1,y,stepLeft-1);
        if(!visitedPlotsContains(new Position(x,y-1, stepLeft-1))
            && checkPathValid(x, y, new Position(0,-1, stepLeft)))
            greedyPath(x,y-1,stepLeft-1);
        if(!visitedPlotsContains(new Position(x,y+1, stepLeft-1))
            && checkPathValid(x, y, new Position(0,1, stepLeft)))
            greedyPath(x,y+1,stepLeft-1);
        if(!visitedPlotsContains(new Position(x+1,y, stepLeft-1))
            && checkPathValid(x, y, new Position(1,0, stepLeft)))
            greedyPath(x+1,y,stepLeft-1);
    }

    public static Position getStartPosition(Integer stepLeft){
        int x = map.stream()
            .filter(line -> line.contains("S"))
            .mapToInt(line -> line.indexOf("S")).findFirst().orElseThrow();
        int y = map.stream()
            .filter(line->line.contains("S"))
            .mapToInt(line -> map.indexOf(line)).findFirst().orElseThrow();
        return new Position(x,y, stepLeft);
    }
}
