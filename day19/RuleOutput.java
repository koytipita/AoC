package day19;

import java.util.List;

public class RuleOutput {
    private String name;
    private List<Range> rangesXMAS;

    public RuleOutput(String name, List<Range> rangesXMAS) {
        this.name = name;
        this.rangesXMAS = rangesXMAS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Range> getRangesXMAS() {
        return rangesXMAS;
    }

    public void setRangesXMAS(List<Range> rangesXMAS) {
        this.rangesXMAS = rangesXMAS;
    }
}
