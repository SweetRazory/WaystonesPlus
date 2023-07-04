package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.waystone.Waystone;

public class IconMenu extends Menu {
    public IconMenu() {
        super(27, ColoredText.getText("&7Test"), 0);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {

    }

    @Override
    public void handleClick(Player player, ItemStack item) {

    }
}
