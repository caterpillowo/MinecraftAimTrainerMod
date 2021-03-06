package caterpillow.trainer.map;

import caterpillow.trainer.util.javaisdumb.Pair;

import java.util.ArrayList;

public class MapInfo {
    private int x, y, z;

    private ArrayList<Pair> blockList;


    public MapInfo(int x, int y, int z, ArrayList<Pair> blockList) {
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

    public ArrayList<Pair> getBlockList() {
        return blockList;
    }

    public void setBlockList(ArrayList<Pair> blockList) {
        this.blockList = blockList;
    }
}
