package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.MetadataValue;
import org.sweetrazory.waystonesplus.gui.inventory.screens.InventoryMemory;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.List;

public class OnWaystoneInteract {
    public OnWaystoneInteract(WaystoneMemory waystoneMemory, PlayerInteractEvent e, InventoryMemory menu) {
        if (e.getHand() == EquipmentSlot.HAND && e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            List<MetadataValue> blockMeta = e.getClickedBlock().getMetadata("waystoneId");
            String exclude = "";
            Waystone waystone = null;
            if (!blockMeta.isEmpty() && e.getPlayer().hasPermission("waystones.interact")) {
                for (String waystoneId : waystoneMemory.getWaystoneIds()) {
                    if (waystoneId.equalsIgnoreCase(blockMeta.get(0).asString())) {
                        waystone = WaystoneMemory.getWaystoneDataMemory().get(blockMeta.get(0).asString());
                        menu.openGUI(0, e.getPlayer(), waystone.getUuid());
                    }
                }
            }
        }
    }
}
