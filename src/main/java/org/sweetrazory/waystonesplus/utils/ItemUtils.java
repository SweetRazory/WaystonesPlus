package org.sweetrazory.waystonesplus.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sweetrazory.waystonesplus.WaystonesPlus;

public class ItemUtils {
    public static boolean hasPersistentData(ItemStack item, String key) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
            NamespacedKey namespacedKey = new NamespacedKey(WaystonesPlus.getInstance(), key);
            return dataContainer.has(namespacedKey, PersistentDataType.STRING);
        }
        return false;
    }

    public static String getPersistentString(ItemStack item, String key) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
            NamespacedKey namespacedKey = new NamespacedKey(WaystonesPlus.getInstance(), key);
            if (dataContainer.has(namespacedKey, PersistentDataType.STRING)) {
                return dataContainer.get(namespacedKey, PersistentDataType.STRING);
            }
        }
        return null;
    }
}
