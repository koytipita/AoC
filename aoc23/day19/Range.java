package aoc23.day19;

public class Range {
    private int startIndex;
    private int endIndex;

    public Range(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public Range getSmallerThan(int value){
        if (startIndex < value && endIndex<value){
            return new Range(startIndex,endIndex);
        }
        if (startIndex < value && endIndex >= value){
            return new Range(startIndex,value-1);
        }
        return null;
    }

    public Range getNotSmallerThan(int value){
        if (startIndex < value && endIndex<value){
            return null;
        }
        if (startIndex < value && endIndex >= value){
            return new Range(value,endIndex);
        }
        return new Range(startIndex,endIndex);
    }

    public Range getNotBiggerThan(int value){
        if (startIndex > value && endIndex > value){  //1 4000  > 1  (1 ,1)
            return null;
        }
        if (startIndex <= value && endIndex > value){
            return new Range(startIndex,value);
        }
        return new Range(startIndex,endIndex);
    }

    public Range getBiggerThan(int value){  //1 4000  > 1  (2 ,4000)
        if (startIndex > value && endIndex > value){
            return new Range(startIndex,endIndex);
        }
        if (startIndex <= value && endIndex > value){
            return new Range(value+1,endIndex);
        }
        return null;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
