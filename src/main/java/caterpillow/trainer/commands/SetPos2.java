package caterpillow.trainer.commands;

import caterpillow.AimTrainer;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;

public class SetPos2 extends CommandBase {
    @Override
    public String getCommandName() {
        return "pos2";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "Sets the position of the second coordinate of whatever ur trying to do.";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        MovingObjectPosition mop = Minecraft.getMinecraft().thePlayer.rayTrace(100, 1);
        if (mop.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK)) {

            AimTrainer.instance.commandManager.pos2 = mop.getBlockPos();

            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Pos2 set to " + mop.getBlockPos().getX() + ", " + mop.getBlockPos().getY() + ", " + mop.getBlockPos().getZ()));
        } else {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Please put your crosshair over a valid block"));
        }
    }
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }
}
