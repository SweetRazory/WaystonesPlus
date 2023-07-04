package org.sweetrazory.waystonesplus.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.sweetrazory.waystonesplus.utils.ColoredText;

import java.util.ArrayList;

public class PlayerHead {
    public static ItemStack get(String name) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(ColoredText.getText("&6" + name));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Custom head");
        skull.setLore(lore);
        skull.setOwner(name);
        item.setItemMeta(skull);

        return item;
    }
}
