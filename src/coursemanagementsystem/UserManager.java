/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.sql.SQLException;

/**
 *
 * @author grc29
 */
public class UserManager {

    private final DBConnector dbConnector;

    public UserManager(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        return dbConnector.authenticateUser(username, password);
    }

    public boolean addUser(String username, String password, String role) throws SQLException {
        // Check if the username already exists
        if (dbConnector.usernameExists(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return false;
        }
        // Proceed with adding the user to the database
        return dbConnector.addUser(username, password, role);
    }

    public boolean modifyPassword(String newPassword) {
        return dbConnector.modifyPassword(newPassword);
    }

    public boolean modifyUserRole(String username, String newRole) {
        return dbConnector.modifyUserRole(username, newRole);
    }

    public boolean deleteUser(String username) {
        return dbConnector.deleteUser(username);
    }

    public String getUserRole(String username) {
        return dbConnector.getUserRole(username);
    }

    public boolean modifyUser(String modifyUsername, String newUsername, String newPassword, String newRole) throws SQLException {
        return dbConnector.modifyUser(modifyUsername, newUsername, newPassword, newRole);
    }

    public boolean usernameExists(String username) {
        // Call a method from the DBConnector to check if the username exists
        return dbConnector.usernameExists(username);
    }

}
