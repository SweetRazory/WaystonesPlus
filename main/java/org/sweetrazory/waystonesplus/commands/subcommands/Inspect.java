package org.sweetrazory.waystonesplus.commands.subcommands;

import org.bukkit.entity.Player;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.utils.SubCommand;

public class Inspect implements SubCommand {
    @Override
    public String getName() {
        return "inspect";
    }


    @Override
    public void run(Player player, String[] args, WaystoneMemory waystoneMemory) {
        System.out.println(player.getTargetBlock(null, 10).getMetadata("waystoneId").get(0).asString());
    }
}
