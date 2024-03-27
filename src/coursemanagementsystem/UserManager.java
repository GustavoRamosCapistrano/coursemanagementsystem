/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

/**
 *
 * @author grc29
 */
public class UserManager {

    private DBConnector dbConnector;

    public UserManager() {
        dbConnector = new DBConnector();
    }

    // Method to add a new user to the system
    public boolean addUser(String username, String password, String role) {
        return dbConnector.addUser(username, password, role);
    }

    // Method to modify an existing user's password
    public boolean changePassword(String username, String newPassword) {
        return dbConnector.changePassword(username, newPassword);
    }

    // Method to delete a user from the system
    public boolean deleteUser(String username) {
        return dbConnector.deleteUser(username);
    }

    // Method to authenticate a user based on username and password
    public boolean authenticateUser(String username, String password) {
        return dbConnector.authenticateUser(username, password);
    }

    // Method to get the role of a user based on username
    public String getUserRole(String username) {
        return dbConnector.getUserRole(username);
    }

    // Method to close database connection
    public void closeConnection() {
        dbConnector.closeConnection();
    }
}
