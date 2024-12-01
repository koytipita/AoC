package aoc24.day17;

public class Unit {
    private Direction direction;
    private Integer value;
    private Position position ;

    public Unit(Direction direction, Integer value, Position position) {
        this.direction = direction;
        this.value = value;
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Position getPos() {
        return position;
    }

    public void setPos(Position position) {
        this.position = position;
    }
}
