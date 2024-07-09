package day5;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day5part2 {

    private static Logger logger = Logger.getLogger(day5part2.class.getName());
    private static String filePath = "day5/input.txt"; // Replace with the actual file path
    private static List<String> lines;

    public static void main(String[] args) {
        System.out.println(getMinLocation());
    }

    public static Long getMinLocation(){
        lines = utils.FileParseUtil.readLinesFromFile(filePath,logger);
        String[] seedArr = lines.get(0).substring(7).split(" ");
        List<SeedRange> seedRangeList = new ArrayList<>();
        for (int i = 0; i < seedArr.length; i+=2) {
            seedRangeList.add(new SeedRange(Long.valueOf(seedArr[i]),Long.valueOf(seedArr[i+1])));
        }
        List<List<Mapping>> allMappings = parseMappings();

        for (List<Mapping> mappings: allMappings) {
            seedRangeList = applyMappings(mappings,seedRangeList);
        }

        return seedRangeList.stream().mapToLong(SeedRange::getStart).min().orElseThrow();
    }



    public static List<SeedRange> applyMappings(List<Mapping> mappings,List<SeedRange> seedRanges){
        List<SeedRange> resultingSeedRange = new ArrayList<>();
        for (SeedRange seedRange: seedRanges) {
            int sizeOfResult = resultingSeedRange.size();
            for (Mapping mapping: mappings) {
                long rangeStart = seedRange.getStart();
                long rangeEndExclusive = seedRange.getStart()+seedRange.getLength();
                long mappingStart = mapping.getSource();
                long mappingEndExclusive = mapping.getSource()+mapping.getLength();
                long possibleRangeStart = Math.max(mappingStart, rangeStart);
                long possibleRangeEnd = Math.min(mappingEndExclusive, rangeEndExclusive);
                long mappingDiff = mapping.getDestination()-mapping.getSource();
                if (possibleRangeStart < possibleRangeEnd){
                    resultingSeedRange.add(new SeedRange(possibleRangeStart+mappingDiff,possibleRangeEnd-possibleRangeStart));
                }
            }
            if (sizeOfResult == resultingSeedRange.size()){
                resultingSeedRange.add(seedRange);
            }
        }
        return resultingSeedRange;
    }

    public static List<List<Mapping>> parseMappings(){
        Pattern pattern = Pattern.compile("(\\w+-to-\\w+ map:)(?s)(.*?)(?=\\n\\w+-to-\\w+ map:|$)");
        List<List<Mapping>> allMappings = new ArrayList<>();
        List<Mapping> mappings = new ArrayList<>();
        for (String line : lines.subList(2, lines.size())) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                continue;
            }
            String[] rangesStrArr =  line.split(" ");
            if(!rangesStrArr[0].isEmpty()){
                mappings.add(new Mapping(Long.valueOf(rangesStrArr[0]),
                    Long.valueOf(rangesStrArr[1]),Long.valueOf(rangesStrArr[2])));
            }
            else{
                allMappings.add(mappings);
                mappings = new ArrayList<>();
            }
        }
        return allMappings;
    }
}
