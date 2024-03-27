/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.sql.SQLException;
import java.util.Scanner;

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
        return dbConnector.addUser(username, password, role);
    }

    public boolean modifyPassword(String username, String newPassword) {
        return dbConnector.modifyPassword(username, newPassword);
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

    public boolean changePasswordOrUsername(String loggedInUser, Scanner scanner) {
        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();
        System.out.println("Enter new username:");
        String newUsername = scanner.nextLine();

        if (dbConnector.modifyPassword(loggedInUser, newPassword) && dbConnector.modifyUsername(loggedInUser, newUsername)) {
            System.out.println("Password and username updated successfully.");
            return true;
        } else {
            System.out.println("Failed to update password or username.");
            return false;
        }
    }

    // Method to modify user
    public void modifyUser(Scanner scanner) {
        System.out.println("Enter username of the user to modify:");
        String username = scanner.nextLine();
        System.out.println("Enter new password for the user:");
        String newPassword = scanner.nextLine();
        System.out.println("Enter new role for the user:");
        String newRole = scanner.nextLine();

        if (dbConnector.modifyPassword(username, newPassword) && dbConnector.modifyUserRole(username, newRole)) {
            System.out.println("User modified successfully.");
        } else {
            System.out.println("Failed to modify user.");
        }
    }
}
