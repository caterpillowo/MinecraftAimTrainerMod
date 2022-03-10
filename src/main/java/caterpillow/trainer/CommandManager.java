package caterpillow.trainer;

import caterpillow.trainer.commands.*;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.ClientCommandHandler;

public class CommandManager {

    // deals with every single command until a gui is made

    public BlockPos pos1;
    public BlockPos pos2;

    public CommandManager() {
        ClientCommandHandler.instance.registerCommand(new CreateScenario());
        ClientCommandHandler.instance.registerCommand(new SetPos1());
        ClientCommandHandler.instance.registerCommand(new SetPos2());
        ClientCommandHandler.instance.registerCommand(new Confirm());
        ClientCommandHandler.instance.registerCommand(new LoadScenario());
        ClientCommandHandler.instance.registerCommand(new TestCommand());
        ClientCommandHandler.instance.registerCommand(new Add());
        ClientCommandHandler.instance.registerCommand(new Skip());
        ClientCommandHandler.instance.registerCommand(new Refresh());

    }

}
