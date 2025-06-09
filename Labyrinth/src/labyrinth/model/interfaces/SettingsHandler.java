package labyrinth.model.interfaces;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public interface SettingsHandler {

    static String getUserName() {
        Properties settings = readSettings();
        return settings.getProperty("username");
    }

    static int getTileSize() {
        Properties settings = readSettings();
        return Integer.parseInt(settings.getProperty("map_tile_size"));
    }

    static double getPlayerSpeed() {
        Properties settings = readSettings();
        return Double.parseDouble(settings.getProperty("player_speed"));
    }

    static double getGhostSpeed() {
        Properties settings = readSettings();
        return Double.parseDouble(settings.getProperty("ghost_speed"));
    }

    static String getUpKey() {
        Properties settings = readSettings();
        return settings.getProperty("up_key");
    }

    static String getLeftKey() {
        Properties settings = readSettings();
        return settings.getProperty("left_key");
    }

    static String getRightKey() {
        Properties settings = readSettings();
        return settings.getProperty("right_key");
    }

    static String getDownKey() {
        Properties settings = readSettings();
        return settings.getProperty("down_key");
    }

    static String getSprintKey() {
        Properties settings = readSettings();
        return settings.getProperty("sprint_key");
    }

    static int getRandomMazeWidth() {
        Properties settings = readSettings();
        return Integer.parseInt(settings.getProperty("random_map_width"));
    }

    static int getRandomMazeHeight() {
        Properties settings = readSettings();
        return Integer.parseInt(settings.getProperty("random_map_height"));
    }

    private static Properties readSettings() {
        Properties properties = new Properties();

        String path = "resources/settings.config";
        try (FileInputStream fis = new FileInputStream(path)) {properties.load(fis);}
        catch (IOException e) {System.out.println("Settings not found");}

        return properties;

    }


}
