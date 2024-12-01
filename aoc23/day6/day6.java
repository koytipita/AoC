package aoc23.day6;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;


public class day6{
    static Logger logger = Logger.getLogger(day6.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day6/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day6/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day6/example3.txt";
    static final String ACTUAL_FILE_PATH = "day6/input.txt";
    static List<String> lines;


    public static void main(String[] args) {
         lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH,logger);
         part2();
    }
    public static void part2(){
        String timeLine = lines.get(0).substring(13);
        String distanceLine = lines.get(1).substring(12);
        long time = Long.parseLong(timeLine.replaceAll("\\s+", ""));
        long distance = Long.parseLong(distanceLine.replaceAll("\\s+", ""));
        long count = LongStream.range(1,time)
            .filter(i -> i* (time-i) > distance)
                    .count();
        System.out.println(count);
    }
    public static void part1(){
        String timeLine = lines.get(0).substring(13);
        String distanceLine = lines.get(1).substring(12);
        List<Integer> times = Arrays.stream(timeLine.split(" +")).map(Integer::parseInt).toList();
        List<Integer> distances = Arrays.stream(distanceLine.split(" +")).map(Integer::parseInt).toList();

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

}