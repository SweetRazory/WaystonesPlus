package org.sweetrazory.waystonesplus.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sweetrazory.waystonesplus.WaystonesPlus;

import java.util.List;

public class ItemBuilder {
    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder displayName(String displayName) {
        itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder persistentData(String key, Object value) {
        NamespacedKey namespacedKey = new NamespacedKey(WaystonesPlus.getInstance(), key);
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        dataContainer.set(namespacedKey, PersistentDataType.STRING, value.toString());
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
