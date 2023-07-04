package org.sweetrazory.waystonesplus.memoryhandlers;

import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class LocalizationManager {
    public static void loadConfig() {
        File configFile = new File(WaystonesPlus.getInstance().getDataFolder().getAbsolutePath() + File.separator + "localization.yml");
        try {
            FileInputStream fis = new FileInputStream(configFile);
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(fis);

            
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
