package day3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class day3 {
    static Logger logger = Logger.getLogger(day3.class.getName());
    static final String EXAMPLE_FILE_PATH = "day3/example.txt";
    static final String ACTUAL_FILE_PATH = "day3/input.txt";
    static int sum = 0;

    public static List<Integer> getNeighborNumbers(MapObject sign, Map map){
        Integer x = sign.getLocationStartIndexX();
        Integer y = sign.getLocationStartIndexY();
        return map.getMapObjects().stream()
                .filter(a -> a.getObjType().equals(MapObject.ObjTypeEnum.NUMBER))
                .filter(number -> {
                    boolean yRange = Math.max(y - 1, number.getLocationStartIndexY()) == Math.min(number.getLocationStartIndexY(), y + 1);
                    boolean xRange = Math.max(x - number.getLength(), number.getLocationStartIndexX()) == Math.min(number.getLocationStartIndexX(), x + 1);
                    return yRange && xRange;
                }).mapToInt(MapObject::getValue).boxed().collect(Collectors.toList());

    }

    public static Integer getGearRatios(MapObject sign, Map map){
        Integer x = sign.getLocationStartIndexX();
        Integer y = sign.getLocationStartIndexY();
        List <Integer> neighbors = map.getMapObjects().stream()
                .filter(a -> a.getObjType().equals(MapObject.ObjTypeEnum.NUMBER))
                .filter(number -> {
                    boolean yRange = Math.max(y - 1, number.getLocationStartIndexY()) == Math.min(number.getLocationStartIndexY(), y + 1);
                    boolean xRange = Math.max(x - number.getLength(), number.getLocationStartIndexX()) == Math.min(number.getLocationStartIndexX(), x + 1);
                    return yRange && xRange;
                }).mapToInt(MapObject::getValue).boxed().collect(Collectors.toList());

        if(neighbors.size()>1){
            return neighbors.stream().reduce(1,(a,b) -> a*b);
        }
        return 0;

    }

    public static void main(String[] args) {
        List<String> lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH, logger);
        Map map = new Map(lines.getFirst().length(),lines.size(),new ArrayList<>());
        List<Integer> numbersToSum = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            Pattern patternNumber = Pattern.compile("\\d+");

            Matcher matcherNumber = patternNumber.matcher(lines.get(i));

            Pattern patternSign = Pattern.compile("[^\\d.]");

            Matcher matcherSign = patternSign.matcher(lines.get(i));

            // Loop through all matches
            while (matcherSign.find()) {
                int index = matcherSign.start();
                map.getMapObjects().add(new MapObject(MapObject.ObjTypeEnum.SIGN,
                        index,i,1,-1));
            }

            // Loop through all matches
            while (matcherNumber.find()) {
                String number = matcherNumber.group();
                int index = matcherNumber.start();
                map.getMapObjects().add(new MapObject(MapObject.ObjTypeEnum.NUMBER,
                        index,i,number.length(),Integer.parseInt(number)));
            }
        }
        for (MapObject mapObject : map.getMapObjects()){
            if (mapObject.getObjType().equals(MapObject.ObjTypeEnum.SIGN)){
                numbersToSum.add(getGearRatios(mapObject,map));
            }
        }
        System.out.println(numbersToSum.stream().reduce(0, Integer::sum));

    }
}
