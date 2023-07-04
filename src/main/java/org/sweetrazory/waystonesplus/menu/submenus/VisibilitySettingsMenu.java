package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.menu.MenuManager;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.Arrays;

public class VisibilitySettingsMenu extends Menu {
    public VisibilitySettingsMenu() {
        super(27, ColoredText.getText("&8Set Visibility"), 0);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {
        ItemStack globalVisibility = new ItemBuilder(Material.YELLOW_CONCRETE_POWDER)
                .persistentData("action", "visibilityGlobal")
                .displayName(ColoredText.getText("&6GLOBAL"))
                .lore(Arrays.asList(ColoredText.getText("&6Set your Waystone's"), ColoredText.getText("&6Visibility to GLOBAL")))
                .build();
        ItemStack publicVisibility = new ItemBuilder(Material.GREEN_CONCRETE_POWDER)
                .persistentData("action", "visibilityPublic")
                .displayName(ColoredText.getText("&aPUBLIC"))
                .lore(Arrays.asList(ColoredText.getText("&6Set your Waystone's"), ColoredText.getText("&6Visibility to PUBLIC")))
                .build();
        ItemStack privateVisibility = new ItemBuilder(Material.RED_CONCRETE_POWDER)
                .persistentData("action", "visibilityPrivate")
                .displayName(ColoredText.getText("&cPRIVATE"))
                .lore(Arrays.asList(ColoredText.getText("&6Set your Waystone's"), ColoredText.getText("&6Visibility to PRIVATE")))
                .build();

        switch (waystone.getVisibility()) {
            case GLOBAL:
                globalVisibility = new ItemBuilder(Material.GRAY_CONCRETE_POWDER)
                        .persistentData("active", false)
                        .displayName(ColoredText.getText("&8GLOBAL"))
                        .lore(Arrays.asList(ColoredText.getText("&8Waystone is"), ColoredText.getText("&8already GLOBAL")))
                        .build();
                break;
            case PUBLIC:
                publicVisibility = new ItemBuilder(Material.GRAY_CONCRETE_POWDER)
                        .persistentData("active", false)
                        .displayName(ColoredText.getText("&8PUBLIC"))
                        .lore(Arrays.asList(ColoredText.getText("&8Waystone is"), ColoredText.getText("&8already PUBLIC")))
                        .build();
                break;
            case PRIVATE:
                privateVisibility = new ItemBuilder(Material.GRAY_CONCRETE_POWDER)
                        .persistentData("active", false)
                        .displayName(ColoredText.getText("&8PRIVATE"))
                        .lore(Arrays.asList(ColoredText.getText("&8Waystone is"), ColoredText.getText("&8already PRIVATE")))
                        .build();
                break;
        }

        setItem(11, globalVisibility);
        setItem(13, publicVisibility);
        setItem(15, privateVisibility);
        ItemStack backButton = new ItemBuilder(Material.BARRIER)
                .displayName(ColoredText.getText("&cReturn to settings select"))
                .persistentData("action", "back")
                .build();
        setItem(22, backButton);
    }

    @Override
    public void handleClick(Player player, ItemStack item) {
        String action = ItemUtils.getPersistentString(item, "action");
        if (action != null) {
            Menu visibilityMenu = new VisibilitySettingsMenu();
            switch (action) {
                case "visibilityGlobal":
                    waystone.setVisibility(Visibility.GLOBAL);
                    DB.updateWaystone(waystone);
                    MenuManager.openMenu(player, visibilityMenu, waystone);
                    break;
                case "visibilityPublic":
                    waystone.setVisibility(Visibility.PUBLIC);
                    DB.updateWaystone(waystone);
                    MenuManager.openMenu(player, visibilityMenu, waystone);
                    break;
                case "visibilityPrivate":
                    waystone.setVisibility(Visibility.PRIVATE);
                    DB.updateWaystone(waystone);
                    MenuManager.openMenu(player, visibilityMenu, waystone);
                    break;
                case "back":
                    Menu settingsMenu = new SettingsMenu();
                    MenuManager.openMenu(player, settingsMenu, waystone);
                    break;
            }
        }
    }
}
