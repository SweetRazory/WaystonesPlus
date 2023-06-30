package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

import java.util.List;

public class OnWaystoneBreak implements Listener {

    public OnWaystoneBreak(WaystoneMemory waystoneMemory, BlockBreakEvent event) {
        try {
            Block block = event.getBlock();
            List<MetadataValue> blockMeta = block.getMetadata("waystoneId");
            List<MetadataValue> blockWaystoneTypeList = block.getMetadata("waystoneType");
            if (!blockWaystoneTypeList.isEmpty() && !blockMeta.isEmpty()) {
                String blockWaystoneType = blockWaystoneTypeList.get(0).asString();
                if (!blockMeta.isEmpty() && WaystoneMemory.getWaystoneTypes().containsKey(blockWaystoneType)) {
                    if (event.getPlayer().hasPermission("waystonesplus.breakwaystone") || event.getPlayer().isOp()) {
                        if (WaystoneMemory.getWaystoneDataMemory().get(blockMeta.get(0).asString()).getVisibility().equals(Visibility.PRIVATE) && !WaystoneMemory.getWaystoneDataMemory().get(blockMeta.get(0).asString()).getOwnerId().equals(event.getPlayer().getUniqueId().toString()) && !event.getPlayer().hasPermission("waystonesplus.breakwaystone.private") && !event.getPlayer().isOp()) {
                            event.setCancelled(true);
                        }
                        Location dropLocation = event.getPlayer().getTargetBlock(null, 5).getLocation().add(0, 1, 0);
                        World world = event.getPlayer().getWorld();
                        String waystoneId = blockMeta.get(0).asString();
                        String waystoneName = WaystoneMemory.getWaystoneDataMemory().get(waystoneId).getName();
                        ItemStack skullItem = new WaystoneSummonItem().getLodestoneHead(waystoneName, blockWaystoneType, null, null, Visibility.PRIVATE);
                        world.dropItemNaturally(dropLocation, skullItem);
                        waystoneMemory.removeWaystone(waystoneId);

                        event.getPlayer().sendTitle("", ChatColor.BLACK.toString(), 0, 20, 10);

                        Bukkit.getWorld(event.getPlayer().getWorld().getName()).save();
                        Bukkit.getPlayer(event.getPlayer().getUniqueId()).saveData();
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
