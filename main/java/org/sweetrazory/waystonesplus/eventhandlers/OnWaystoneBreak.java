package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.sweetrazory.waystonesplus.items.WaystoneSummonItem;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;

import java.util.List;

public class OnWaystoneBreak implements Listener {
    public OnWaystoneBreak(WaystoneMemory waystoneMemory, BlockBreakEvent e) {
        List<MetadataValue> blockMeta = e.getBlock().getMetadata("waystoneId");
        String blockWaystoneType = e.getBlock().getMetadata("waystoneType").get(0).asString();
        if (!blockMeta.isEmpty()) {
            if (waystoneMemory.getWaystoneTypes().containsKey(blockWaystoneType)) {
                Location dropLocation = e.getPlayer().getTargetBlock(null, 5).getLocation().add(0, 1, 0);
                World world = e.getPlayer().getWorld();
                String waystoneName = WaystoneMemory.getWaystoneDataMemory().get(blockMeta.get(0).asString()).getName();
                ItemStack skullItem = new WaystoneSummonItem().getLodestoneHead(waystoneName, waystoneMemory, blockWaystoneType, null, null);
                world.dropItemNaturally(dropLocation, skullItem);
            }
            waystoneMemory.removeWaystone(blockMeta.get(0).asString());
        }
    }
}
