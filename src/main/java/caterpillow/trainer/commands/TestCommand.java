package caterpillow.trainer.commands;

import caterpillow.AimTrainer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.MovingObjectPosition;

public class TestCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "test";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "tests shit";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {

        if (Minecraft.getMinecraft().objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK)) {

            BlockPos pos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();





            IBlockState blockState = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getBlockState(pos);


            Block block = blockState.getBlock();

            int meta = block.getMetaFromState(blockState);

            IBlockState iBlockState = block.getStateFromMeta(meta);

            System.out.println(block);
            System.out.println(blockState);
            System.out.println(meta);
            System.out.println(iBlockState);

            Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(pos.east(), iBlockState);
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}