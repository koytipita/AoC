package aoc24.day15;

public class Lens {
    private String word;
    private Integer focalLength;

    public Lens(String word, Integer focalLength) {
        this.word = word;
        this.focalLength = focalLength;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(Integer focalLength) {
        this.focalLength = focalLength;
    }
}
