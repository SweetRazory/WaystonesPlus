package org.sweetrazory.waystonesplus.waystone;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.Nullable;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.types.BlockType;
import org.sweetrazory.waystonesplus.types.WaystoneType;
import org.sweetrazory.waystonesplus.utils.DB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Waystone {
    String id;
    String name;
    Location location;
    String type;
    String ownerId;
    Particle particle;
    Visibility visibility;
    List<Integer> entities;
    Material icon;

    public Waystone(String id, String name, Location location, String type, String ownerId, Particle particle, Visibility visibility, @Nullable List<Integer> entities, Material icon) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
        this.ownerId = ownerId;
        this.particle = particle;
        this.visibility = visibility;
        this.entities = entities;
        this.icon = icon;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }

    public void createWaystone() {
        WaystoneMemory.initParticles(this);
        entities = Arrays.asList(spawnStructure());
    }

    public void changeType(String newType) {
        if (newType != null) {
            removeStructure();
            setType(newType);
            spawnStructure();
            DB.updateWaystone(this);
        }
    }

    public Integer[] spawnStructure() {
        World world = getLocation().getWorld();
        Block baseBlock = world.getBlockAt(getLocation());
        MetadataValue waypointIdentifier = new FixedMetadataValue(WaystonesPlus.getInstance(), id);
        MetadataValue waypointType = new FixedMetadataValue(WaystonesPlus.getInstance(), type);
        WaystoneType wst = WaystoneMemory.getWaystoneTypes().get(type);
        for (BlockType block : wst.getBlocks()) {
            Block newBlock = world.getBlockAt(new Location(baseBlock.getWorld(),
                    baseBlock.getLocation().getX() + block.getRelative().get("x").doubleValue(),
                    baseBlock.getLocation().getY() + block.getRelative().get("y").doubleValue(),
                    baseBlock.getLocation().getZ() + block.getRelative().get("z").doubleValue()
            ));
            newBlock.setType(block.getMaterial());
            newBlock.setMetadata("waystoneId", waypointIdentifier);
            newBlock.setMetadata("waystoneType", waypointType);
        }

        Integer[] blockDisplayIds = new Integer[wst.getBlockDisplays().size()];
        for (int i = 0; i < wst.getBlockDisplays().size(); i++) {
            Integer entityId = wst.getBlockDisplays().get(i).spawnBlockDisplay(id, getLocation());
            blockDisplayIds[i] = entityId;
        }

        return blockDisplayIds;
    }

    public void removeStructure() {
        Block block1 = location.getBlock().getRelative(0, 1, 0);
        Block block2 = location.getBlock().getRelative(0, 2, 0);
        Block block3 = location.getBlock().getRelative(0, 3, 0);

        block1.setType(Material.AIR);
        block2.setType(Material.AIR);
        block3.setType(Material.AIR);

        List<Entity> entities = location.getWorld().getEntities();

        for (Entity entity : entities) {
            if ((entity.getCustomName() != null && entity.getCustomName().equalsIgnoreCase(getId()))) {
                entity.remove();
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void delete() {
        removeStructure();
        DB.deleteWaystone(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Particle getParticle() {
        return particle;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;

        WaystoneMemory.changeParticles(this);
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public List<Integer> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    public void setEntities(List<Integer> entities) {
        this.entities = entities;
    }
}
