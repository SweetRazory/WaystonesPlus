package org.sweetrazory.waystonesplus.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class BlockDisplaySpawner {
    public Integer spawnBlockDisplay(World world, Location targetBlock, Material material, Vector3f translation, Vector3f scale, String waystoneId) {
        BlockData blockData = material.createBlockData();
        BlockDisplay blockDisplay = (BlockDisplay) world.spawnEntity(new Location(targetBlock.getWorld(), targetBlock.getX(), targetBlock.getY() + 3, targetBlock.getZ()), EntityType.BLOCK_DISPLAY);
        blockDisplay.setBlock(blockData);
        Transformation transformation = new Transformation(translation, new AxisAngle4f(), scale, new AxisAngle4f());
        blockDisplay.setTransformation(transformation);
        blockDisplay.setCustomName(waystoneId);
        return blockDisplay.getEntityId();
    }


}
