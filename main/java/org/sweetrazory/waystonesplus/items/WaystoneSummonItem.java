package org.sweetrazory.waystonesplus.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;
import org.sweetrazory.waystonesplus.Main;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.types.WaystoneType;

import java.lang.reflect.Field;
import java.util.UUID;

public class WaystoneSummonItem {

    public ItemStack getLodestoneHead(@Nullable String name, WaystoneMemory waystoneMemory, String type) {
        ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();

        GameProfile gameProfile = new GameProfile(UUID.fromString("9bf98ec7-ca26-45b2-a7f2-976f7655d361"), null);
        gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjUwMjE2NTk3ZjE2YmNkYzIyZjEwYTNjYzIyOTljYTg4NGM1N2U0Njg1MGZiOGRlZjAxODk1NjYyZDM5MDQwNCJ9fX0=", null));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        skullItem.setItemMeta(skullMeta);
        ItemMeta itemMeta = skullItem.getItemMeta();
        itemMeta.setDisplayName(name != null ? name : "Waystone maker");

        NamespacedKey waystoneId = new NamespacedKey(Main.getInstance(), "waystoneId");
        NamespacedKey waystoneType = new NamespacedKey(Main.getInstance(), "waystoneType");
        String waystoneUUID = UUID.randomUUID().toString();
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        dataContainer.set(waystoneId, PersistentDataType.STRING, waystoneUUID);
        WaystoneType ws = waystoneMemory.getWaystoneTypes().get(type);
        dataContainer.set(waystoneType, PersistentDataType.STRING, "lodestone");
        skullItem.setItemMeta(itemMeta);

        return skullItem;
    }
}
