/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    // Method to authenticate a user
    public boolean authenticateUser(String username, String password) {
        return dbConnector.authenticateUser(username, password);
    }

    // Method to add a new user
    public boolean addUser(String username, String password, String role) {
        return dbConnector.addUser(username, password, role);
    }

    // Method to change password for an existing user
    public boolean changePassword(String username, String newPassword) {
        return dbConnector.changePassword(username, newPassword);
    }

    // Method to delete an existing user
    public boolean deleteUser(String username) {
        return dbConnector.deleteUser(username);
    }

    // Method to get the role of a user based on username
    public String getUserRole(String username) {
        return dbConnector.getUserRole(username);
    }
}