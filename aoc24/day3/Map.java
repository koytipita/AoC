package aoc24.day3;

import aoc24.day3.MapObject;
import java.util.List;

public class Map {
    private Integer horizontalSize;
    private Integer verticalSize;
    private List<MapObject> mapObjects;

    public Map(Integer horizontalSize, Integer verticalSize, List<MapObject> mapObjects) {
        this.horizontalSize = horizontalSize;
        this.verticalSize = verticalSize;
        this.mapObjects = mapObjects;
    }

    public Integer getHorizontalSize() {
        return horizontalSize;
    }

    public void setHorizontalSize(Integer horizontalSize) {
        this.horizontalSize = horizontalSize;
    }

    public Integer getVerticalSize() {
        return verticalSize;
    }

    public void setVerticalSize(Integer verticalSize) {
        this.verticalSize = verticalSize;
    }

    public List<MapObject> getMapObjects() {
        return mapObjects;
    }

    public void setMapObjects(List<MapObject> mapObjects) {
        this.mapObjects = mapObjects;
    }
}
