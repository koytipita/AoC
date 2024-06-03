package day18;

import java.util.Objects;

public class Position {
    private Long x;
    private Long y;
    private String direction;

    public Position(Long x, Long y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Position addRelativePosition(Position otherPosition){
        Position position = new Position(this.getX(), this.getY(),this.getDirection());
        String otherDirection = otherPosition.getDirection();
        switch (otherDirection){
            case "R" -> {
                position.setX(x + otherPosition.getX());
                position.setDirection("R");
            }
            case "D" -> {
                position.setY(y + otherPosition.getY());
                position.setDirection("D");
            }
            case "L" -> {
                position.setX(x + otherPosition.getX());
                position.setDirection("L");
            }
            case "U" -> {
                position.setY(y + otherPosition.getY());
                position.setDirection("U");
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
        return position;
    }

    public void addValue(long value, String direction){
        switch (direction){
            case "0" -> {
                x += value;
                this.direction = "R";
            }
            case "1" -> {
                y += value;
                this.direction = "D";
            }
            case "2" -> {
                x -= value;
                this.direction = "L";
            }
            case "3" -> {
                y -= value;
                this.direction = "U";
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }
    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
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
        hash = 53 * hash + this.y.intValue();
        return hash;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
