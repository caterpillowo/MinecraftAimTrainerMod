package caterpillow.trainer.util;

import net.minecraft.util.BlockPos;

public class PositionUtils {

    public static boolean isPosEqual(BlockPos pos1, BlockPos pos2) {
        return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ();
    }

}
