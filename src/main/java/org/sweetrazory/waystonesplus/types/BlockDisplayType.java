package org.sweetrazory.waystonesplus.types;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.UUID;

public class BlockDisplayType {
    private final Material material;
    private final Vector3f translation;
    private final Vector3f scale;
    private UUID entityId;

    /**
     * TODO: Implement
     *  private AxisAngle4f leftRotation;
     *  private AxisAngle4f leftRotation;
     */

    public BlockDisplayType(Material material, Vector3f translation, Vector3f scale) {
        this.material = material;
        this.translation = translation;
        this.scale = scale;
    }


    public Integer spawnBlockDisplay(String waystoneId, Location location) {
        BlockDisplay blockDisplay = (BlockDisplay) (location.getWorld()).spawnEntity(new Location(location.getWorld(), location.getX(), location.getY() + 3, location.getZ()), EntityType.BLOCK_DISPLAY);

        this.entityId = blockDisplay.getUniqueId();
        blockDisplay.setBlock(material.createBlockData());
        Transformation transformation = new Transformation(translation, new AxisAngle4f(), scale, new AxisAngle4f());
        blockDisplay.setTransformation(transformation);
        blockDisplay.setCustomName(waystoneId);
        return blockDisplay.getEntityId();
    }

    public void removeBlockDisplay() {
        Bukkit.getEntity(entityId).remove();
    }

    public Material getMaterial() {
        return material;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public UUID getEntityId() {
        return entityId;
    }
}
