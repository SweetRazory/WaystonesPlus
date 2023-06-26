package org.sweetrazory.waystonesplus.gui.inventory.screens;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sweetrazory.waystonesplus.Main;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InventoryMemory implements Listener {
    private Inventory gui;
    //
    private Player player;
    private ItemStack[] waystoneList;
    private int pageIndex;

    public InventoryMemory() {
    }

    public void openGUI(int pageIndex, Player player, String exclude) {
        this.pageIndex = pageIndex;
        this.player = player;
        this.gui = Bukkit.createInventory(player, 4 * 9, "Teleport to Waystone:");
        ItemStack[] waystones = new ItemStack[WaystoneMemory.getWaystoneDataMemory().size()];
        int index = 0;
        for (String waystoneId : WaystoneMemory.getWaystoneDataMemory().keySet()) {
            if (waystoneId.equalsIgnoreCase(exclude)) {
                continue;
            }

            Waystone waystone = WaystoneMemory.getWaystoneDataMemory().get(waystoneId);
            ItemStack waystoneItem = new ItemStack(Material.GRASS_BLOCK);
            ItemMeta waystoneItemMeta = waystoneItem.getItemMeta();
            PersistentDataContainer waystoneData = waystoneItemMeta.getPersistentDataContainer();
            waystoneData.set(new NamespacedKey(Main.getInstance(), "waystone_visibility"), PersistentDataType.STRING, waystone.getVisibility().getValue());
            waystoneData.set(new NamespacedKey(Main.getInstance(), "waystone_world"), PersistentDataType.STRING, waystone.getLocation().getWorld().getName());
            waystoneData.set(new NamespacedKey(Main.getInstance(), "waystone_x"), PersistentDataType.INTEGER, (int) waystone.getLocation().getX());
            waystoneData.set(new NamespacedKey(Main.getInstance(), "waystone_y"), PersistentDataType.INTEGER, (int) waystone.getLocation().getY());
            waystoneData.set(new NamespacedKey(Main.getInstance(), "waystone_z"), PersistentDataType.INTEGER, (int) waystone.getLocation().getZ());

            waystoneItemMeta.setDisplayName(ChatColor.GOLD + waystone.getName());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.YELLOW + "This Waystone is at:");
            lore.add(ChatColor.YELLOW + "  X: " + waystone.getLocation().getX());
            lore.add(ChatColor.YELLOW + "  Y: " + waystone.getLocation().getY());
            lore.add(ChatColor.YELLOW + "  Z: " + waystone.getLocation().getZ());
            waystoneItemMeta.setLore(lore);
            waystoneItem.setItemMeta(waystoneItemMeta);

            waystones[index] = waystoneItem;
            index++;
        }

        waystoneList = waystones;

        gui.setContents(loadCurrentPage(pageIndex, waystoneList));
        player.openInventory(gui);
    }

    public void nextPage() {
        if (waystoneList != null && (pageIndex + 1) * 14 < waystoneList.length) {
            pageIndex++;
            gui.setContents(loadCurrentPage(pageIndex, waystoneList));
            player.updateInventory();
        }
    }

    public void prevPage() {
        if (pageIndex > 0) {
            pageIndex--;
            gui.setContents(loadCurrentPage(pageIndex, waystoneList));
            player.updateInventory();
        }
    }

    public void close() {
        player.closeInventory();
    }

    public ItemStack[] loadCurrentPage(int pageIndex, ItemStack[] waystones) {
        ItemStack[][] guiContent = new ItemStack[][]{
                {filler(), filler(), filler(), filler(), filler(), filler(), filler(), filler(), filler()},
                {filler(), null, null, null, null, null, null, null, filler()},
                {filler(), null, null, null, null, null, null, null, filler()},
                {filler(), filler(), filler(), prevPageItem(), onCloseItem(), nextPageItem(), filler(), filler(), filler()},
        };

        int startIndex = pageIndex * 14;
        int endIndex = Math.min(startIndex + 14, waystones.length);

        for (int i = startIndex; i < endIndex; i++) {
            int row = ((i - startIndex) / 7) + 1;
            int column = ((i - startIndex) % 7) + 1;
            guiContent[row][column] = waystones[i];
        }

        return Stream.of(guiContent).flatMap(Stream::of).toArray(ItemStack[]::new);
    }

    private ItemStack filler() {
        return new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    }

    private ItemStack onCloseItem() {
        ItemStack onClose = new ItemStack(Material.BARRIER);
        ItemMeta onCloseMeta = onClose.getItemMeta();
        onCloseMeta.setDisplayName("Close");
        onClose.setItemMeta(onCloseMeta);
        return onClose;
    }

    private ItemStack prevPageItem() {
        ItemStack prev = new ItemStack(Material.SNOWBALL);
        ItemMeta prevMeta = prev.getItemMeta();
        prevMeta.setDisplayName("Previous");
        prev.setItemMeta(prevMeta);
        return prev;
    }

    private ItemStack nextPageItem() {
        ItemStack next = new ItemStack(Material.SNOWBALL);
        ItemMeta nextMeta = next.getItemMeta();
        nextMeta.setDisplayName("Next");
        next.setItemMeta(nextMeta);
        return next;
    }
}
