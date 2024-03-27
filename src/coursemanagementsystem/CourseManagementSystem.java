package coursemanagementsystem;

import java.util.Scanner;

/**
 *
 * @author grc29
 */
public class CourseManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DBConnector dbConnector = new DBConnector();
        UserManager userManager = new UserManager(dbConnector);
        ReportManager reportManager = new ReportManager(); // Create an instance of ReportManager
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

            // Determine user role
            String userRole = userManager.getUserRole(loggedInUser);

            // Display menu based on user role
            Menu.displayMenu(userRole);

            int choice = Menu.getUserChoice(scanner);

            switch (userRole) {
                case "Admin":
                    // Handle admin menu options
                    break;
                case "Office":
                    // Handle office menu options
                    switch (choice) {
                        case 1:
                            // Generate Course Report
                            String courseReport = reportManager.generateCourseReport();
                            System.out.println(courseReport);
                            exportReportMenu(reportManager, scanner, courseReport);
                            break;
                        case 2:
                            // Generate Student Report
                            String studentReport = reportManager.generateStudentReport();
                            System.out.println(studentReport);
                            exportReportMenu(reportManager, scanner, studentReport);
                            break;
                        case 3:
                            // Generate Lecturer Report
                            String lecturerReport = reportManager.generateLecturerReport();
                            System.out.println(lecturerReport);
                            exportReportMenu(reportManager, scanner, lecturerReport);
                            break;
                        case 4:
                            // Change Own Username/Password
                            changeOwnUsernamePassword(userManager, loggedInUser, scanner);
                            break;
                        case 5:
                            // Exit
                            exit = true;
                            break;
                    }
                    break;
                case "Lecturer":
                    // Handle lecturer menu options
                    switch (choice) {
                        case 1:
                            // Generate Lecturer Report
                            String lecturerReport = reportManager.generateLecturerReport();
                            System.out.println(lecturerReport);
                            exportReportMenu(reportManager, scanner, lecturerReport);
                            break;
                        case 2:
                            // Change Own Username/Password
                            changeOwnUsernamePassword(userManager, loggedInUser, scanner);
                            break;
                        case 3:
                            // Exit
                            exit = true;
                            break;
                    }
                    break;
            }
        }

        // Close scanner
        scanner.close();
    }

    // Method to handle changing own username/password
    private static void changeOwnUsernamePassword(UserManager userManager, String loggedInUser, Scanner scanner) {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        if (userManager.changePassword(loggedInUser, newPassword) && userManager.changeUsername(loggedInUser, newUsername)) {
            System.out.println("Username and password changed successfully.\n");
        } else {
            System.out.println("Failed to change username or password.\n");
        }
    }

    // Method to handle exporting report to TXT or CSV
    private static void exportReportMenu(ReportManager reportManager, Scanner scanner, String report) {
        System.out.println("Export options:");
        System.out.println("1. Export to TXT");
        System.out.println("2. Export to CSV");
        System.out.println("3. Back to main menu");
        int exportChoice = Menu.getUserChoice(scanner);
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
}