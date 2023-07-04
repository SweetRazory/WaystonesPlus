package org.sweetrazory.waystonesplus.menu;

import org.bukkit.entity.Player;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {
    private static Map<Player, Menu> openMenus = null;
    private static Map<Player, Waystone> playerWaystones = null; // Add a map to store player-waystone associations

    public MenuManager() {
        openMenus = new HashMap<>();
        playerWaystones = new HashMap<>();
    }

    public static void openMenu(Player player, Menu menu, Waystone waystone) {
        closeMenu(player); // Close any existing menu for the player

        openMenus.put(player, menu);
        playerWaystones.put(player, waystone); // Store the player-waystone association
        menu.open(player, waystone);
    }

    public static void closeMenu(Player player) {
        Menu menu = openMenus.remove(player);
        if (menu != null) {
            menu.close(player);
        }
    }

    public static boolean hasOpenMenu(Player player) {
        return openMenus.containsKey(player);
    }

    public static Menu getOpenMenu(Player player) {
        return openMenus.get(player);
    }

    public static Waystone getPlayerWaystone(Player player) {
        return playerWaystones.get(player);
    }
}
