package day22;

import java.util.Arrays;
import java.util.List;

public class Brick {
    private int[] firstPoint;
    private int[] secondPoint;

    public Brick(int[] firstPoint, int[] secondPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    public Brick copy() {
        return new Brick(Arrays.copyOf(firstPoint, firstPoint.length), Arrays.copyOf(secondPoint, secondPoint.length));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brick brick = (Brick) o;
        return Arrays.equals(firstPoint, brick.firstPoint) && Arrays.equals(secondPoint, brick.secondPoint);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(firstPoint);
        result = 31 * result + Arrays.hashCode(secondPoint);
        return result;
    }
}
