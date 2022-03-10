package caterpillow.trainer.scenarios;

import caterpillow.trainer.scenarios.Scenario;
import caterpillow.trainer.util.files.FileManager;
import com.google.common.io.Files;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.io.File;
import java.util.ArrayList;

public class ScenarioManager {

    private ArrayList<String> scenarioNames;

    private ArrayList<Scenario> loadedScenarios;


    public ScenarioManager() {
        refresh();
    }

    // loads all the local scenarios
    public void refresh() {
        scenarioNames = new ArrayList<>();
        loadedScenarios = new ArrayList<>();

        loadScenarios();
    }

    public ArrayList<Scenario> getScenarios() {
        return loadedScenarios;
    }

    public Scenario getLoadedScenario(String name) {
         Scenario loadedScenario = loadedScenarios.stream().filter(scenario -> scenario.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        if (loadedScenario == null) {
            loadedScenario = loadedScenarios.stream().filter(scenario -> (scenario.getName() + " - " + scenario.getCreator()).equalsIgnoreCase(name)).findFirst().orElse(null);
        }
        return loadedScenario;
    }

    // loads all the local scenarios
    private void loadScenarios() {
        for (File fileEntry : FileManager.getSavesDir().listFiles()) {
            if (fileEntry.isFile())
                scenarioNames.add(Files.getNameWithoutExtension(fileEntry.getName()));
        }
    }

    // loads the given scenario
    // return true if the scenario is loaded (in the loadedScenarios list)
    public boolean loadScenario(String fileName) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7a" + "Loading scenario " + fileName));

        Scenario scenario = getLoadedScenario(fileName);

        if (scenario != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7a" + "Scenario reloaded " + fileName));
            return true;
        }

        String scenarioName = scenarioNames.stream().filter(name -> name.startsWith(fileName)).findFirst().orElse(null);

//        System.out.println("\n" + scenarioName + "\n" + fileName + "\n");

        if (scenarioName == null) {
            return false;
        }

        scenario = FileManager.readFromJson(FileManager.saveFileLocation(scenarioName), Scenario.class);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00A7a" + "Scenario loaded " + fileName));
        loadedScenarios.add(scenario);
        return true;
    }

}
