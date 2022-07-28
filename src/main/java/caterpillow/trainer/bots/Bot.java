package caterpillow.trainer.bots;

import caterpillow.trainer.scenarios.ScenarioPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;

public abstract class Bot {

    protected ScenarioPlayer scenarioPlayer;

    public int botNumber;

    public ArrayList<BlockPos> getSpawnpoints() {
        return spawnpoints;
    }

    public void setSpawnpoints(ArrayList<BlockPos> spawnpoints) {
        this.spawnpoints = spawnpoints;
    }

    public ArrayList<BlockPos> spawnpoints;
    public Vec3 position;
    public int targetEntityId;
    private BotInfo botInfo;

    public Bot(ScenarioPlayer scenarioPlayer, int botNumber) {
        this.scenarioPlayer = scenarioPlayer;
        this.botNumber = botNumber;
        this.targetEntityId = -1;
    }

    public abstract void start();

    public abstract void onHit(Vec3 hitVec);

    public abstract void onTick();

    public abstract void onWorldRender(float partialTicks);

    public Vec3 getPosition() {
        return position;
    }
}
