package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.menu.MenuManager;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.List;
import java.util.Map;

public class ExploredMenu extends Menu {
    private static final int ITEMS_PER_PAGE = 45;

    public ExploredMenu(int page) {
        super(54, "List of Explorers", page);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {
        List<Map<String, String>> explorers = DB.getExplorers(waystone.getId());
        int startIndex = page * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, explorers.size());

        for (int i = startIndex; i < endIndex; i++) {
            Map<String, String> playerData = explorers.get(i);
            ItemStack playerHead = new ItemBuilder(Material.PLAYER_HEAD)
                    .displayName(ColoredText.getText("&6" + playerData.get("playerName")))
                    .build();
            setItem(i - startIndex, playerHead);
        }

        if (page > 0) {
            ItemStack previousPageItem = new ItemBuilder(Material.ARROW)
                    .displayName(ColoredText.getText("&cPrevious Page"))
                    .persistentData("action", "prevPage")
                    .persistentData("page", String.valueOf(page - 1))
                    .build();
            setItem(45, previousPageItem);
        }

        if (endIndex < explorers.size()) {
            ItemStack nextPageItem = new ItemBuilder(Material.ARROW)
                    .displayName(ColoredText.getText("&aNext Page"))
                    .persistentData("action", "nextPage")
                    .persistentData("page", String.valueOf(page + 1))
                    .build();
            setItem(53, nextPageItem);
        }

        ItemStack backButton = new ItemBuilder(Material.BARRIER)
                .displayName(ColoredText.getText("&cReturn to option select"))
                .persistentData("action", "menu")
                .build();
        setItem(49, backButton);
    }

    @Override
    public void handleClick(Player player, ItemStack item) {
        String action = ItemUtils.getPersistentString(item, "action");
        if (action != null) {
            if (action.equals("menu")) {
                Menu menu = new SelectorMenu();
                MenuManager.openMenu(player, menu, waystone);
            } else if (action.equals("prevPage")) {
                int prevPage = Integer.parseInt(ItemUtils.getPersistentString(item, "page"));
                Menu prevMenu = new ExploredMenu(prevPage);
                MenuManager.openMenu(player, prevMenu, waystone);
            } else if (action.equals("nextPage")) {
                int nextPage = Integer.parseInt(ItemUtils.getPersistentString(item, "page"));
                Menu nextMenu = new ExploredMenu(nextPage);
                MenuManager.openMenu(player, nextMenu, waystone);
            }
        }
    }
}
