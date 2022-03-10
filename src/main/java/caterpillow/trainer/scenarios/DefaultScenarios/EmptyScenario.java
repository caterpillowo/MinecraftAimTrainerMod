package caterpillow.trainer.scenarios.DefaultScenarios;

import caterpillow.trainer.bots.BotInfo;
import caterpillow.trainer.map.MapInfo;
import caterpillow.trainer.player.PlayerInfo;
import caterpillow.trainer.scenarios.Scenario;

import java.util.ArrayList;

public class EmptyScenario extends Scenario {

    // test empty scenario

    public EmptyScenario() {
        super("Empty Scenario", "caterpillow", new MapInfo(5, 5, 5, new ArrayList<>()), new PlayerInfo(), new BotInfo());
    }

}
