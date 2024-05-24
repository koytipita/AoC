package day13;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day13 {
    static Logger logger = Logger.getLogger(day13.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day13/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day13/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day13/example3.txt";
    static final String ACTUAL_FILE_PATH = "day13/input.txt";

    public static List<List<String>> readMapsHorizontal(String filePath, Logger logger) {
        List<List<String>> maps = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (true){
                List<String> lines = new ArrayList<>();
                boolean finish = false;
                String line;
                while (true) {
                    line = reader.readLine();
                    if (line == null){
                        finish = true;
                        break;
                    }
                    if (line.isEmpty()){
                        break;
                    }
                    lines.add(line);
                }
                if (finish){
                    maps.add(lines);
                    break;
                }
                maps.add(lines);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING,"Error reading file: " + e.getMessage());
        }
        return maps;
    }

    public static List<List<String>> convertMapHorizontalToVertical(List<List<String>> mapsHorizontal){
        List<List<String>> mapsVertical = new ArrayList<>();
        IntStream.range(0, mapsHorizontal.size()).forEach(i -> {
            List<String> mapHorizontal = mapsHorizontal.get(i);
            List<String> mapVertical = new ArrayList<>();
            IntStream.range(0,mapHorizontal.getFirst().length()).forEach(j -> {
                String row = mapHorizontal.stream().map(line -> line.substring(j,j+1)).collect(Collectors.joining());
                mapVertical.add(row);
            });
            mapsVertical.add(mapVertical);
        });
        return mapsVertical;
    }

    public static boolean checkSymmetryFromBack(List<String> map , Integer candidateIndex){     //0 1 2 3 4 5 6 7 8 size =9   5-(6 -5)   # . # . # . # .        6 , 5 , 5       7 3  8 3
        Integer lastElementIndex = map.size() -1 ;
        int symmetricOfLastElementIndex = candidateIndex - ((lastElementIndex-candidateIndex)-1);
        int i = lastElementIndex;
        int j= symmetricOfLastElementIndex;
        for (  ; i > candidateIndex; i--,j++) {
            if (!map.get(i).equals(map.get(j))){
                return false;
            }
        }
        return true;
    }
    public static boolean checkSymmetryFromFront(List<String> map , Integer candidateIndex){     //0 1 2 3 4 5 6 7 8 size =9   0 2 5    5-(6 -5)   # . # . # . # .        6 , 5 , 5       7 3  8 3
        int firstElementIndex = 0;
        int symmetricOfFirstIndex = candidateIndex*2+1;
        int i = firstElementIndex;
        int j = symmetricOfFirstIndex;
        for (  ; i <= candidateIndex; i++,j--) {
            if (!map.get(i).equals(map.get(j))){
                return false;
            }
        }
        return true;
    }
    public static boolean countSymmetryFromBackAndFindSmudge(List<String> map , Integer candidateIndex){     //0 1 2 3 4 5 6 7 8 size =9   5-(6 -5)   # . # . # . # .        6 , 5 , 5       7 3  8 3
        Integer lastElementIndex = map.size() -1 ;
        int symmetricOfLastElementIndex = candidateIndex - ((lastElementIndex-candidateIndex)-1);
        int expectedCountToBeSymmetric = (lastElementIndex-candidateIndex)*map.getFirst().length();
        int count = 0;
        int i = lastElementIndex;
        int j= symmetricOfLastElementIndex;
        for (  ; i > candidateIndex; i--,j++) {
            for (int k = 0; k < map.getFirst().length(); k++) {
                if(map.get(i).charAt(k) == map.get(j).charAt(k)){
                    count+=1;
                }
            }
        }
        return expectedCountToBeSymmetric - count == 1;
    }
    public static boolean countSymmetryFromFrontAndFindSmudge(List<String> map , Integer candidateIndex){     //0 1 2 3 4 5 6 7 8 size =9   5-(6 -5)   # . # . # . # .        6 , 5 , 5       7 3  8 3
        int firstElementIndex = 0;
        int symmetricOfFirstIndex = candidateIndex*2+1;
        int expectedCountToBeSymmetric = (candidateIndex+1)*(map.getFirst().length());
        int count = 0;
        int i = firstElementIndex;
        int j = symmetricOfFirstIndex;
        for (  ; i <= candidateIndex; i++,j--) {
            for (int k = 0; k < map.getFirst().length(); k++) {
                if(map.get(i).charAt(k) == map.get(j).charAt(k)){
                    count+=1;
                }
            }
        }
        return expectedCountToBeSymmetric - count == 1;
    }


    public static Long sumAllMaps(List<List<String>> mapsHorizontal, List<List<String>> mapsVertical){
        long sum = 0L;
        for (int mapIndex = 0 ; mapIndex < mapsHorizontal.size(); mapIndex++) {
            boolean verticalSymmetry = false;
            boolean horizontalSymmetry = false;
            List<String> mapHorizontal = mapsHorizontal.get(mapIndex);
            List<String> mapVertical = mapsVertical.get(mapIndex);
            int candidateBoundaryHorizontal = (mapHorizontal.size()+1)/2-1;
            int candidateIndexHorizontal = mapHorizontal.size()-2;
            int candidateBoundaryVertical = (mapVertical.size()+1)/2-1;
            int candidateIndexVertical = mapVertical.size()-2;

            for (; candidateIndexHorizontal >= candidateBoundaryHorizontal ; candidateIndexHorizontal--) {
                horizontalSymmetry = countSymmetryFromBackAndFindSmudge(mapHorizontal,candidateIndexHorizontal);
                if(horizontalSymmetry){
                    break;
                }
            }
            for (; candidateIndexVertical >= candidateBoundaryVertical ; candidateIndexVertical--) {
                verticalSymmetry = countSymmetryFromBackAndFindSmudge(mapVertical,candidateIndexVertical);
                if(verticalSymmetry){
                    break;
                }
            }
            if (!horizontalSymmetry && !verticalSymmetry){
                candidateIndexHorizontal = 0;
                candidateIndexVertical = 0;
                for (; candidateIndexHorizontal < candidateBoundaryHorizontal ; candidateIndexHorizontal++) {
                    horizontalSymmetry = countSymmetryFromFrontAndFindSmudge(mapHorizontal,candidateIndexHorizontal);
                    if(horizontalSymmetry){
                        break;
                    }
                }
                for (; candidateIndexVertical < candidateBoundaryVertical ; candidateIndexVertical++) {
                    verticalSymmetry = countSymmetryFromFrontAndFindSmudge(mapVertical,candidateIndexVertical);
                    if(verticalSymmetry){
                        break;
                    }
                }
            }

            if (horizontalSymmetry)
                sum += (candidateIndexHorizontal+1)* 100L;
            if (verticalSymmetry)
                sum += candidateIndexVertical+1;
        }
        return sum;
    }


    public static void main(String[] args) {
        List<List<String>> mapsHorizontal = readMapsHorizontal(ACTUAL_FILE_PATH, logger);
        List<List<String>> mapsVertical = convertMapHorizontalToVertical(mapsHorizontal);
        Long sum = sumAllMaps(mapsHorizontal,mapsVertical);
        logger.log(Level.INFO, String.valueOf(sum));
    }
}
