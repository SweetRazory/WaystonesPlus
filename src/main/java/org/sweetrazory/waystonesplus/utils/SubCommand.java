package org.sweetrazory.waystonesplus.utils;

import org.bukkit.entity.Player;

public interface SubCommand {
    String getName();

    void run(Player player, String[] args);
}
