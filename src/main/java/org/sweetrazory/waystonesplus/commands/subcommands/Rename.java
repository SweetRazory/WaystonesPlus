package org.sweetrazory.waystonesplus.commands.subcommands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.utils.SubCommand;

import java.util.Arrays;

public class Rename implements SubCommand {
    @Override
    public String getName() {
        return "rename";
    }

    @Override
    public void run(Player player, String[] args) {
        if (!player.hasPermission("waystonesplus.command.rename") || !player.isOp()) {
            ItemStack waystoneItem = player.getItemInHand();
            if (waystoneItem.getType().equals(Material.PLAYER_HEAD) && ItemUtils.hasPersistentData(waystoneItem, "waystoneType")) {
                if (args.length <= 1) {
                    player.sendMessage(ColoredText.getText(LangManager.waystoneNameMissing));
                    return;
                }

                ItemMeta waystoneItemMeta = waystoneItem.getItemMeta();
                if (waystoneItemMeta == null) {
                    player.sendMessage(ColoredText.getText(LangManager.invalidItem));
                    return;
                }

                waystoneItemMeta.setDisplayName(
                        ColoredText.getText(
                                String.join(" ", Arrays.copyOfRange(args, 1, args.length))
                        )
                );
                waystoneItem.setItemMeta(waystoneItemMeta);
                player.setItemInHand(waystoneItem);
            } else {
                player.sendMessage(ColoredText.getText(LangManager.noItemHeld));
            }
        } else {
            player.sendMessage(ColoredText.getText(LangManager.noPermission));
        }
    }
}
