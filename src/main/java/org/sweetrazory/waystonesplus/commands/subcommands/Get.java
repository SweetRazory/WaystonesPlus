package org.sweetrazory.waystonesplus.commands.subcommands;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.items.WaystoneSummonItem;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.SubCommand;

import java.util.Arrays;

public class Get implements SubCommand {
    @Override
    public String getName() {
        return "get";
    }

    @Override
    public void run(Player player, String[] args) {
        if (player.hasPermission("waystonesplus.command.get") || player.isOp()) {
            if (args.length > 1) {
                if (WaystoneMemory.getWaystoneTypes().containsKey(args[1].toLowerCase())) {
                    String name = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                    // TODO: length handling
                    ItemStack skullItem = WaystoneSummonItem.getLodestoneHead(name, args[1].toLowerCase(), null, null, Visibility.GLOBAL);
                    player.getInventory().addItem(skullItem);
                } else {
                    player.sendMessage(Color.ORANGE + LangManager.waystoneTypeNonexistent);
                }
            } else {
                player.sendMessage(ColoredText.getText(LangManager.waystoneTypeMissing));
            }
        } else {
            player.sendMessage(ColoredText.getText(LangManager.noPermission));
        }
    }
}
