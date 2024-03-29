/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author grc29
 */
public class Menu {

    private final DBConnector dbConnector;
    private final ReportManager reportManager;

    public Menu(DBConnector dbConnector, ReportManager reportManager) {
        this.dbConnector = dbConnector;
        this.reportManager = reportManager;
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
        switch (userRole.toLowerCase()) {
            case "admin":
                System.out.println(MENU_OPTIONS_ADMIN);
                break;
            case "office":
                System.out.println(MENU_OPTIONS_OFFICE);
                break;
            case "lecturer":
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
            String username = scanner.nextLine().toLowerCase(); // Convert username to lowercase
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
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                if (isValidChoice(choice, userRole.toLowerCase())) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    private static boolean isValidChoice(int choice, String userRole) {
        switch (userRole) {
            case "admin":
                return choice >= 1 && choice <= 6;
            case "office":
                return choice >= 1 && choice <= 5;
            case "lecturer":
                return choice >= 1 && choice <= 4;
            default:
                return false;
        }
    }

    public void startCourseManagementSystem(Scanner scanner, UserManager userManager) throws SQLException {
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
                if (userRole.equals("admin")) {
                    // Call the method to process admin choice
                    processAdminChoice(choice, userManager, loggedInUser, scanner);
                } else if (userRole.equals("lecturer")) {
                    // Call the method to process lecturer choice
                    processLecturerChoice(choice, userManager, loggedInUser, scanner);
                } else if (userRole.equals("office")) {
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
                String username = scanner.nextLine().toLowerCase(); // Convert username to lowercase
                System.out.println("Enter password:");
                String password = scanner.nextLine();
                System.out.println("Enter role:");
                String role = scanner.nextLine().toLowerCase(); // Convert role to lowercase
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
                String modifyUsername = scanner.nextLine().toLowerCase(); // Convert username to lowercase
                System.out.println("Enter the new username:");
                String newUsername = scanner.nextLine().toLowerCase(); // Convert username to lowercase
                System.out.println("Enter the new password:");
                String newPassword = scanner.nextLine();
                System.out.println("Enter the new role:");
                String newRole = scanner.nextLine().toLowerCase(); // Convert role to lowercase
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
                String deleteUsername = scanner.nextLine().toLowerCase(); // Convert username to lowercase
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
                newUsername = scanner.nextLine().toLowerCase(); // Convert username to lowercase
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

    public void processLecturerChoice(int choice, UserManager userManager, String loggedInUser, Scanner scanner) {
        boolean success;
        String newUsername; // Declare newUsername here
        switch (choice) {
            case 1:
                try {
                // Generate lecturer report data
                String reportData = reportManager.generateOwnLecturerReport(loggedInUser);
                // Display report format menu
                displayReportFormatMenuLecturerChoice(scanner, reportData);
            } catch (SQLException e) {
                System.out.println("An error occurred while generating the lecturer report: " + e.getMessage());
            }
            break;
            case 2:
                // Change own username
                System.out.println("Enter your new username:");
                newUsername = scanner.nextLine().toLowerCase(); // Convert username to lowercase
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

    private void displayReportFormatMenuLecturerChoice(Scanner scanner, String reportData) {
        System.out.println("Choose report format:");
        System.out.println("1. TXT file");
        System.out.println("2. CSV file");
        System.out.println("3. NetBeans Console");

        int choice = getUserChoice(scanner, "lecturer");

        switch (choice) {
            case 1:
                reportManager.generateOwnLecturerReportInTxt(reportData);
                break;
            case 2:
                reportManager.generateOwnLecturerReportInCsv(reportData);
                break;
            case 3:
                reportManager.generateOwnLecturerReportInConsole(reportData);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    // Method to process office choices
    public void processOfficeChoice(int choice, UserManager userManager, String loggedInUser, Scanner scanner) throws SQLException {
        switch (choice) {
            case 1:
                // Generate course report in txt csv and console
                handleCourseReportOptions(scanner);
                break;
            case 2:
                // Generate student report in txt csv and console
                handleStudentReportOptions(scanner);
                break;
            case 3:
                // Generate lecturer report in txt csv and console
                handleLecturerReportOptions(scanner);
                break;
            case 4:
                // Change own username/password
                System.out.println("Enter your new username:");
                String newUsername = scanner.nextLine().toLowerCase(); // Convert username to lowercase
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

    public void handleCourseReportOptions(Scanner scanner) throws SQLException {
        System.out.println("Choose an option for student report:");
        System.out.println("1. Generate report in TXT");
        System.out.println("2. Generate report in CSV");
        System.out.println("3. Print report to console");

        int reportChoice;
        while (true) {
            try {
                System.out.print("\nEnter your choice: ");
                reportChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                break; // Break the loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        switch (reportChoice) {
            case 1:
                generateCourseReport("TXT");
                break;
            case 2:
                generateCourseReport("CSV");
                break;
            case 3:
                generateCourseReport("CONSOLE");
                break;
            default:
                System.out.println("Invalid report format choice! Please choose a valid option.");
                break;
        }
    }

    public void generateCourseReport(String format) {
        reportManager.generateCourseReport(format); // Ensure "String" is capitalized
    }

    public void printCourseReport() throws SQLException {
        // Call the existing displayCourseReport method from ReportManager
        reportManager.displayCourseReport();
    }

    // Similarly, you can define methods for student and lecturer reports
    public void handleStudentReportOptions(Scanner scanner) throws SQLException {
        System.out.println("Choose an option for student report:");
        System.out.println("1. Generate report in TXT");
        System.out.println("2. Generate report in CSV");
        System.out.println("3. Print report to console");
        int reportChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (reportChoice) {
            case 1:
                generateStudentReport("TXT");
                // Assuming you have a method for generating student report in TXT format
                break;
            case 2:
                generateStudentReport("CSV");
                break;
            case 3:
                printStudentReport();
                break;
            default:
                System.out.println("Invalid report format choice! Please choose a valid option.");
                break;
        }
    }

    public void generateStudentReport(String format) throws SQLException {
        reportManager.generateStudentReport(format);
    }

    public void printStudentReport() throws SQLException {
        List<Student> students = dbConnector.getAllStudents();
        reportManager.displayStudentReport(students);
    }

    public void handleLecturerReportOptions(Scanner scanner) throws SQLException {
        System.out.println("Choose an option for lecturer report:");
        System.out.println("1. Generate report in TXT");
        System.out.println("2. Generate report in CSV");
        System.out.println("3. Print report to console");
        int reportChoice = scanner.nextInt();

        switch (reportChoice) {
            case 1:
                generateLecturerReport("TXT");
                break;
            case 2:
                generateLecturerReport("CSV");
                break;
            case 3:
                printLecturerReport();
                break;
            default:
                System.out.println("Invalid report format choice! Please choose a valid option.");
        }
    }

    public void generateLecturerReport(String format) throws SQLException {
        reportManager.generateLecturerReport(format);
    }

    public void printLecturerReport() throws SQLException {
        List<Lecturer> lecturers = dbConnector.getAllLecturers();
        reportManager.displayLecturerReport(lecturers);
    }
}
