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
public class Menu {

    private final DBConnector dbConnector;
    private final ReportManager reportManager; // Include ReportManager

    public Menu(DBConnector dbConnector, ReportManager reportManager) {
        this.dbConnector = dbConnector;
        this.reportManager = reportManager; // Initialize ReportManager
    }

    private static final String MENU_OPTIONS_ADMIN = "Menu Options:\n"
            + "1. Add User\n"
            + "2. Modify User\n"
            + "3. Delete User\n"
            + "4. Change Own Username\n"
            + "5. Change Own Password\n"
            + "6. Exit";

    private static final String MENU_OPTIONS_OFFICE = "Menu Options:\n"
            + "1. Generate Course Report\n"
            + "2. Generate Student Report\n"
            + "3. Generate Lecturer Report\n"
            + "4. Change Own Username/Password\n"
            + "5. Exit";

    private static final String MENU_OPTIONS_LECTURER = "Menu Options:\n"
            + "1. Generate Lecturer Report\n"
            + "2. Change Own Username\n"
            + "3. Change Own Password\n"
            + "4. Exit";

    public static void displayMenu(String userRole) {
        switch (userRole) {
            case "Admin":
                System.out.println(MENU_OPTIONS_ADMIN);
                break;
            case "Office":
                System.out.println(MENU_OPTIONS_OFFICE);
                break;
            case "Lecturer":
                System.out.println(MENU_OPTIONS_LECTURER);
                break;
            default:
                System.out.println("Invalid user role. Please contact the administrator.\n");
                break;
        }
    }

