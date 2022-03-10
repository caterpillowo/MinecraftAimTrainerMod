package caterpillow.trainer.commands;

import caterpillow.AimTrainer;
import caterpillow.trainer.scenarios.Scenario;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CreateScenario extends CommandBase {
    @Override
    public String getCommandName() {
        return "createscenario";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "createscenario <scenario name>. Creates a scenario based on an existing scenario. If scenario name left blank, it will create a brand new scenario.";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        if (strings.length == 0) {
            // creates new empty scenario
            AimTrainer.instance.createScenario();
        } else {
            // uses local scenario as base if it exists
            if (AimTrainer.instance.scenarioManager.loadScenario(String.join(" ", strings))) {
                Scenario existingScenario = AimTrainer.instance.scenarioManager.getLoadedScenario(String.join(" ", strings));
                AimTrainer.instance.createScenario(existingScenario);
            } else {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7c" + "Please specify an existing scenario."));
            }
        }
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
