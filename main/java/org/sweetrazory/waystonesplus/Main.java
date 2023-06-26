package org.sweetrazory.waystonesplus;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.sweetrazory.waystonesplus.memoryhandlers.CommandManager;
import org.sweetrazory.waystonesplus.memoryhandlers.EventController;
import org.sweetrazory.waystonesplus.memoryhandlers.MemoryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends JavaPlugin implements Listener {
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        new MemoryManager();
        EventController eventController = new EventController(MemoryManager.getWaystoneMemory(), MemoryManager.getInventoryMemory());
        getServer().getPluginManager().registerEvents(eventController, this);

        MemoryManager.getWaystoneMemory();

        List<String> commandAliases = new ArrayList<>();

        commandAliases.add("waystones");
        commandAliases.add("waystone");
        commandAliases.add("wsp");
        commandAliases.add("waystonesplus");
        commandAliases.add("waystoneplus");

        for (String commandAlias : commandAliases) {
            Objects.requireNonNull(getCommand(commandAlias)).setExecutor(new CommandManager(MemoryManager.getWaystoneMemory()));
        }
    }
}

