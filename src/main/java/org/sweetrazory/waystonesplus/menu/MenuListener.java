package org.sweetrazory.waystonesplus.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.sweetrazory.waystonesplus.waystone.Waystone;

public class MenuListener implements Listener {
    private final MenuManager menuManager;

    public MenuListener(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (MenuManager.hasOpenMenu(player)) {
            Menu menu = MenuManager.getOpenMenu(player);
            Waystone waystone = MenuManager.getPlayerWaystone(player); // Retrieve the associated Waystone
            menu.onInventoryClick(event, waystone); // Pass the Waystone to onInventoryClick
        }
    }
}
