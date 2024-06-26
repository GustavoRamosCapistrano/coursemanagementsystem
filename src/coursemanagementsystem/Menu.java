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
 * This class represents the menu of the course management system.
 * It provides methods for displaying menus, handling user login, and processing user choices based on their role.
 * 
 * @author grc29
 */
public class Menu {

    private final DBConnector dbConnector;
    private final ReportManager reportManager;

    /**
     * Constructor for the Menu class.
     * 
     * @param dbConnector The database connector.
     * @param reportManager The report manager.
     */
    public Menu(DBConnector dbConnector, ReportManager reportManager) {
        this.dbConnector = dbConnector;
        this.reportManager = reportManager;
    }

    // Menu options for different user roles
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

    /**
     * Displays the menu based on the user role.
     * 
     * @param userRole The role of the user.
     */
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

    /**
     * Handles the user login process.
     * 
     * @param scanner The scanner object for user input.
     * @param userManager The user manager.
     * @return The username of the logged-in user.
     * @throws SQLException If a SQL exception occurs.
     */
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

    /**
     * Gets the user's choice from the menu.
     * 
     * @param scanner The scanner object for user input.
     * @param userRole The role of the logged-in user.
     * @return The user's choice.
     */
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

    /**
     * Checks if the user's choice is valid.
     * 
     * @param choice The user's choice.
     * @param userRole The role of the logged-in user.
     * @return True if the choice is valid, otherwise false.
     */
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

    /**
     * Starts the course management system.
     * 
     * @param scanner The scanner object for user input.
     * @param userManager The user manager.
     * @throws SQLException If a SQL exception occurs.
     */
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

    /**
     * Processes the admin's choice.
     * 
     * @param choice The admin's choice.
     * @param userManager The user manager.
     * @param loggedInUser The name of the logged-in user.
     * @param scanner The scanner object for user input.
     * @throws SQLException If a SQL exception occurs.
     */
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

    /**
     * Processes the lecturer's choice.
     * 
     * @param choice The lecturer's choice.
     * @param userManager The user manager.
     * @param loggedInUser The name of the logged-in user.
     * @param scanner The scanner object for user input.
     * @throws SQLException If a SQL exception occurs.
     */
    public void processLecturerChoice(int choice, UserManager userManager, String loggedInUser, Scanner scanner) throws SQLException {
        switch (choice) {
            case 1:
                handleOwnLecturerReportOptions(scanner, loggedInUser);
                break;
            case 2:
                // Change own username
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

    /**
     * Processes the office's choice.
     * 
     * @param choice The office's choice.
     * @param userManager The user manager.
     * @param loggedInUser The name of the logged-in user.
     * @param scanner The scanner object for user input.
     * @throws SQLException If a SQL exception occurs.
     */
    public void processOfficeChoice(int choice, UserManager userManager, String loggedInUser, Scanner scanner) throws SQLException {
        boolean reportGenerated = false;
        
        while (!reportGenerated) {
            switch (choice) {
                case 1:
                    // Generate course report in txt, csv, and console
                    handleCourseReportOptions(scanner);
                    reportGenerated = true;
                    break;
                case 2:
                    // Generate student report in txt, csv, and console
                    handleStudentReportOptions(scanner);
                    reportGenerated = true;
                    break;
                case 3:
                    // Generate lecturer report in txt, csv, and console
                    handleLecturerReportOptions(scanner);
                    reportGenerated = true;
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
    }

    /**
     * Handles the options for generating course reports.
     * 
     * @param scanner The scanner object for user input.
     * @throws SQLException If a SQL exception occurs.
     */
    public void handleCourseReportOptions(Scanner scanner) throws SQLException {
        // Fetch course information from the database
        List<Course> courses = dbConnector.getCoursesReport();

        System.out.println("Choose an option for course report:");
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

        String reportFormat;
        switch (reportChoice) {
            case 1:
                reportFormat = "TXT";
                break;
            case 2:
                reportFormat = "CSV";
                break;
            case 3:
                reportFormat = "CONSOLE";
                break;
            default:
                System.out.println("Invalid report format choice! Please choose a valid option.");
                return; // Return from the method if the choice is invalid
        }

        // Pass the reportFormat and courses list to generateCourseReport method
        reportManager.generateCourseReport(reportFormat, courses);
    }

    // Similarly, you can define methods for student and lecturer reports

    /**
     * Handles the options for generating student reports.
     * 
     * @param scanner The scanner object for user input.
     * @throws SQLException If a SQL exception occurs.
     */
    public void handleStudentReportOptions(Scanner scanner) throws SQLException {
        List<String> studentDetails = dbConnector.getStudentsReport();

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

        String reportFormat;
        switch (reportChoice) {
            case 1:
                reportFormat = "TXT";
                break;
            case 2:
                reportFormat = "CSV";
                break;
            case 3:
                reportFormat = "CONSOLE";
                break;
            default:
                System.out.println("Invalid report format choice! Please choose a valid option.");
                return; // Return from the method if the choice is invalid
        }

        // Pass the reportFormat and studentDetails list to generateStudentReport method
        reportManager.generateStudentReport(reportFormat, studentDetails);
    }

    /**
     * Handles the options for generating lecturer reports.
     * 
     * @param scanner The scanner object for user input.
     * @throws SQLException If a SQL exception occurs.
     */
    public void handleLecturerReportOptions(Scanner scanner) throws SQLException {
        List<Lecturer> lecturers = dbConnector.getLecturersReport();

        System.out.println("Choose an option for lecturer report:");
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

        String reportFormat;
        switch (reportChoice) {
            case 1:
                reportFormat = "TXT";
                break;
            case 2:
                reportFormat = "CSV";
                break;
            case 3:
                reportFormat = "CONSOLE";
                break;
            default:
                System.out.println("Invalid report format choice! Please choose a valid option.");
                return; // Return from the method if the choice is invalid
        }

        // Pass the reportFormat and lecturers list to generateLecturerReport method
        reportManager.generateLecturerReport(reportFormat, lecturers);
    }

    /**
     * Handles the options for generating a lecturer's own report.
     * 
     * @param scanner The scanner object for user input.
     * @param loggedInUser The name of the logged-in user.
     * @throws SQLException If a SQL exception occurs.
     */
    public void handleOwnLecturerReportOptions(Scanner scanner, String loggedInUser) throws SQLException {
        Lecturer lecturer = dbConnector.getLecturerByUsername(loggedInUser); // Fetch lecturer details

        System.out.println("Choose an option for your report:");
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

        String reportFormat;
        switch (reportChoice) {
            case 1:
                reportFormat = "TXT";
                break;
            case 2:
                reportFormat = "CSV";
                break;
            case 3:
                reportFormat = "CONSOLE";
                break;
            default:
                System.out.println("Invalid report format choice! Please choose a valid option.");
                return; // Return from the method if the choice is invalid
        }

        // Pass the reportFormat and lecturer details to generateOwnLecturerReport method
        reportManager.generateOwnLecturerReport(reportFormat, lecturer);
    }
}