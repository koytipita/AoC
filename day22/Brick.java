package day22;

import java.util.List;

public class Brick {
    private int[] firstPoint;
    private int[] secondPoint;

    public Brick(int[] firstPoint, int[] secondPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    public int[] getFirstPoint() {
        return firstPoint;
    }

    public void setFirstPoint(int[] firstPoint) {
        this.firstPoint = firstPoint;
    }

    public int[] getSecondPoint() {
        return secondPoint;
    }

    public void setSecondPoint(int[] secondPoint) {
        this.secondPoint = secondPoint;
    }
}
