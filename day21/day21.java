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
        map = utils.FileParseUtil.readLinesFromFile(EXAMPLE1_FILE_PATH, logger);
        sizeX = map.getFirst().length();
        sizeY = map.size();
        int stepLeft = 6;
        Position startPosition = getStartPosition(stepLeft);
        greedyPath(startPosition.getX(), startPosition.getY(), stepLeft);
        System.out.println(visitedPlots.size());


    }

    public static int encode(int x, int y) {
        int shiftedX = x + OFFSET;
        int shiftedY = y + OFFSET;
        return shiftedX * 10 + shiftedY; // Combine the values uniquely
    }

    // Decode the int value back into two integers
    public static int[] decode(int encoded) {
        int shiftedX = encoded / 10;
        int shiftedY = encoded % 10;
        int x = shiftedX - OFFSET;
        int y = shiftedY - OFFSET;
        return new int[]{x, y};
    }

    public static boolean checkPathValid(int x, int y, Position move, Integer stepLeft){
        int moveX = move.getX();
        int moveY = move.getY();
        List<Position> stations = new ArrayList<>();
        int encoded = encode(moveX, moveY);

        switch (encoded) {
            case 13: // (-2, 0)
                boolean insideBoundaries13 = x+moveX >= 0 && y < sizeY && y >= 0;
                if(insideBoundaries13 && (map.get(y).substring(x+moveX,x).contains("#"))){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, stepLeft));
                break;
            case 24: // (-1, 1)
                boolean insideBoundaries24 =  x+moveX >= 0 && y+moveY < sizeY && y+moveY >= 0 && x+moveX < sizeX;
                if( insideBoundaries24 && !((!map.get(y).substring(x+moveX,x).contains("#")
                    && !map.get(y+moveY).substring(x+moveX,x).contains("#"))
                    || (!map.get(y+moveY).substring(x,x+1).contains("#")
                    && !map.get(y+moveY).substring(x+moveX,x).contains("#")))){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, stepLeft));
                break;
            case 22: // (-1, -1)
                boolean insideBoundaries22 = x+moveX >= 0 && y+moveY >= 0 && x+moveX < sizeX && y+moveY <sizeY;
                if( insideBoundaries22
                    && !((!map.get(y).substring(x+moveX,x).contains("#")
                    && !map.get(y+moveY).substring(x+moveX,x).contains("#"))
                    || (!map.get(y+moveY).substring(x,x+1).contains("#")
                    && !map.get(y+moveY).substring(x+moveX,x).contains("#")))){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, stepLeft));
                break;
            case 35: // (0, 2)
                boolean insideBoundaries35 = y+moveY < sizeY  && x < sizeX && x >= 0;
                if(insideBoundaries35 && !(!map.get(y+moveY-1).substring(x,x+1).contains("#")
                    && !map.get(y+moveY).substring(x,x+1).contains("#"))){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, stepLeft));
                break;
            case 31: // (0, -2)
                boolean insideBoundaries31 = y+moveY >= 0 && x < sizeX && x >= 0;
                if(insideBoundaries31
                    && !(!map.get(y+moveY+1).substring(x,x+1).contains("#")
                    && !map.get(y+moveY).substring(x,x+1).contains("#"))){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, stepLeft));
                break;
            case 42: // (1, -1)
                boolean insideBoundaries42 = x+moveX < sizeX && y+moveY >= 0 && x+moveX >=0 && y+moveY <sizeY;
                if(insideBoundaries42
                    && !((!map.get(y).substring(x+moveX,x+moveX+1).contains("#")
                    && !map.get(y+moveY).substring(x+moveX,x+moveX+1).contains("#"))
                    || (!map.get(y+moveY).substring(x,x+1).contains("#")
                    && !map.get(y+moveY).substring(x+moveX,x+moveX+1).contains("#")))){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, stepLeft));
                break;
            case 44: // (1, 1)
                boolean insideBoundaries44 = x+moveX < sizeX && y+moveY < sizeY && y+moveY >= 0 && x+moveX >=0;
                if( insideBoundaries44
                    && !((!map.get(y).substring(x+moveX,x+moveX+1).contains("#")
                    && !map.get(y+moveY).substring(x+moveX,x+moveX+1).contains("#"))
                    || (!map.get(y+moveY).substring(x,x+1).contains("#")
                    && !map.get(y+moveY).substring(x+moveX,x+moveX+1).contains("#")))){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, stepLeft));
                break;
            case 53: // (2, 0)
                boolean insideBoundaries53 = x+moveX < sizeX && y < sizeY && y >= 0 && x>=0;
                if(insideBoundaries53 && map.get(y).substring(x + 1, x + moveX + 1).contains("#")){
                    break;
                }
                stations.add(new Position(x+moveX,y+moveY, stepLeft));
                break;
            default:
                throw new IllegalStateException();
        }


        visitedPlots.addAll(stations);
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
        visitedPlots.add(new Position(x,y,stepLeft));
        if (stepLeft==0){
            return;
        }
        if (!visitedPlotsContains(new Position(x-2,y, stepLeft-2)) && checkPathValid(x, y, new Position(-2,0, stepLeft-2), stepLeft-2)) greedyPath(x-2,y,stepLeft-2);
        if (!visitedPlotsContains(new Position(x-1,y-1, stepLeft-2)) && checkPathValid(x, y, new Position(-1,-1, stepLeft-2), stepLeft-2)) greedyPath(x-1,y-1,stepLeft-2);
        if (!visitedPlotsContains(new Position(x-1,y+1, stepLeft-2)) && checkPathValid(x, y, new Position(-1,1, stepLeft-2), stepLeft-2)) greedyPath(x-1,y+1,stepLeft-2);
        if (!visitedPlotsContains(new Position(x,y-2, stepLeft-2)) && checkPathValid(x, y, new Position(0,-2, stepLeft-2), stepLeft-2)) greedyPath(x,y-2,stepLeft-2);
        if (!visitedPlotsContains(new Position(x,y+2, stepLeft-2)) && checkPathValid(x, y, new Position(0,2, stepLeft-2), stepLeft-2)) greedyPath(x,y+2,stepLeft-2);
        if (!visitedPlotsContains(new Position(x+1,y-1, stepLeft-2)) && checkPathValid(x, y, new Position(1,-1, stepLeft-2), stepLeft-2)) greedyPath(x+1,y-1,stepLeft-2);
        if (!visitedPlotsContains(new Position(x+1,y+1, stepLeft-2)) && checkPathValid(x, y, new Position(1,1, stepLeft-2), stepLeft-2)) greedyPath(x+1,y+1,stepLeft-2);
        if (!visitedPlotsContains(new Position(x+2,y, stepLeft-2)) && checkPathValid(x, y, new Position(2,0, stepLeft-2), stepLeft-2)) greedyPath(x+2,y,stepLeft-2);

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
