package caterpillow.trainer.util.files;


import caterpillow.trainer.scenarios.Scenario;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {
    private static Gson gson = new Gson();

    private static File ROOT_DIR = new File("Pillow Aim Trainer");

    private static File SAVES_DIR = new File(ROOT_DIR, "Saves");

    private static File STATS_DIR = new File(ROOT_DIR, "Stats");


    public FileManager() {
        if (!ROOT_DIR.exists())
            ROOT_DIR.mkdirs();
        if (!STATS_DIR.exists())
            STATS_DIR.mkdirs();
        if (!SAVES_DIR.exists())
            SAVES_DIR.mkdirs();
    }

    public static Gson getGson() {
        return gson;
    }

    public static File getRootDirR() {
        return ROOT_DIR;
    }

    public static File getStatsDir() {
        return STATS_DIR;
    }

    public static File getSavesDir() {
        return SAVES_DIR;
    }

    public static File saveFileLocation(Scenario scenario) {
        return new File(FileManager.getSavesDir(), scenario.getName() + " - " + scenario.getCreator() + ".json");
    }

    public static File saveFileLocation(String fileName) {
        return new File(FileManager.getSavesDir(), fileName + ".json");
    }

    // takes the exact file location (its directory and name.json and everything) and the data u want to put in it
    public static boolean writeToFile(File file, Object object) {
        try {
            System.out.println(file);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(gson.toJson(object).getBytes());
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // takes the exact file location (its directory and name.json and everything) and the class type
    public static <T> T readFromJson(File file, Class<T> c) {
        try {
            if (!file.exists())
                file.mkdirs();
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
                builder.append(line);
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
            return (T) gson.fromJson(builder.toString(), c);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
