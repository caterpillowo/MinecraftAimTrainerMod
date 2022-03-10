package caterpillow.trainer.commands;

import caterpillow.AimTrainer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class Skip extends CommandBase {
    @Override
    public String getCommandName() {
        return "skip";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "Skips whatever needs to be skipped.";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        AimTrainer.instance.skip();
    }

    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }
}
