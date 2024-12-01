package aoc24.day17;

import java.util.Objects;

public class Vertex {
    private Position position;
    private Integer value;
    private Integer rightCount;
    private Integer straightCount;
    private Integer distance;
    private Vertex previous;

    public Vertex(Position position, Integer value, Integer rightCount, Integer straightCount) {
        this.position = position;
        this.value = value;
        this.rightCount = rightCount;
        this.straightCount = straightCount;
        this.distance = Integer.MAX_VALUE-1000;
        this.previous = null;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
    public Integer getRightCount() {
        return rightCount;
    }

    public void setRightCount(Integer rightCount) {
        this.rightCount = rightCount;
    }

    public Integer getStraightCount() {
        return straightCount;
    }

    public void setStraightCount(Integer straightCount) {
        this.straightCount = straightCount;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }
}
