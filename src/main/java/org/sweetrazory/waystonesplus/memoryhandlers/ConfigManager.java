package org.sweetrazory.waystonesplus.memoryhandlers;

import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class ConfigManager {
    public static int lootSpawnChance;
    public static int minimumDistance;
    public static Visibility defaultVisibility;
    public static boolean enableCrafting;
    public static boolean enableNotification;
    public static int teleportCountdown;
    public static int waystonePlaceCooldown;
    public static int waystoneTeleportCooldown;

    public static void loadConfig() {
        File configFile = new File(WaystonesPlus.getInstance().getDataFolder().getAbsolutePath() + File.separator + "config.yml");
        try {
            FileInputStream fis = new FileInputStream(configFile);
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(fis);

            lootSpawnChance = (int) config.getOrDefault("loot-chance", 25);
            minimumDistance = (int) config.getOrDefault("minimum-distance", 50);
            defaultVisibility = Visibility.fromString((String) config.getOrDefault("default-visibility", Visibility.PRIVATE.name()));
            enableCrafting = (boolean) config.getOrDefault("enable-crafting", true);
            enableNotification = (boolean) config.getOrDefault("new-waystone-notification", true);
            teleportCountdown = (int) config.getOrDefault("teleport-countdown", 3);
            waystonePlaceCooldown = (int) config.getOrDefault("waystone-place-cooldown", 30);
            waystoneTeleportCooldown = (int) config.getOrDefault("teleport-cooldown", 30);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
