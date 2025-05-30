package com.untilldown.DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.untilldown.Model.Enums.AvatarType; // Make sure
import com.untilldown.Model.User;

public class UserDataManager {

    // DATABASE_URL defines where your database file will be.
    // "jdbc:sqlite:users.db" means a file named 'users.db' will be created/used
    // in your project's root directory (or wherever your application is run from).
    private static final String DATABASE_URL = "jdbc:sqlite:users.db";

    // --- Constructor (and ensuring database/table exist) ---
    public UserDataManager() {
        // When an instance of UserDataManager is created, it will
        // automatically try to create the users table if it doesn't exist.
        createUsersTable();
    }

    /**
     * Establishes a connection to the SQLite database.
     * This is a helper method used by all other database operations.
     * @return A Connection object to the database. Returns null if connection fails.
     */
    private Connection connect() {
        Connection conn = null;
        try {
            // DriverManager.getConnection attempts to establish a connection to the database.
            // If 'users.db' doesn't exist, SQLite will create it.
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging purposes
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
            + "totalPoints INTEGER DEFAULT 0,"
            + "totalKills INTEGER DEFAULT 0,"
            + "maxLifeTime REAL DEFAULT 0.0"
            + ");";

        // try-with-resources ensures that Connection and Statement are closed automatically.
        try (Connection conn = connect(); // Get a connection
             Statement stmt = conn.createStatement()) { // Create a statement object
            stmt.execute(sql); // Execute the SQL to create the table
            System.out.println("Users table created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating users table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds a new user's data to the 'users' table.
     * This is a "Create" operation (C in CRUD).
     * @param user The User object containing the data to add.
     * @return True if the user was successfully added, false if an error occurred (e.g., username already exists).
     */
    public boolean addUser(User user) {
        // The '?' are placeholders for values we will provide later using PreparedStatement.
        // This protects against SQL Injection.
        String sql = "INSERT INTO users(username, password, securityAnswer, isGuest, avatarAssigned, totalPoints, totalKills, maxLifeTime) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the values for the placeholders in the SQL query.
            // The index starts from 1.
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getSecurityAnswer());
            pstmt.setInt(4, user.isGuest() ? 1 : 0); // SQLite stores booleans as 0 or 1
            pstmt.setString(5, user.getAvatarAssigned() != null ? user.getAvatarAssigned().name() : null);
            pstmt.setInt(6, user.getTotalPoints());
            pstmt.setInt(7, user.getTotalKills());
            pstmt.setFloat(8, user.getMaxLifeTime());

            pstmt.executeUpdate(); // Execute the insert query
            System.out.println("User '" + user.getUsername() + "' added successfully.");
            return true;
        } catch (SQLException e) {
            // Check if the error is due to a unique username constraint violation
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
     * @return An ArrayList of User objects, containing all users found in the database.
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, securityAnswer, isGuest, avatarAssigned, totalPoints, totalKills, maxLifeTime FROM users";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) { // ResultSet holds the results of the query

            // Iterate through the rows returned by the query
            while (rs.next()) {
                // Create a new User object for each row
                // Note: If you need to store the 'id' from the database, you'd add an 'id' field to your User class.
                User user = new User(rs.getString("username"), rs.getString("password"));
                user.setSecurityAnswer(rs.getString("securityAnswer"));
                user.setGuest(rs.getInt("isGuest") == 1); // Convert integer back to boolean
                String avatarName = rs.getString("avatarAssigned");
                if (avatarName != null && !avatarName.isEmpty()) { // Check if name is valid
                    try {
                        user.setAvatarAssigned(AvatarType.valueOf(avatarName));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Warning: Invalid AvatarType '" + avatarName + "' for user " + user.getUsername());
                        // Optionally set a default avatar or null
                        user.setAvatarAssigned(null);
                    }
                } else {
                    user.setAvatarAssigned(null); // No avatar assigned
                }
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
     * @param username The username to search for.
     * @return The User object if found, otherwise null.
     */
    public User findUser(String username) {
        String sql = "SELECT id, username, password, securityAnswer, isGuest, avatarAssigned, totalPoints, totalKills, maxLifeTime FROM users WHERE username = ?";
        User user = null;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username); // Set the username for the WHERE clause
            ResultSet rs = pstmt.executeQuery();

            // If a user is found (ResultSet has at least one row)
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

    public boolean removeAllUsers() {
        // DELETE FROM users; will remove all rows
        String sql = "DELETE FROM users";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) { // Using Statement as there are no placeholders

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
     * @param user The User object with updated data. The username property of this object
     * is used to identify which record to update.
     * @return True if the user was updated successfully, false otherwise (e.g., user not found).
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET password = ?, securityAnswer = ?, isGuest = ?, avatarAssigned = ?, totalPoints = ?, totalKills = ?, maxLifeTime = ? WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getSecurityAnswer());
            pstmt.setInt(3, user.isGuest() ? 1 : 0);
            pstmt.setString(4, user.getAvatarAssigned() != null ? user.getAvatarAssigned().name() : null);
            pstmt.setInt(5, user.getTotalPoints());
            pstmt.setInt(6, user.getTotalKills());
            pstmt.setFloat(7, user.getMaxLifeTime());
            pstmt.setString(8, user.getUsername()); // Use username in WHERE clause to target the correct user

            int rowsAffected = pstmt.executeUpdate(); // Returns the number of rows updated
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

    // Inside UserDataManager.java

    /**
     * Updates an existing user's data in the database, including the possibility of changing their username.
     * This is an "Update" operation (U in CRUD).
     * @param oldUsername The current username of the user to be updated.
     * @param updatedUser The User object with the new data, including the new username if it's being changed.
     * @return True if the user was updated successfully, false otherwise (e.g., user not found or new username already exists).
     */
    public boolean updateUser(String oldUsername, User updatedUser) {
        // The SQL query now includes the 'username' field in the SET clause,
        // and uses 'oldUsername' in the WHERE clause to find the record.
        String sql = "UPDATE users SET username = ?, password = ?, securityAnswer = ?, isGuest = ?, avatarAssigned = ?, totalPoints = ?, totalKills = ?, maxLifeTime = ? WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the new values
            pstmt.setString(1, updatedUser.getUsername()); // New username
            pstmt.setString(2, updatedUser.getPassword());
            pstmt.setString(3, updatedUser.getSecurityAnswer());
            pstmt.setInt(4, updatedUser.isGuest() ? 1 : 0);
            pstmt.setString(5, updatedUser.getAvatarAssigned() != null ? updatedUser.getAvatarAssigned().name() : null);
            pstmt.setInt(6, updatedUser.getTotalPoints());
            pstmt.setInt(7, updatedUser.getTotalKills());
            pstmt.setFloat(8, updatedUser.getMaxLifeTime());

            // Use the old username to identify the row to update
            pstmt.setString(9, oldUsername); // Old username in WHERE clause

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
     * @param username The username of the user to remove.
     * @return True if the user was removed successfully, false otherwise (e.g., user not found).
     */
    public boolean removeUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username); // Set the username for the WHERE clause

            int rowsAffected = pstmt.executeUpdate(); // Returns the number of rows deleted
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
