package org.sweetrazory.waystonesplus.memoryhandlers;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.joml.Vector3f;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.items.WaystoneSummonItem;
import org.sweetrazory.waystonesplus.types.BlockDisplayType;
import org.sweetrazory.waystonesplus.types.BlockType;
import org.sweetrazory.waystonesplus.types.WaystoneType;
import org.sweetrazory.waystonesplus.utils.Console;
import org.sweetrazory.waystonesplus.waystone.Waystone;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WaystoneMemory {
    private static final Map<String, Waystone> waystoneDataMemory = new HashMap<>();
    private static final String WAYSTONES = "waystones";
    private static final Map<String, WaystoneType> waystoneTypeMemory = new HashMap<>();
    private static File waystonesFolder;

    public WaystoneMemory() {
        waystonesFolder = new File(WaystonesPlus.getInstance().getDataFolder(), WAYSTONES);

        loadWaystoneTypes();
        enableHandler();
    }

    public static Map<String, Waystone> getWaystoneDataMemory() {
        return waystoneDataMemory;
    }

    public static Map<String, WaystoneType> getWaystoneTypes() {
        return Collections.unmodifiableMap(waystoneTypeMemory);
    }

    public static void saveWaystoneConfig(Waystone waystone) {
        File[] files = waystonesFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && file.getName().equals(waystone.getUuid())) {
                    File configFile = new File(file, "config.yml");

                    if (configFile.exists()) {
                        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                        config.set("visibility", waystone.getVisibility().getValue());
                        config.set("name", waystone.getName());
                        try {
                            config.save(configFile);
                        } catch (IOException e) {
                            WaystonesPlus.Logger().warning("Couldn't update Waystone data. It will reset to it's initial state after restart!");
                        }
                    }
                }
            }
        }
    }

    public static String[] getWaystoneIds() {
        return waystoneDataMemory.keySet().toArray(new String[0]);
    }

    public void loadWaystoneData(String waystoneId) {
        if (waystoneId == null) {
            if (waystonesFolder.exists() && waystonesFolder.isDirectory()) {
                File[] waystoneFiles = waystonesFolder.listFiles(File::isDirectory);
                if (waystoneFiles != null) {
                    for (File waystoneFile : waystoneFiles) {
                        String waystoneUUID = waystoneFile.getName();
                        loadWaystoneConfig(waystoneUUID);
                    }
                } else {
                    Bukkit.broadcastMessage(Color.ORANGE + "There are no Waystones to load.");
                }
            }
        } else {
            loadWaystoneConfig(waystoneId);
        }
    }

    public void addWaystone(String name, WaystoneType waystoneType, Location location, String type, Player owner, Visibility visibility) {
        String uuid = UUID.randomUUID().toString();
        Waystone newWaystone = new Waystone(name, uuid, waystoneType, location, type, owner.getUniqueId().toString(), visibility);
        newWaystone.createWaystone(newWaystone);
        createWaystoneConfig(name, uuid, newWaystone, newWaystone.getEntityIds());
    }

    public void removeWaystone(String waystoneId) {
        Waystone waystoneToDelete = waystoneDataMemory.get(waystoneId);
        if (waystoneToDelete != null) {
            File waystoneFolder = new File(waystonesFolder, waystoneId);
            if (waystoneFolder.exists() && waystoneFolder.isDirectory()) {
                waystoneToDelete.waystoneDelete();
                deleteFolder(waystoneFolder);
                waystoneDataMemory.remove(waystoneId);
            }
        }
    }

    private void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }

    public void loadWaystoneConfig(String waystoneId) {
        File waystoneFolder = new File(waystonesFolder, waystoneId);
        if (waystoneFolder.exists() && waystoneFolder.isDirectory()) {
            File configFile = new File(waystoneFolder, "config.yml");
            if (configFile.exists() && configFile.isFile()) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                String worldName = config.getString("location.world");
                double x = config.getDouble("location.x");
                double y = config.getDouble("location.y");
                double z = config.getDouble("location.z");
                String type = config.getString("type");
                String ownerId = config.getString("owner");
                String name = config.getString("name");
                String visibility = config.getString("visibility");
                Integer[] entityIds = config.getIntegerList("entityIds").toArray(new Integer[0]);
                Integer[] entityIdList = new Integer[entityIds.length];
                System.arraycopy(entityIds, 0, entityIdList, 0, entityIds.length);

                Location location = new Location(WaystonesPlus.getInstance().getServer().getWorld(worldName), x, y, z);
                Waystone loadedWaystone = new Waystone(name, waystoneId, waystoneTypeMemory.get(type), location, type, ownerId, Visibility.fromString(visibility));

                loadedWaystone.setEntityIds(entityIdList);
                waystoneDataMemory.put(waystoneId, loadedWaystone);
            }  // TODO: Handle missing config file for the waystone
        }  // TODO: Handle invalid waystone folder
    }

    public void loadWaystoneTypes() {
        File configFile = new File(WaystonesPlus.getInstance().getDataFolder().getAbsolutePath() + File.separator + "waystones.yml");
        try {
            FileInputStream fis = new FileInputStream(configFile);
            Yaml yaml = new Yaml();

            Map<String, Object> config = yaml.load(fis);

            List<Map<String, Object>> waystoneTypes = (List<Map<String, Object>>) config.get("waystones");

            if (waystoneTypes != null) {
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

                    ItemStack craftResult = new WaystoneSummonItem().getLodestoneHead(null, typeName, headOwnerId, textures, Visibility.PRIVATE);
                    NamespacedKey recipeName = new NamespacedKey(WaystonesPlus.getInstance(), typeName + "_recipe");

                    ShapedRecipe recipe = new ShapedRecipe(recipeName, new ItemStack(craftResult));
                    List<String> craftingList = (List<String>) waystone.get("crafting");
                    recipe.shape("123", "456", "789");

                    char[] symbols = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
                    for (int i = 0; i < craftingList.size(); i++) {
                        String ingredient = craftingList.get(i);
                        char symbol = symbols[i];
                        Material material = Material.matchMaterial(ingredient);

                        recipe.setIngredient(symbol, material);
                    }
                    waystoneTypeMemory.put(typeName, new WaystoneType(typeName, blocks, blockDisplays, recipe, headOwnerId, textures));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void enableHandler() {
        File[] files = new File(WaystonesPlus.getInstance().getDataFolder().getAbsolutePath()).listFiles();

        boolean found = false;

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && file.getName().equals(WAYSTONES)) {
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            if (waystonesFolder.mkdir()) {
                new Console.info("The 'waystones' folder has been created.");
            } else {
                new Console.error("Failed to create the 'waystones' folder.");
            }
        }

        loadWaystoneTypes();
        loadWaystoneData(null);

        for (Waystone waystone : waystoneDataMemory.values()) {
            waystone.enableHandler();
        }
    }

    public void createWaystoneConfig(String name, String waystoneId, Waystone waystone, Integer[] entityIds) {
        if (!waystonesFolder.exists()) {
            waystonesFolder.mkdir();
        }
        File[] files = waystonesFolder.listFiles();
//        Main.Logger().warning(Arrays.stream(files).toArray().toString());

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && file.getName().equals(waystone.getUuid())) {
                    // Waystone folder already exists
                    return;
                }
            }
        }

        File waystoneFolder = new File(waystonesFolder, waystone.getUuid());
        if (waystoneFolder.mkdir()) {
            File waystoneConfig = new File(waystoneFolder, "config.yml");

            // Create a YAML representation of the waystone config
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            Map<String, Object> configData = new HashMap<>();

            configData.put("id", waystone.getUuid());
            configData.put("name", waystone.getName());

            Map<String, Double> coords = new HashMap<>();
            coords.put("x", waystone.getLocation().getX());
            coords.put("y", waystone.getLocation().getY());
            coords.put("z", waystone.getLocation().getZ());
            configData.put("location", coords);
            configData.put("location.world", waystone.getLocation().getWorld().getName());

            List<String> newEntityIds = new ArrayList<>();
            for (Integer entityId : entityIds) {
                newEntityIds.add(entityId.toString());
            }
            configData.put("entityIds", newEntityIds);

            configData.put("type", waystone.getType());
            configData.put("owner", waystone.getOwnerId());
            configData.put("visibility", waystone.getVisibility().name());

            try (FileWriter writer = new FileWriter(waystoneConfig)) {
                String yamlString = yaml.dump(configData);
                yamlString = yamlString.replace("'\"", "\"").replace("\"'", "\"");
                writer.write(yamlString);

                waystoneDataMemory.put(waystoneId, waystone);
            } catch (IOException e) {
                new Console.error("Failed to write config for " + waystone.getUuid() + ".");
                e.printStackTrace();
            }
        } else {
            new Console.error("Failed to create folder for " + waystone.getUuid() + ".");
        }
    }
}
