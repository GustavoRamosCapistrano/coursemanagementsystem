/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package coursemanagementsystem;

import java.util.Scanner;

/**
 *
 * @author grc29
 */
public class CourseManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create UserManager instance
        UserManager userManager = new UserManager();

        // Test adding a new user
        boolean addUserResult = userManager.addUser("testUser", "password123", "Office");
        System.out.println("Add User Result: " + addUserResult);

        // Test authenticating a user
        boolean authenticateUserResult = userManager.authenticateUser("testUser", "password123");
        System.out.println("Authenticate User Result: " + authenticateUserResult);

        // Test modifying user password
        boolean changePasswordResult = userManager.changePassword("testUser", "newPassword123");
        System.out.println("Change Password Result: " + changePasswordResult);

        // Test retrieving user role
        String userRole = userManager.getUserRole("testUser");
        System.out.println("User Role: " + userRole);

        // Test deleting a user
        boolean deleteUserResult = userManager.deleteUser("testUser");
        System.out.println("Delete User Result: " + deleteUserResult);

        // Close database connection
        userManager.closeConnection();

        // Create ReportManager instance
        ReportManager reportManager = new ReportManager();

        // Generate reports
        String courseReport = reportManager.generateCourseReport();
        System.out.println(courseReport);

        String studentReport = reportManager.generateStudentReport();
        System.out.println(studentReport);

        String lecturerReport = reportManager.generateLecturerReport("John Doe"); // Example lecturer name
        System.out.println(lecturerReport);

        // Close database connection
        reportManager.closeConnection();
    }
}
