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
private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "java";

    private static final String MENU_OPTIONS = "Menu Options:\n" +
            "1. Generate Course Report\n" +
            "2. Generate Student Report\n" +
            "3. Generate Lecturer Report\n" +
            "4. Exit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        ReportManager reportManager = new ReportManager();
        String loggedInUser = null;

        // Login loop
        while (loggedInUser == null) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (userManager.authenticateUser(username, password)) {
                loggedInUser = username;
                System.out.println("Login successful!\n");
            } else {
                System.out.println("Invalid username or password. Please try again.\n");
            }
        }

        // Main menu loop
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome, " + loggedInUser + "!\n");
            System.out.println(MENU_OPTIONS);
            System.out.print("\nEnter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println(reportManager.generateCourseReport());
                    break;
                case 2:
                    System.out.println(reportManager.generateStudentReport());
                    break;
                case 3:
                    System.out.print("Enter lecturer name: ");
                    String lecturerName = scanner.nextLine();
                    System.out.println(reportManager.generateLecturerReport(lecturerName));
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.\n");
                    break;
            }
        }

        // Close database connection
        userManager.closeConnection();
        reportManager.closeConnection();
        scanner.close();
    }
}