package aoc23.day17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class day17_dp_trial {
    static Logger logger = Logger.getLogger(day17_dp_trial.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day17/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day17/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day17/example3.txt";
    static final String ACTUAL_FILE_PATH = "day17/input.txt";
    static List<String> map = new ArrayList<>();

    static Map<Position, Integer> memoryDp = new HashMap<>();

    public static Unit getLeft(Integer x, Integer y,Integer leftCount){
        if (y.equals(0) || leftCount.equals(1)){
            return null;
        }
        Integer value = Integer.valueOf(map.get(y-1).substring(x,x+1));
        return new Unit(Direction.LEFT,value,new Position(x,y-1));
    }

    public static Unit getRight(Integer x, Integer y,Integer rightCount){
        if (y.equals(map.size()-1) || rightCount.equals(3)){
            return null;
        }
        Integer value = Integer.valueOf(map.get(y+1).substring(x,x+1));
        return new Unit(Direction.RIGHT,value,new Position(x,y+1));
    }

    public static Unit getStraight(Integer x, Integer y, Integer straightCount){
        if (x.equals(map.getFirst().length()-1) || straightCount.equals(3)){
            return null;
        }
        Integer value = Integer.valueOf(map.get(y).substring(x+1,x+2));
        return new Unit(Direction.STRAIGHT,value,new Position(x+1,y));
    }

    public static Unit getBack(Integer x, Integer y, Integer backCount){
        if (x.equals(0) || backCount.equals(1)){
            return null;
        }
        Integer value = Integer.valueOf(map.get(y).substring(x-1,x));
        return new Unit(Direction.BACK,value,new Position(x-1,y));
    }

    public static Integer findMin(Integer heatLossLeft,Integer heatLossStraight, Integer heatLossRight, Integer heatLossBack){
        return Math.min(Math.min(heatLossLeft,heatLossStraight),Math.min(heatLossRight,heatLossBack));
    }

    public static Integer calculateMinHeatLoss(Integer x, Integer y,Integer leftCount, Integer rightCount,Integer straightCount,Integer backCount){
        Integer currentUnitHeatLoss = Integer.valueOf(map.get(y).substring(x,x+1));
        Position position = new Position(x,y);
        if(leftCount.equals(3) || rightCount.equals(3) || straightCount.equals(3) || backCount.equals(3)){
            return Integer.MAX_VALUE -10000;
        }
        if(x.equals(map.getFirst().length()-1) && y.equals(map.size()-1)){
            return currentUnitHeatLoss;
        }
        if(x.equals(map.getFirst().length()-2) && y.equals(map.size()-1) && !straightCount.equals(3)){
            return currentUnitHeatLoss + Integer.parseInt(map.get(y).substring(x+1,x+2));
        }
        if(x.equals(map.getFirst().length()-1) && y.equals(map.size()-2) && !rightCount.equals(3)){
            return currentUnitHeatLoss + Integer.parseInt(map.get(y+1).substring(x,x+1));
        }
        if (memoryDp.get(position) != null){
            return memoryDp.get(position);
        }
        int heatLossLeft = Integer.MAX_VALUE;
        int heatLossRight = Integer.MAX_VALUE;
        int heatLossStraight = Integer.MAX_VALUE;
        int heatLossBack = Integer.MAX_VALUE;
        Unit straightUnit = getStraight(x,y,straightCount);
        if (straightUnit != null && backCount.equals(0)){
            heatLossStraight = currentUnitHeatLoss + calculateMinHeatLoss(x+1,y,0,0,straightCount+1, 0);
        }
        else{
            Unit backUnit = getBack(x,y,straightCount);
            if (backUnit != null && straightCount.equals(0)){
                heatLossBack = currentUnitHeatLoss + calculateMinHeatLoss(x-1,y,0,0,0, backCount+1);
            }
        }
        Unit rightUnit = getRight(x,y,rightCount);
        if (rightUnit != null && leftCount.equals(0)){
            heatLossRight = currentUnitHeatLoss + calculateMinHeatLoss(x,y+1,0,rightCount+1,0, 0);
        }
        else {
            Unit leftUnit = getLeft(x, y, leftCount);
            if (leftUnit != null && rightCount.equals(0)) {
                heatLossLeft = currentUnitHeatLoss + calculateMinHeatLoss(x, y - 1, leftCount + 1, 0, 0, 0);
            }
        }
        Integer minHeatLoss = findMin(heatLossLeft,heatLossStraight,heatLossRight,heatLossBack);
        if (minHeatLoss.equals(heatLossBack)){
            memoryDp.put(position,heatLossBack);
        }
        if (minHeatLoss.equals(heatLossLeft)){
            memoryDp.put(position,heatLossLeft);
        }
        if (minHeatLoss.equals(heatLossRight)){
            memoryDp.put(position,heatLossRight);
        }
        if (minHeatLoss.equals(heatLossStraight)){
            memoryDp.put(position,heatLossStraight);
        }
        return minHeatLoss;
    }

    public static void main(String[] args) {
        map = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        Integer minHeatLoss = calculateMinHeatLoss(0,0,0,0,0, 0);
        for (Position name: memoryDp.keySet()) {
            String keyY = name.getY().toString();
            String keyX = name.getX().toString();
            String value = memoryDp.get(name).toString();
            System.out.println("y: "+keyY + " "+ " x: "+ keyX +" value: " + value);
        }
        logger.log(Level.INFO, String.valueOf(minHeatLoss));

    }
}
