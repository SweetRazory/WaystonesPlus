package org.sweetrazory.waystonesplus.menu;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

public abstract class Menu {
    protected Inventory inventory;
    protected int page;
    protected Waystone waystone;

    public Menu(int size, String title, int page) {
        this.page = page;
        inventory = Bukkit.createInventory(null, size, title);
    }

    public abstract void initializeItems(Player player, Waystone waystone);

    public void open(Player player, Waystone waystone) {
        this.waystone = waystone;
        initializeItems(player, waystone);
        player.openInventory(inventory);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    public void setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    public abstract void handleClick(Player player, ItemStack item);

    public void onInventoryClick(InventoryClickEvent event, Waystone waystone) {
        if (event.getInventory() != inventory) {
            return;
        }

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item != null) {
            if (ItemUtils.getPersistentString(item, "action") != null) {
                player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON, 10, 1);
            }
            handleClick(player, item);
        }
    }
}
