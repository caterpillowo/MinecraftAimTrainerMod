package caterpillow.trainer.bots;

import caterpillow.trainer.scenarios.ScenarioPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;

public abstract class BotBase {

    private ScenarioPlayer scenarioPlayer;

    public int botNumber;

    public ArrayList<BlockPos> getSpawnpoints() {
        return spawnpoints;
    }

    public ArrayList<BlockPos> spawnpoints;
    public Vec3 position;
    private BotInfo botInfo;
    private BotReflectionCrap botReflectionCrap;

    public BotBase(ScenarioPlayer scenarioPlayer, BotReflectionCrap botReflectionCrap, int botNumber) {
        this.scenarioPlayer = scenarioPlayer;
        this.botNumber = botNumber;
        this.botReflectionCrap = botReflectionCrap;
    }

    public void start() {
    }

    public void onHit(Vec3 hitVec) {

    }

    public void onTick() {

    }

    public void onWorldRender(float partialTicks) {

    }

    public void setReflectionObject(BotReflectionCrap botReflectionCrap) {
        this.botReflectionCrap = botReflectionCrap;
    }

}
