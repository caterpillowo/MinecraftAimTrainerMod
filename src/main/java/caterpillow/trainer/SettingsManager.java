package caterpillow.trainer;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SettingsManager {

    public IBlockState getTargetBlockState() {
        return GameRegistry.findBlock("minecraft", blockName).getStateFromMeta(targetMeta);
    }

    public String blockName = "wool";
    public int targetMeta = 5;
}
