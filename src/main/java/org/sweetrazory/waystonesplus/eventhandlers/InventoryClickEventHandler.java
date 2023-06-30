package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.sweetrazory.waystonesplus.gui.inventory.MenuInventory;

public class InventoryClickEventHandler implements Listener {

    private final Player player;
    private final MenuInventory menu;

    public InventoryClickEventHandler(Player player, MenuInventory menu) {
        this.player = player;
        this.menu = menu;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() != player) {
            return; // Ignore clicks from other players
        }

        // Handle the inventory click event for the specific player and menu
        menu.onMenuClick(event);

        // Check if the player closed their inventory
        if (event.getInventory().equals(player.getOpenInventory().getTopInventory())) {
            // Player closed their inventory, remove the event handler
            menu.removeInventoryClickHandler();
        }
    }
}
