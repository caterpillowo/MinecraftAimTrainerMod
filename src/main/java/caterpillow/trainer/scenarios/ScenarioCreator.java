package caterpillow.trainer.scenarios;

import caterpillow.AimTrainer;
import caterpillow.trainer.bots.BotInfo;
import caterpillow.trainer.map.MapInfo;
import caterpillow.trainer.util.Renderer;
import caterpillow.trainer.util.files.FileManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class ScenarioCreator {

    protected Minecraft mc = Minecraft.getMinecraft();

    // this class handles the creating of a scenario
    private Scenario scenario;

    private ArrayList<Stage> stages;

    private int currentStage;

    private ArrayList<String> messages;

    private BlockPos minPos;
    private BlockPos maxPos;

    private int botType = 1;

    public ScenarioCreator() {
        setup();
    }

    public ScenarioCreator(Scenario scenario) {
        this.scenario = scenario;
        setup();
    }

    private void setup() {
        // pretty obvious
        MinecraftForge.EVENT_BUS.register(this);

        // begins logging
        messages = new ArrayList<String>();

        // creates the scenario for editing (duh)
        scenario = new Scenario();

        // sets name to player
        scenario.setCreator(Minecraft.getMinecraft().getSession().getUsername());

        // loads the stages needed to create a scenario into a list
        loadStages();

        // pretty obvious
        currentStage = 0;

        doNextStage();
    }

    public void confirm() {
        if (stages.get(currentStage).confirm()) {
            currentStage++;
            while (currentStage < stages.size() && !stages.get(currentStage).shouldShow()) {
                currentStage++;
            }
            if (currentStage < stages.size()) {
                doNextStage();
                messages.clear();
            } else {
                // havent done anythign here yet
                finish();
            }
        }
    }

    public void add() {
        stages.get(currentStage).add();
    }

    public void skip() {
        currentStage++;
        if (currentStage < stages.size()) {
            doNextStage();
            messages.clear();
        } else {
            // havent done anythign here yet
            finish();
        }
    }

    private void doNextStage() {
        stages.get(currentStage).start();
    }

    @SubscribeEvent
    public void onMessageSent(ServerChatEvent event) {
        messages.add(0, event.message);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (currentStage < stages.size())
            stages.get(currentStage).clientTick();
    }

    @SubscribeEvent
    public void on2D(TickEvent.RenderTickEvent event) {
        if (currentStage < stages.size())
            stages.get(currentStage).render2D();
    }

    @SubscribeEvent
    public void on3D(RenderWorldLastEvent event) {
        if (currentStage < stages.size())
            stages.get(currentStage).render3D(event.partialTicks);
    }

    private void loadStages() {
        stages = new ArrayList<Stage>();

        stages.clear();


        // stage 1: set a name for the scenario
        stages.add(new Stage("Set Name", "Type a name into chat for the scenario you are creating. Do /confirm to confirm.") {
            @Override
            public boolean confirm() {
                if (!messages.isEmpty()) {
                    scenario.setName(messages.get(0));
                    return true;
                } else {
                    printToChatRed("Please type a name in chat.");
                    return false;
                }

            }
        });

        // stage 2: select rectangular area to be map
        stages.add(new Stage("Set Map", "Please select two opposing corners of your map using /pos1 and /pos2. Do /confirm to confirm.") {

            private BlockPos pos1;
            private BlockPos pos2;

            @Override
            public void start() {
                AimTrainer.instance.commandManager.pos1 = null;
                AimTrainer.instance.commandManager.pos2 = null;
                minPos = null;
                maxPos = null;
                super.start();
            }

            @Override
            public void clientTick() {
                pos1 = AimTrainer.instance.commandManager.pos1;
                pos2 = AimTrainer.instance.commandManager.pos2;
                if (pos1 != null && pos2 != null) {
                    minPos = new BlockPos(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
                    maxPos = new BlockPos(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
                }
            }

            @Override
            public boolean confirm() {
                if (AimTrainer.instance.commandManager.pos1 != null && AimTrainer.instance.commandManager.pos2 != null) {
                    // use super complex coding to save the selected area into a list of blocks

                    printToChatGreen("minPos: " + minPos.getX() + ", " + minPos.getY() + ", " + minPos.getZ());
                    printToChatGreen("maxPos: " + maxPos.getX() + ", " + maxPos.getY() + ", " + maxPos.getZ());

                    int xDiff = maxPos.getX() - minPos.getX() + 1;
                    int yDiff = maxPos.getY() - minPos.getY() + 1;
                    int zDiff = maxPos.getZ() - minPos.getZ() + 1;

                    ArrayList<SimpleEntry> blockList = new ArrayList<>();

                    for (int x = 0; x < xDiff; x++) {
                        for (int y = 0; y < yDiff; y++) {
                            for (int z = 0; z < zDiff; z++) {
//                                blockList.add(getBlock(new BlockPos(x + minPos.getX(), y + minPos.getY(), z + minPos.getZ())).getRegistryName().split(":")[1]);
//                                blockList.add((IBlockState) getBlock(new BlockPos(x + minPos.getX(), y + minPos.getY(), z + minPos.getZ())).getBlockState());
//                                blockList.add(Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getBlockState());
//                                System.out.println(Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getBlockState(new BlockPos(x + minPos.getX(), y + minPos.getY(), z + minPos.getZ())).getProperties());

                                BlockPos pos = new BlockPos(x + minPos.getX(), y + minPos.getY(), z + minPos.getZ());
                                IBlockState blockState = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getBlockState(pos);
                                Block block = blockState.getBlock();

                                int meta = block.getMetaFromState(blockState);

                                blockList.add(new SimpleEntry(block.getRegistryName().split(":")[1], meta));

                            }
                        }
                    }

                    scenario.setMapInfo(new MapInfo(xDiff, yDiff, zDiff, blockList));
                    AimTrainer.instance.commandManager.pos1 = null;
                    AimTrainer.instance.commandManager.pos2 = null;
                    return true;
                }
                printToChatRed("Make sure to select two corners using the commands.");
                return false;
            }

            @Override
            public void render3D(float partialTicks) {

                if (minPos != null && maxPos != null) {
                    Renderer.render(minPos, maxPos, Color.GREEN.getRGB());
                    return;
                }
                if (pos1 != null) {
                    Renderer.render(pos1, Color.GREEN.getRGB());
                }
                if (pos2 != null) {
                    Renderer.render(pos2, Color.GREEN.getRGB());
                }

            }
        });

        // stage 3: select what bot type to use
        stages.add(new Stage("Bot Type", "Please enter the name of the bot type (only \"Tile\" for now). Do /confirm to confirm.") {
            @Override
            public void start() {
                scenario.setBotInfo(new BotInfo());
                super.start();
            }


            @Override
            public boolean confirm() {
                if (!messages.isEmpty()) {
                    scenario.getBotInfo().botType = messages.get(0);
                    return true;
                } else {
                    printToChatRed("Please type a valid number in chat.");
                    return false;
                }
            }
        });

        // stage 4.1.1: set how many bots for bot 1
        // TODO: need to add check to make sure there arent more bots than spawn places
        stages.add(new Stage("Set Bot Number (Bot Type 1)", "Type how many bots you want. Do /confirm to confirm.") {

            @Override
            public boolean shouldShow() {
                return AimTrainer.instance.scenarioCreator.botType == 1;
            }

            @Override
            public boolean confirm() {
                if (!messages.isEmpty()) {
                    try {
                        scenario.getBotInfo().botCount = Integer.parseInt(messages.get(0));
                    } catch (Exception e) {
                        printToChatRed("Please type a valid number in chat.");
                    }
                    return true;
                } else {
                    printToChatRed("Please type a valid number in chat.");
                    return false;
                }
            }
        });

        // stage 4.1.2: set bot spawn regions
        // TODO: need to add check to make sure there arent more bots than spawn places
        stages.add(new Stage("Set Bot Spawn Regions (Bot Type 1)", "Do commands /pos1 and /pos2 to set a region. Do /add to add selected region. Do /confirm to add region and end.") {

            ArrayList<SimpleEntry<BlockPos, BlockPos>> spawnRegions = new ArrayList<>();

            private BlockPos pos1;
            private BlockPos pos2;

            @Override
            public void start() {
                AimTrainer.instance.commandManager.pos1 = null;
                AimTrainer.instance.commandManager.pos2 = null;
                super.start();
            }

            @Override
            public boolean shouldShow() {
                return AimTrainer.instance.scenarioCreator.botType == 1;
            }

            @Override
            public void add() {
                pos1 = AimTrainer.instance.commandManager.pos1;
                pos2 = AimTrainer.instance.commandManager.pos2;
                if (minPos != null && pos1 != null && pos2 != null) {
                    // adds a Entry of coordinates relative to the minPos

                    BlockPos minPos1 = new BlockPos(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
                    BlockPos maxPos2 = new BlockPos(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));

                    scenario.getBotInfo().spawnRegions.add(new SimpleEntry<>(minPos1.subtract(minPos), maxPos2.subtract(minPos)));
                    printToChatGreen("Added region " + minPos1.subtract(minPos) + " to " + maxPos2.subtract(minPos));
                    AimTrainer.instance.commandManager.pos1 = null;
                    AimTrainer.instance.commandManager.pos2 = null;
                } else {
                    printToChatGreen("Please select both positions.");
                }
            }

            @Override
            public boolean confirm() {
                pos1 = AimTrainer.instance.commandManager.pos1;
                pos2 = AimTrainer.instance.commandManager.pos2;
                if (minPos != null && pos1 != null && pos2 != null) {
                    // adds a Entry of coordinates relative to the minPos
                    BlockPos minPos1 = new BlockPos(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
                    BlockPos maxPos2 = new BlockPos(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));

                    scenario.getBotInfo().spawnRegions.add(new SimpleEntry<>(minPos1.subtract(minPos), maxPos2.subtract(minPos)));
                    printToChatGreen("Added region " + minPos1.subtract(minPos) + " to " + maxPos2.subtract(minPos));
                    AimTrainer.instance.commandManager.pos1 = null;
                    AimTrainer.instance.commandManager.pos2 = null;
                    return true;
                } else {
                    printToChatGreen("Please select both positions.");
                    return false;
                }
            }

            @Override
            public void clientTick() {
                pos1 = AimTrainer.instance.commandManager.pos1;
                pos2 = AimTrainer.instance.commandManager.pos2;
            }

            @Override
            public void render3D(float partialTicks) {

                if (pos1 != null && pos2 != null) {
                    BlockPos minPos1 = new BlockPos(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
                    BlockPos maxPos2 = new BlockPos(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
                    Renderer.render(minPos1, maxPos2, Color.GREEN.getRGB());
                    return;
                }
                if (pos1 != null) {
                    Renderer.render(pos1, Color.GREEN.getRGB());
                }
                if (pos2 != null) {
                    Renderer.render(pos2, Color.GREEN.getRGB());
                }

            }

        });
    }

    public Block getBlock(BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
    }

    // each stage has its own conditions and stuff
    public abstract class Stage {
        private String parameterName;
        private String instruction;

        public Stage(String parameterName, String instruction) {
            this.parameterName = parameterName;
            this.instruction = instruction;
        }

        // this is so that we can skip this stage if we dont need it
        public boolean shouldShow() {
            return true;
        }

        public void start() {
            printToChatGreen(instruction);
        }

        // this confirms if the stage has been completed
        public boolean confirm() {
            return false;
        }

        // renders stuff if needed
        public void render3D(float partialTicks) {
        }

        // renders stuff if needed
        public void render2D() {
        }

        // basic tick stuff
        public void clientTick() {
        }

        // allows you to stack inputs
        public void add() {

        }

    }

    private void finish() {
        // do whatever is needed to finish creating this scenario
        // currently just prints the scenario to chat
        System.out.println(scenario.getMapInfo().getX() + " : " + scenario.getMapInfo().getY() + " : " + scenario.getMapInfo().getZ());
        System.out.println(scenario.getMapInfo().getBlockList());

        FileManager.writeToFile(FileManager.saveFileLocation(scenario), scenario);

        AimTrainer.instance.scenarioManager.refresh();

        MinecraftForge.EVENT_BUS.unregister(this);
    }

    private void printToChatGreen(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7a" + msg));
    }

    private void printToChatRed(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7c" + msg));
    }
}
