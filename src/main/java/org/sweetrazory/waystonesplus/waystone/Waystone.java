package org.sweetrazory.waystonesplus.waystone;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.memoryhandlers.MemoryManager;
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
    private final WaystoneType waystoneType;
    private Visibility visibility;
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

    public WaystoneType getWaystoneType() {
        return waystoneType;
    }

    public String getName() {
        return name == null || name.equalsIgnoreCase("New Waystone") ? "New Waystone" : name;
    }

    private void spawnParticles(Location location) {
        // Iterate through all online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            // Check if the player is within the render distance of the particle location
            if (player.getLocation().getWorld().equals(location.getWorld()) && player.getLocation().distance(location) <= Bukkit.getServer().getViewDistance() * 16) {
                // Spawn particles for the player
                player.spawnParticle(Particle.ENCHANTMENT_TABLE, location, 1, 0.075, 0, 0.075);
            }
        }
    }

    public void enableHandler() {
        spawnStructure();

        particleSpawner = new BukkitRunnable() {
            @Override
            public void run() {
                spawnParticles(new Location(location.getWorld(), location.getX() + 0.5, location.getY() + 2.5, location.getZ() + 0.5));
            }
        };

        particleSpawner.runTaskTimer(WaystonesPlus.getInstance(), 0, 2);
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

        List<Entity> entities = location.getWorld().getEntities();

        for (Entity entity : entities) {
            for (Integer entityId : entityIds) {
                if ((entity.getCustomName() != null && entity.getCustomName().equalsIgnoreCase(getUuid())) || entity.getEntityId() == entityId) {
                    entity.remove();
                }
            }
        }
    }

    public Integer[] spawnStructure() {
        World world = getLocation().getWorld();
        Block baseBlock = world.getBlockAt(getLocation());
        MetadataValue waypointIdentifier = new FixedMetadataValue(WaystonesPlus.getInstance(), uuid);
        MetadataValue waypointType = new FixedMetadataValue(WaystonesPlus.getInstance(), type);

        for (BlockType block : waystoneType.getBlocks()) {
            Block newBlock = world.getBlockAt(new Location(baseBlock.getWorld(),
                    baseBlock.getLocation().getX() + block.getRelative().get("x").doubleValue(),
                    baseBlock.getLocation().getY() + block.getRelative().get("y").doubleValue(),
                    baseBlock.getLocation().getZ() + block.getRelative().get("z").doubleValue()
            ));
            newBlock.setType(block.getMaterial());
            newBlock.setMetadata("waystoneId", waypointIdentifier);
            newBlock.setMetadata("waystoneType", waypointType);
        }

        Integer[] blockDisplayIds = new Integer[waystoneType.getBlockDisplays().size()];
        for (int i = 0; i < waystoneType.getBlockDisplays().size(); i++) {
            Integer entityId = waystoneType.getBlockDisplays().get(i).spawnBlockDisplay(uuid, getLocation());
            blockDisplayIds[i] = entityId;
        }

        return blockDisplayIds;
    }

    public void createWaystone(Waystone waystone) {
        WaystoneMemory WaystoneMemory = MemoryManager.getWaystoneMemory();
        particleSpawner = new BukkitRunnable() {
            @Override
            public void run() {
                spawnParticles(new Location(location.getWorld(), location.getX() + 0.5, location.getY() + 2.5, location.getZ() + 0.5));
            }
        };

        particleSpawner.runTaskTimer(WaystonesPlus.getInstance(), 0, 2);

        entityIds = spawnStructure();

        WaystoneMemory.createWaystoneConfig(waystone.getName(), waystone.getUuid(), this, waystone.getEntityIds());
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;

        WaystoneMemory.saveWaystoneConfig(this);
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
