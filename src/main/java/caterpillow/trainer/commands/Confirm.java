package caterpillow.trainer.commands;

import caterpillow.AimTrainer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class Confirm extends CommandBase {
    @Override
    public String getCommandName() {
        return "confirm";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "Confirms whatever needs to be confirmed.";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        AimTrainer.instance.confirm();
    }

    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }
}
