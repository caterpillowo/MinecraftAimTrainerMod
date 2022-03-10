package caterpillow.trainer.scenarios;

import caterpillow.trainer.bots.BotReflectionCrap;
import caterpillow.trainer.bots.DefaultBots;
import caterpillow.trainer.bots.testing.Tile;
import caterpillow.trainer.util.files.BotCompiler;
import caterpillow.trainer.util.files.ClassInfo;
import caterpillow.trainer.util.files.Javac;
import caterpillow.trainer.util.javaisdumb.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import scala.collection.script.Message;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ScenarioLoader {

    private Scenario scenario;

    public ScenarioPlayer scenarioPlayer;

    private HashMap<String, Block> blockTable = new HashMap<>();

    private BlockPos player;

    public ScenarioLoader(Scenario scenario) {
        this.scenario = scenario;

        this.scenarioPlayer = new ScenarioPlayer(scenario);

        player = new BlockPos(Minecraft.getMinecraft().thePlayer).down().west().south();

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


        ArrayList<Pair> blockList = scenario.getMapInfo().getBlockList();

        for (int x = player.getX(); x < xDimension + player.getX(); x++) {
            for (int y = player.getY(); y < yDimension + player.getY(); y++) {
                for (int z = player.getZ(); z < zDimension + player.getZ(); z++) {
                    Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(x, y, z), pairToState(blockList.get(count)));
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
        try {
//            Class<?> compiledBotClass = BotCompiler.getBotFromSource(scenario.getBotInfo().botCode);
//            Class<?> compiledBotClass = BotCompiler.getBotFromSource(DefaultBots.TILE);

            List<ClassInfo> classInfoList = Javac.compile(Arrays.asList(DefaultBots.TILE));

            IHateJava iHateJava = new IHateJava();
            iHateJava.setByte(classInfoList.get(0).bytes);
            Class compiledBotClass = iHateJava.findClass(classInfoList.get(0).className);


            for (int botNumber = 0; botNumber < scenario.getBotInfo().botCount; botNumber++) {
                BotReflectionCrap botReflectionCrap = new BotReflectionCrap();

//                System.out.println(Arrays.deepToString(compiledBotClass.getDeclaredConstructors()));
//                System.out.println(compiledBotClass.getConstructors()[0].getParameterTypes());
//                Class<?>[] parameters = compiledBotClass.getConstructors()[0].getParameterTypes();
//                System.out.println(Arrays.deepToString(compiledBotClass.getConstructors()[0].getParameters()));
//                System.out.println(Modifier.toString(compiledBotClass.getConstructors()[0].getModifiers()));
//                System.out.println(parameters[0].isAssignableFrom(ScenarioPlayer.class));
//
//                Constructor constructor = compiledBotClass.getDeclaredConstructor(new Class<?>[]{ScenarioPlayer.class, BotReflectionCrap.class, int.class}); //ScenarioPlayer.class, BotReflectionCrap.class, int.class
//                Object bot = constructor.newInstance(scenarioPlayer, botReflectionCrap, botNumber); //scenarioPlayer, botReflectionCrap, botNumber


                Constructor constructor = Tile.class.getDeclaredConstructor(ScenarioPlayer.class, BotReflectionCrap.class, int.class);
                Object bot = constructor.newInstance(scenarioPlayer, botReflectionCrap, botNumber);


                botReflectionCrap.setObj(bot);
                System.out.println(scenarioPlayer);
                this.scenarioPlayer
                        .botList.add(
                        new Pair<>(
                                bot,
                                botReflectionCrap));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    private Constructor getConstructorWhatAmIDoing() {
//
//    }

    private void loadBots() {

        ArrayList<Pair<BlockPos, BlockPos>> spawnRegions = scenario.getBotInfo().spawnRegions;

        ArrayList<BlockPos> spawnpoints = new ArrayList<>();

        for (Pair<BlockPos, BlockPos> pair : spawnRegions) {
            BlockPos minPos = pair.getKey().add(player);
            BlockPos maxPos = pair.getValue().add(player);

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

        for (Pair<Object, BotReflectionCrap> pair : scenarioPlayer.botList) {
            pair.getValue().setSpawnpoints(spawnpoints);
        }

    }


    private IBlockState pairToState(Pair pair) {
        if (!blockTable.containsKey(pair.getKey())) {
            blockTable.put((String) pair.getKey(), GameRegistry.findBlock("minecraft", (String) pair.getKey()));
        }
        return blockTable.get((String) pair.getKey()).getStateFromMeta(((Double) pair.getValue()).intValue());
    }


}
