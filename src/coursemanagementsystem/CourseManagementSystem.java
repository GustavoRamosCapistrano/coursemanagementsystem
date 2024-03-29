package coursemanagementsystem;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * This class represents the main course management system.
 * It provides functionalities for user login, menu display, and processing user choices based on their role.
 * 
 * @author grc29
 */
public class CourseManagementSystem {

    /**
     * The main method of the course management system.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        DBConnector dbConnector = new DBConnector(); // Declare and initialize dbConnector
        try (Scanner scanner = new Scanner(System.in)) {
            UserManager userManager = new UserManager(dbConnector);
            ReportManager reportManager = new ReportManager(dbConnector);
            String loggedInUser = null;

            // Login loop
            while (loggedInUser == null) {
                loggedInUser = Menu.login(scanner, userManager); // Update loggedInUser
            }

            // Main menu loop
            boolean exit = false;
            while (!exit) {
                System.out.println("Welcome, " + loggedInUser + "!\n");

                // Determine user role
                String userRole = userManager.getUserRole(loggedInUser);

                // Display menu based on user role
                Menu.displayMenu(userRole);

                int choice = Menu.getUserChoice(scanner, userRole);

                // Process user choice based on role
                processUserChoice(choice, userRole, userManager, loggedInUser, scanner, reportManager, dbConnector);
            }
        } catch (SQLException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    /**
     * Processes the user choice based on their role.
     * 
     * @param choice The user's choice.
     * @param userRole The role of the logged-in user.
     * @param userManager The user manager.
     * @param loggedInUser The name of the logged-in user.
     * @param scanner The scanner object for user input.
     * @param reportManager The report manager.
     * @param dbConnector The database connector.
     * @throws SQLException If a SQL exception occurs.
     */
    private static void processUserChoice(int choice, String userRole, UserManager userManager,
            String loggedInUser, Scanner scanner, ReportManager reportManager,
            DBConnector dbConnector) throws SQLException {
        Menu menu = new Menu(dbConnector, reportManager);
        switch (userRole) {
            case "admin":
                menu.processAdminChoice(choice, userManager, loggedInUser, scanner);
                break;
            case "lecturer":
                menu.processLecturerChoice(choice, userManager, loggedInUser, scanner);
                break;
            case "office":
                menu.processOfficeChoice(choice, userManager, loggedInUser, scanner);
                break;
            default:
                System.out.println("Invalid user role. Please contact the administrator.");
        }
    }
}
