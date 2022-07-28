package caterpillow.trainer.scenarios;

import caterpillow.trainer.bots.Bot;
import caterpillow.trainer.bots.bots.Tile;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class ScenarioLoader {

    private Scenario scenario;

    public ScenarioPlayer scenarioPlayer;

    private HashMap<String, Block> blockTable = new HashMap<>();

    private BlockPos player;

    private HashMap<String, Class> botClasses;

    public ScenarioLoader(Scenario scenario) {
        this.scenario = scenario;

        this.scenarioPlayer = new ScenarioPlayer(scenario);

        player = new BlockPos(Minecraft.getMinecraft().thePlayer).down().west().south();

        botClasses = new HashMap<>();

        botClasses.put(Tile.class.getSimpleName(), Tile.class);

        loadMap();

        loadBots();

        scenarioPlayer.start();

    }

    private void loadMap() {

        blockTable.clear();

        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7a" + "Building map for " + scenario.getName() + " - " + scenario.getCreator()));

        int xDimension = scenario.getMapInfo().getX();
        int yDimension = scenario.getMapInfo().getY();
        int zDimension = scenario.getMapInfo().getZ();

        int count = 0;


        ArrayList<SimpleEntry> blockList = scenario.getMapInfo().getBlockList();

        for (int x = player.getX(); x < xDimension + player.getX(); x++) {
            for (int y = player.getY(); y < yDimension + player.getY(); y++) {
                for (int z = player.getZ(); z < zDimension + player.getZ(); z++) {
                    Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(x, y, z), SimpleEntryToState(blockList.get(count)));
                    count++;
                }
            }
        }
    }

    class IHateJava extends ClassLoader {

        private byte[] b;

        public void setByte(byte[] b) {
            this.b = b;
        }

        @Override
        public Class findClass(String name) {

            return defineClass(name, b, 0, b.length);
        }
    }

    private boolean createBots() {
        Constructor<Bot> botConstructor = null;
        try {
            botConstructor = botClasses.get(scenario.getBotInfo().botType).getConstructor(ScenarioPlayer.class, int.class);
            for (int botNumber = 0; botNumber < scenario.getBotInfo().botCount; botNumber++) {
                System.out.println(scenarioPlayer);
                this.scenarioPlayer.botList.add(botConstructor.newInstance(scenarioPlayer, botNumber));
            }
            return true;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }

    }

//    private Constructor getConstructorWhatAmIDoing() {
//
//    }

    private void loadBots() {

        ArrayList<SimpleEntry<BlockPos, BlockPos>> spawnRegions = scenario.getBotInfo().spawnRegions;

        ArrayList<BlockPos> spawnpoints = new ArrayList<>();

        for (SimpleEntry<BlockPos, BlockPos> SimpleEntry : spawnRegions) {
            BlockPos minPos = SimpleEntry.getKey().add(player);
            BlockPos maxPos = SimpleEntry.getValue().add(player);

            for (int x = minPos.getX(); x < maxPos.getX() + 1; x++) {
                for (int y = minPos.getY(); y < maxPos.getY() + 1; y++) {
                    for (int z = minPos.getZ(); z < maxPos.getZ() + 1; z++) {
                        spawnpoints.add(new BlockPos(x, y, z));
                    }
                }
            }
        }

        // creates the bots
        if (!createBots()) {
            //TODO: MAKE IT STOP IF IT CRASHES LMAO
        }

        scenarioPlayer.availableBlocks = new ArrayList<>(spawnpoints);

        for (Bot SimpleEntry : scenarioPlayer.botList) {
            SimpleEntry.setSpawnpoints(spawnpoints);
        }

    }


    private IBlockState SimpleEntryToState(SimpleEntry SimpleEntry) {
        if (!blockTable.containsKey(SimpleEntry.getKey())) {
            blockTable.put((String) SimpleEntry.getKey(), GameRegistry.findBlock("minecraft", (String) SimpleEntry.getKey()));
        }
        return blockTable.get((String) SimpleEntry.getKey()).getStateFromMeta(((Double) SimpleEntry.getValue()).intValue());
    }


}
