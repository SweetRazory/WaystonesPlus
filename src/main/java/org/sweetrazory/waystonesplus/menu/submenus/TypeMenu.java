package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.menu.MenuManager;
import org.sweetrazory.waystonesplus.types.WaystoneType;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.ArrayList;
import java.util.List;

public class TypeMenu extends Menu {
    public TypeMenu() {
        super(36, "Change Waystone's type", 0);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {
        List<WaystoneType> waystoneTypes = new ArrayList<>(WaystoneMemory.getWaystoneTypes().values());
        for (int i = 0; i < waystoneTypes.size(); i++) {
            WaystoneType waystoneType = waystoneTypes.get(i);
            setItem(i, new ItemBuilder(waystoneType.getBlocks().get(1).getMaterial())
                    .displayName(ColoredText.getText("&6" + waystoneType.getTypeName()))
                    .persistentData("action", "changeType")
                    .persistentData("type", waystoneType.getTypeName())
                    .build());
        }

        ItemStack backButton = new ItemBuilder(Material.BARRIER)
                .displayName(ColoredText.getText("&cReturn to option select"))
                .persistentData("action", "menu")
                .build();
        setItem(31, backButton);
    }

    @Override
    public void handleClick(Player player, ItemStack item) {
        String action = ItemUtils.getPersistentString(item, "action");
        if (action != null) {
            if (action.equals("changeType")) {
                String typeName = ItemUtils.getPersistentString(item, "type");
                waystone.changeType(typeName);
                player.playSound(waystone.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 10, 1);
                player.closeInventory();
            } else if (action.equals("menu")) {
                Menu settingsMenu = new SettingsMenu();
                MenuManager.openMenu(player, settingsMenu, waystone);
            }
        }
    }
}
