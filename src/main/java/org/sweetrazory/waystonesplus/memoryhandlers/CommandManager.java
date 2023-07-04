package org.sweetrazory.waystonesplus.memoryhandlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.sweetrazory.waystonesplus.commands.subcommands.*;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.utils.SubCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new Get());
        subCommands.add(new Help());
        subCommands.add(new Rename());
        subCommands.add(new Reload());
        subCommands.add(new SetVisibility());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length > 0) {
            for (SubCommand subCommand : getSubCommands()) {
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    subCommand.run(player, args);
                }
            }
        } else {
            new Help().run(player, args);
        }

        return false;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            List<String> completions = new ArrayList<>();
            final List<String> commands = new ArrayList<>();
            Player player = (Player) sender;
            if (args.length == 1) {
                if (player.hasPermission("waystonesplus.command.get") || player.isOp()) {
                    commands.add("get");
                }
                if (player.hasPermission("waystonesplus.command.visibility") || player.isOp()) {
                    commands.add("setvisibility");
                }
                if (player.hasPermission("waystonesplus.command.rename") || player.isOp()) {
                    commands.add("rename");
                }
                if (player.hasPermission("waystonesplus.command.reload") || player.isOp()) {
                    commands.add("rename");
                }
                StringUtil.copyPartialMatches(args[0], commands, completions);
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("get")) {
                    if (player.hasPermission("waystonesplus.command.get") || player.isOp()) {
                        commands.addAll(WaystoneMemory.getWaystoneTypes().keySet());
                    }
                }
                if (args[0].equalsIgnoreCase("setvisibility")) {
                    if (player.hasPermission("waystonesplus.command.visibility") || player.isOp()) {
                        for (Visibility visibility : Visibility.values()) {
                            commands.add(visibility.name());
                        }

                    }
                }
                if (args[0].equalsIgnoreCase("rename")) {
                    if (player.hasPermission("waystonesplus.command.rename") || player.isOp()) {
                        commands.add("&6Example");
                    }
                }
                StringUtil.copyPartialMatches(args[1], commands, completions);
            }
            Collections.sort(completions);
            return completions;
        }
        return null;
    }
}
