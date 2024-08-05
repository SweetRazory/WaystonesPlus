package org.sweetrazory.waystonesplus.utils;

import org.bukkit.Bukkit;

public class Version {
    private static String bukkitVersion = Bukkit.getVersion();

    public static Boolean isMC194() {
        return bukkitVersion.contains("1.19.4");
    }

    public static Boolean isMC20() {
        return bukkitVersion.contains("1.20");
    }
    
    public static Boolean isMC21() {
        return bukkitVersion.contains("1.21");
    }
}
