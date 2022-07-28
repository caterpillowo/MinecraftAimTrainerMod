package caterpillow.trainer.scenarios;

import caterpillow.trainer.bots.Bot;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

public class ScenarioPlayer {

    private Scenario scenario;

    private BlockPos lastHit;
    private boolean hit = false;
    private int click = 0;

    public ScenarioPlayer(Scenario scenario) {
        this.scenario = scenario;
        this.botList = new ArrayList<>();
        this.availableBlocks = new ArrayList<>();

    }

    public void start() {
        MinecraftForge.EVENT_BUS.register(this);
        for (Bot pair : botList) {
            pair.start();
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent clientTick) {
        if (click == 1) {
            Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0f, 1.0f);
            click = 0;
        } else if (click == 2) {
            Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0f, 2.0f);
            click = 0;
        }
        for (Bot pair : botList) {
            pair.onTick();
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent renderWorldLastEvent) {
//        lastHit = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
//        if (lastHit != null) {
//            Renderer.render(lastHit, hit ? Color.GREEN.getRGB() : Color.RED.getRGB());
//        }
        for (Bot pair : botList) {
            pair.onWorldRender(renderWorldLastEvent.partialTicks);
        }
    }

    @SubscribeEvent
    public void onClick(MouseEvent event) {
        if (event.buttonstate && event.button == 0) {
            System.out.println("scenarioplayer click");
            MovingObjectPosition mop = Minecraft.getMinecraft().thePlayer.rayTrace(20, 1);
            if (mop.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK)) {
                System.out.println("hit some block");
                for (Bot pair : botList) {
                    if (new Vec3i(pair.getPosition().xCoord, pair.getPosition().yCoord, pair.getPosition().zCoord).equals(mop.getBlockPos())) {
                        System.out.println("hit is true");
                        hit = true;
                        click = 2;
                        lastHit = mop.getBlockPos();
                        pair.onHit(mop.hitVec);
                        return;
                    }
                }
                hit = false;
            }
//            if (mop.typeOfHit.equals(MovingObjectPosition.MovingObjectType.ENTITY)) {
//                for (Bot pair : botList) {
//                    if (pair.getPosition().equals(mop.getBlockPos())) {
//                        hit = true;
//                        click = 2;
//                        lastHit = mop.getBlockPos();
//                        pair.onHit(mop.hitVec);
//                        return;
//                    }
//                }
//                hit = false;
//            }
            click = 1;
            lastHit = mop.getBlockPos();
        }
    }


    public boolean isBlockOccupied(BlockPos blockPos) {
        for (Bot pair : botList) {
            if (pair.getPosition().xCoord == blockPos.getX() && pair.getPosition().yCoord == blockPos.getY() && pair.getPosition().zCoord == blockPos.getZ()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<BlockPos> availableBlocks;

    public void updateAvailableBlocks(BlockPos before, BlockPos after) {
        if (before != null)
            availableBlocks.add(before);
        availableBlocks.removeIf(blockPos -> blockPos.equals(after));
    }

    public ArrayList<Bot> botList;

}
