package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;


public class day6{
    static Logger logger = Logger.getLogger(day6.class.getName());


    public static void main(String[] args) {

        String filePath = "day6/input.txt"; // Replace with the actual file path
        List<String> lines = readLinesFromFile(filePath);
        String timeLine = lines.get(0).substring(13);
        String distanceLine = lines.get(1).substring(12);
        List<Integer> times = Arrays.stream(timeLine.split(" +")).map(a -> Integer.parseInt(a)).toList();
        List<Integer> distances = Arrays.stream(distanceLine.split(" +")).map(a -> Integer.parseInt(a)).toList();

        Double multipliedValue = IntStream.range(0,times.size()).mapToDouble(index -> {
            double count = (double) 0;
            for(int i = 1; i < times.get(index);i++){
                if( i* (times.get(index)-i) > distances.get(index)){
                count += 1;
                }
            }
            return count;
        }).reduce(1, (a, b) -> a * b);
        System.out.println(multipliedValue);
    }
    public static List<String> readLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING,"Error reading file: " + e.getMessage());
        }
        return lines;
    }

}