package day16;

import java.util.List;

public class Beam {
    private Direction direction;
    private Integer x;
    private Integer y;
    private Boolean isAlive;

    public Beam(Direction direction, Integer x, Integer y, Boolean isAlive) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }
}
