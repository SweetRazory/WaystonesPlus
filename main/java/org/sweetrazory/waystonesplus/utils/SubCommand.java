package org.sweetrazory.waystonesplus.utils;

import org.bukkit.entity.Player;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;

public interface SubCommand {
    String getName();

    void run(Player player, String[] args, WaystoneMemory waystoneMemory);
}
