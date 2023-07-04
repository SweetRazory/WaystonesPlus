package org.sweetrazory.waystonesplus.commands.subcommands;

import org.bukkit.entity.Player;
import org.sweetrazory.waystonesplus.memoryhandlers.ConfigManager;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.SubCommand;

public class Reload implements SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void run(Player player, String[] args) {
        if (player.hasPermission("waystonesplus.command.reload") || player.isOp()) {
            player.sendMessage(ColoredText.getText("&cReloading WaystonesPlus's config and waystones."));
            ConfigManager.loadConfig();
            WaystoneMemory.initializeWaystones();
        }
    }
}
