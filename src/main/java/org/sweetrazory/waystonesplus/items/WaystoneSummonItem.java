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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.types.WaystoneType;
import org.sweetrazory.waystonesplus.utils.ColoredText;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class WaystoneSummonItem {
    public static ItemStack getLodestoneHead(@Nullable String name, String type, @Nullable String headOwnerId, @Nullable String texturesString, @NotNull Visibility visibility) {
        ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
        WaystoneType ws = WaystoneMemory.getWaystoneTypes().get(type);

        String playerId = "9bf98ec7-ca26-45b2-a7f2-976f7655d361";
        String textures = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjUwMjE2NTk3ZjE2YmNkYzIyZjEwYTNjYzIyOTljYTg4NGM1N2U0Njg1MGZiOGRlZjAxODk1NjYyZDM5MDQwNCJ9fX0=";

        GameProfile gameProfile = new GameProfile(UUID.fromString(headOwnerId == null ? ws.getSpawnItemHeadId() != null ? ws.getSpawnItemHeadId() : playerId : headOwnerId), null);
        gameProfile.getProperties().put("textures", new Property("textures", texturesString == null ? ws.getSpawnItemTextures() != null ? ws.getSpawnItemTextures() : textures : texturesString, null));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        skullItem.setItemMeta(skullMeta);
        ItemMeta itemMeta = skullItem.getItemMeta();
        itemMeta.setDisplayName(name != null && name.length() > 0 ?
                ColoredText.getText(name) :
                ColoredText.getText(LangManager.newWaystoneName));

        NamespacedKey waystoneType = new NamespacedKey(WaystonesPlus.getInstance(), "waystoneType");
        NamespacedKey waystoneVisibility = new NamespacedKey(WaystonesPlus.getInstance(), "waystoneVisibility");
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        dataContainer.set(waystoneType, PersistentDataType.STRING, type);
        dataContainer.set(waystoneVisibility, PersistentDataType.STRING, visibility.name());
        itemMeta.setLore(new ArrayList<String>() {{
            add(ColoredText.getText(visibility == Visibility.PRIVATE ? "&cPRIVATE" : visibility == Visibility.PUBLIC ? "&aPUBLIC" : "&eGLOBAL"));
        }});
        skullItem.setItemMeta(itemMeta);

        return skullItem;
    }
}
