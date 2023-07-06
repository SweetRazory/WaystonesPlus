package org.sweetrazory.waystonesplus.memoryhandlers;

import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class LangManager {
    public static String versionWarning;
    public static String waystoneTypeNonexistent;
    public static String waystoneTypeMissing;
    public static String waystoneVisibilityMissing;
    public static String waystoneNameMissing;
    public static String noPermission;
    public static String reload;
    public static String notOwner;
    public static String wait;
    public static String newWaystoneName;
    public static String explorersMenuTitle;
    public static String selectorMenuTitle;
    public static String settingsMenuTitle;
    public static String iconMenuTitle;
    public static String typeMenuTitle;
    public static String teleportMenuTitle;
    public static String visibilityMenuTitle;
    public static String itemsMenuTitle;
    public static String prevPage;
    public static String nextPage;
    public static String returnText;
    public static String blocks;
    public static String particleMenuTitle;
    public static String blocksMenuTitle;
    public static String items;
    public static String noItemHeld;
    public static String newWaystoneTitle;
    public static String newWaystoneSubtitle;
    public static String invalidItem;
    public static String invalidVisibility;

    public static void loadConfig() {
        File configFile = new File(WaystonesPlus.getInstance().getDataFolder().getAbsolutePath() + File.separator + "localization.yml");
        try {
            FileInputStream fis = new FileInputStream(configFile);
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(fis);

            versionWarning = (String) config.getOrDefault("version-warning", "[ WaystonesPlus ] This plugin only supports 1.19.4 or higher versions!");
            waystoneTypeNonexistent = (String) config.getOrDefault("waystone-type-nonexistent", "A waystone variation with that name doesn't exist!");
            waystoneTypeMissing = (String) config.getOrDefault("waystone-type-missing", "You need to provide the Waystone type.");
            waystoneVisibilityMissing = (String) config.getOrDefault("waystone-visibility-missing", "You need to provide the Waystone visibility.");
            waystoneNameMissing = (String) config.getOrDefault("waystone-name-missing", "You need to provide the Waystone name.");
            noPermission = (String) config.getOrDefault("no-permission", "You don't have permissions to do that!");
            reload = (String) config.getOrDefault("reload", "&cReloading WaystonesPlus's config and waystones.");
            notOwner = (String) config.getOrDefault("not-owner", "&cYou are not the owner of this Waystone.");
            wait = (String) config.getOrDefault("wait", "&cPlease wait a moment...");
            newWaystoneName = (String) config.getOrDefault("new-waystone-name", "&aEnter a new name for the Waystone:");
            explorersMenuTitle = (String) config.getOrDefault("explorers-menu-title", "&6Waystone Explorers Menu");
            selectorMenuTitle = (String) config.getOrDefault("selector-menu-title", "&6Waystone Option Selector");
            settingsMenuTitle = (String) config.getOrDefault("settings-menu-title", "&6Waystone Settings");
            iconMenuTitle = (String) config.getOrDefault("icon-menu-title", "&6Waystone Icon");
            typeMenuTitle = (String) config.getOrDefault("type-menu-title", "&6Waystone Type");
            teleportMenuTitle = (String) config.getOrDefault("teleport-menu-title", "&6Waystone Teleportation");
            visibilityMenuTitle = (String) config.getOrDefault("visibility-menu-title", "&6Waystone Visibility");
            itemsMenuTitle = (String) config.getOrDefault("items-menu-title", "&6Waystone Items");
            prevPage = (String) config.getOrDefault("prev-page", "&ePrevious Page");
            nextPage = (String) config.getOrDefault("next-page", "&eNext Page");
            returnText = (String) config.getOrDefault("return-text", "&eReturn");
            blocks = (String) config.getOrDefault("blocks", "&6Blocks");
            particleMenuTitle = (String) config.getOrDefault("particle-menu-title", "&6Waystone Particles");
            blocksMenuTitle = (String) config.getOrDefault("blocks-menu-title", "&6Blocks");
            items = (String) config.getOrDefault("items", "&6Items");
            noItemHeld = (String) config.getOrDefault("no-item-held", "&cYou are not holding an item.");
            invalidItem = (String) config.getOrDefault("invalid-item", "&cInvalid item. Please hold an item in your hand.");
            invalidVisibility = (String) config.getOrDefault("invalid-visibility", "&cInvalid visibility. Valid options: public, private.");
            newWaystoneTitle = (String) config.getOrDefault("new-waystone-title", "&8New Waystone:");
            newWaystoneSubtitle = (String) config.getOrDefault("new-waystone-subtitle", "&6%waystone_name%");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
