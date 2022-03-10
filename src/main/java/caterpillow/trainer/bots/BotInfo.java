package caterpillow.trainer.bots;

import caterpillow.trainer.util.javaisdumb.Pair;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

import java.util.ArrayList;

public class BotInfo {

    // this will contain a list of every single bot and where they spawn and what rules they follow are also included here to a certain extent
//    public ArrayList<BotAttributes> botAttributesList = new ArrayList<>();

    public String botCode;

    public int botCount;

//    public ArrayList<Pair<BlockPos, BlockPos>> getSpawnRegions() {
//        return spawnRegions;
//    }
//
//    public void setSpawnRegions(ArrayList<Pair<BlockPos, BlockPos>> spawnRegions) {
//        this.spawnRegions = spawnRegions;
//    }

    // contains pairs of coordinates to create regions
    // regions will be seperated into coordinates and added to the spawnpoints of the bots
    // these coordinates will be relative to minPos
    public ArrayList<Pair<BlockPos, BlockPos>> spawnRegions = new ArrayList<>();

    public BotInfo() {
    }
}
