package org.sweetrazory.waystonesplus.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.sweetrazory.waystonesplus.utils.SubCommand;

public class Help implements SubCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void run(Player player, String[] args) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4--------[ &6WSP Help (1/1) &4]--------"));
        if (player.hasPermission("waystonesplus.command.get") || player.isOp()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/wsp get <Waystone Type> [custom name] - gives you a Waystone of that type, and custom name (If you set any)."));
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/wsp help [page] - brings out this message."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4-------------------------------"));
    }
}
