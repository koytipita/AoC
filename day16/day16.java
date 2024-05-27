package day16;

import utils.StreamUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class day16 {
    static Logger logger = Logger.getLogger(day16.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day16/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day16/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day16/example3.txt";
    static final String ACTUAL_FILE_PATH = "day16/input.txt";
    static List<String> energyMap = new ArrayList<>();
    static List<String> map = new ArrayList<>();
    static List<String> splitMap = new ArrayList<>();
    static List<Beam> beamList = new ArrayList<>();

    public static void energizeTile(Beam beam){
        int x = beam.getX();
        int y = beam.getY();
        StringBuilder line = new StringBuilder(energyMap.get(y));
        line.setCharAt(x, '#');
        energyMap.set(y, line.toString());
    }

    public static Beam beamStep(Beam beam){
        Direction direction = beam.getDirection();
        Beam beamStepped = null;
        switch (direction){
            case Direction.EAST -> beamStepped = beamStepEast(beam);
            case Direction.WEST -> beamStepped = beamStepWest(beam);
            case Direction.SOUTH -> beamStepped = beamStepSouth(beam);
            case Direction.NORTH-> beamStepped = beamStepNorth(beam);
        }
        return beamStepped;
    }

    public static Beam beamStepEast(Beam beam){
        Integer x = beam.getX();
        Integer y = beam.getY();
        if(x.equals(map.getFirst().length())){ // check boundaries
            beam.setAlive(false);
            return beam;
        }
        char item = map.get(y).charAt(x); //read east item
        switch (item){
            case '/':
                beam.setY(y-1);
                beam.setDirection(Direction.NORTH);
                break;
            case '\\':
                beam.setY(y+1);
                beam.setDirection(Direction.SOUTH);
                break;
            case '|':
                //energizeTile(beam);
                //beam.setAlive(false);
                //beamList.add(new Beam(Direction.NORTH,x,y-1,true));
                if(setPositionSplitMap(x,y)) {
                    beam.setY(y-1);
                    beam.setDirection(Direction.NORTH);
                    beamList.add(new Beam(Direction.SOUTH, x, y + 1, true));
                }
                else{
                    beam.setAlive(false);
                }
                break;
            default:
                x +=1; //stepEast
                beam.setX(x);
                break;
        }
        return beam;
    }

    public static Beam beamStepWest(Beam beam){
        Integer x = beam.getX();
        Integer y = beam.getY();
        if(x.equals(-1)){ // check boundaries
            beam.setAlive(false);
            return beam;
        }
        char item = map.get(y).charAt(x); //read east item
        switch (item){
            case '/':
                beam.setY(y+1);
                beam.setDirection(Direction.SOUTH);
                break;
            case '\\':
                beam.setY(y-1);
                beam.setDirection(Direction.NORTH);
                break;
            case '|':
                //energizeTile(beam);
                //beam.setAlive(false);
                //beamList.add(new Beam(Direction.NORTH,x,y-1,true));
                if(setPositionSplitMap(x,y)) {
                    beam.setY(y - 1);
                    beam.setDirection(Direction.NORTH);
                    beamList.add(new Beam(Direction.SOUTH, x, y + 1, true));
                }
                else{
                    beam.setAlive(false);
                }
                break;
            default:
                x -=1; //stepEast
                beam.setX(x);
                break;
        }
        return beam;
    }

    public static Beam beamStepSouth(Beam beam){
        Integer x = beam.getX();
        Integer y = beam.getY();
        if(y.equals(map.size())){ // check boundaries
            beam.setAlive(false);
            return beam;
        }
        char item = map.get(y).charAt(x); //read east item
        switch (item){
            case '/':
                beam.setX(x-1);
                beam.setDirection(Direction.WEST);
                break;
            case '\\':
                beam.setX(x+1);
                beam.setDirection(Direction.EAST);
                break;
            case '-':
                //energizeTile(beam);
                //beam.setAlive(false);
                //beamList.add(new Beam(Direction.EAST,x+1,y,true));
                if(setPositionSplitMap(x,y)) {
                    beam.setX(x + 1);
                    beam.setDirection(Direction.EAST);
                    beamList.add(new Beam(Direction.WEST, x - 1, y, true));
                }
                else{
                    beam.setAlive(false);
                }
                break;
            default:
                y +=1; //stepEast
                beam.setY(y);
                break;
        }
        return beam;
    }

    public static Beam beamStepNorth(Beam beam){
        Integer x = beam.getX();
        Integer y = beam.getY();
        if(y.equals(-1)){ // check boundaries
            beam.setAlive(false);
            return beam;
        }
        char item = map.get(y).charAt(x); //read east item
        switch (item){
            case '/':
                beam.setX(x+1);
                beam.setDirection(Direction.EAST);
                break;
            case '\\':
                beam.setX(x-1);
                beam.setDirection(Direction.WEST);
                break;
            case '-':
                //energizeTile(beam);
                //beam.setAlive(false);
                //beamList.add(new Beam(Direction.EAST,x+1,y,true));
                if(setPositionSplitMap(x,y)){
                    beam.setX(x+1);
                    beam.setDirection(Direction.EAST);
                    beamList.add(new Beam(Direction.WEST,x-1,y,true));
                }
                else{
                    beam.setAlive(false);
                }
                break;
            default:
                y -=1; //stepEast
                beam.setY(y);
                break;
        }

        return beam;
    }
    public static Integer countEnergizedTiles(){
        Integer count = 0;
        for (int i = 0; i < energyMap.size(); i++) {
            String line = energyMap.get(i);
            count += line.length() - line.replace("#", "").length();
        }
        return count;
    }
    public static void traverseMapWithBeams(){
        beamList.add(new Beam(Direction.EAST,0,0,true));
        while (beamList.stream().anyMatch(Beam::getAlive)){
            Stream<Beam> beamStream = beamList.stream().filter(Beam::getAlive);
            beamList = StreamUtil.getArrayListFromStream(beamStream);
            int beamListSize = beamList.size();
            for (int i = 0; i < beamListSize; i++) {
                Beam beam = beamList.get(i);
                Integer x = beam.getX();
                Integer y = beam.getY();
                if(x.equals(map.getFirst().length()) || x.equals(-1) || y.equals(map.size()) || y.equals(-1)){
                    beam.setAlive(false);
                    beamList.set(i,beam);
                    continue;
                }
                if (energyMap.get(y).charAt(x) != '#') {
                    energizeTile(beam);
                }
                beam = beamStep(beam);
                beamList.set(i,beam);
            }
        }
    }

    public static boolean setPositionSplitMap(Integer x, Integer y){
        char charAtSplitPosition = splitMap.get(y).charAt(x);
        if(charAtSplitPosition == '!'){
            return false;
        }
        StringBuilder line = new StringBuilder(splitMap.get(y));
        line.setCharAt(x, '!');
        splitMap.set(y, line.toString());
        return true;
    }

    public static void main(String[] args) {
        map = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        energyMap = new ArrayList<>(Collections.nCopies(map.size(),".".repeat(map.getFirst().length())));
        splitMap = new ArrayList<>(Collections.nCopies(map.size(),".".repeat(map.getFirst().length())));

        traverseMapWithBeams();
        Integer sum = countEnergizedTiles();
        logger.log(Level.INFO, String.valueOf(sum));
    }
}
