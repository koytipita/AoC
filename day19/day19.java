package day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day19 {
    static Logger logger = Logger.getLogger(day19.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day19/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day19/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day19/example3.txt";
    static final String ACTUAL_FILE_PATH = "day19/input.txt";
    static List<String> rules;
    static List<String> lines;
    static long sum = 0L;

    public static List<String> splitRules(String str) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            String content = matcher.group(1);
            String[] parts = content.split(",");
            result.addAll(Arrays.asList(parts));
        }

        return result;
    }

    public static String applyRule(String rule,String line, Integer ruleCount){
        String currentRule = splitRules(rule).get(ruleCount);
        List<String> varList = splitRules(line);
        if (!currentRule.contains(":")){
            return currentRule;
        }
        String operator = currentRule.contains(">") ? ">" : "<";
        int operatorIndex = currentRule.indexOf(operator);
        int colonIndex = currentRule.indexOf(":");
        String variable = currentRule.substring(0,operatorIndex);
        int value = Integer.parseInt(currentRule.substring(operatorIndex+1,colonIndex));
        String ifOutput = currentRule.substring(colonIndex+1);
        int varValue = varList.stream().filter(str -> str.contains(variable)).findAny().map(str -> Integer.parseInt(str.substring(2))).orElseThrow();
        if(operator.equals(">")){
            if (varValue > value) return ifOutput;
            else return "";
        }
        if (varValue < value) return ifOutput;
        else return "";
    }

    public static String getRule(String ruleName){
        return rules.stream().filter(rule ->{
            String name = rule.substring(0,rule.indexOf("{"));
            return name.equals(ruleName);
        }).findAny().orElseThrow();
    }

    public static void getSum(){
        int blankIndex = rules.indexOf("");
        lines = rules.subList(blankIndex+1,rules.size());
        rules = rules.subList(0,blankIndex);
        String startingRule = getRule("in");
        lines.forEach(line -> {
            String currentRule = startingRule;
            boolean accepted = false;
            boolean rejected = false;
            int ruleCount = 0;
            while (true){
                String ruleOutput = applyRule(currentRule,line,ruleCount);
                while (ruleOutput.isEmpty()){
                    ruleCount +=1;
                    ruleOutput = applyRule(currentRule,line,ruleCount);
                }
                if (ruleOutput.equals("A")){
                    accepted = true;
                    break;
                }
                if (ruleOutput.equals("R")){
                    rejected = true;
                    break;
                }
                currentRule = getRule(ruleOutput);
                ruleCount = 0;
            }
            if (accepted){
                List<String> varList = splitRules(line);
                varList.stream().map(str -> Integer.parseInt(str.substring(2))).forEach(value -> sum += value);
            }
        });
    }

    public static void main(String[] args) {
        rules = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH,logger);
        getSum();
        System.out.println(sum);


    }
}
