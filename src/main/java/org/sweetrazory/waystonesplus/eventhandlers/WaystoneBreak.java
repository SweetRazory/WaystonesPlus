package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.items.WaystoneSummonItem;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.List;

public class WaystoneBreak implements Listener {
    public WaystoneBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        try {
            Player player = event.getPlayer();
            Block block = event.getBlock();
            List<MetadataValue> blockMeta = block.getMetadata("waystoneId");
            List<MetadataValue> blockWaystoneTypeList = block.getMetadata("waystoneType");

            if (blockWaystoneTypeList.isEmpty() || blockMeta.isEmpty()) return;

            String blockWaystoneType = blockWaystoneTypeList.get(0).asString();

            if (!WaystoneMemory.getWaystoneTypes().containsKey(blockWaystoneType)) return;

            Waystone waystone = DB.getWaystone(blockMeta.get(0).asString());

            if (waystone == null) return;

            if (event.getPlayer().hasPermission("waystonesplus.breakwaystone") || event.getPlayer().isOp()) {
                if (waystone.getVisibility().equals(Visibility.PRIVATE) && !waystone.getOwnerId().equals(event.getPlayer().getUniqueId().toString()) && !event.getPlayer().hasPermission("waystonesplus.breakwaystone.private") && !event.getPlayer().isOp()) {
                    player.sendMessage(ColoredText.getText(LangManager.notOwner));
                    event.setCancelled(true);
                    return;
                }
                Location dropLocation = event.getPlayer().getTargetBlock(null, 5).getLocation().add(0, 1, 0);
                World world = event.getPlayer().getWorld();
                String waystoneName = waystone.getName();
                ItemStack skullItem = WaystoneSummonItem.getLodestoneHead(waystoneName, blockWaystoneType, null, null, waystone.getVisibility());
                world.dropItemNaturally(dropLocation, skullItem);
                waystone.delete();
            } else {
                player.sendMessage(ColoredText.getText(LangManager.noPermission));
                event.setCancelled(true);
            }
        } catch (IndexOutOfBoundsException error) {
            WaystonesPlus.getInstance().getLogger().warning("A non-fatal error occurred in WaystoneBreak.java. INDEX_OUT_OF_BOUNDS");
        }
    }
}
