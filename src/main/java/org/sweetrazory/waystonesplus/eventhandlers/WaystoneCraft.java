package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.sweetrazory.waystonesplus.memoryhandlers.ConfigManager;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.types.WaystoneType;

import java.util.Map;

public class WaystoneCraft {
    public WaystoneCraft(CraftItemEvent event) {
        if(event.isCancelled()) {
            return;
        }
            Recipe recipe = event.getRecipe();
            if(recipe instanceof ShapedRecipe) {
                ShapedRecipe eventRecipe = (ShapedRecipe) event.getRecipe();
                Map<String, WaystoneType> waystoneTypes = WaystoneMemory.getWaystoneTypes();
                Player player = (Player) event.getWhoClicked();
                boolean isWaystoneRecipe = waystoneTypes.values()
                        .stream()
                        .map(WaystoneType::getRecipe)
                        .map(ShapedRecipe::getKey)
                        .anyMatch(key -> key.equals(eventRecipe.getKey()));
                if (isWaystoneRecipe && ConfigManager.enableCrafting) {
                    if (!player.hasPermission("waystonesplus.craft") && !player.isOp()) {
                        event.setCancelled(true);
                    }
                }
            }
    }
}
