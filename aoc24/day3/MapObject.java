package aoc24.day3;

public class MapObject {

    public MapObject(ObjTypeEnum objType, Integer locationStartIndexX, Integer locationStartIndexY, Integer length, Integer value) {
        this.objType = objType;
        this.locationStartIndexX = locationStartIndexX;
        this.locationStartIndexY = locationStartIndexY;
        this.length = length;
        this.value = value;
    }

    public ObjTypeEnum getObjType() {
        return objType;
    }

    public void setObjType(ObjTypeEnum objType) {
        this.objType = objType;
    }

    public Integer getLocationStartIndexX() {
        return locationStartIndexX;
    }

    public void setLocationStartIndexX(Integer locationStartIndexX) {
        this.locationStartIndexX = locationStartIndexX;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getLocationStartIndexY() {
        return locationStartIndexY;
    }

    public void setLocationStartIndexY(Integer locationStartIndexY) {
        this.locationStartIndexY = locationStartIndexY;
    }

    enum ObjTypeEnum{
        SIGN,
        NUMBER
    }

    private ObjTypeEnum objType;
    private Integer locationStartIndexX;
    private Integer locationStartIndexY;
    private Integer length;
    private Integer value;
}
