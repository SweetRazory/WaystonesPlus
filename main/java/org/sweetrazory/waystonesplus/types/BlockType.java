package org.sweetrazory.waystonesplus.types;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class BlockType {
    private final int x;
    private final int y;
    private final int z;
    private final Material material;

    public BlockType(int x, int y, int z, Material material) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public Map<String, Integer> getRelative() {
        return new HashMap<String, Integer>() {{
            put("x", x);
            put("y", y);
            put("z", z);
        }};
    }
}