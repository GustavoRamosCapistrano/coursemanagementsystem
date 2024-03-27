package coursemanagementsystem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
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
            "4. Export Report\n" +
            "5. Manage Users\n" +
            "6. Exit";

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

            if (authenticateUser(username, password)) {
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
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    String courseReport = reportManager.generateCourseReport();
                    System.out.println(courseReport);
                    exportReport(scanner, courseReport);
                    break;
                case 2:
                    String studentReport = reportManager.generateStudentReport();
                    System.out.println(studentReport);
                    exportReport(scanner, studentReport);
                    break;
                case 3:
                    String lecturerReport = reportManager.generateLecturerReport();
                    System.out.println(lecturerReport);
                    exportReport(scanner, lecturerReport);
                    break;
                case 4:
                    // Export report submenu
                    exportReportMenu(scanner, reportManager);
                    break;
                case 5:
                    manageUsers(userManager, scanner);
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.\n");
                    break;
            }
        }

        // Close database connection
        userManager.closeConnection();
        reportManager.closeConnection();
        scanner.close();
    }

    // Method to authenticate user
    private static boolean authenticateUser(String username, String password) {
        return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
    }

    // Method to get user choice from input
    private static int getUserChoice(Scanner scanner) {
        while (true) {
            System.out.print("\nEnter your choice: ");
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n");
            }
        }
    }

    // Method to manage users (add, modify, delete)
    private static void manageUsers(UserManager userManager, Scanner scanner) {
        boolean userManagement = true;
        while (userManagement) {
            System.out.println("\nUser Management:");
            System.out.println("1. Add User");
            System.out.println("2. Modify Password");
            System.out.println("3. Delete User");
            System.out.println("4. Back to Main Menu");
            System.out.print("\nEnter your choice: ");
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    addUser(userManager, scanner);
                    break;
                case 2:
                    modifyPassword(userManager, scanner);
                    break;
                case 3:
                    deleteUser(userManager, scanner);
                    break;
                case 4:
                    userManagement = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.\n");
                    break;
            }
        }
    }

    // Method to add a new user
    private static void addUser(UserManager userManager, Scanner scanner) {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();

        boolean addUserResult = userManager.addUser(username, password, role);
        if (addUserResult) {
            System.out.println("User added successfully.\n");
        } else {
            System.out.println("Failed to add user. Please try again.\n");
        }
    }

    // Method to modify a user's password
    private static void modifyPassword(UserManager userManager, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        boolean changePasswordResult = userManager.changePassword(username, newPassword);
        if (changePasswordResult) {
            System.out.println("Password changed successfully.\n");
        } else {
            System.out.println("Failed to change password. Please try again.\n");
        }
    }

    // Method to delete a user
    private static void deleteUser(UserManager userManager, Scanner scanner) {
        System.out.print("Enter username to delete: ");
        String username = scanner.nextLine();

        boolean deleteUserResult = userManager.deleteUser(username);
        if (deleteUserResult) {
            System.out.println("User deleted successfully.\n");
        } else {
            System.out.println("Failed to delete user. Please try again.\n");
        }
    }

    // Method to export report
    private static void exportReportMenu(Scanner scanner, ReportManager reportManager) {
        System.out.println("\nExport Report Menu:");
        System.out.println("1. Export Course Report");
        System.out.println("2. Export Student Report");
        System.out.println("3. Export Lecturer Report");
        System.out.println("4. Back to Main Menu");
        System.out.print("\nEnter your choice: ");
        int choice = getUserChoice(scanner);

        switch (choice) {
            case 1:
                String courseReport = reportManager.generateCourseReport();
                exportReport(scanner, courseReport);
                break;
            case 2:
                String studentReport = reportManager.generateStudentReport();
                exportReport(scanner, studentReport);
                break;
            case 3:
                String lecturerReport = reportManager.generateLecturerReport();
                exportReport(scanner, lecturerReport);
                break;
            case 4:
                // Back to main menu
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 4.\n");
                break;
        }
    }

    // Method to export report to chosen format
    private static void exportReport(Scanner scanner, String report) {
        System.out.println("\nChoose export format:");
        System.out.println("1. TXT");
        System.out.println("2. CSV");
        System.out.println("3. Cancel");
        System.out.print("\nEnter your choice: ");
        int choice = getUserChoice(scanner);

        switch (choice) {
            case 1:
                System.out.print("Enter file name: ");
                String txtFileName = scanner.nextLine();
                if (reportManager.exportReportToTxt(report, txtFileName)) {
                    System.out.println("Report exported to TXT successfully.\n");
                } else {
                    System.out.println("Failed to export report to TXT. Please try again.\n");
                }
                break;
            case 2:
                System.out.print("Enter file name: ");
                String csvFileName = scanner.nextLine();
                if (reportManager.exportReportToCsv(report, csvFileName)) {
                    System.out.println("Report exported to CSV successfully.\n");
                } else {
                    System.out.println("Failed to export report to CSV. Please try again.\n");
                }
                break;
            case 3:
                System.out.println("Export canceled.\n");
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.\n");
                break;
        }
    }
}