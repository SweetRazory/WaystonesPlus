package org.sweetrazory.waystonesplus.menu;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.memoryhandlers.ConfigManager;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
import org.sweetrazory.waystonesplus.menu.submenus.SelectorMenu;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TeleportMenu extends Menu {
    public TeleportMenu(int page) {
        super(45, ColoredText.getText(LangManager.teleportMenuTitle), page);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {
        ItemStack filler = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName(" ").build();
        inventory.setContents(Arrays.asList(filler, filler, filler, filler, filler, filler, filler, filler, filler,
                filler, null, null, null, null, null, null, null, filler,
                filler, null, null, null, null, null, null, null, filler,
                filler, null, null, null, null, null, null, null, filler,
                filler, filler, filler, filler, filler, filler, filler, filler, filler).toArray(new ItemStack[0]));
        List<Waystone> waystones = DB.getWaystones(player.getUniqueId().toString(), page, 21, waystone.getId());
        int listSize = 0;
        try {
            listSize = DB.getWaystonesSize(player.getUniqueId().toString(), waystone.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int k = 0;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 8; j++) {
                if (waystones.size() > k) {
                    Waystone currWaystone = waystones.get(k);
                    ItemStack waystoneItem = new ItemBuilder(currWaystone.getIcon())
                            .displayName(ColoredText.getText(currWaystone.getName()))
                            .persistentData("waystoneId", waystones.get(k).getId())
                            .persistentData("action", "teleport")
                            .build();
                    setItem(i * 9 + j, waystoneItem);
                    k++;
                }
            }
        }
        if (listSize > 21 * page + 21) {
            setItem(41, new ItemBuilder(Material.SNOWBALL).persistentData("action", "nextPage").persistentData("page", page).build());
        }

        if (page > 0) {
            setItem(39, new ItemBuilder(Material.SNOWBALL).persistentData("action", "prevPage").persistentData("page", page).build());
        }

        ItemStack backButton = new ItemBuilder(Material.BARRIER)
                .displayName(ColoredText.getText(LangManager.returnText))
                .persistentData("action", "menu")
                .build();
        if (waystone.getOwnerId().equals(player.getUniqueId().toString())) {
            setItem(40, backButton);
        }
    }

    @Override
    public void handleClick(Player player, ItemStack item) {
        String action = ItemUtils.getPersistentString(item, "action");
        if (action != null) {
            switch (action) {
                case "teleport":
                    teleportToWaystone(player, DB.getWaystone(ItemUtils.getPersistentString(item, "waystoneId")));
                    break;
                case "menu":
                    Menu menuSelector = new SelectorMenu();
                    MenuManager.openMenu(player, menuSelector, waystone);
                    break;
                case "prevPage": {
                    int page = Integer.parseInt(ItemUtils.getPersistentString(item, "page"));
                    Menu teleportMenu = new TeleportMenu(page - 1);
                    MenuManager.openMenu(player, teleportMenu, waystone);
                    break;
                }
                case "nextPage": {
                    int page = Integer.parseInt(ItemUtils.getPersistentString(item, "page"));
                    Menu teleportMenu = new TeleportMenu(page + 1);
                    MenuManager.openMenu(player, teleportMenu, waystone);
                    break;
                }
            }
        }
    }

    public void teleportToWaystone(Player player, Waystone waystone) {
        if (waystone != null) {
            if (waystone.getVisibility() != null && waystone.getVisibility().equals(Visibility.PRIVATE) && !player.hasPermission("waystonesplus.teleport.private") && !player.isOp() && waystone.getOwnerId() != null && !waystone.getOwnerId().equals(player.getUniqueId().toString())) {
                return;
            }

            player.closeInventory();
            int cooldown = (int) WaystonesPlus.cooldownManager.getRemainingCooldown(player, "waystoneTeleport");
            if (WaystonesPlus.cooldownManager.getRemainingCooldown(player, "waystoneTeleport") > 0 && !player.hasPermission("waystonesplus.cooldown.teleport") && !player.isOp()) {
                player.sendMessage(ColoredText.getText("&7You need to wait " + cooldown + " second(s) before teleporting again!"));
                return;
            }

            if (ConfigManager.teleportCountdown > 0 && !player.hasPermission("waystonesplus.countdown.teleport")) {
                final int countdownDuration = ConfigManager.teleportCountdown * 20;
                final int countdownInterval = 20;

                AtomicInteger remainingTime = new AtomicInteger(countdownDuration / 20);
                AtomicBoolean countdownRunning = new AtomicBoolean(true);

                BukkitRunnable countdownRunnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        int time = remainingTime.getAndDecrement();

                        if (time > 0) {
                            player.sendTitle(ColoredText.getText("&7Teleporting in:"), ColoredText.getText("&6" + time));
                        } else {
                            player.resetTitle();
                            player.teleport(new Location(Bukkit.getWorld(waystone.getLocation().getWorld().getName()), waystone.getLocation().getX() - 1.5, waystone.getLocation().getY() + 1.2, waystone.getLocation().getZ() + 0.5, -90, 4.5f));
                            player.sendTitle(ColoredText.getText("&6" + waystone.getName()), null);
                            countdownRunning.set(false);
                            this.cancel();
                        }
                    }
                };

                countdownRunnable.runTaskTimer(WaystonesPlus.getInstance(), 0, countdownInterval);

                Location initialLocation = player.getLocation().getBlock().getLocation();

                Bukkit.getPluginManager().registerEvents(new Listener() {
                    @EventHandler
                    public void onPlayerMove(PlayerMoveEvent event) {
                        Player movedPlayer = event.getPlayer();
                        Location currentLocation = event.getTo().getBlock().getLocation();

                        if (movedPlayer.equals(player) && countdownRunning.get() && !currentLocation.equals(initialLocation)) {
                            player.resetTitle();
                            player.sendTitle(ColoredText.getText("&cTeleport"), ColoredText.getText("&cCancelled"));
                            countdownRunnable.cancel();
                            countdownRunning.set(false);
                        }
                    }
                }, WaystonesPlus.getInstance());
            } else {
                player.resetTitle();
                player.teleport(new Location(Bukkit.getWorld(waystone.getLocation().getWorld().getName()), waystone.getLocation().getX() - 1.5, waystone.getLocation().getY() + 1.2, waystone.getLocation().getZ() + 0.5, -90, 4.5f));
                player.sendTitle(ColoredText.getText("&6" + waystone.getName()), null);
            }

            if (WaystonesPlus.cooldownManager.getRemainingCooldown(player, "waystoneTeleport") == 0 && !player.hasPermission("waystonesplus.cooldown.teleport") && !player.isOp()) {
                WaystonesPlus.cooldownManager.addPlayerCooldown(player, "waystoneTeleport", ConfigManager.waystoneTeleportCooldown);
            }
        }
    }
}
