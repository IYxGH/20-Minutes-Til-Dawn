package com.untilldown.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.untilldown.Model.Enums.AvatarType;
import com.untilldown.Model.User;

public class UserDataManager {

    private static final String DATABASE_URL = "jdbc:sqlite:users.db";

    public UserDataManager() {
        createUsersTable();
    }

    /**
     * Establishes a connection to the SQLite database.
     * This is a helper method used by all other database operations.
     *
     * @return A Connection object to the database. Returns null if connection fails.
     */
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Creates the 'users' table in the database if it does not already exist.
     * This SQL statement mirrors the table design we discussed in Step 3.
     */
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "username TEXT NOT NULL UNIQUE,"
            + "password TEXT NOT NULL," // IMPORTANT: In a real app, store HASHED passwords!
            + "securityAnswer TEXT,"
            + "isGuest INTEGER DEFAULT 0," // 0 for false, 1 for true
            + "avatarAssigned TEXT," // Store AvatarType enum name as text
            + "customAvatarImagePath TEXT," // New field for custom avatar image path
            + "totalPoints INTEGER DEFAULT 0,"
            + "totalKills INTEGER DEFAULT 0,"
            + "maxLifeTime REAL DEFAULT 0.0"
            + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Users table created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating users table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds a new user's data to the 'users' table.
     * This is a "Create" operation (C in CRUD).
     *
     * @param user The User object containing the data to add.
     * @return True if the user was successfully added, false if an error occurred (e.g., username already exists).
     */
    public boolean addUser(User user) {
        String sql = "INSERT INTO users(username, password, securityAnswer, isGuest, avatarAssigned, customAvatarImagePath, totalPoints, totalKills, maxLifeTime) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getSecurityAnswer());
            pstmt.setInt(4, user.isGuest() ? 1 : 0);
            pstmt.setString(5, user.getAvatarAssigned() != null ? user.getAvatarAssigned().name() : null);
            pstmt.setString(6, user.getCustomAvatarImagePath()); // Set custom avatar image path
            pstmt.setInt(7, user.getTotalPoints());
            pstmt.setInt(8, user.getTotalKills());
            pstmt.setFloat(9, user.getMaxLifeTime());

            pstmt.executeUpdate();
            System.out.println("User '" + user.getUsername() + "' added successfully.");
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed: users.username")) {
                System.err.println("Error: Username '" + user.getUsername() + "' already exists.");
            } else {
                System.err.println("Error adding user: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all users from the 'users' table.
     * This is a "Read" operation (R in CRUD).
     *
     * @return An ArrayList of User objects, containing all users found in the database.
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, securityAnswer, isGuest, avatarAssigned, customAvatarImagePath, totalPoints, totalKills, maxLifeTime FROM users";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("password"));
                user.setSecurityAnswer(rs.getString("securityAnswer"));
                user.setGuest(rs.getInt("isGuest") == 1);
                String avatarName = rs.getString("avatarAssigned");
                if (avatarName != null && !avatarName.isEmpty()) {
                    try {
                        user.setAvatarAssigned(AvatarType.valueOf(avatarName));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Warning: Invalid AvatarType '" + avatarName + "' for user " + user.getUsername());
                        user.setAvatarAssigned(null);
                    }
                } else {
                    user.setAvatarAssigned(null);
                }
                user.setCustomAvatarImagePath(rs.getString("customAvatarImagePath")); // Get custom avatar image path
                user.setTotalPoints(rs.getInt("totalPoints"));
                user.setTotalKills(rs.getInt("totalKills"));
                user.setMaxLifeTime(rs.getFloat("maxLifeTime"));

                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Finds a single user in the database by their username.
     *
     * @param username The username to search for.
     * @return The User object if found, otherwise null.
     */
    public User findUser(String username) {
        String sql = "SELECT id, username, password, securityAnswer, isGuest, avatarAssigned, customAvatarImagePath, totalPoints, totalKills, maxLifeTime FROM users WHERE username = ?";
        User user = null;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"));
                user.setSecurityAnswer(rs.getString("securityAnswer"));
                user.setGuest(rs.getInt("isGuest") == 1);
                String avatarName = rs.getString("avatarAssigned");
                if (avatarName != null && !avatarName.isEmpty()) {
                    try {
                        user.setAvatarAssigned(AvatarType.valueOf(avatarName));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Warning: Invalid AvatarType '" + avatarName + "' for user " + user.getUsername());
                        user.setAvatarAssigned(null);
                    }
                } else {
                    user.setAvatarAssigned(null);
                }
                user.setCustomAvatarImagePath(rs.getString("customAvatarImagePath")); // Get custom avatar image path
                user.setTotalPoints(rs.getInt("totalPoints"));
                user.setTotalKills(rs.getInt("totalKills"));
                user.setMaxLifeTime(rs.getFloat("maxLifeTime"));
            }
        } catch (SQLException e) {
            System.err.println("Error finding user: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Removes all users from the database.
     *
     * @return True if all users were successfully removed, false otherwise.
     */
    public boolean removeAllUsers() {
        String sql = "DELETE FROM users";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println(rowsAffected + " users removed from the database.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error removing all users: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing user's data in the database.
     * This is an "Update" operation (U in CRUD).
     *
     * @param user The User object with updated data. The username property of this object
     * is used to identify which record to update.
     * @return True if the user was updated successfully, false otherwise (e.g., user not found).
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET password = ?, securityAnswer = ?, isGuest = ?, avatarAssigned = ?, customAvatarImagePath = ?, totalPoints = ?, totalKills = ?, maxLifeTime = ? WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getSecurityAnswer());
            pstmt.setInt(3, user.isGuest() ? 1 : 0);
            pstmt.setString(4, user.getAvatarAssigned() != null ? user.getAvatarAssigned().name() : null);
            pstmt.setString(5, user.getCustomAvatarImagePath()); // Update custom avatar image path
            pstmt.setInt(6, user.getTotalPoints());
            pstmt.setInt(7, user.getTotalKills());
            pstmt.setFloat(8, user.getMaxLifeTime());
            pstmt.setString(9, user.getUsername());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User '" + user.getUsername() + "' updated successfully.");
                return true;
            } else {
                System.out.println("User '" + user.getUsername() + "' not found for update.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing user's data in the database, including the possibility of changing their username.
     * This is an "Update" operation (U in CRUD).
     *
     * @param oldUsername The current username of the user to be updated.
     * @param updatedUser The User object with the new data, including the new username if it's being changed.
     * @return True if the user was updated successfully, false otherwise (e.g., user not found or new username already exists).
     */
    public boolean updateUser(String oldUsername, User updatedUser) {
        String sql = "UPDATE users SET username = ?, password = ?, securityAnswer = ?, isGuest = ?, avatarAssigned = ?, customAvatarImagePath = ?, totalPoints = ?, totalKills = ?, maxLifeTime = ? WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedUser.getUsername());
            pstmt.setString(2, updatedUser.getPassword());
            pstmt.setString(3, updatedUser.getSecurityAnswer());
            pstmt.setInt(4, updatedUser.isGuest() ? 1 : 0);
            pstmt.setString(5, updatedUser.getAvatarAssigned() != null ? updatedUser.getAvatarAssigned().name() : null);
            pstmt.setString(6, updatedUser.getCustomAvatarImagePath()); // Update custom avatar image path
            pstmt.setInt(7, updatedUser.getTotalPoints());
            pstmt.setInt(8, updatedUser.getTotalKills());
            pstmt.setFloat(9, updatedUser.getMaxLifeTime());
            pstmt.setString(10, oldUsername);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User '" + oldUsername + "' updated to '" + updatedUser.getUsername() + "' successfully.");
                return true;
            } else {
                System.out.println("User '" + oldUsername + "' not found for update.");
                return false;
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed: users.username")) {
                System.err.println("Error: New username '" + updatedUser.getUsername() + "' already exists.");
            } else {
                System.err.println("Error updating user: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes a user from the database by their username.
     * This is a "Delete" operation (D in CRUD).
     *
     * @param username The username of the user to remove.
     * @return True if the user was removed successfully, false otherwise (e.g., user not found).
     */
    public boolean removeUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User '" + username + "' removed successfully.");
                return true;
            } else {
                System.out.println("User '" + username + "' not found for removal.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error removing user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
