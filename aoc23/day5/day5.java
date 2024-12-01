package aoc23.day5;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class day5{
    private static Logger logger = Logger.getLogger(day5.class.getName());
    private static String filePath = "src/main/aoc24.day5/input.txt"; // Replace with the actual file path



    public static void main(String[] args) {
    List<String> lines = utils.FileParseUtil.readLinesFromFile(filePath,logger);
    String[] seedArr = lines.get(0).substring(7).split(" ");
    BigInteger[] seedIntArr = new BigInteger[20];
    for(int i = 0;i < seedArr.length;i++)
    {
        seedIntArr[i] = BigInteger.valueOf(Long.parseLong(seedArr[i]));
    }
    List<BigInteger> seeds = List.of(seedIntArr);
    List<List<List<BigInteger>>> allMapValues = new ArrayList<>();
    List<List<BigInteger>> oneMapValues = new ArrayList<>();

    Pattern pattern = Pattern.compile("(\\w+-to-\\w+ map:)(?s)(.*?)(?=\\n\\w+-to-\\w+ map:|$)");
    int mapIndex = -1;
    for (String line : lines.subList(2, lines.size())) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            mapIndex += 1;
            continue;
        }
        String[] rangesStrArr =  line.split(" ");
        BigInteger[] rangesIntArr = new BigInteger[3];
        for(int i = 0;i < rangesStrArr.length;i++)
        {
            if(!rangesStrArr[0].isEmpty()){
                rangesIntArr[i] = BigInteger.valueOf(Long.parseLong(rangesStrArr[i]));
            }
            else{
                allMapValues.add(oneMapValues);
                oneMapValues = new ArrayList<>();
            }
        }
        if(rangesIntArr[0] != null){
            List<BigInteger> ranges = List.of(rangesIntArr);
            oneMapValues.add(ranges);
        }
    }
    allMapValues.add(oneMapValues);

    BigInteger minLocation = seeds.stream()
        .map(seed -> mapRanges(seed,allMapValues.get(0)))
        .map(seed1 -> mapRanges(seed1,allMapValues.get(1)))
        .map(seed2 -> mapRanges(seed2,allMapValues.get(2)))
        .map(seed3 -> mapRanges(seed3,allMapValues.get(3)))
        .map(seed4 -> mapRanges(seed4,allMapValues.get(4)))
        .map(seed5 -> mapRanges(seed5,allMapValues.get(5)))
        .map(seed6 -> mapRanges(seed6,allMapValues.get(6)))
            .min(BigInteger::compareTo).get();
    System.out.println(minLocation);

    List<BigInteger> seedStarts = IntStream.range(0, seeds.size())
                    .filter(i -> i % 2 == 0)
                    .mapToObj(seeds::get)
                    .toList();
    List<BigInteger> seedRanges = IntStream.range(0, seeds.size())
            .filter(i -> i % 2 == 1)
            .mapToObj(seeds::get)
            .toList();

    Stream<Object> seedsArr = IntStream.range(0, seedStarts.size()).
            mapToObj(seedIndex -> {
        return IntStream.range(seedStarts.get(seedIndex).intValue(),(seedStarts.get(seedIndex).add(seedRanges.get(seedIndex)).intValue())).toArray();
        }
    );

    //List<Integer> seedsList = seedsArr.forEach(seedsArr ->).collect(Collectors.toList());




}

public static BigInteger mapRanges(BigInteger source, List<List<BigInteger>> ranges){
    for (List<BigInteger> range: ranges) {
        if(source.compareTo(range.get(1)) >= 0 && source.compareTo(range.get(1).add(range.get(2))) <= 0 ){
            return range.get(0).add(source.subtract(range.get(1)));
        }
    }
    return source;
}

}