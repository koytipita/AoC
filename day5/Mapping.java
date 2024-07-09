package day5;

public class Mapping {
    private Long destination;
    private Long source;
    private Long length;
    public Mapping(Long destination, Long source, Long length) {
        this.destination = destination;
        this.source = source;
        this.length = length;
    }

    public Long getDestination() {
        return destination;
    }

    public void setDestination(Long destination) {
        this.destination = destination;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }
}
