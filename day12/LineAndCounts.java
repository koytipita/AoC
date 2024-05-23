package day12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

public class LineAndCounts {
    private String line;
    private Integer number;

    public LineAndCounts(String line, Integer number) {
        this.line = line;
        this.number = number;
    }

    public LineAndCounts(String line, List<Integer> integers) {
        this.line = line;
        Double number = 0.0;
        double digit = pow(18,integers.size()-1);
        for (int i = 0; i <integers.size() ; i++,digit/=18) {
            number += integers.get(i)*digit;
        }
        this.number = number.intValue();
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Integer> getNumberList(){
        Integer oldNumber = number;
        List<Integer> segments = new ArrayList<>();
        while (number > 0) {
            int segment = number % 18;
            segments.add(segment);
            number /= 18;
        }
        Collections.reverse(segments);
        number = oldNumber;
        return segments;
    }
}
