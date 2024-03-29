/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.sql.SQLException;

/*
 * This class manages user-related operations in the course management system.
 * It interacts with the database to authenticate users, add, modify, and delete users,
 * and retrieve user roles.
 */

public class UserManager {

    // Attribute
    private final DBConnector dbConnector;

    // Constructor
    public UserManager(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // Method to authenticate a user
    public boolean authenticateUser(String username, String password) throws SQLException {
        return dbConnector.authenticateUser(username, password);
    }

    // Method to add a new user
    public boolean addUser(String username, String password, String role) throws SQLException {
        // Check if the username already exists
        if (dbConnector.usernameExists(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return false;
        }
        // Proceed with adding the user to the database
        return dbConnector.addUser(username, password, role);
    }

    // Method to modify user password
    public boolean modifyPassword(String newPassword) {
        return dbConnector.modifyPassword(newPassword);
    }

    // Method to modify user role
    public boolean modifyUserRole(String username, String newRole) {
        return dbConnector.modifyUserRole(username, newRole);
    }

    // Method to delete a user
    public boolean deleteUser(String username) {
        return dbConnector.deleteUser(username);
    }

    // Method to get user role
    public String getUserRole(String username) {
        return dbConnector.getUserRole(username);
    }

    // Method to modify user details
    public boolean modifyUser(String modifyUsername, String newUsername, String newPassword, String newRole) throws SQLException {
        return dbConnector.modifyUser(modifyUsername, newUsername, newPassword, newRole);
    }

    // Method to check if username exists
    public boolean usernameExists(String username) {
        // Call a method from the DBConnector to check if the username exists
        return dbConnector.usernameExists(username);
    }
}