package org.sweetrazory.waystonesplus.commands.subcommands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
        if (player.hasPermission("waystonesplus.command.reload") || player.isOp()) {
            ItemStack waystoneItem = player.getItemInHand();
            if (waystoneItem.getType().equals(Material.PLAYER_HEAD) && ItemUtils.hasPersistentData(waystoneItem, "waystoneType")) {
                if (args.length > 1) {
                    ItemMeta waystoneItemMeta = waystoneItem.getItemMeta();
                    if (waystoneItemMeta != null) {
                        waystoneItemMeta.setDisplayName(
                                ColoredText.getText(
                                        String.join(" ", Arrays.copyOfRange(args, 1, args.length))
                                )
                        );
                        waystoneItem.setItemMeta(waystoneItemMeta);
                        player.setItemInHand(waystoneItem);
                    }
                }
            }
        }
    }
}
