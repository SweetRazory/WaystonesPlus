package org.sweetrazory.waystonesplus.commands.subcommands;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.utils.SubCommand;

import java.util.Collections;

public class SetVisibility implements SubCommand {
    @Override
    public String getName() {
        return "setvisibility";
    }

    @Override
    public void run(Player player, String[] args) {
        if (player.hasPermission("waystonesplus.command.reload") || player.isOp()) {
            ItemStack waystoneItem = player.getItemInHand();
            if (waystoneItem.getType().equals(Material.PLAYER_HEAD) && ItemUtils.hasPersistentData(waystoneItem, "waystoneVisibility")) {
                if (args.length == 2) {
                    ItemMeta waystoneItemMeta = waystoneItem.getItemMeta();
                    if (waystoneItemMeta != null && Visibility.fromString(args[1]) != null) {
                        PersistentDataContainer persWaystoneData = waystoneItemMeta.getPersistentDataContainer();
                        NamespacedKey namespacedKey = new NamespacedKey(WaystonesPlus.getInstance(), "waystoneVisibility");
                        persWaystoneData.remove(namespacedKey);
                        persWaystoneData.set(namespacedKey, PersistentDataType.STRING, Visibility.fromString(args[1]).name());
                        String visibility = "";
                        if (args[1].equals(Visibility.PRIVATE.name())) {
                            visibility = "&cPRIVATE";
                        } else if (args[1].equals(Visibility.PUBLIC.name())) {
                            visibility = "&aPUBLIC";
                        } else if (args[1].equals(Visibility.GLOBAL.name())) {
                            visibility = "&eGLOBAL";
                        }
                        waystoneItemMeta.setLore(Collections.singletonList(ColoredText.getText(visibility)));
                        waystoneItem.setItemMeta(waystoneItemMeta);
                        player.setItemInHand(waystoneItem);
                    }
                }
            }
        }
    }
}
