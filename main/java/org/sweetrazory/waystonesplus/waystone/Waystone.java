package org.sweetrazory.waystonesplus.waystone;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.sweetrazory.waystonesplus.Main;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.types.BlockType;
import org.sweetrazory.waystonesplus.types.WaystoneType;

import java.util.List;

public class Waystone {
    private final String uuid;
    private final String name;
    private final Location location;
    private final String type;
    private final String ownerId;
    private final Visibility visibility;
    WaystoneType waystoneType;
    private Integer[] entityIds = new Integer[]{};
    private BukkitRunnable particleSpawner;

    public Waystone(String name, String uuid, WaystoneType waystoneType, Location location, String type, String ownerId, Visibility visibility) {
        this.waystoneType = waystoneType;
        this.uuid = uuid;
        this.name = name;
        this.location = location;
        this.type = type;
        this.ownerId = ownerId;
        this.visibility = visibility;
    }

    public Integer[] getEntityIds() {
        return entityIds;
    }

    public void setEntityIds(Integer[] entityIds) {
        this.entityIds = entityIds;
    }

    public String getName() {
        return name == null || name.equalsIgnoreCase("Waystone maker") ? "New Waystone" : name;
    }

    private void spawnParticles(Location location) {
        // Iterate through all online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            // Check if the player is within the render distance of the particle location
            if (player.getLocation().distance(location) <= Bukkit.getServer().getViewDistance() * 16) {
                // Spawn particles for the player
                player.spawnParticle(Particle.ENCHANTMENT_TABLE, location, 1, 0.075, 0, 0.075);
            }
        }
    }

    public void enableHandler() {
        spawnStructure(location);

        particleSpawner = new BukkitRunnable() {
            @Override
            public void run() {
                spawnParticles(new Location(location.getWorld(), location.getX() + 0.5, location.getY() + 2.5, location.getZ() + 0.5));
            }
        };

        particleSpawner.runTaskTimer(Main.getInstance(), 0, 2);
    }

    public void waystoneDelete() {
        if (!particleSpawner.isCancelled()) {
            particleSpawner.cancel();
        }

        Block block1 = location.getBlock().getRelative(0, 1, 0);
        Block block2 = location.getBlock().getRelative(0, 2, 0);
        Block block3 = location.getBlock().getRelative(0, 3, 0);

        block1.setType(Material.AIR);
        block2.setType(Material.AIR);
        block3.setType(Material.AIR);

        List<Entity> entities = Bukkit.getWorld("world").getEntities();

        for (Entity entity : entities) {
            for (Integer entityId : entityIds) {
                if ((entity.getCustomName() != null && entity.getCustomName().equalsIgnoreCase(getUuid())) || entity.getEntityId() == entityId) {
                    entity.remove();
                }
            }
        }
    }

    public Integer[] spawnStructure(Location location) {
        World world = Bukkit.getWorld("world");
        assert world != null;
        Block baseBlock = world.getBlockAt(location);
        MetadataValue waypointIdentifier = new FixedMetadataValue(Main.getInstance(), uuid);

        for (BlockType block : waystoneType.getBlocks()) {
            Block newBlock = world.getBlockAt(new Location(baseBlock.getWorld(),
                    baseBlock.getLocation().getX() + block.getRelative().get("x").doubleValue(),
                    baseBlock.getLocation().getY() + block.getRelative().get("y").doubleValue(),
                    baseBlock.getLocation().getZ() + block.getRelative().get("z").doubleValue()
            ));
            newBlock.setType(block.getMaterial());
            newBlock.setMetadata("waystoneId", waypointIdentifier);
        }

        Integer[] blockDisplayIds = new Integer[waystoneType.getBlockDisplays().size()];
        for (int i = 0; i < waystoneType.getBlockDisplays().size(); i++) {
            Integer entityId = waystoneType.getBlockDisplays().get(i).spawnBlockDisplay(uuid, location);
            blockDisplayIds[i] = entityId;
        }

        return blockDisplayIds;
    }


    public void createWaystone(Waystone waystone, Block targetBlock) {
        WaystoneMemory WaystoneMemory = new WaystoneMemory();
        particleSpawner = new BukkitRunnable() {
            @Override
            public void run() {
                spawnParticles(new Location(location.getWorld(), location.getX() + 0.5, location.getY() + 2.5, location.getZ() + 0.5));
            }
        };

        particleSpawner.runTaskTimer(Main.getInstance(), 0, 2);

        entityIds = spawnStructure(targetBlock.getLocation());

        WaystoneMemory.saveWaystone(waystone.getName(), waystone.getUuid(), this, waystone.getEntityIds());
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public String getUuid() {
        return uuid;
    }

    public Location getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }
}