    public static String login(Scanner scanner, UserManager userManager) throws SQLException {
        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (userManager.authenticateUser(username, password)) {
                System.out.println("Login successful!\n");
                return username; // Return the username if login is successful
            } else {
                System.out.println("Invalid username or password. Please try again.\n");
            }
        }
    }

    public static int getUserChoice(Scanner scanner, String userRole) {
        while (true) {
            System.out.print("\nEnter your choice: ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (isValidChoice(choice, userRole)) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n");
            }
        }
    }

    private static boolean isValidChoice(int choice, String userRole) {
        switch (userRole) {
            case "Admin":
                return choice >= 1 && choice <= 6; // Changed to include option 6
            case "Office":
                return choice >= 1 && choice <= 5;
            case "Lecturer":
                return choice >= 1 && choice <= 3;
            case "Export": // Include a case for "Export" role
                return choice >= 1 && choice <= 3; // Assuming there are 3 export options
            default:
                return false;
        }
    }

    public void startCourseManagementSystem(Scanner scanner, UserManager userManager, DBConnector dbConnector) throws SQLException {
        String loggedInUser = login(scanner, userManager);

        // Main menu loop
        while (true) {
            System.out.println("Welcome, " + loggedInUser + "!\n");

            // Determine user role
            String userRole = userManager.getUserRole(loggedInUser);

            // Display menu based on user role
            displayMenu(userRole);

            int choice = getUserChoice(scanner, userRole);

            try {
                if (userRole.equals("Admin")) {
                    // Call the method to process admin choice
                    processAdminChoice(choice, userManager, loggedInUser, scanner);
                } else if (userRole.equals("Lecturer")) {
                    // Call the method to process lecturer choice
                    processLecturerChoice(choice, userManager, loggedInUser, scanner);
                } else if (userRole.equals("Office")) {
                    // Call the method to process office choice
                    processOfficeChoice(choice, userManager, loggedInUser, scanner);
                } else {
                    System.out.println("Invalid user role. Please contact the administrator.");
                }
            } catch (SQLException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    public void processAdminChoice(int choice, UserManager userManager, String loggedInUser, Scanner scanner) throws SQLException {
        boolean success;

        switch (choice) {
            case 1:
                // Add new user
                System.out.println("Enter username:");
                String username = scanner.nextLine();
                System.out.println("Enter password:");
                String password = scanner.nextLine();
                System.out.println("Enter role:");
                String role = scanner.nextLine();
                try {
                    success = userManager.addUser(username, password, role);
                    if (success) {
                        System.out.println("User added successfully.");
                    } else {
                        System.out.println("Failed to add user.");
                    }
                } catch (SQLException e) {
                    System.out.println("An error occurred while adding user: " + e.getMessage());
                }
                break;
            case 2:
                // Modify user
                System.out.println("Enter the username of the user you want to modify:");
                String modifyUsername = scanner.nextLine();
                System.out.println("Enter the new username:");
                String newUsername = scanner.nextLine();
                System.out.println("Enter the new password:");
                String newPassword = scanner.nextLine();
                System.out.println("Enter the new role:");
                String newRole = scanner.nextLine();
                success = userManager.modifyUser(modifyUsername, newUsername, newPassword, newRole);
                if (success) {
                    System.out.println("User modified successfully.");
                } else {
                    System.out.println("Failed to modify user.");
                }
                break;
            case 3:
                // Delete user
                System.out.println("Enter the username of the user you want to delete:");
                String deleteUsername = scanner.nextLine();
                success = userManager.deleteUser(deleteUsername);
                if (success) {
                    System.out.println("User deleted successfully.");
                } else {
                    System.out.println("Failed to delete user.");
                }
                break;
            case 4:
                // Change own username
                System.out.println("Enter your new username:");
                 newUsername = scanner.nextLine();
                success = dbConnector.modifyOwnUser(loggedInUser, newUsername);
                if (success) {
                    System.out.println("Username changed successfully. Please restart the program.");
                    System.exit(0); // Terminate the program
                } else {
                    System.out.println("Failed to change username.");
                }
                break;
            case 5:
                // Change own password
                System.out.println("Enter your new password:");
                 newPassword = scanner.nextLine();
                success = dbConnector.modifyOwnPassword(loggedInUser, newPassword);
                if (success) {
                    System.out.println("Password changed successfully. Please restart the program.");
                    System.exit(0); // Terminate the program
                } else {
                    System.out.println("Failed to change password.");
                }
                break;
            case 6:
                // Exit
                System.out.println("Exiting program...");
                System.exit(0); // Terminate the program
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // Method to process lecturer choices
    public void processLecturerChoice(int choice, UserManager userManager, String loggedInUser, Scanner scanner) {
        boolean success;
        String newUsername; // Declare newUsername here
        switch (choice) {
            case 1:
                // Generate report
                String lecturerReport = reportManager.generateLecturerReport();
                reportManager.exportReportToTxt(lecturerReport, "Lecturer_Report");
                break;
            case 2:
                // Change own username
                System.out.println("Enter your new username:");
                newUsername = scanner.nextLine();
                success = dbConnector.modifyOwnUser(loggedInUser, newUsername);
                if (success) {
                    System.out.println("Username changed successfully. Please restart the program.");
                    System.exit(0); // Terminate the program
                } else {
                    System.out.println("Failed to change username.");
                }
                break;
            case 3:
                // Change own password
                System.out.println("Enter your new password:");
                String newPassword = scanner.nextLine();
                success = dbConnector.modifyOwnPassword(loggedInUser, newPassword);
                if (success) {
                    System.out.println("Password changed successfully. Please restart the program.");
                    System.exit(0); // Terminate the program
                } else {
                    System.out.println("Failed to change password.");
                }
                break;
            case 4:
                // Exit
                System.out.println("Exiting program...");
                System.exit(0); // Terminate the program
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    // Method to process office choices
    public void processOfficeChoice(int choice, UserManager userManager, String loggedInUser, Scanner scanner) throws SQLException {
        switch (choice) {
            case 1:
                // Generate course report
                String courseReport = reportManager.generateCourseReport();
                reportManager.exportReportToTxt(courseReport, "Course_Report");
                break;
            case 2:
                // Generate student report
                String studentReport = reportManager.generateStudentReport();
                reportManager.exportReportToTxt(studentReport, "Student_Report");
                break;
            case 3:
                // Generate lecturer report
                String lecturerReport = reportManager.generateLecturerReport();
                reportManager.exportReportToTxt(lecturerReport, "Lecturer_Report");
                break;
            case 4:
                // Change own username/password
                System.out.println("Enter your new username:");
                String newUsername = scanner.nextLine();
                boolean success = dbConnector.modifyOwnUser(loggedInUser, newUsername);
                if (success) {
                    System.out.println("Username changed successfully. Please restart the program.");
                    System.exit(0); // Terminate the program
                } else {
                    System.out.println("Failed to change username.");
                }
                break;
            case 5:
                // Exit
                System.out.println("Exiting program...");
                System.exit(0); // Terminate the program
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}