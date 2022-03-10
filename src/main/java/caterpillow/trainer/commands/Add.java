package caterpillow.trainer.commands;

import caterpillow.AimTrainer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class Add extends CommandBase {
    @Override
    public String getCommandName() {
        return "add";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "Adds whatever needs to be added.";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        AimTrainer.instance.add();
    }

    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }
}