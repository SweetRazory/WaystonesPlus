package org.sweetrazory.waystonesplus.memoryhandlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.sweetrazory.waystonesplus.commands.subcommands.Get;
import org.sweetrazory.waystonesplus.commands.subcommands.Help;
import org.sweetrazory.waystonesplus.utils.SubCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final WaystoneMemory waystoneMemory;
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(WaystoneMemory waystoneMemory) {
        this.waystoneMemory = waystoneMemory;

        subCommands.add(new Get());
        subCommands.add(new Help());
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
                StringUtil.copyPartialMatches(args[0], commands, completions);
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("get")) {
                    if (player.hasPermission("waystonesplus.command.get") || player.isOp()) {
                        commands.addAll(WaystoneMemory.getWaystoneTypes().keySet());
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
