package caterpillow.trainer.map;


import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class MapInfo {
    private int x, y, z;

    private ArrayList<SimpleEntry> blockList;


    public MapInfo(int x, int y, int z, ArrayList<SimpleEntry> blockList) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockList = blockList;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public ArrayList<AbstractMap.SimpleEntry> getBlockList() {
        return blockList;
    }

    public void setBlockList(ArrayList<SimpleEntry> blockList) {
        this.blockList = blockList;
    }
}
