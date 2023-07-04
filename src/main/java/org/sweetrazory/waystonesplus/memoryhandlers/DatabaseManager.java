package org.sweetrazory.waystonesplus.memoryhandlers;

import org.sweetrazory.waystonesplus.WaystonesPlus;

import java.io.File;
import java.sql.*;

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

            System.out.println("Waystone removed successfully.");
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
                    System.out.println("Failed to create database directory.");
                    return;
                }
            }

            if (!databaseFile.exists()) {
                connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
                createWaystonesTable();
                createExploredWaystonesTable();
                System.out.println("Database created and initialized successfully.");
            } else {
                connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
                System.out.println("Database initialized successfully.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
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
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
