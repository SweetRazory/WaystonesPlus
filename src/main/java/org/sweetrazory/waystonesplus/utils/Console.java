package org.sweetrazory.waystonesplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Logger;

public class Console {
    protected final Logger logger = Bukkit.getLogger();

    public static class info extends Console {
        public info(String s) {
            logger.info(ChatColor.GREEN + s);
        }
    }

    public static class error extends Console {
        public error(String s) {
            logger.warning(ChatColor.RED + s);
        }
    }
}
