package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.types.WaystoneType;

import java.util.Map;

public class OnWaystoneCraft {
    public OnWaystoneCraft(CraftItemEvent event) {
        ShapedRecipe eventRecipe = (ShapedRecipe) event.getRecipe();
        Map<String, WaystoneType> waystoneTypes = WaystoneMemory.getWaystoneTypes();
        Player player = (Player) event.getWhoClicked();
        boolean isWaystoneRecipe = waystoneTypes.values()
                .stream()
                .map(WaystoneType::getRecipe)
                .map(ShapedRecipe::getKey)
                .anyMatch(key -> key.equals(eventRecipe.getKey()));

        if (isWaystoneRecipe && !player.hasPermission("waystonesplus.craft") && !player.isOp()) {
            event.setCancelled(true);
        }
    }
}
