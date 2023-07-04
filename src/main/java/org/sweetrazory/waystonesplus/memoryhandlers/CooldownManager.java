package org.sweetrazory.waystonesplus.memoryhandlers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CooldownManager {
    private final Map<Player, Map<String, Long>> cooldowns;

    public CooldownManager() {
        cooldowns = new HashMap<>();
    }

    public void addPlayerCooldown(Player player, String eventName, long cooldownSeconds) {
        Map<String, Long> eventCooldowns = cooldowns.computeIfAbsent(player, k -> new HashMap<>());
        long currentTime = System.currentTimeMillis();
        long cooldownTime = currentTime + (cooldownSeconds * 1000);
        eventCooldowns.put(eventName, cooldownTime);
    }

    public boolean hasCooldown(Player player, String eventName) {
        return cooldowns.containsKey(player) && cooldowns.get(player).containsKey(eventName);
    }

    public long getRemainingCooldown(Player player, String eventName) {
        if (hasCooldown(player, eventName)) {
            long currentTime = System.currentTimeMillis();
            long cooldownTime = cooldowns.get(player).get(eventName);
            long remainingTime = cooldownTime - currentTime;
            if (remainingTime <= 0) {
                cooldowns.get(player).remove(eventName);
                if (cooldowns.get(player).isEmpty()) {
                    cooldowns.remove(player);
                }
                return 0;
            }
            return remainingTime / 1000;
        }
        return 0;
    }
}
