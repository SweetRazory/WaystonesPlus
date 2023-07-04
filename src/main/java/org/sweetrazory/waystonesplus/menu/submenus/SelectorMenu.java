package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.menu.MenuManager;
import org.sweetrazory.waystonesplus.menu.TeleportMenu;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.Arrays;

public class SelectorMenu extends Menu {
    public SelectorMenu() {
        super(27, "Waystone menu", 0);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {
        ItemStack filler = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName(" ").build();
        inventory.setContents(Arrays.asList(filler, filler, filler, filler, filler, filler, filler, filler, filler,
                filler, filler, null, filler, filler, filler, null, filler, filler,
                filler, filler, filler, filler, filler, filler, filler, filler, filler).toArray(new ItemStack[0]));
        ItemStack teleportList = new ItemBuilder(Material.PAINTING)
                .displayName(ColoredText.getText("&6Waystone list"))
                .lore(Arrays.asList(
                        ColoredText.getText("&eClick here to select"),
                        ColoredText.getText("&eA Waystone you want"),
                        ColoredText.getText("&eTo teleport!")))
                .persistentData("action", "teleportList").build();
        setItem(11, teleportList);
        ItemStack settings = new ItemBuilder(Material.CAMPFIRE)
                .displayName(ColoredText.getText("&6Settings"))
                .lore(Arrays.asList(
                        ColoredText.getText("&eClick here to modify"),
                        ColoredText.getText("&eThis Waystone's"),
                        ColoredText.getText("&eSettings, like Visibility")))
                .persistentData("action", "settings").build();
        setItem(15, settings);
    }

    @Override
    public void handleClick(Player player, ItemStack item) {
        String action = ItemUtils.getPersistentString(item, "action");
        if (action != null) {
            switch (action) {
                case "teleportList":
                    Menu teleportList = new TeleportMenu(0);
                    MenuManager.openMenu(player, teleportList, waystone);
                    break;
                case "settings":
                    Menu settings = new SettingsMenu();
                    MenuManager.openMenu(player, settings, waystone);
                    break;
            }
        }
    }
}
