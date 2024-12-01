package aoc24.day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class day19 {
    static Logger logger = Logger.getLogger(day19.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day19/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day19/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day19/example3.txt";
    static final String ACTUAL_FILE_PATH = "day19/input.txt";
    static List<String> rules;
    static List<String> lines;
    static long sum = 0L;
    static long combinations = 0L;

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

    public static RuleOutput applyRulePartTwoGetIf(String rule,List<Range> rangesXMAS,int ruleCount){
        List<String> ruleList = splitRules(rule);
        if (ruleCount >= ruleList.size()){
            rangesXMAS.set(0,null);
            return new RuleOutput("R",rangesXMAS);
        }
        String currentRule = ruleList.get(ruleCount);
        if (!currentRule.contains(":")){
            return new RuleOutput(currentRule,rangesXMAS);
        }
        String operator = currentRule.contains(">") ? ">" : "<";
        int operatorIndex = currentRule.indexOf(operator);
        int colonIndex = currentRule.indexOf(":");
        String variable = currentRule.substring(0,operatorIndex);
        int value = Integer.parseInt(currentRule.substring(operatorIndex+1,colonIndex));
        String ifOutput = currentRule.substring(colonIndex+1);
        if(operator.equals(">")){
            if (variable.equals("x")){
                rangesXMAS.set(0,rangesXMAS.get(0).getBiggerThan(value));
            }
            if (variable.equals("m")){
                rangesXMAS.set(1,rangesXMAS.get(1).getBiggerThan(value));
            }
            if (variable.equals("a")){
                rangesXMAS.set(2,rangesXMAS.get(2).getBiggerThan(value));
            }
            if (variable.equals("s")){
                rangesXMAS.set(3,rangesXMAS.get(3).getBiggerThan(value));
            }
            return new RuleOutput(ifOutput,rangesXMAS);
        }
        if (variable.equals("x")){
            rangesXMAS.set(0,rangesXMAS.get(0).getSmallerThan(value));
        }
        if (variable.equals("m")){
            rangesXMAS.set(1,rangesXMAS.get(1).getSmallerThan(value));
        }
        if (variable.equals("a")){
            rangesXMAS.set(2,rangesXMAS.get(2).getSmallerThan(value));
        }
        if (variable.equals("s")){
            rangesXMAS.set(3,rangesXMAS.get(3).getSmallerThan(value));
        }
        return new RuleOutput(ifOutput,rangesXMAS);
    }
    public static RuleOutput applyRulePartTwoGetIfNot(String rule,List<Range> rangesXMAS,int ruleCount){
        List<String> ruleList = splitRules(rule);
        if (ruleCount >= ruleList.size()){
            rangesXMAS.set(0,null);
            return new RuleOutput("R",rangesXMAS);
        }
        String currentRule = ruleList.get(ruleCount);
        if (!currentRule.contains(":")){
            return new RuleOutput(currentRule,rangesXMAS);
        }
        String operator = currentRule.contains(">") ? ">" : "<";
        int operatorIndex = currentRule.indexOf(operator);
        int colonIndex = currentRule.indexOf(":");
        String variable = currentRule.substring(0,operatorIndex);
        int value = Integer.parseInt(currentRule.substring(operatorIndex+1,colonIndex));
        if(operator.equals(">")){
            if (variable.equals("x")){
                rangesXMAS.set(0,rangesXMAS.get(0).getNotBiggerThan(value));
            }
            if (variable.equals("m")){
                rangesXMAS.set(1,rangesXMAS.get(1).getNotBiggerThan(value));
            }
            if (variable.equals("a")){
                rangesXMAS.set(2,rangesXMAS.get(2).getNotBiggerThan(value));
            }
            if (variable.equals("s")){
                rangesXMAS.set(3,rangesXMAS.get(3).getNotBiggerThan(value));
            }
            return new RuleOutput("fdsdfd",rangesXMAS);
        }
        if (variable.equals("x")){
            rangesXMAS.set(0,rangesXMAS.get(0).getNotSmallerThan(value));
        }
        if (variable.equals("m")){
            rangesXMAS.set(1,rangesXMAS.get(1).getNotSmallerThan(value));
        }
        if (variable.equals("a")){
            rangesXMAS.set(2,rangesXMAS.get(2).getNotSmallerThan(value));
        }
        if (variable.equals("s")){
            rangesXMAS.set(3,rangesXMAS.get(3).getNotSmallerThan(value));
        }
        return new RuleOutput("fdsdfd",rangesXMAS);
    }

    public static long countCombinationsPartTwo(String currentRuleName,int ruleCount,List<Range> rangesXMAS){
        long count = 0L;
        if (currentRuleName.equals("A"))
            return rangesXMAS.stream()
                .mapToLong(range -> range.getEndIndex()-range.getStartIndex()+1)
                .reduce(1 , (a,b) -> a*b);
        if(currentRuleName.equals("R")) return 0L;
        String currentRuleLine = getRule(currentRuleName);
        RuleOutput ruleOutputIf = applyRulePartTwoGetIf(currentRuleLine,new ArrayList<>(rangesXMAS),ruleCount);
        RuleOutput ruleOutputIfNot = applyRulePartTwoGetIfNot(currentRuleLine,new ArrayList<>(rangesXMAS),ruleCount);

        if(IntStream.range(0,4).noneMatch(i -> ruleOutputIf.getName().equals("R") || ruleOutputIf.getRangesXMAS() == null || ruleOutputIf.getRangesXMAS().get(i) == null)){
            count += countCombinationsPartTwo(ruleOutputIf.getName(),0,ruleOutputIf.getRangesXMAS());
        }
        if(IntStream.range(0,4).noneMatch(i -> ruleOutputIfNot.getName().equals("R") || ruleOutputIfNot.getRangesXMAS() == null || ruleOutputIfNot.getRangesXMAS().get(i) == null)){
            count += countCombinationsPartTwo(currentRuleName,ruleCount+1,ruleOutputIfNot.getRangesXMAS());
        }

        return count;
    }

    public static void countSum(){
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
        //countSum();
        int blankIndex = rules.indexOf("");
        rules = rules.subList(0,blankIndex);
        List<Range> rangesXMAS = new ArrayList<>();
        rangesXMAS.add(new Range(1,4000)); //X
        rangesXMAS.add(new Range(1,4000)); //M
        rangesXMAS.add(new Range(1,4000)); //A
        rangesXMAS.add(new Range(1,4000)); //S
        combinations = countCombinationsPartTwo("in",0,rangesXMAS);
        //System.out.println(sum);
        System.out.println(combinations);


    }
}
