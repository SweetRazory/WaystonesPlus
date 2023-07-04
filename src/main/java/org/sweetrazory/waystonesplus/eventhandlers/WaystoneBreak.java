package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.items.WaystoneSummonItem;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.List;

public class WaystoneBreak implements Listener {

    public WaystoneBreak(BlockBreakEvent event) {
        try {
            Block block = event.getBlock();
            List<MetadataValue> blockMeta = block.getMetadata("waystoneId");
            List<MetadataValue> blockWaystoneTypeList = block.getMetadata("waystoneType");
            if (!blockWaystoneTypeList.isEmpty() && !blockMeta.isEmpty()) {
                String blockWaystoneType = blockWaystoneTypeList.get(0).asString();
                if (!blockMeta.isEmpty() && WaystoneMemory.getWaystoneTypes().containsKey(blockWaystoneType)) {
                    Waystone waystone = DB.getWaystone(blockMeta.get(0).asString());
                    if (waystone != null && event.getPlayer().hasPermission("waystonesplus.breakwaystone") || event.getPlayer().isOp()) {
                        if (waystone.getVisibility().equals(Visibility.PRIVATE) && !waystone.getOwnerId().equals(event.getPlayer().getUniqueId().toString()) && !event.getPlayer().hasPermission("waystonesplus.breakwaystone.private") && !event.getPlayer().isOp()) {
                            event.setCancelled(true);
                        }
                        Location dropLocation = event.getPlayer().getTargetBlock(null, 5).getLocation().add(0, 1, 0);
                        World world = event.getPlayer().getWorld();
                        String waystoneName = waystone.getName();
                        ItemStack skullItem = new WaystoneSummonItem().getLodestoneHead(waystoneName, blockWaystoneType, null, null, Visibility.PRIVATE);
                        world.dropItemNaturally(dropLocation, skullItem);
                        waystone.delete();
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
        } catch (IndexOutOfBoundsException error) {
            WaystonesPlus.getInstance().getLogger().warning("A non-fatal error occurred.");
        }
    }
}
