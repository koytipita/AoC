package aoc23.day21;

import java.util.Objects;

public class Position {
    private Integer x;
    private Integer y;
    private Integer stepLeft;

    public Position(Integer x, Integer y, Integer stepLeft) {
        this.x = x;
        this.y = y;

        this.stepLeft = stepLeft;
    }

    public Position addRelativePosition(Position otherPosition){
        return new Position(this.x + otherPosition.getX(), this.y+ otherPosition.getY(), stepLeft);
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
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Position other = (Position) obj;
        if (!Objects.equals(this.x, other.x)) {
            return false;
        }

        if (!Objects.equals(this.y, other.y)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.x != null ? this.x.hashCode() : 0);
        hash = 53 * hash + (this.y != null ? this.y.hashCode() : 0);
        return hash;
    }

    public Integer getStepLeft() {
        return stepLeft;
    }

    public void setStepLeft(Integer stepLeft) {
        this.stepLeft = stepLeft;
    }
}
