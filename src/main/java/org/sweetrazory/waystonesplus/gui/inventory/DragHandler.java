package org.sweetrazory.waystonesplus.gui.inventory;

import org.bukkit.event.inventory.InventoryDragEvent;

public class DragHandler {

    public DragHandler(InventoryDragEvent event) {
        if (event.getView().getTitle().equals("Teleport to Waystone:")) {
            event.setCancelled(true);
        }
    }
}
