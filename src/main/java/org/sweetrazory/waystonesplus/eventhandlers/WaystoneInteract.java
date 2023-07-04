package org.sweetrazory.waystonesplus.eventhandlers;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.memoryhandlers.LangManager;
import org.sweetrazory.waystonesplus.utils.ColoredText;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.util.List;

public class WaystoneInteract {
    public Waystone getInteractedWaystone(PlayerInteractEvent e) {
        Waystone waystone = null;
        if (e.getHand() == EquipmentSlot.HAND && e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            List<MetadataValue> blockMeta = e.getClickedBlock().getMetadata("waystoneId");
            if (!blockMeta.isEmpty() && (e.getPlayer().hasPermission("waystonesplus.interact") || e.getPlayer().isOp())) {
                waystone = DB.getWaystone(blockMeta.get(0).asString());
                Visibility waystoneVisibility = waystone.getVisibility();
                if (waystoneVisibility.equals(Visibility.PRIVATE) && !e.getPlayer().hasPermission("waystonesplus.interact.private") && !e.getPlayer().isOp()) {
                    e.getPlayer().sendMessage(LangManager.notOwner);
                    return null;
                }

                List<String> exploredIds = DB.getExploredWaystoneIds(e.getPlayer().getUniqueId().toString(), null, null);

                if (waystoneVisibility.equals(Visibility.PUBLIC) && !exploredIds.contains(waystone.getId()) && !waystone.getOwnerId().equals(e.getPlayer().getUniqueId().toString())) {
                    e.getPlayer().sendTitle(ColoredText.getText(waystone.getName()), ColoredText.getText("&6is now Explored!"));
                    Location fireworkSpawn = e.getPlayer().getTargetBlock(null, 5).getLocation();
                    spawnAndExplodeFirework(new Location(fireworkSpawn.getWorld(), fireworkSpawn.getBlockX() + 0.4, fireworkSpawn.getBlockY(), fireworkSpawn.getBlockZ() + 0.4));
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 1.1f);
                    DB.insertOrUpdateExploredWaystone(e.getPlayer().getName(), e.getPlayer().getUniqueId().toString(), waystone.getId());
                    return null;
                }
            }
        }
        return waystone;
    }

    private void spawnAndExplodeFirework(Location location) {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkEffect effect = FireworkEffect.builder()
                .flicker(false)
                .trail(true)
                .with(FireworkEffect.Type.BALL)
                .withColor(Color.WHITE)
                .build();
        firework.setFireworkMeta(createFireworkMeta(effect, location));

        // Schedule a task to explode the firework instantly
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                firework.detonate();
            }
        }.runTaskLater(WaystonesPlus.getInstance(), 1L);
    }

    private FireworkMeta createFireworkMeta(FireworkEffect effect, Location location) {
        FireworkMeta meta = ((Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK)).getFireworkMeta();
        meta.addEffect(effect);
        meta.setPower(0);
        return meta;
    }
}
