package org.sweetrazory.waystonesplus.types;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

public class WaystoneType {
    private final String typeName;
    List<BlockType> blocks;
    List<BlockDisplayType> BlockDisplays;
    ShapedRecipe recipe;
    String spawnItemHeadId;
    String spawnItemTextures;

    public WaystoneType(String typeName, List<BlockType> blocks, List<BlockDisplayType> blockDisplays, ShapedRecipe recipe, String spawnItemHeadId, String spawnItemTextures) {
        this.typeName = typeName;
        this.blocks = blocks;
        BlockDisplays = blockDisplays;
        this.recipe = recipe;
        this.spawnItemHeadId = spawnItemHeadId;
        this.spawnItemTextures = spawnItemTextures;

        Bukkit.getServer().addRecipe(recipe);
    }

    public String getTypeName() {
        return typeName;
    }

    public List<BlockType> getBlocks() {
        return blocks;
    }

    public List<BlockDisplayType> getBlockDisplays() {
        return BlockDisplays;
    }

    public String getSpawnItemHeadId() {
        return spawnItemHeadId;
    }

    public String getSpawnItemTextures() {
        return spawnItemTextures;
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }
}
