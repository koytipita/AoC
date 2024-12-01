package aoc23.day15;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class day15 {
    static Logger logger = Logger.getLogger(day15.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day15/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day15/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day15/example3.txt";
    static final String ACTUAL_FILE_PATH = "day15/input.txt";
    static Map<Integer,List<Lens>> boxes = new HashMap<>();

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

    public static void executeCommands(List<String> commands){

        commands.forEach(command -> {
            int dashSignIndex = command.indexOf("-");
            int equalsSignIndex = command.indexOf("=");
            int signIndex = dashSignIndex == -1 ? equalsSignIndex : dashSignIndex;
            String word = command.substring(0,signIndex);
            int hashValue = calculateHash(word);
            List<Lens> lensList= boxes.get(hashValue);
            if(signIndex == dashSignIndex){
                Optional<Integer> indexToBeRemoved = IntStream.range(0, lensList.size())
                        .filter(index -> lensList.get(index).getWord().equals(word)).boxed().findFirst();
                indexToBeRemoved.ifPresent(index -> lensList.remove(index.intValue()));
            }
            if(signIndex == equalsSignIndex){
                Integer focalLength = Integer.valueOf(command.substring(equalsSignIndex+1));
                Optional<Integer> indexToBeReplaced = IntStream.range(0, lensList.size())
                        .filter(index -> lensList.get(index).getWord().equals(word)).boxed().findFirst();
                indexToBeReplaced.ifPresentOrElse(
                        index -> lensList.set(index,new Lens(word,focalLength)),
                        () -> lensList.add(new Lens(word,focalLength))
                );
            }
        });
    }

    public static Integer calculateSumOfBoxes(){
        Integer sum = 0;
        for (int key = 0; key < 256; key++) {
            List<Lens> lensList = boxes.get(key);
            for (int indexInList = 0; indexInList < lensList.size(); indexInList++) {
                sum += (key+1) * (indexInList+1) * lensList.get(indexInList).getFocalLength();
            }
        }
        return sum;
    }


    public static void main(String[] args) {
        List<String> words = utils.FileParseUtil.readStringsFromFile(ACTUAL_FILE_PATH, logger);
        for (int i = 0; i < 256; i++) {
            boxes.put(i,new ArrayList<>());
        }
        //Integer sum1 = words.stream().mapToInt(aoc24.day15::calculateHash).sum();
        executeCommands(words);
        Integer sum2 = calculateSumOfBoxes();
        //logger.log(Level.INFO, String.valueOf(sum1));
        logger.log(Level.INFO, String.valueOf(sum2));
    }
}
