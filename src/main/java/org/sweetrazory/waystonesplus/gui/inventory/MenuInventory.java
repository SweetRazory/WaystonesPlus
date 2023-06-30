package org.sweetrazory.waystonesplus.gui.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.eventhandlers.InventoryClickEventHandler;
import org.sweetrazory.waystonesplus.gui.inventory.screens.OptionSelector;
import org.sweetrazory.waystonesplus.gui.inventory.screens.WaystoneSelector;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.utils.Keys;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MenuInventory implements Listener {
    private final Waystone waystone;
    private Inventory gui;
    private Player player;
    private ItemStack[] waystoneList;
    private int pageIndex;

    public MenuInventory(Waystone waystone) {
        this.waystone = waystone;
    }

    public void removeInventoryClickHandler() {
        HandlerList.unregisterAll(new InventoryClickEventHandler(player, this));
    }

    public void onMenuClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains("Waystone menu")) {
            new OptionSelector(event, this, waystone).InventoryClickHandler();
        } else if (event.getView().getTitle().equals("Teleport to Waystone:")) {
            new WaystoneSelector(event, player, this).InventoryClickHandler();
        }
    }

    public void close() {
        openGUI(player, waystone);
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

    public ItemStack filler() {
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        if (fillerMeta != null) {
            fillerMeta.setDisplayName(WaystonesPlus.coloredText("&r "));
        }

        filler.setItemMeta(fillerMeta);

        return filler;
    }

    public void openGUI(Player player, Waystone waystone) {
        ItemStack waystoneList = new ItemStack(Material.PAINTING);
        ItemStack waystoneSettings = new ItemStack(Material.NETHER_STAR);
        ItemStack waystoneVisibility = new ItemStack(Material.SPYGLASS);

        ItemMeta waystoneListMeta = waystoneList.getItemMeta();
        ItemMeta waystoneSettingsMeta = waystoneSettings.getItemMeta();
        ItemMeta waystoneVisibilityMeta = waystoneVisibility.getItemMeta();



        if (waystoneListMeta != null) {
            waystoneListMeta.getPersistentDataContainer().set(Keys.clickEvent, PersistentDataType.STRING, "openWaystoneList");
            waystoneListMeta.setDisplayName(WaystonesPlus.coloredText("&6&lWaystones list"));
        }
        if (waystoneSettingsMeta != null) {
            waystoneSettingsMeta.getPersistentDataContainer().set(Keys.clickEvent, PersistentDataType.STRING, "openWaystoneSettings");
            waystoneSettingsMeta.setDisplayName(WaystonesPlus.coloredText("&b&lWaystone settings"));
        }
        if (waystoneVisibilityMeta != null) {
            waystoneVisibilityMeta.getPersistentDataContainer().set(Keys.clickEvent, PersistentDataType.STRING, "toggleWaystoneVisibility");
            switch (waystone.getVisibility()) {
                case GLOBAL:
                    waystoneVisibilityMeta.setDisplayName(WaystonesPlus.coloredText("&e&lGlobally accessible"));
                    waystoneVisibilityMeta.setLore(Arrays.asList(
                            WaystonesPlus.coloredText("&7This Waystone's visibility"),
                            WaystonesPlus.coloredText("&7Level is &e&lGLOBAL"),
                            WaystonesPlus.coloredText("&7Cannot be changed")));
                    break;
                case PUBLIC:
                    waystoneVisibilityMeta.setDisplayName(WaystonesPlus.coloredText("&e&lToggle visibility"));
                    waystoneVisibilityMeta.setLore(Arrays.asList(
                            WaystonesPlus.coloredText("&7This Waystone's visibility"),
                            WaystonesPlus.coloredText("&7Level is &2&lPUBLIC"),
                            WaystonesPlus.coloredText("&7Click to change it to &c&lPRIVATE")));

                    waystoneVisibilityMeta.setDisplayName(WaystonesPlus.coloredText("&e&lToggle visibility"));
                    break;
                case PRIVATE:
                    waystoneVisibilityMeta.setDisplayName(WaystonesPlus.coloredText("&e&lToggle visibility"));
                    waystoneVisibilityMeta.setLore(Arrays.asList(
                            WaystonesPlus.coloredText("&7This Waystone's visibility"),
                            WaystonesPlus.coloredText("&7Level is &c&lPRIVATE"),
                            WaystonesPlus.coloredText("&7Click to change it to &2&lPUBLIC")));
                    break;
            }
        }

        waystoneList.setItemMeta(waystoneListMeta);
        waystoneSettings.setItemMeta(waystoneSettingsMeta);
        waystoneVisibility.setItemMeta(waystoneVisibilityMeta);


        ItemStack[] guiContent = new ItemStack[]{
                filler(), filler(), filler(), filler(), filler(), filler(), filler(), filler(), filler(),
                filler(), filler(), waystoneList, filler(), waystoneSettings, filler(), waystoneVisibility, filler(), filler(),
                filler(), filler(), filler(), filler(), filler(), filler(), filler(), filler(), filler(),
        };
        this.player = player;
        this.gui = Bukkit.createInventory(player, 3 * 9, "Waystone menu");
        gui.setContents(guiContent);
        player.openInventory(gui);
    }

    public void openWaystoneList(Waystone exclude) {
        this.pageIndex = pageIndex;
        this.player = player;
        this.gui = Bukkit.createInventory(player, 4 * 9, "Teleport to Waystone:");
        ItemStack[] waystones = new ItemStack[WaystoneMemory.getWaystoneDataMemory().size()];
        int index = 0;
        for (Waystone currWaystone : WaystoneMemory.getWaystoneDataMemory().values()) {
            if (currWaystone.equals(exclude)) {
                continue;
            }

            if (currWaystone.getVisibility().equals(Visibility.PRIVATE) &&
                    !currWaystone.getOwnerId().equals(player.getUniqueId().toString()) &&
                    !player.isOp() &&
                    !player.hasPermission("waystones.teleport.private")) {
                continue;
            }

            Waystone waystone = WaystoneMemory.getWaystoneDataMemory().get(currWaystone.getUuid());
            ItemStack waystoneItem = new ItemStack(waystone.getWaystoneType().getBlocks().get(1).getMaterial());
            ItemMeta waystoneItemMeta = waystoneItem.getItemMeta();

            PersistentDataContainer waystoneData = waystoneItemMeta.getPersistentDataContainer();
            waystoneData.set(Keys.waystoneVisibility, PersistentDataType.STRING, waystone.getVisibility().name());
            waystoneData.set(Keys.waystoneWorld, PersistentDataType.STRING, waystone.getLocation().getWorld().getName());
            waystoneData.set(Keys.waystoneOwner, PersistentDataType.STRING, waystone.getOwnerId());
            waystoneData.set(Keys.waystoneX, PersistentDataType.INTEGER, waystone.getLocation().getBlockX());
            waystoneData.set(Keys.waystoneY, PersistentDataType.INTEGER, waystone.getLocation().getBlockY());
            waystoneData.set(Keys.waystoneZ, PersistentDataType.INTEGER, waystone.getLocation().getBlockZ());

            waystoneItemMeta.setDisplayName(ChatColor.GOLD + waystone.getName());
            List<String> lore = new ArrayList<>();
            lore.add(WaystonesPlus.coloredText("&eWaystone is: &6" + waystone.getVisibility().name()));
            lore.add(WaystonesPlus.coloredText("&eWaystone location:"));
            lore.add(WaystonesPlus.coloredText("&e  World: &6" + waystone.getLocation().getWorld().getName()));
            lore.add(WaystonesPlus.coloredText("&e  X: &6" + waystone.getLocation().getX()));
            lore.add(WaystonesPlus.coloredText("&e  Y: &6" + waystone.getLocation().getY()));
            lore.add(WaystonesPlus.coloredText("&e  Z: &6" + waystone.getLocation().getZ()));
            waystoneItemMeta.setLore(lore);
            waystoneItem.setItemMeta(waystoneItemMeta);

            waystones[index] = waystoneItem;
            index++;
        }

        waystoneList = waystones;

        gui.setContents(loadCurrentPage(pageIndex, waystoneList));
        player.openInventory(gui);
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
