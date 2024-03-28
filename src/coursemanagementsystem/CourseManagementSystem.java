package coursemanagementsystem;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author grc29
 */
public class CourseManagementSystem {

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