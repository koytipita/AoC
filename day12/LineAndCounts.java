package day12;

import java.util.List;

public class LineAndCounts {
    private String line;
    private List<Long> numberList;


    public LineAndCounts(String line, List<Long> numberList) {
        this.line = line;
        this.numberList = numberList;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public List<Long> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<Long> numberList) {
        this.numberList = numberList;
    }
}
