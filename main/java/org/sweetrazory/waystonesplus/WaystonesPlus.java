package org.sweetrazory.waystonesplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.sweetrazory.waystonesplus.memoryhandlers.CommandManager;
import org.sweetrazory.waystonesplus.memoryhandlers.EventController;
import org.sweetrazory.waystonesplus.memoryhandlers.MemoryManager;

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

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        String bukkitVersion = Bukkit.getVersion();

        if (!bukkitVersion.contains("1.19.4") && !bukkitVersion.contains("1.20")) {
            getLogger().warning("[WaystonesPlus] This plugin only supports 1.19.4 or higher versions!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        new MemoryManager();
        EventController eventController = new EventController(MemoryManager.getWaystoneMemory());
        getServer().getPluginManager().registerEvents(eventController, this);

        MemoryManager.getWaystoneMemory();

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

