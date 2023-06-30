package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.MetadataValue;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.List;

public class WaystoneInteract {

    public Waystone getInteractedWaystone(PlayerInteractEvent e) {
        Waystone waystone = null;
        if (e.getHand() == EquipmentSlot.HAND && e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            List<MetadataValue> blockMeta = e.getClickedBlock().getMetadata("waystoneId");
            if (!blockMeta.isEmpty() && (e.getPlayer().hasPermission("waystonesplus.interact") || e.getPlayer().isOp())) {
                for (String waystoneId : WaystoneMemory.getWaystoneIds()) {
                    if (waystoneId.equalsIgnoreCase(blockMeta.get(0).asString())) {
                        Visibility waystoneVisibility = WaystoneMemory.getWaystoneDataMemory().get(waystoneId).getVisibility();
                        String waystoneOwner = WaystoneMemory.getWaystoneDataMemory().get(waystoneId).getOwnerId();
                        if (waystoneVisibility.equals(Visibility.PRIVATE) && !waystoneOwner.equals(e.getPlayer().getUniqueId().toString()) && !e.getPlayer().hasPermission("waystonesplus.interact.private") && !e.getPlayer().isOp()) {
                            return null;
                        }
                        waystone = WaystoneMemory.getWaystoneDataMemory().get(blockMeta.get(0).asString());
                    }
                }
            }
        }
        return waystone;
    }
}
