package org.sweetrazory.waystonesplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.sweetrazory.waystonesplus.memoryhandlers.CommandManager;
import org.sweetrazory.waystonesplus.memoryhandlers.EventController;
import org.sweetrazory.waystonesplus.memoryhandlers.MemoryManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WaystonesPlus extends JavaPlugin implements Listener {
    private static WaystonesPlus instance;

    public static WaystonesPlus getInstance() {
        return instance;
    }

    public static Logger Logger() {
        return getInstance().getLogger();
    }

    public static String coloredText(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void loadWaystonesConfig() {
//        String[] additionalFiles = {"file1.yml", "file2.yml", "file3.yml"};
//
//        for (String fileName : additionalFiles) {
//            File additionalFile = new File(getDataFolder(), fileName);
//
//            if (!additionalFile.exists()) {
//                saveResource(fileName, false);
//            }
//        } saved for later if more files come in

        File waystonesFile = new File(getDataFolder(), "waystones.yml");
        File configFile = new File(getDataFolder(), "config.yml");

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

    @Override
    public void onEnable() {
        instance = this;
        loadWaystonesConfig();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        String bukkitVersion = Bukkit.getVersion();
        if (!bukkitVersion.contains("1.19.4") && !bukkitVersion.contains("1.20")) {
            getLogger().warning("[WaystonesPlus] This plugin only supports 1.19.4 or higher versions!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        EventController eventController = new EventController(MemoryManager.getWaystoneMemory());
        getServer().getPluginManager().registerEvents(eventController, this);

        List<String> commandAliases = new ArrayList<>();

        commandAliases.add("waystones");
        commandAliases.add("waystone");
        commandAliases.add("wsp");
        commandAliases.add("waystonesplus");
        commandAliases.add("waystoneplus");

        for (String commandAlias : commandAliases) {
            getCommand(commandAlias).setExecutor(new CommandManager(MemoryManager.getWaystoneMemory()));
        }
    }

//    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

//    }
}

