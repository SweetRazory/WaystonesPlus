package org.sweetrazory.waystonesplus;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.sweetrazory.waystonesplus.memoryhandlers.*;
import org.sweetrazory.waystonesplus.menu.MenuListener;
import org.sweetrazory.waystonesplus.menu.MenuManager;
import org.sweetrazory.waystonesplus.utils.ColoredText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WaystonesPlus extends JavaPlugin implements Listener {
    public static CooldownManager cooldownManager = new CooldownManager();
    public static ConfigManager configMemory = new ConfigManager();
    public static MenuManager menuManager = new MenuManager();
    private static WaystonesPlus instance;
    private DatabaseManager databaseManager;

    public static WaystonesPlus getInstance() {
        return instance;
    }

    public static Logger Logger() {
        return getInstance().getLogger();
    }

    @Override
    public void onEnable() {
        instance = this;
        loadWaystonesConfig();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        ConfigManager.loadConfig();
        LangManager.loadConfig();
        databaseManager = new DatabaseManager();
        databaseManager.initializeDatabase();
        databaseManager.migrateWaystones();

        String bukkitVersion = Bukkit.getVersion();
        if (!bukkitVersion.contains("1.19.4") && !bukkitVersion.contains("1.20")) {
            getLogger().warning(LangManager.versionWarning);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        new WaystoneMemory();
        new ColoredText();

        EventController eventController = new EventController();
        getServer().getPluginManager().registerEvents(eventController, this);
        getServer().getPluginManager().registerEvents(new MenuListener(menuManager), this);

        List<String> commandAliases = new ArrayList<String>() {{
            add("waystones");
            add("waystone");
            add("wsp");
            add("waystonesplus");
            add("waystoneplus");
        }};

        for (String commandAlias : commandAliases) {
            getCommand(commandAlias).setExecutor(new CommandManager());
        }
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.closeConnection();
        }
    }

    public void loadWaystonesConfig() {
        File waystonesFile = new File(getDataFolder(), "waystones.yml");
        File configFile = new File(getDataFolder(), "config.yml");
        File localizationFile = new File(getDataFolder(), "localization.yml");
        if (!localizationFile.exists()) {
            saveResource("localization.yml", false);
        }

        if (!waystonesFile.exists()) {
            if (configFile.exists()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                saveWaystonesConfig(config);

                configFile.delete();
                saveResource("config.yml", false);

                return;
            }

            saveResource("waystones.yml", false);
        } else if (configFile.exists()) {
            YamlConfiguration waystonesConfig = YamlConfiguration.loadConfiguration(waystonesFile);
            YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            if (waystonesConfig.isConfigurationSection("waystones") && config.isConfigurationSection("waystones")) {
                config.set("waystones", null);
                saveConfig();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(waystonesFile);
        config.options().copyDefaults(true);
        saveWaystonesConfig(config);
    }


    public void saveWaystonesConfig(FileConfiguration config) {
        try {
            config.save(new File(getDataFolder(), "waystones.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

