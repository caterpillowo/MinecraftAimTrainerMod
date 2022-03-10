package caterpillow.trainer.bots.testing;

import caterpillow.AimTrainer;
import caterpillow.trainer.bots.BotInfo;
import caterpillow.trainer.bots.BotReflectionCrap;
import caterpillow.trainer.scenarios.ScenarioPlayer;
import caterpillow.trainer.util.PositionUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Random;

public class Tile {

    private ScenarioPlayer scenarioPlayer;

    public int botNumber;

    public ArrayList<BlockPos> getSpawnpoints() {
        return spawnpoints;
    }

    public ArrayList<BlockPos> spawnpoints;
    public ArrayList<BlockPos> availableSpawns;
    public Vec3 position;
    private BotInfo botInfo;
    private BotReflectionCrap botReflectionCrap;
    Random generator;
    IBlockState iBlockState;

    public Tile(ScenarioPlayer scenarioPlayer, caterpillow.trainer.bots.BotReflectionCrap botReflectionCrap, int botNumber) {
        this.scenarioPlayer = scenarioPlayer;
        this.botNumber = botNumber;
        this.botReflectionCrap = botReflectionCrap;
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
        this.availableSpawns = new ArrayList<>(spawnpoints);
        for (BlockPos pos : scenarioPlayer.takenBlocks) {
            availableSpawns.removeIf(blockPos -> PositionUtils.isPosEqual(blockPos, pos));
        }
        // grabs a random available spawnspot
        int index = generator.nextInt(availableSpawns.size());
        BlockPos newPos = availableSpawns.get(index);

        // sets the old block to its original form
        if (position != null)
            Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(position), iBlockState);

        // saves the new spot's original block
        iBlockState = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getBlockState(newPos);

        // updates scenarioplayer's taken blocks
        if (position != null)
            scenarioPlayer.updateTakenBlocks(new BlockPos(position), newPos);
        else
            scenarioPlayer.updateTakenBlocks(null, newPos);

        // sets the new block to the target
        Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(newPos, AimTrainer.instance.settingsManager.getTargetBlockState());



        // sets the position field to the new position
        position = new Vec3(newPos.getX(), newPos.getY(), newPos.getZ());
    }

    public void setReflectionObject(BotReflectionCrap botReflectionCrap) {
        this.botReflectionCrap = botReflectionCrap;
    }

}
