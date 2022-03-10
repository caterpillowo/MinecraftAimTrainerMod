package caterpillow.trainer.commands;

import caterpillow.AimTrainer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class Refresh extends CommandBase {
    @Override
    public String getCommandName() {
        return "refresh";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "Reloads all the scenarios.";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        AimTrainer.instance.scenarioManager.refresh();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}