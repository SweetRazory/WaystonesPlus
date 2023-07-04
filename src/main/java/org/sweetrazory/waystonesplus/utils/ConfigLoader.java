package org.sweetrazory.waystonesplus.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.sweetrazory.waystonesplus.WaystonesPlus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {
    private final File configFile;
    private final FileConfiguration config;

    public ConfigLoader(String filePath) {
        File dataFolder = WaystonesPlus.getInstance().getDataFolder();

        // Create directories if they don't exist
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File file = new File(dataFolder, filePath);
        this.configFile = file;

        // Create the file if it doesn't exist
        if (!configFile.exists()) {
            try {
                // Create parent directories if they don't exist
                File parentDir = configFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }

                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }


    public Object get(String path, Object defaultValue) {
        if (!config.contains(path)) {
            config.set(path, defaultValue);
            save();
        }
        return config.get(path, defaultValue);
    }

    public String getString(String path, String defaultValue) {
        if (!config.contains(path)) {
            config.set(path, defaultValue);
            save();
        }
        return config.getString(path, defaultValue);
    }

    public int getInt(String path, int defaultValue) {
        if (!config.contains(path)) {
            config.set(path, defaultValue);
            save();
        }
        return config.getInt(path, defaultValue);
    }

    public double getDouble(String path, Double defaultValue) {
        if (!config.contains(path)) {
            config.set(path, defaultValue);
            save();
        }
        return config.getDouble(path, defaultValue);
    }

    public boolean getBoolean(String path, boolean defaultValue) {
        if (!config.contains(path)) {
            config.set(path, defaultValue);
            save();
        }
        return config.getBoolean(path, defaultValue);
    }

    public List<String> getStringList(String path, List<String> defaultValue) {
        if (!config.contains(path)) {
            config.set(path, defaultValue);
            save();
        }
        return config.getStringList(path);
    }

    public List<Integer> getIntegerList(String path, List<Integer> defaultValue) {
        if (!config.contains(path)) {
            config.set(path, defaultValue);
            save();
        }
        return config.getIntegerList(path);
    }

    public List<Double> getDoubleList(String path, List<Double> defaultValue) {
        if (!config.contains(path)) {
            config.set(path, defaultValue);
            save();
        }
        return config.getDoubleList(path);
    }

    public List<Boolean> getBooleanList(String path, List<Boolean> defaultValue) {
        if (!config.contains(path)) {
            config.set(path, defaultValue);
            save();
        }
        return config.getBooleanList(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return config.getConfigurationSection(path);
    }

    public void set(String path, Object value) {
        if (value instanceof List) {
            List<?> listValue = (List<?>) value;
            List<Object> currentList = (List<Object>) config.getList(path, new ArrayList<>());

            // Add the new elements to the existing list
            for (Object element : listValue) {
                if (!currentList.contains(element)) {
                    currentList.add(element);
                }
            }

            config.set(path, currentList);
        } else {
            config.set(path, value);
        }

        save();
    }

    public boolean containsInList(String path, Object value) {
        if (config.contains(path)) {
            List<?> list = config.getList(path);
            return list != null && list.contains(value);
        }
        return false;
    }

    public void save() {
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
