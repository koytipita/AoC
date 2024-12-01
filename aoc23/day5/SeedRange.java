package aoc23.day5;

public class SeedRange {
    private Long start;
    private Long length;

    public SeedRange(Long start, Long end) {
        this.start = start;
        this.length = end;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }
}
