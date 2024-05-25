package day15;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class day15 {
    static Logger logger = Logger.getLogger(day15.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day15/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day15/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day15/example3.txt";
    static final String ACTUAL_FILE_PATH = "day15/input.txt";

    public static Integer calculateHash(String word){
        int currentValue = 0;
        for (int i = 0; i < word.length(); i++) {
            int asciiValue = word.charAt(i);
            currentValue += asciiValue;
            currentValue *= 17;
            currentValue %= 256;
        }
        return currentValue;
    }


    public static void main(String[] args) {
        List<String> words = utils.FileParseUtil.readStringsFromFile(ACTUAL_FILE_PATH, logger);
        Integer sum = words.stream().mapToInt(day15::calculateHash).sum();
        logger.log(Level.INFO, String.valueOf(sum));
    }
}
