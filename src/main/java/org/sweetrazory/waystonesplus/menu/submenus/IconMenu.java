package org.sweetrazory.waystonesplus.menu.submenus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
import org.sweetrazory.waystonesplus.menu.Menu;
import org.sweetrazory.waystonesplus.menu.MenuManager;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.ItemBuilder;
import org.sweetrazory.waystonesplus.utils.ItemUtils;
import org.sweetrazory.waystonesplus.waystone.Waystone;

public class IconMenu extends Menu {
    public IconMenu() {
        super(27, ColoredText.getText(LangManager.iconMenuTitle), 0);
    }

    @Override
    public void initializeItems(Player player, Waystone waystone) {
        ItemStack filler = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName(" ").build();
        inventory.setContents(new ItemStack[]
                {filler, filler, filler, filler, filler, filler, filler, filler, filler,
                        filler, filler, null, filler, filler, filler, null, filler, filler,
                        filler, filler, filler, filler, filler, filler, filler, filler, filler});
        ItemStack solid = new ItemBuilder(Material.GRASS_BLOCK)
                .persistentData("action", "solidMenu")
                .displayName(ColoredText.getText(LangManager.blocks))
                .build();
        setItem(11, solid);
        ItemStack nonSolid = new ItemBuilder(Material.FLOWER_POT)
                .persistentData("action", "nonBlockMenu")
                .displayName(ColoredText.getText(LangManager.items))
                .build();
        setItem(15, nonSolid);

        setItem(22, new ItemBuilder(Material.BARRIER).displayName(ColoredText.getText(LangManager.returnText)).persistentData("action", "selectorMenu").build());
    }

    @Override
    public void handleClick(Player player, ItemStack item) {
        String action = ItemUtils.getPersistentString(item, "action");

        if (action != null) {
            if (action.equals("solidMenu")) {
                Menu solidMenu = new SolidBlockMenu(0);
                MenuManager.openMenu(player, solidMenu, waystone);
            } else if (action.equals("nonBlockMenu")) {
                Menu nonSolidMenu = new NonBlockMenu(0);
                MenuManager.openMenu(player, nonSolidMenu, waystone);
            } else if (action.equals("selectorMenu")) {
                Menu settingsMenu = new SettingsMenu();
                MenuManager.openMenu(player, settingsMenu, waystone);
            }
        }
    }
}
