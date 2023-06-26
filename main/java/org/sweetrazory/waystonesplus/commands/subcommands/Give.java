package org.sweetrazory.waystonesplus.commands.subcommands;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.items.WaystoneSummonItem;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.utils.SubCommand;

import java.util.Arrays;

public class Give implements SubCommand {
    @Override
    public String getName() {
        return "give";
    }

    @Override
    public void run(Player player, String[] args, WaystoneMemory waystoneMemory) {
        if (args.length > 1) {
            if (waystoneMemory.getWaystoneTypes().containsKey(args[1])) {
                String name = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                // TODO: length handling
                ItemStack skullItem = new WaystoneSummonItem().getLodestoneHead(name, waystoneMemory, args[1]);
                player.getInventory().addItem(skullItem);
            } else {
                player.sendMessage(Color.ORANGE + "A waystone variation with that name doesn't exist!");
            }
        } // TODO: no waystone type given
    }
}
