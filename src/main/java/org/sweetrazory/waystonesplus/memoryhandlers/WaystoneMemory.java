package org.sweetrazory.waystonesplus.memoryhandlers;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.joml.Vector3f;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.items.WaystoneSummonItem;
import org.sweetrazory.waystonesplus.types.BlockDisplayType;
import org.sweetrazory.waystonesplus.types.BlockType;
import org.sweetrazory.waystonesplus.types.WaystoneType;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.waystone.Waystone;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class WaystoneMemory {
    private static final Map<String, BukkitTask> waystoneParticleMemory = new HashMap<>();
    private static final Map<String, WaystoneType> waystoneTypeMemory = new HashMap<>();

    public WaystoneMemory() {
        enableHandler();
    }

    public static Map<String, WaystoneType> getWaystoneTypes() {
        return Collections.unmodifiableMap(waystoneTypeMemory);
    }

    public static void removeParticles(String id) {
        if (waystoneParticleMemory.containsKey(id)) {
            waystoneParticleMemory.get(id).cancel();
            waystoneParticleMemory.remove(id);
        }
    }

    public static void changeParticles(Waystone waystone) {
        DB.updateWaystone(waystone);
        if (waystoneParticleMemory.containsKey(waystone.getId())) {
            waystoneParticleMemory.get(waystone.getId()).cancel();
            if (waystone.getParticle() != null) {
                waystoneParticleMemory.put(waystone.getId(), new BukkitRunnable() {
                    @Override
                    public void run() {
                        spawnParticles(new Location(waystone.getLocation().getWorld(), waystone.getLocation().getX() + 0.5, waystone.getLocation().getY() + 2, waystone.getLocation().getZ() + 0.5), waystone.getParticle());
                    }
                }.runTaskTimer(WaystonesPlus.getInstance(), 0, 2));
            }
        }
    }

    private static void spawnParticles(Location location, Particle particle) {
        if (particle != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getLocation().getWorld().equals(location.getWorld()) && player.getLocation().distance(location) <= Bukkit.getServer().getViewDistance() * 16) {
                    player.spawnParticle(particle, location, 1, 0.5, 0.5, 0.5);
                }
            }
        }
    }

    public static void initParticles(Waystone waystone) {
        waystoneParticleMemory.put(waystone.getId(), new BukkitRunnable() {
            @Override
            public void run() {
                spawnParticles(new Location(waystone.getLocation().getWorld(), waystone.getLocation().getX() + 0.5, waystone.getLocation().getY() + 2.5, waystone.getLocation().getZ() + 0.5), waystone.getParticle());
            }
        }.runTaskTimer(WaystonesPlus.getInstance(), 0, 2));
    }

    public static void initializeWaystones() {
        List<Waystone> waystoneInfos = DB.getAllWaystones();
        for (Waystone waystoneInfo : waystoneInfos) {
            waystoneInfo.spawnStructure();
            waystoneParticleMemory.put(waystoneInfo.getId(), new BukkitRunnable() {
                @Override
                public void run() {
                    spawnParticles(new Location(waystoneInfo.getLocation().getWorld(), waystoneInfo.getLocation().getX() + 0.5, waystoneInfo.getLocation().getY() + 2.5, waystoneInfo.getLocation().getZ() + 0.5), waystoneInfo.getParticle());
                }
            }.runTaskTimer(WaystonesPlus.getInstance(), 0, 2));
        }
    }

    public void loadWaystoneTypes() {
        File configFile = new File(WaystonesPlus.getInstance().getDataFolder().getAbsolutePath() + File.separator + "waystones.yml");
        try {
            FileInputStream fis = new FileInputStream(configFile);
            Yaml yaml = new Yaml();

            Map<String, Object> config = yaml.load(fis);

            List<Map<String, Object>> waystoneTypes = (List<Map<String, Object>>) config.get("waystones");

            if (waystoneTypes == null) return;
            for (Map<String, Object> waystone : waystoneTypes) {
                String typeName = (String) waystone.get("name");
                List<BlockType> blocks = new ArrayList<>();
                List<BlockDisplayType> blockDisplays = new ArrayList<>();

                List<Map<String, Object>> blockList = (List<Map<String, Object>>) waystone.get("blocks");
                for (Map<String, Object> block : blockList) {
                    int x = Integer.parseInt(String.valueOf(block.get("x")));
                    int y = Integer.parseInt(String.valueOf(block.get("y")));
                    int z = Integer.parseInt(String.valueOf(block.get("z")));
                    Material material = Material.matchMaterial(String.valueOf(block.get("name")));
                    blocks.add(new BlockType(x, y, z, material));
                }

                List<Map<String, Object>> blockDisplayList = (List<Map<String, Object>>) waystone.get("blockDisplays");
                for (Map<String, Object> blockDisplay : blockDisplayList) {
                    Map<String, Object> offset = (Map<String, Object>) blockDisplay.get("offset");
                    Float offsetX = Float.parseFloat((String) offset.get("x"));
                    Float offsetY = Float.parseFloat((String) offset.get("y"));
                    Float offsetZ = Float.parseFloat((String) offset.get("z"));

                    Vector3f translation = new Vector3f(offsetX != null ? offsetX : 0f, offsetY != null ? offsetY : 0f, offsetZ != null ? offsetZ : 0f);

                    Map<String, Object> scales = (Map<String, Object>) blockDisplay.get("scale");
                    Float scaleX = Float.parseFloat((String) scales.get("x"));
                    Float scaleY = Float.parseFloat((String) scales.get("y"));
                    Float scaleZ = Float.parseFloat((String) scales.get("z"));

                    Vector3f scale = new Vector3f(scaleX != null ? scaleX : 0f, scaleY != null ? scaleY : 0f, scaleZ != null ? scaleZ : 0f);

                    Material material = Material.matchMaterial(String.valueOf(blockDisplay.get("name")));
                    blockDisplays.add(new BlockDisplayType(material, translation, scale));
                }

                String headOwnerId = ((Map<String, String>) waystone.get("spawnItem")).get("playerId");
                String textures = ((Map<String, String>) waystone.get("spawnItem")).get("textures");
                ShapedRecipe recipe = null;
                if (ConfigManager.enableCrafting) {
                    ItemStack craftResult = new WaystoneSummonItem().getLodestoneHead(null, typeName, headOwnerId, textures, ConfigManager.defaultVisibility);
                    NamespacedKey recipeName = new NamespacedKey(WaystonesPlus.getInstance(), typeName + "_recipe");

                    List<String> craftingList = (List<String>) waystone.get("crafting");
                    if (craftingList != null && !craftingList.isEmpty() && craftingList instanceof List && craftingList.size() == 9) {
                        recipe = new ShapedRecipe(recipeName, new ItemStack(craftResult));
                        recipe.shape("123", "456", "789");

                        System.out.println(craftingList);

                        char[] symbols = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
                        for (int i = 0; i < craftingList.size(); i++) {
                            String ingredient = craftingList.get(i);

                            if (ingredient.toLowerCase().equals("air")) continue;

                            char symbol = symbols[i];
                            Material material = Material.matchMaterial(ingredient);

                            recipe.setIngredient(symbol, material);
                        }
                    }
                    waystoneTypeMemory.put(typeName, new WaystoneType(typeName, blocks, blockDisplays, recipe, headOwnerId, textures));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void enableHandler() {
        loadWaystoneTypes();
        initializeWaystones();
    }
}