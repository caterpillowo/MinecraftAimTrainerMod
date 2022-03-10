package caterpillow.trainer.scenarios;

import caterpillow.trainer.bots.BotReflectionCrap;
import caterpillow.trainer.util.PositionUtils;
import caterpillow.trainer.util.javaisdumb.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScenarioPlayer {


    private Scenario scenario;

    private BlockPos lastHit;
    private boolean hit = false;
    private int click = 0;

    public ScenarioPlayer(Scenario scenario) {
        this.scenario = scenario;
        this.botList = new ArrayList<Pair<Object, BotReflectionCrap>>();
        this.takenBlocks = new ArrayList<>();

    }

    public void start() {
        MinecraftForge.EVENT_BUS.register(this);
        for (Pair<Object, BotReflectionCrap> pair : botList) {
            pair.getValue().start();
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
        for (Pair<Object, BotReflectionCrap> pair : botList) {
            pair.getValue().onTick();
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent renderWorldLastEvent) {
//        lastHit = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
//        if (lastHit != null) {
//            Renderer.render(lastHit, hit ? Color.GREEN.getRGB() : Color.RED.getRGB());
//        }
        for (Pair<Object, BotReflectionCrap> pair : botList) {
            pair.getValue().onWorldRender(renderWorldLastEvent.partialTicks);
        }
    }

    @SubscribeEvent
    public void onClick(MouseEvent event) {
        if (event.buttonstate && event.button == 0) {
            MovingObjectPosition mop = Minecraft.getMinecraft().thePlayer.rayTrace(20, 1);
            if (mop.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK)) {
                for (Pair<Object, BotReflectionCrap> pair : botList) {
                    if (PositionUtils.isPosEqual(new BlockPos(pair.getValue().getPosition()), mop.getBlockPos())) {
                        hit = true;
                        click = 2;
                        lastHit = mop.getBlockPos();
                        pair.getValue().onHit(mop.hitVec);
                        return;
                    }
                }
                hit = false;
            }
            click = 1;
            lastHit = mop.getBlockPos();
        }
    }


    public boolean isBlockOccupied(BlockPos blockPos) {
        for (Pair<Object, BotReflectionCrap> pair : botList) {
            if (pair.getValue().getPosition().xCoord == blockPos.getX() && pair.getValue().getPosition().yCoord == blockPos.getY() && pair.getValue().getPosition().zCoord == blockPos.getZ()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<BlockPos> takenBlocks;

    public void updateTakenBlocks(BlockPos before, BlockPos after) {
        if (before != null)
            takenBlocks.removeIf(blockPos -> PositionUtils.isPosEqual(blockPos, before));
        takenBlocks.add(after);
    }

    public ArrayList<Pair<Object, BotReflectionCrap>> botList;

}
