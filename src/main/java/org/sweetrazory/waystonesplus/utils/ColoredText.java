package org.sweetrazory.waystonesplus.utils;

import org.bukkit.ChatColor;

public class ColoredText {

    public ColoredText() {
    }

    public static String getText(String s) {
        return ChatColor.translateAlternateColorCodes('&', s.replaceAll("\uFFFD", "&"));
    }
}
