package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.List;
import java.util.Map;

public class ExploredMenu extends Menu {
    public ExploredMenu() {
        super(27, "List of Explorers", 0);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {
        List<Map<String, String>> explorers = DB.getExplorers(waystone.getId());
        int i = 0;
        for (Map<String, String> playerData : explorers) {
            ItemStack playerHead = new ItemBuilder(Material.PLAYER_HEAD).displayName(ColoredText.getText("&6" + playerData.get("playerName"))).build();
            setItem(i, playerHead);
        }

    }

    @Override
    public void handleClick(Player player, ItemStack item) {

    }
}
