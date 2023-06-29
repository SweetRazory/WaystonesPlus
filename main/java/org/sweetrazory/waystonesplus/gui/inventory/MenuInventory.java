package org.sweetrazory.waystonesplus.gui.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sweetrazory.waystonesplus.Main;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.eventhandlers.InventoryClickEventHandler;
import org.sweetrazory.waystonesplus.gui.inventory.screens.OptionSelector;
import org.sweetrazory.waystonesplus.gui.inventory.screens.WaystoneSelector;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
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
            fillerMeta.setDisplayName(Main.coloredText("&r "));
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

        NamespacedKey clickEvent = new NamespacedKey(Main.getInstance(), "clickEvent");

        waystoneListMeta.getPersistentDataContainer().set(clickEvent, PersistentDataType.STRING, "openWaystoneList");
        waystoneSettingsMeta.getPersistentDataContainer().set(clickEvent, PersistentDataType.STRING, "openWaystoneSettings");
        waystoneVisibilityMeta.getPersistentDataContainer().set(clickEvent, PersistentDataType.STRING, "toggleWaystoneVisibility");


        if (waystoneListMeta != null) {
            waystoneListMeta.setDisplayName(Main.coloredText("&6&lWaystones list"));
        }
        if (waystoneSettingsMeta != null) {
            waystoneSettingsMeta.setDisplayName(Main.coloredText("&b&lWaystone settings"));
        }
        if (waystoneVisibilityMeta != null) {
            switch (waystone.getVisibility().getValue()) {
                case "GLOBAL":
                    waystoneVisibilityMeta.setDisplayName(Main.coloredText("&e&lGlobally accessible"));
                    waystoneVisibilityMeta.setLore(Arrays.asList(
                            Main.coloredText("&7This Waystone's visibility"),
                            Main.coloredText("&7Level is &e&lGLOBAL"),
                            Main.coloredText("&7Cannot be changed")));
                    break;
                case "PUBLIC":
                    waystoneVisibilityMeta.setDisplayName(Main.coloredText("&e&lToggle visibility"));
                    waystoneVisibilityMeta.setLore(Arrays.asList(
                            Main.coloredText("&7This Waystone's visibility"),
                            Main.coloredText("&7Level is &2&lPUBLIC"),
                            Main.coloredText("&7Click to change it to &c&lPRIVATE")));

                    waystoneVisibilityMeta.setDisplayName(Main.coloredText("&e&lToggle visibility"));
                    break;
                case "PRIVATE":
                    waystoneVisibilityMeta.setDisplayName(Main.coloredText("&e&lToggle visibility"));
                    waystoneVisibilityMeta.setLore(Arrays.asList(
                            Main.coloredText("&7This Waystone's visibility"),
                            Main.coloredText("&7Level is &c&lPRIVATE"),
                            Main.coloredText("&7Click to change it to &2&lPUBLIC")));
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
            waystoneData.set(new NamespacedKey(Main.getInstance(), "waystone_visibility"), PersistentDataType.STRING, waystone.getVisibility().getValue());
            waystoneData.set(new NamespacedKey(Main.getInstance(), "waystone_world"), PersistentDataType.STRING, waystone.getLocation().getWorld().getName());
            waystoneData.set(new NamespacedKey(Main.getInstance(), "waystone_owner"), PersistentDataType.STRING, waystone.getOwnerId());
            waystoneData.set(new NamespacedKey(Main.getInstance(), "waystone_x"), PersistentDataType.INTEGER, (int) waystone.getLocation().getX());
            waystoneData.set(new NamespacedKey(Main.getInstance(), "waystone_y"), PersistentDataType.INTEGER, (int) waystone.getLocation().getY());
            waystoneData.set(new NamespacedKey(Main.getInstance(), "waystone_z"), PersistentDataType.INTEGER, (int) waystone.getLocation().getZ());

            waystoneItemMeta.setDisplayName(ChatColor.GOLD + waystone.getName());
            List<String> lore = new ArrayList<>();
            lore.add(Main.coloredText("&eWaystone is: &6" + waystone.getVisibility().getValue()));
            lore.add(Main.coloredText("&eWaystone location:"));
            lore.add(Main.coloredText("&e  World: &6" + waystone.getLocation().getWorld().getName()));
            lore.add(Main.coloredText("&e  X: &6" + waystone.getLocation().getX()));
            lore.add(Main.coloredText("&e  Y: &6" + waystone.getLocation().getY()));
            lore.add(Main.coloredText("&e  Z: &6" + waystone.getLocation().getZ()));
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