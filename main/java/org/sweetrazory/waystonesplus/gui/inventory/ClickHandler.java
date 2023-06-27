package org.sweetrazory.waystonesplus.gui.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sweetrazory.waystonesplus.Main;
import org.sweetrazory.waystonesplus.gui.inventory.screens.InventoryMemory;
import org.sweetrazory.waystonesplus.utils.Console;

import java.util.Objects;

public class ClickHandler implements Listener {
    public ClickHandler(InventoryClickEvent event, InventoryMemory menu) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("Teleport to Waystone:")) {
            event.setCancelled(true);
            if (player.hasPermission("waystonesplus.teleport") || player.isOp()) {
                Inventory clickedInventory = event.getClickedInventory();
                InventoryType.SlotType slotType = event.getSlotType();

                if (clickedInventory != null && slotType != InventoryType.SlotType.OUTSIDE) {
                    try {
                        ItemStack clickedItem = event.getCurrentItem();
                        assert clickedItem != null;
                        if (Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName().equalsIgnoreCase("Previous") && clickedItem.getType().equals(Material.SNOWBALL)) {
                            menu.prevPage();
                        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("Next") && clickedItem.getType().equals(Material.SNOWBALL)) {
                            menu.nextPage();
                        } else if (clickedItem.getType().equals(Material.BARRIER)) {
                            menu.close();
                        } else {
                            ItemMeta itemMeta = clickedItem.getItemMeta();
                            PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();

//                      String visibility = dataContainer.get(new NamespacedKey(plugin, "waystone_visibility"), PersistentDataType.STRING); TODO: Implement this
                            String world = dataContainer.get(new NamespacedKey(Main.getInstance(), "waystone_world"), PersistentDataType.STRING);
                            Integer x = dataContainer.get(new NamespacedKey(Main.getInstance(), "waystone_x"), PersistentDataType.INTEGER);
                            Integer y = dataContainer.get(new NamespacedKey(Main.getInstance(), "waystone_y"), PersistentDataType.INTEGER);
                            Integer z = dataContainer.get(new NamespacedKey(Main.getInstance(), "waystone_z"), PersistentDataType.INTEGER);

                            assert world != null;
                            player.teleport(new Location(Bukkit.getWorld(world), x - 1.5, y + 1.2, z + 0.5, -90, 4.5f));
                        }
                    } catch (Exception e) {
                        new Console.error(e.toString());
                    }
                }
            }
        }
    }
}
