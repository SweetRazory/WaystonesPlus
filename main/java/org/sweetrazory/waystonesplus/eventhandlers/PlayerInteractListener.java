package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sweetrazory.waystonesplus.Main;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.types.WaystoneType;
import org.sweetrazory.waystonesplus.waystone.Waystone;

public class PlayerInteractListener implements Listener {
    private final WaystoneMemory waystoneMemory;

    public PlayerInteractListener(WaystoneMemory waystoneMemory) {
        this.waystoneMemory = waystoneMemory;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = event.getItemInHand();


        if (item.getType() != Material.PLAYER_HEAD) {
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return;
        }

        String waystoneName = itemMeta.getDisplayName();
        NamespacedKey waystoneType = new NamespacedKey(Main.getInstance(), "waystoneType");
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        String waystoneTypeValue = dataContainer.get(waystoneType, PersistentDataType.STRING);
        if (!waystoneTypeValue.isEmpty()) {
            if (player.hasPermission("waystonesplus.placewaystone") || player.isOp()) {
                for (Waystone waystone : WaystoneMemory.getWaystoneDataMemory().values()) {
                    // TODO Switch config.yml to waystonetypes.yml, add config.yml and define minimum waystone distance
                    if ((waystone.getLocation().getWorld() != null && waystone.getLocation().getWorld().equals(event.getBlockPlaced().getWorld()) && waystone.getLocation().distance(event.getBlockPlaced().getLocation()) < 50 && !player.hasPermission("waystonesplus.distanceoverride")) && !player.isOp()) {
                        event.getBlockPlaced().setType(Material.AIR);
                        event.getPlayer().sendMessage("You can't place Waystones this close to each-other (50 Blocks)");
                        return;
                    }
                }
            } else if (!player.hasPermission("waystonesplus.placewaystone") && !player.isOp()) {
                event.getBlockPlaced().setType(Material.AIR);
                return;
            }
        }

        Location temp = event.getBlockPlaced().getLocation();
        Location placedBlockLocation = new Location(temp.getWorld(), temp.getX(), temp.getY() - 1, temp.getZ());

        if (waystoneTypeValue != null) {
            WaystoneType ws = WaystoneMemory.getWaystoneTypes().get(waystoneTypeValue.toLowerCase());
            if (ws != null) {
                addWaystoneAndNotify(!waystoneName.equals("New Waystone") ? waystoneName : "New Waystone", player, ws, placedBlockLocation);
            } else {
                player.sendMessage(Color.ORANGE + "Faulty block detected. (How did we get here?)");
            }
        }
    }

    private void addWaystoneAndNotify(String name, Player player, WaystoneType waystoneType, Location location) {
        waystoneMemory.addWaystone(name, waystoneType, location, waystoneType.getTypeName().toLowerCase(), player, Visibility.fromString("GLOBAL"));
        player.sendMessage("Waystone is ready to use!");
    }

}
