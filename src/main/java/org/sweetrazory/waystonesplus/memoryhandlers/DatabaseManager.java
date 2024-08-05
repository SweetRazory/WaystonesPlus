package org.sweetrazory.waystonesplus.memoryhandlers;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.sweetrazory.waystonesplus.WaystonesPlus;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.utils.DB;
import org.sweetrazory.waystonesplus.waystone.Waystone;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    public static Connection connection;

    public static ResultSet execute(String query, Object... parameters) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            setParameters(statement, parameters);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    private static void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }

    public static int executeUpdate(String query, Object... parameters) {
        int rowsAffected = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            setParameters(statement, parameters);
            rowsAffected = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static void removeWaystone(String waystoneId) {
        try {
            String deleteWaystoneQuery = "DELETE FROM waystones WHERE id = ?";
            String deleteExploredWaystonesQuery = "DELETE FROM explored_waystones WHERE waystoneId = ?";

            PreparedStatement deleteWaystoneStatement = connection.prepareStatement(deleteWaystoneQuery);
            deleteWaystoneStatement.setString(1, waystoneId);
            deleteWaystoneStatement.executeUpdate();
            deleteWaystoneStatement.close();

            PreparedStatement deleteExploredWaystonesStatement = connection.prepareStatement(deleteExploredWaystonesQuery);
            deleteExploredWaystonesStatement.setString(1, waystoneId);
            deleteExploredWaystonesStatement.executeUpdate();
            deleteExploredWaystonesStatement.close();

            WaystonesPlus.Logger().info("Waystone removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");

            String databasePath = WaystonesPlus.getInstance().getDataFolder() + "/database/database.db";

            File databaseFile = new File(databasePath);
            File parentDirectory = databaseFile.getParentFile();

            if (parentDirectory != null && !parentDirectory.exists()) {
                if (!parentDirectory.mkdirs()) {
                    WaystonesPlus.Logger().info("Failed to create database directory.");
                    return;
                }
            }

            if (!databaseFile.exists()) {
                connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
                createWaystonesTable();
                createExploredWaystonesTable();
                WaystonesPlus.Logger().info("Database created and initialized successfully.");
            } else {
                connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
                WaystonesPlus.Logger().info("Database initialized successfully.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void migrateWaystones() {
        File pluginFolder = new File(WaystonesPlus.getInstance().getDataFolder().getAbsolutePath());

        File waystonesFolder = new File(pluginFolder, "waystones");
        if (!waystonesFolder.exists() || !waystonesFolder.isDirectory()) {
            WaystonesPlus.Logger().info("No Waystones to be migrate.");
            return;
        }

        List<Waystone> waystones = new ArrayList<>();

        findConfigFiles(waystonesFolder, waystones);

        for (Waystone waystone : waystones) {
            WaystonesPlus.Logger().info("Parsed Waystone: " + waystone.getId());
            DB.insertWaystone(waystone);
        }

        if (!waystones.isEmpty()) {
            try {
                FileUtils.deleteDirectory(waystonesFolder);
                WaystonesPlus.Logger().info("Deleted 'waystones' folder.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void findConfigFiles(File directory, List<Waystone> waystones) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals("config.yml")) {
                    WaystonesPlus.Logger().info("Found 'config.yml' file");
                    Waystone waystone = parseConfigYaml(file);
                    if (waystone != null) {
                        waystones.add(waystone);
                    }
                } else if (file.isDirectory()) {
                    findConfigFiles(file, waystones);
                }
            }
        }
    }

    private Waystone parseConfigYaml(File configFile) {
        try (FileInputStream fis = new FileInputStream(configFile);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(isr);

            String owner = (String) config.get("owner");
            String visibility = (String) config.get("visibility");
            String name = (String) config.getOrDefault("name", LangManager.newWaystoneName);
            name = name.replaceAll("\uFFFD", "&");

            Map<String, Double> locationMap = (Map<String, Double>) config.get("location");
            double x = locationMap.get("x");
            double y = locationMap.get("y");
            double z = locationMap.get("z");

            String id = (String) config.get("id");
            String type = (String) config.get("type");
            String world = (String) config.getOrDefault("location.world", Bukkit.getServer().getWorlds().get(0).getName());

            List<String> entityIdStrings = (List<String>) config.get("entityIds");
            List<Integer> entityIds = new ArrayList<>();
            for (String entityIdString : entityIdStrings) {
                int entityId = Integer.parseInt(entityIdString);
                entityIds.add(entityId);
            }
            // Create and return the Waystone object
            Waystone waystone = new Waystone(id, name, new Location(Bukkit.getWorld(world), x, y, z), type, owner, Particle.ENCHANT, Visibility.fromString(visibility), entityIds, Material.LODESTONE);
            return waystone;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void createWaystonesTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS waystones (" +
                "id TEXT PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "location TEXT NOT NULL, " +
                "entityIds TEXT NOT NULL, " +
                "type TEXT NOT NULL, " +
                "owner TEXT NOT NULL, " +
                "icon TEXT NOT NULL, " +
                "visibility TEXT NOT NULL, " +
                "particle TEXT " +
                ")";

        try {
            Statement statement = connection.createStatement();
            statement.execute(createTableQuery);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createExploredWaystonesTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS explored_waystones (playerId TEXT NOT NULL, playerName TEXT NOT NULL, waystoneId TEXT NOT NULL);";
        try {
            Statement statement = connection.createStatement();
            statement.execute(createTableQuery);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                WaystonesPlus.Logger().info("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
