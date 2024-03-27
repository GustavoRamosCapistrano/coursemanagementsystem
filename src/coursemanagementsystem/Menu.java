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

    private static final String MENU_OPTIONS_ADMIN = "Menu Options:\n"
            + "1. Add User\n"
            + "2. Modify User and Password\n"
            + "3. Modify User Role\n"
            + "4. Delete User\n"
            + "5. Back to main menu";

    private static final String MENU_OPTIONS_OFFICE = "Menu Options:\n"
            + "1. Generate Course Report\n"
            + "2. Generate Student Report\n"
            + "3. Generate Lecturer Report\n"
            + "4. Change Own Username/Password\n"
            + "5. Exit";

    private static final String MENU_OPTIONS_LECTURER = "Menu Options:\n"
            + "1. Generate Lecturer Report\n"
            + "2. Change Own Username/Password\n"
            + "3. Exit";

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
                return choice >= 1 && choice <= 5;
            case "Office":
                return choice >= 1 && choice <= 5;
            case "Lecturer":
                return choice >= 1 && choice <= 3;
            default:
                return false;
        }
    }

    public static void exportReportMenu(ReportManager reportManager, Scanner scanner, String report) {
        System.out.println("Export options:");
        System.out.println("1. Export to TXT");
        System.out.println("2. Export to CSV");
        System.out.println("3. Back to main menu");
        int exportChoice = getUserChoice(scanner, "Export");
        switch (exportChoice) {
            case 1:
                // Export to TXT
                System.out.print("Enter filename: ");
                String txtFilename = scanner.nextLine();
                reportManager.exportReportToTxt(report, txtFilename);
                break;
            case 2:
                // Export to CSV
                System.out.print("Enter filename: ");
                String csvFilename = scanner.nextLine();
                reportManager.exportReportToCsv(report, csvFilename);
                break;
            case 3:
                // Back to main menu
                break;
            default:
                System.out.println("Invalid choice. Back to main menu.\n");
                break;
        }
    }

    public static void addUser(UserManager userManager, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();
        try {
            if (userManager.addUser(username, password, role)) {
                System.out.println("User added successfully.\n");
            } else {
                System.out.println("Failed to add user.\n");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void processAdminChoice(int choice, UserManager userManager, String loggedInUser, Scanner scanner) throws SQLException {
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
                    boolean success = userManager.addUser(username, password, role);
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
                boolean success = userManager.modifyUser(modifyUsername, newUsername, newPassword, newRole);
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
                // Change password or username
                System.out.println("Enter your new username:");
                String newPasswordUsername = scanner.nextLine();
                System.out.println("Enter your new password:");
                String newPasswordPassword = scanner.nextLine();
                boolean changeSuccess = userManager.changePasswordOrUsername(loggedInUser, scanner);
                if (changeSuccess) {
                    System.out.println("Password or username changed successfully.");
                } else {
                    System.out.println("Failed to change password or username.");
                }
                break;
            case 5:
                // Exit
                System.out.println("Exiting program...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // Method to process lecturer choices
    public static void processLecturerChoice(int choice, UserManager userManager, String loggedInUser, Scanner scanner) {
        switch (choice) {
            case 1:
                // Modify user
                userManager.modifyUser(scanner);
                break;
            case 2:
                // Change password or username
                userManager.changePasswordOrUsername(loggedInUser, scanner);
                break;
            case 3:
                // Exit
                System.out.println("Exiting program...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

}
