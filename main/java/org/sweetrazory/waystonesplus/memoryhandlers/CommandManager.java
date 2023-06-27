package org.sweetrazory.waystonesplus.memoryhandlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.sweetrazory.waystonesplus.commands.subcommands.Get;
import org.sweetrazory.waystonesplus.commands.subcommands.Help;
import org.sweetrazory.waystonesplus.utils.SubCommand;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {
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
}
