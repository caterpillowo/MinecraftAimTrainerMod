package caterpillow.trainer.bots.bots;

import caterpillow.AimTrainer;
import caterpillow.trainer.bots.Bot;
import caterpillow.trainer.scenarios.ScenarioPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Random;

public class Tile extends Bot {


    public ArrayList<BlockPos> getSpawnpoints() {
        return spawnpoints;
    }

    Random generator;
    IBlockState iBlockState;

    public Tile(ScenarioPlayer scenarioPlayer, int botNumber) {
        super(scenarioPlayer, botNumber);
    }

    public void start() {
        generator = new Random();
        System.out.println("omg guys it started");
        spawn();
    }

    public void onHit(Vec3 hitVec) {
        spawn();
    }

    public void onTick() {

    }

    public void onWorldRender(float partialTicks) {

    }

    public void spawn() {
        System.out.println(spawnpoints);
        System.out.println(scenarioPlayer);
        System.out.println(scenarioPlayer.availableBlocks);
        // grabs a random available spawnspot
        int index = generator.nextInt(scenarioPlayer.availableBlocks.size());
        BlockPos newPos = scenarioPlayer.availableBlocks.get(index);

        // sets the old block to its original form
        if (position != null)
            Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(position), iBlockState);

        // saves the new spot's original block
        iBlockState = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getBlockState(newPos);

        // updates scenarioplayer's taken blocks
        if (position != null)
            scenarioPlayer.updateAvailableBlocks(new BlockPos(position), newPos);
        else
            scenarioPlayer.updateAvailableBlocks(null, newPos);

        // sets the new block to the target
        Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(newPos, AimTrainer.instance.settingsManager.getTargetBlockState());


        // sets the position field to the new position
        position = new Vec3(newPos.getX(), newPos.getY(), newPos.getZ());
    }

}