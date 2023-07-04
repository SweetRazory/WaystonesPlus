package org.sweetrazory.waystonesplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.sweetrazory.waystonesplus.enums.Visibility;
import org.sweetrazory.waystonesplus.memoryhandlers.DatabaseManager;
import org.sweetrazory.waystonesplus.memoryhandlers.WaystoneMemory;
import org.sweetrazory.waystonesplus.waystone.Waystone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DB {
    public static List<WaystoneParticleInfo> getAllWaystoneInfo() {
        List<WaystoneParticleInfo> waystoneInfoList = new ArrayList<>();
        String query = "SELECT id, particle, location FROM waystones";
        ResultSet resultSet = DatabaseManager.execute(query);

        try {
            while (resultSet.next()) {
                String uuid = resultSet.getString("id");
                String particleString = resultSet.getString("particle");
                String locationString = resultSet.getString("location");

                Particle particle = particleString.equals("off") ? null : Particle.valueOf(particleString);
                Location location = parseLocationString(locationString);

                WaystoneParticleInfo waystoneInfo = new WaystoneParticleInfo(uuid, particle, location);
                waystoneInfoList.add(waystoneInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return waystoneInfoList;
    }

    private static Location parseLocationString(String locationString) {
        // Parse JSON manually
        String[] locationParts = locationString.split(",");
        String worldName = locationParts[0].split("=")[1];
        double x = Double.parseDouble(locationParts[1].split("=")[1]);
        double y = Double.parseDouble(locationParts[2].split("=")[1]);
        double z = Double.parseDouble(locationParts[3].split("=")[1]);

        World world = Bukkit.getWorld(worldName);

        if (world != null) {
            return new Location(world, x, y, z);
        } else {
            // Handle if the world does not exist
            return null;
        }
    }


    public static List<Waystone> getPlayerWaystones(String playerId) {
        List<Waystone> waystones = new ArrayList<>();
        String query = "SELECT * FROM waystones WHERE owner = '" + playerId + "'";
        ResultSet resultSet = DatabaseManager.execute(query);

        try {
            while (resultSet.next()) {
                String uuid = resultSet.getString("id");
                String name = resultSet.getString("name");

                String locationObject = resultSet.getString("location");
                // Parse JSON manually
                String[] locationParts = locationObject.split(",");
                String worldName = locationParts[0].split("=")[1];
                double x = Double.parseDouble(locationParts[1].split("=")[1]);
                double y = Double.parseDouble(locationParts[2].split("=")[1]);
                double z = Double.parseDouble(locationParts[3].split("=")[1]);

                Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
                String entityIds = resultSet.getString("entityIds");
                String type = resultSet.getString("type");
                String owner = resultSet.getString("owner");
                String particle = resultSet.getString("particle");
                String visibility = resultSet.getString("visibility");

                String[] entityIdStrings = entityIds.split(",");
                Integer[] entityIdsArray = new Integer[entityIdStrings.length];
                for (int i = 0; i < entityIdStrings.length; i++) {
                    entityIdsArray[i] = Integer.parseInt(entityIdStrings[i]);
                }

                Waystone waystone = new Waystone(uuid, name, location, type, owner, particle.equals("off") ? null : Particle.valueOf(particle), Visibility.fromString(visibility), Arrays.asList(entityIdsArray));
                waystones.add(waystone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return waystones;
    }

    public static List<Waystone> getGlobalAndPublicWaystones() {
        List<Waystone> waystones = new ArrayList<>();
        String query = "SELECT * FROM waystones WHERE visibility IN ('GLOBAL', 'PUBLIC')";
        ResultSet resultSet = DatabaseManager.execute(query);

        try {
            while (resultSet.next()) {
                String uuid = resultSet.getString("id");
                String name = resultSet.getString("name");

                String locationObject = resultSet.getString("location");
                // Parse JSON manually
                String[] locationParts = locationObject.split(",");
                String worldName = locationParts[0].split("=")[1];
                double x = Double.parseDouble(locationParts[1].split("=")[1]);
                double y = Double.parseDouble(locationParts[2].split("=")[1]);
                double z = Double.parseDouble(locationParts[3].split("=")[1]);

                Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
                String entityIds = resultSet.getString("entityIds");
                String type = resultSet.getString("type");
                String owner = resultSet.getString("owner");
                String particle = resultSet.getString("particle");
                String visibility = resultSet.getString("visibility");

                String[] entityIdStrings = entityIds.split(",");
                Integer[] entityIdsArray = new Integer[entityIdStrings.length];
                for (int i = 0; i < entityIdStrings.length; i++) {
                    entityIdsArray[i] = Integer.parseInt(entityIdStrings[i]);
                }

                Waystone waystone = new Waystone(uuid, name, location, type, owner, particle.equals("off") ? null : Particle.valueOf(particle), Visibility.fromString(visibility), Arrays.asList(entityIdsArray));
                waystones.add(waystone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return waystones;
    }

    public static List<Waystone> getAllWaystones() {
        List<Waystone> waystones = new ArrayList<>();
        String query = "SELECT * FROM waystones";
        ResultSet resultSet = DatabaseManager.execute(query);

        try {
            while (resultSet.next()) {
                String uuid = resultSet.getString("id");
                String name = resultSet.getString("name");

                String locationObject = resultSet.getString("location");
                // Parse JSON manually
                String[] locationParts = locationObject.split(",");
                String worldName = locationParts[0].split("=")[1];
                double x = Double.parseDouble(locationParts[1].split("=")[1]);
                double y = Double.parseDouble(locationParts[2].split("=")[1]);
                double z = Double.parseDouble(locationParts[3].split("=")[1]);

                Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
                String entityIds = resultSet.getString("entityIds");
                String type = resultSet.getString("type");
                String owner = resultSet.getString("owner");
                String particle = resultSet.getString("particle");
                String visibility = resultSet.getString("visibility");

                String[] entityIdStrings = entityIds.split(",");
                Integer[] entityIdsArray = new Integer[entityIdStrings.length];
                for (int i = 0; i < entityIdStrings.length; i++) {
                    entityIdsArray[i] = Integer.parseInt(entityIdStrings[i]);
                }
                Waystone waystone = new Waystone(uuid, name, location, type, owner, particle.equals("off") ? null : Particle.valueOf(particle), Visibility.fromString(visibility), Arrays.asList(entityIdsArray));
                waystones.add(waystone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return waystones;
    }

    public static void insertOrUpdateExploredWaystone(String name, String playerId, String waystoneId) {
        insertExploredWaystone(name, playerId, waystoneId);
    }

    public static List<String> getExploredAndPrivateWaystoneIds(String playerId, int pageNumber, int pageSize) {
        List<String> waystoneIds = new ArrayList<>();
        String query = "SELECT waystoneId FROM explored_waystones WHERE playerId = ? UNION " +
                "SELECT waystoneId FROM private_waystones WHERE playerId = ? LIMIT ? OFFSET ?";
        int offset = (pageNumber - 1) * pageSize;

        ResultSet resultSet = DatabaseManager.execute(query, playerId, playerId, pageSize, offset);

        try {
            while (resultSet.next()) {
                String waystoneId = resultSet.getString("waystoneId");
                waystoneIds.add(waystoneId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return waystoneIds;
    }

    public static List<Waystone> getWaystones(String playerId, Integer pageNumber, Integer pageSize, String waystoneId) {
        List<Waystone> waystones = new ArrayList<>();
        String query = "SELECT w.id, w.name, w.location, w.entityIds, w.type, w.owner, w.visibility, w.particle " +
                "FROM waystones w " +
                "LEFT JOIN explored_waystones we ON w.id = we.waystoneId " +
                "WHERE w.owner = \"" + playerId + "\" OR (we.playerId = \"" + playerId + "\" AND w.visibility = \"PUBLIC\") OR w.visibility = \"GLOBAL\"";

        if (waystoneId != null) {
            query += " AND w.id <> '" + waystoneId + "'";
        }

        if (pageNumber != null && pageSize != null) {
            int offset = (pageNumber) * pageSize;
            query += " LIMIT " + pageSize + " OFFSET " + offset;
        }


        ResultSet resultSet = DatabaseManager.execute(query);

        try {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String locationObject = resultSet.getString("location");
                String[] locationParts = locationObject.split(",");

                if (locationParts.length != 4) {
                    // Invalid location format, skip this waystone
                    continue;
                }

                String worldName = locationParts[0].split("=")[1];
                double x = Double.parseDouble(locationParts[1].split("=")[1]);
                double y = Double.parseDouble(locationParts[2].split("=")[1]);
                double z = Double.parseDouble(locationParts[3].split("=")[1]);
                Location location = new Location(Bukkit.getWorld(worldName), x, y, z);

                String entityIds = resultSet.getString("entityIds");
                String type = resultSet.getString("type");
                String owner = resultSet.getString("owner");
                String particle = resultSet.getString("particle");
                String visibility = resultSet.getString("visibility");
                String[] entityIdStrings = entityIds.split(",");
                Integer[] entityIdsArray = new Integer[entityIdStrings.length];
                for (int i = 0; i < entityIdStrings.length; i++) {
                    entityIdsArray[i] = Integer.parseInt(entityIdStrings[i]);
                }

                Particle particleEnum = particle.equals("off") ? null : Particle.valueOf(particle.toUpperCase());
                Visibility visibilityEnum = Visibility.fromString(visibility);

                Waystone waystone = new Waystone(id, name, location, type, owner, particleEnum, visibilityEnum, Arrays.asList(entityIdsArray));
                waystones.add(waystone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the ResultSet after processing
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return waystones;
    }

    public static List<String> getExploredWaystoneIds(String playerId, Integer pageNumber, Integer pageSize) {
        List<String> waystoneIds = new ArrayList<>();
        String query = "SELECT waystoneId FROM explored_waystones WHERE playerId = ?";

        if (pageNumber != null && pageSize != null) {
            int offset = (pageNumber - 1) * pageSize;
            query += " LIMIT " + pageSize + " OFFSET " + offset;
        }

        ResultSet resultSet = DatabaseManager.execute(query, playerId);

        try {
            while (resultSet.next()) {
                String waystoneId = resultSet.getString("waystoneId");
                waystoneIds.add(waystoneId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return waystoneIds;
    }


    private static void insertExploredWaystone(String name, String playerId, String waystoneId) {
        String query = "INSERT INTO explored_waystones (playerName, playerId, waystoneId) VALUES (?, ?, ?)";
        DatabaseManager.executeUpdate(query, name, playerId, waystoneId);
    }

//    private static void updateExploredWaystones(String playerId, String waystoneId) {
//        // Delete the existing records for the player
////        deleteExploredWaystones(playerId);
//        insertExploredWaystone(playerId, waystoneId);
//    }

//    private static void deleteExploredWaystones(String playerId) {
//        String query = "DELETE FROM explored_waystones WHERE playerId = ?";
//        DatabaseManager.executeUpdate(query, playerId);
//    }

    public static List<Map<String, String>> getExplorers(String waystoneId) {
        String query = "SELECT playerId, playerName from explored_waystones we where we.waystoneId = \"" + waystoneId + "\";";
        ResultSet resultSet = DatabaseManager.execute(query);
        List<Map<String, String>> ids = new ArrayList<>();
        try {
            if (resultSet.next()) {
                ids.add(new HashMap<String, String>() {
                    {
                        put("playerId", resultSet.getString("playerId"));
                        put("playerName", resultSet.getString("playerName"));
                    }
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ids;
    }

    public static Waystone getWaystone(String uuid) {
        String query = "SELECT * FROM waystones WHERE id = '" + uuid + "'";
        ResultSet resultSet = DatabaseManager.execute(query);

        try {
            if (resultSet.next()) {
                String name = resultSet.getString("name");

                String locationObject = resultSet.getString("location");
                // Parse JSON manually
                String[] locationParts = locationObject.split(",");
                String worldName = locationParts[0].split("=")[1];
                double x = Double.parseDouble(locationParts[1].split("=")[1]);
                double y = Double.parseDouble(locationParts[2].split("=")[1]);
                double z = Double.parseDouble(locationParts[3].split("=")[1]);

                Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
                String entityIds = resultSet.getString("entityIds");
                String type = resultSet.getString("type");
                String owner = resultSet.getString("owner");
                String particle = resultSet.getString("particle");
                String visibility = resultSet.getString("visibility");

                String[] entityIdStrings = entityIds.split(",");
                Integer[] entityIdsArray = new Integer[entityIdStrings.length];
                for (int i = 0; i < entityIdStrings.length; i++) {
                    entityIdsArray[i] = Integer.parseInt(entityIdStrings[i]);
                }

                Waystone waystone = new Waystone(uuid, name, location, type, owner, particle.equals("off") ? null : Particle.valueOf(particle), Visibility.fromString(visibility), Arrays.asList(entityIdsArray));
                return waystone;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void insertWaystone(Waystone waystone) {
        String query = "INSERT INTO waystones (id, name, location, entityIds, type, owner, visibility, particle) VALUES (" +
                "'" + waystone.getId() + "', " +
                "'" + waystone.getName() + "', " +
                "'" + getLocationString(waystone.getLocation()) + "', " +
                "'" + getEntityIdsString(waystone.getEntities()) + "', " +
                "'" + waystone.getType() + "', " +
                "'" + waystone.getOwnerId() + "', " +
                "'" + waystone.getVisibility().toString() + "', " +
                "'" + (waystone.getParticle() != null ? waystone.getParticle().toString() : "off") + "')";

        DatabaseManager.executeUpdate(query);
    }

    private static String getLocationString(Location location) {
        return "world=" + location.getWorld().getName() +
                ",x=" + location.getX() +
                ",y=" + location.getY() +
                ",z=" + location.getZ();
    }

    private static String getEntityIdsString(List<Integer> entityIds) {
        return entityIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }


    public static void updateWaystone(Waystone waystone) {
        String query = "UPDATE waystones SET " +
                "name = '" + waystone.getName() + "', " +
                "type = '" + waystone.getType() + "', " +
                "visibility = '" + waystone.getVisibility().toString() + "', " +
                "particle = '" + (waystone.getParticle() != null ? waystone.getParticle().toString() : "off") + "' " +
                "WHERE id = '" + waystone.getId() + "'";
        DatabaseManager.executeUpdate(query);
    }

    public static void deleteWaystone(Waystone waystone) {
        waystone.setParticle(null);
        WaystoneMemory.changeParticles(waystone);
        DatabaseManager.removeWaystone(waystone.getId());
    }

    public static int getWaystonesSize(String playerId, String waystoneId) throws SQLException {
        String query = "SELECT count(*) as count " +
                "FROM waystones w " +
                "LEFT JOIN explored_waystones we ON w.id = we.waystoneId " +
                "WHERE w.owner = \"" + playerId + "\" OR (we.playerId = \"" + playerId + "\" AND w.visibility = \"PUBLIC\") OR w.visibility = \"GLOBAL\"";

        if (waystoneId != null) {
            query += " AND w.id <> '" + waystoneId + "'";
        }

        ResultSet resultSet = DatabaseManager.execute(query);

        return resultSet.getInt("count");
    }
}
