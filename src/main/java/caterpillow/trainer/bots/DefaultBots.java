package caterpillow.trainer.bots;

public class DefaultBots {
    private static final String TILE =
            "package caterpillow.trainer.bots.testing;\n" +
                    "\n" +
                    "import caterpillow.AimTrainer;\n" +
                    "import caterpillow.trainer.bots.BotInfo;\n" +
                    "import caterpillow.trainer.bots.BotReflectionCrap;\n" +
                    "import caterpillow.trainer.scenarios.ScenarioPlayer;\n" +
                    "import caterpillow.trainer.util.PositionUtils;\n" +
                    "import net.minecraft.block.state.IBlockState;\n" +
                    "import net.minecraft.client.Minecraft;\n" +
                    "import net.minecraft.util.BlockPos;\n" +
                    "import net.minecraft.util.Vec3;\n" +
                    "\n" +
                    "import java.util.ArrayList;\n" +
                    "import java.util.Random;\n" +
                    "\n" +
                    "public class Tile {\n" +
                    "\n" +
                    "    private ScenarioPlayer scenarioPlayer;\n" +
                    "\n" +
                    "    public int botNumber;\n" +
                    "\n" +
                    "    public ArrayList<BlockPos> getSpawnpoints() {\n" +
                    "        return spawnpoints;\n" +
                    "    }\n" +
                    "\n" +
                    "    public ArrayList<BlockPos> spawnpoints;\n" +
                    "    public ArrayList<BlockPos> availableSpawns;\n" +
                    "    public Vec3 position;\n" +
                    "    private BotInfo botInfo;\n" +
                    "    private BotReflectionCrap botReflectionCrap;\n" +
                    "    Random generator;\n" +
                    "    IBlockState iBlockState;\n" +
                    "\n" +
                    "    public Tile(ScenarioPlayer scenarioPlayer, caterpillow.trainer.bots.BotReflectionCrap botReflectionCrap, int botNumber) {\n" +
                    "        this.scenarioPlayer = scenarioPlayer;\n" +
                    "        this.botNumber = botNumber;\n" +
                    "        this.botReflectionCrap = botReflectionCrap;\n" +
                    "    }\n" +
                    "\n" +
                    "    public void start() {\n" +
                    "        generator = new Random();\n" +
                    "        System.out.println(\"omg guys it started\");\n" +
                    "        spawn();\n" +
                    "    }\n" +
                    "\n" +
                    "    public void onHit(Vec3 hitVec) {\n" +
                    "        spawn();\n" +
                    "    }\n" +
                    "\n" +
                    "    public void onTick() {\n" +
                    "\n" +
                    "    }\n" +
                    "\n" +
                    "    public void onWorldRender(float partialTicks) {\n" +
                    "\n" +
                    "    }\n" +
                    "\n" +
                    "    public void spawn() {\n" +
                    "        this.availableSpawns = new ArrayList<>(spawnpoints);\n" +
                    "        for (BlockPos pos : scenarioPlayer.takenBlocks) {\n" +
                    "            availableSpawns.removeIf(blockPos -> PositionUtils.isPosEqual(blockPos, pos));\n" +
                    "        }\n" +
                    "        // grabs a random available spawnspot\n" +
                    "        int index = generator.nextInt(availableSpawns.size());\n" +
                    "        BlockPos newPos = availableSpawns.get(index);\n" +
                    "\n" +
                    "        // sets the old block to its original form\n" +
                    "        if (position != null)\n" +
                    "            Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(position), iBlockState);\n" +
                    "\n" +
                    "        // saves the new spot's original block\n" +
                    "        iBlockState = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getBlockState(newPos);\n" +
                    "\n" +
                    "        // updates scenarioplayer's taken blocks\n" +
                    "        if (position != null)\n" +
                    "            scenarioPlayer.updateTakenBlocks(new BlockPos(position), newPos);\n" +
                    "        else\n" +
                    "            scenarioPlayer.updateTakenBlocks(null, newPos);\n" +
                    "\n" +
                    "        // sets the new block to the target\n" +
                    "        Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(newPos, AimTrainer.instance.settingsManager.getTargetBlockState());\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        // sets the position field to the new position\n" +
                    "        position = new Vec3(newPos.getX(), newPos.getY(), newPos.getZ());\n" +
                    "    }\n" +
                    "\n" +
                    "    public void setReflectionObject(BotReflectionCrap botReflectionCrap) {\n" +
                    "        this.botReflectionCrap = botReflectionCrap;\n" +
                    "    }\n" +
                    "\n" +
                    "}\n";
}
