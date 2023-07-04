package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.menu.MenuManager;
import org.sweetrazory.waystonesplus.types.WaystoneType;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TypeMenu extends Menu {
    public TypeMenu() {
        super(27, ColoredText.getText(LangManager.typeMenuTitle), 0);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {
        ItemStack filler = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName(" ").build();
        inventory.setContents(Arrays.asList(filler, filler, filler, filler, filler, filler, filler, filler, filler,
                filler, null, null, null, null, null, null, null, filler,
                filler, filler, filler, filler, filler, filler, filler, filler, filler).toArray(new ItemStack[0]));
        List<WaystoneType> waystoneTypes = new ArrayList<>(WaystoneMemory.getWaystoneTypes().values());
        for (int i = 0; i < waystoneTypes.size(); i++) {
            WaystoneType waystoneType = waystoneTypes.get(i);
            setItem(i + 10, new ItemBuilder(waystoneType.getBlocks().get(1).getMaterial())
                    .displayName(ColoredText.getText("&6" + waystoneType.getTypeName().toUpperCase(Locale.ROOT)))
                    .persistentData("action", "changeType")
                    .persistentData("type", waystoneType.getTypeName())
                    .build());
        }

        ItemStack backButton = new ItemBuilder(Material.BARRIER)
                .displayName(ColoredText.getText(LangManager.returnText))
                .persistentData("action", "menu")
                .build();
        setItem(22, backButton);
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
