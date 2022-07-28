package caterpillow;

import caterpillow.trainer.*;
import caterpillow.trainer.scenarios.ScenarioCreator;
import caterpillow.trainer.scenarios.ScenarioLoader;
import caterpillow.trainer.scenarios.ScenarioManager;
import caterpillow.trainer.util.files.FileManager;
import caterpillow.trainer.scenarios.Scenario;
import com.google.gson.Gson;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = AimTrainer.MODID, version = AimTrainer.VERSION)
public class AimTrainer {

    // TODO: make one json file containing the names and links of all the other official scenarios (in pairs) so that when you launch the mod
    // it checks the file and downloads a list of all the scenarios
    // and u can browse through that list and download the scenario you wnt to play
    // either temporarily or also save it locally
    // if its already saved locally it will load from there
    // also include the mod build version in each scenario file

    // TODO: flying bat tracking scenario

    // TODO: bat, small slime, medium slime and zombie tracking
    // movement algorithm thing will be 3 motion values that change positively or negatively randomly and
    // will invert when touching a boundary


    public static final String MODID = "AimTrainer";
    public static final String VERSION = "1.0";

    public static AimTrainer instance;

    // different states of doing stuff
    public State state = State.IDLE;


    public CommandManager commandManager;
    public SettingsManager settingsManager;
    public ScenarioManager scenarioManager;
    public ScenarioCreator scenarioCreator;
    public FileManager fileManager;
    public ScenarioLoader scenarioLoader;

    // possible spawn points for blocks are selected as regions added together so u can do the pos1 pos2 command multiple times

    @EventHandler
    public void init(FMLInitializationEvent event) {
        instance = new AimTrainer();
    }

    public AimTrainer() {
        this.fileManager = new FileManager();
        this.commandManager = new CommandManager();
        this.settingsManager = new SettingsManager();
        this.scenarioManager = new ScenarioManager();

    }

    public boolean createScenario() {

        if (!state.equals(State.CREATING)) {
            // u sure u wanna restart
        }

        // need to add all the checks

        state = State.CREATING;
        scenarioCreator = new ScenarioCreator();
        return true;
    }

    public void confirm() {
        if (scenarioCreator != null) {
            scenarioCreator.confirm();
        }
    }

    public void add() {
        if (scenarioCreator != null) {
            scenarioCreator.add();
        }
    }

    public void skip() {
        if (scenarioCreator != null) {
            scenarioCreator.skip();
        }
    }

    public boolean createScenario(Scenario scenario) {
        if (!state.equals(State.CREATING)) {
            // u sure u wanna restart
        }

        // need to add all the checks

        if (scenarioLoader != null) {
            if (scenarioLoader.scenarioPlayer != null) {
                MinecraftForge.EVENT_BUS.unregister(scenarioLoader.scenarioPlayer);
            }
        }

        state = State.CREATING;
        scenarioCreator = new ScenarioCreator(scenario);
        scenarioLoader = null;
        return true;
    }

    public void loadScenario(Scenario scenario) {

        if (!state.equals(State.PLAYING)) {
            // u sure u wanna restart
        }

        // need to add all the checks
        if (scenarioLoader != null) {
            if (scenarioLoader.scenarioPlayer != null) {
                MinecraftForge.EVENT_BUS.unregister(scenarioLoader.scenarioPlayer);
            }
        }

        state = State.PLAYING;

        System.out.println(new Gson().toJson(scenario));

        scenarioLoader = new ScenarioLoader(scenario);
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        IDLE, PLAYING, CREATING;
    }


}
