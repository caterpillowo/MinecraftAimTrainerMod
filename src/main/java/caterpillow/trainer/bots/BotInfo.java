package caterpillow.trainer.bots;

import net.minecraft.util.BlockPos;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class BotInfo {

    // this will contain a list of every single bot and where they spawn and what rules they follow are also included here to a certain extent
//    public ArrayList<BotAttributes> botAttributesList = new ArrayList<>();

    public String botType;

    public int botCount;

//    public ArrayList<SimpleEntry<BlockPos, BlockPos>> getSpawnRegions() {
//        return spawnRegions;
//    }
//
//    public void setSpawnRegions(ArrayList<SimpleEntry<BlockPos, BlockPos>> spawnRegions) {
//        this.spawnRegions = spawnRegions;
//    }

    // contains SimpleEntrys of coordinates to create regions
    // regions will be seperated into coordinates and added to the spawnpoints of the bots
    // these coordinates will be relative to minPos
    public ArrayList<SimpleEntry<BlockPos, BlockPos>> spawnRegions = new ArrayList<>();

    public BotInfo() {
    }
}
