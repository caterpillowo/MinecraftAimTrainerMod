package caterpillow.trainer.commands;

import caterpillow.AimTrainer;
import caterpillow.trainer.scenarios.Scenario;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class LoadScenario extends CommandBase {
    @Override
    public String getCommandName() {
        return "loadscenario";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "loadscenario <local scenario name>. Loads a local scenario.";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        if (strings.length == 0) {
            // needs to have a name duh
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7c" + "Please specify a file name."));
        } else {
            // loads local scenario and checks if the name is valid
            if (AimTrainer.instance.scenarioManager.loadScenario(String.join(" ", strings))) {
                // uses local scenario as base if it exists
                Scenario existingScenario = AimTrainer.instance.scenarioManager.getLoadedScenario(String.join(" ", strings));
                AimTrainer.instance.loadScenario(existingScenario);
            }
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
