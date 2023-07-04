package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.waystone.Waystone;

public class OwnerMenu extends Menu {
    public OwnerMenu(int size, String title, int page) {
        super(size, title, page);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {

    }

    @Override
    public void handleClick(Player player, ItemStack item) {

    }
}
