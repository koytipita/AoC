package day21;

import java.util.*;
import java.util.logging.Logger;

public class day21 {
    static Logger logger = Logger.getLogger(day21.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day21/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day21/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day21/example3.txt";
    static final String ACTUAL_FILE_PATH = "day21/input.txt";
    static List<String> map;
    static Set<Position> visitedPlots = new HashSet<>();
    static Integer sizeX;
    static Integer sizeY;
    private static final int OFFSET = 3;

    public static void main(String[] args) {
        map = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        sizeX = map.getFirst().length();
        sizeY = map.size();
        int stepLeft = 64;
        Position startPosition = getStartPosition(stepLeft);
        greedyPath(startPosition.getX(), startPosition.getY(), stepLeft);
        System.out.println(visitedPlots.size());


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
                boolean insideBoundaries23 =  x+moveX >= 0 && y+moveY < sizeY && y+moveY >= 0 && x+moveX < sizeX;
                if( insideBoundaries23 && (map.get(y).substring(x-1,x).contains("#"))){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, move.getStepLeft()-1));
                break;
            case 32: // (0, -1)
                boolean insideBoundaries32 = x+moveX >= 0 && y+moveY >= 0 && x+moveX < sizeX && y+moveY <sizeY;
                if( insideBoundaries32 &&  map.get(y-1).substring(x,x+1).contains("#")){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, move.getStepLeft()-1));
                break;
            case 34: // (0, 1)
                boolean insideBoundaries34 = x+moveX < sizeX && y+moveY >= 0 && x+moveX >=0 && y+moveY <sizeY;
                if(insideBoundaries34 && map.get(y+1).substring(x,x+1).contains("#")){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, move.getStepLeft()-1));
                break;
            case 43: // (1, 0)
                boolean insideBoundaries44 = x+moveX < sizeX && y+moveY < sizeY && y+moveY >= 0 && x+moveX >=0;
                if( insideBoundaries44 && map.get(y).substring(x+1,x+2).contains("#")){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, move.getStepLeft()-1));
                break;
            default:
                throw new IllegalStateException();
        }
        if (move.getStepLeft() -1 %2 == 0) {
            visitedPlots.addAll(stations);
        }
        return !stations.isEmpty();
    }

    public static boolean visitedPlotsContains(Position position){
        if (visitedPlots.contains(position)){
            for (Position position1 : visitedPlots) {
                if (position1.equals(position)) {
                    if (position1.getStepLeft() < position.getStepLeft()) {
                        visitedPlots.remove(position1);
                        visitedPlots.add(position);
                        return false;
                    }
                    return true;
                }
            }
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
