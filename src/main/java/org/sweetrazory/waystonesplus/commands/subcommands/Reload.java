package org.sweetrazory.waystonesplus.commands.subcommands;

import org.bukkit.entity.Player;
import org.sweetrazory.waystonesplus.memoryhandlers.ConfigManager;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
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
            player.sendMessage(ColoredText.getText(LangManager.reload));
            ConfigManager.loadConfig();
            WaystoneMemory.initializeWaystones();
        } else {
            player.sendMessage(ColoredText.getText(LangManager.noPermission));
        }
    }
}
