package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.memoryhandlers.ConfigManager;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.types.BlockType;
import org.sweetrazory.waystonesplus.types.WaystoneType;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.List;
import java.util.UUID;

public class WaystonePlace implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }

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
        NamespacedKey waystoneTypeKey = new NamespacedKey(WaystonesPlus.getInstance(), "waystoneType");
        NamespacedKey waystoneVisibility = new NamespacedKey(WaystonesPlus.getInstance(), "waystoneVisibility");
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        String waystoneTypeValue = dataContainer.get(waystoneTypeKey, PersistentDataType.STRING);
        String waystoneVisibilityValue = dataContainer.get(waystoneVisibility, PersistentDataType.STRING);

        if (waystoneTypeValue != null && waystoneVisibilityValue != null) {
            if (!player.hasPermission("waystonesplus.placewaystone") && !player.isOp()) {
                player.sendMessage(ColoredText.getText(LangManager.noPermission));
                event.getBlockPlaced().setType(Material.AIR);
                event.setCancelled(true);

                return;
            }

            if (player.hasPermission("waystonesplus.placewaystone") || player.isOp()) {
                // TODO Switch config.yml to waystonetypes.yml, add config.yml and define minimum waystone distance
                if (!player.hasPermission("waystonesplus.cooldown.placewaystone")) {
                    int playerCooldown = (int) WaystonesPlus.cooldownManager.getRemainingCooldown(player, "waystonePlace");

                    if (playerCooldown != 0) {
                        event.getBlockPlaced().setType(Material.AIR);
                        event.getPlayer().sendMessage(ColoredText.getText(LangManager.wait.replaceAll("%cooldown%", String.valueOf(playerCooldown))));
//                        event.getPlayer().sendMessage(ColoredText.getText("&7You need to wait " + playerCooldown + " second(s)"));
                        event.setCancelled(true);

                        return;
                    } else {
                        WaystonesPlus.cooldownManager.addPlayerCooldown(player, "waystonePlace", ConfigManager.waystonePlaceCooldown);
                    }
                }

                Location temp = event.getBlockPlaced().getLocation();
                Location placedBlockLocation = new Location(temp.getWorld(), temp.getX(), temp.getY() - 1, temp.getZ());

                WaystoneType waystoneType = WaystoneMemory.getWaystoneTypes().get(waystoneTypeValue.toLowerCase());

                if (waystoneType != null) {
                    waystoneName = !waystoneName.equals("New Waystone") ? waystoneName : "New Waystone";
                    addWaystoneAndNotify(!waystoneName.equals("New Waystone") ? waystoneName : "New Waystone", player, waystoneType, placedBlockLocation, Visibility.fromString(waystoneVisibilityValue), Particle.ENCHANT);
                    if (ConfigManager.enableNotification) {
                        player.sendTitle(ColoredText.getText(LangManager.newWaystoneTitle), ColoredText.getText(LangManager.newWaystoneSubtitle.replace("%waystone_name%", waystoneName)), 20, 40, 20);
                    }

                } else {
                    player.sendMessage(Color.ORANGE + "Faulty block detected. (How did we get here?)");
                }

            }
        }


    }

    private void addWaystoneAndNotify(String name, Player player, WaystoneType waystoneType, Location location, Visibility visibility, Particle particle) {
        Waystone waystone = new Waystone(UUID.randomUUID().toString(), name, location, waystoneType.getTypeName(), player.getUniqueId().toString(), particle, visibility, (List) null, ((BlockType) waystoneType.getBlocks().get(1)).getMaterial());
        waystone.createWaystone();
        DB.insertWaystone(waystone);
    }

}
