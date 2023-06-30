package org.sweetrazory.waystonesplus.gui.inventory.screens;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.gui.inventory.MenuInventory;
import org.sweetrazory.waystonesplus.utils.Console;
import org.sweetrazory.waystonesplus.utils.Keys;

public class WaystoneSelector {
    private final InventoryClickEvent event;
    private final Player player;
    private final MenuInventory menu;

    public WaystoneSelector(InventoryClickEvent event, Player player, MenuInventory menu) {
        this.event = event;
        this.player = player;
        this.menu = menu;
    }

    public void InventoryClickHandler() {
        event.setCancelled(true);
        if (player.hasPermission("waystonesplus.teleport") || player.isOp()) {
            Inventory clickedInventory = event.getClickedInventory();
            InventoryType.SlotType slotType = event.getSlotType();

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedInventory != null && slotType != InventoryType.SlotType.OUTSIDE) {
                try {
                    if (clickedItem.getItemMeta() == null) {
                        return;
                    }
                    if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("Previous") && clickedItem.getType().equals(Material.SNOWBALL)) {
                        menu.prevPage();
                    } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("Next") && clickedItem.getType().equals(Material.SNOWBALL)) {
                        menu.nextPage();
                    } else if (clickedItem.getType().equals(Material.BARRIER)) {
                        menu.close();
                    } else if (!clickedItem.getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                        ItemMeta itemMeta = clickedItem.getItemMeta();
                        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();

                        String visibility = dataContainer.get(Keys.waystoneVisibility, PersistentDataType.STRING);
                        String world = dataContainer.get(Keys.waystoneWorld, PersistentDataType.STRING);
                        String owner = dataContainer.get(Keys.waystoneOwner, PersistentDataType.STRING);
                        Integer x = dataContainer.get(Keys.waystoneX, PersistentDataType.INTEGER);
                        Integer y = dataContainer.get(Keys.waystoneY, PersistentDataType.INTEGER);
                        Integer z = dataContainer.get(Keys.waystoneZ, PersistentDataType.INTEGER);

                        if (visibility != null && visibility.equals(Visibility.PRIVATE.name()) && !player.hasPermission("waystonesplus.teleport.private") && !player.isOp() && owner != null && !owner.equals(player.getUniqueId().toString())) {
                            return;
                        }

                        player.teleport(new Location(Bukkit.getWorld(world), x - 1.5, y + 1.2, z + 0.5, -90, 4.5f));
                    }
                } catch (Exception e) {
                    new Console.error(e.toString());
                }
            }
        }
    }
}
