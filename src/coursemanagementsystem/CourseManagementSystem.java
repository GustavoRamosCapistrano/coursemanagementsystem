package coursemanagementsystem;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author grc29
 */
public class CourseManagementSystem {

    public static void main(String[] args) throws SQLException {
        try (Scanner scanner = new Scanner(System.in)) {
            DBConnector dbConnector = new DBConnector();
            UserManager userManager = new UserManager(dbConnector);
            ReportManager reportManager = new ReportManager();
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

                // Create an instance of Menu
                Menu menu = new Menu(dbConnector, reportManager);
                
                if (userRole.equals("Admin")) {
                    // Call the method on the Menu instance
                    menu.processAdminChoice(choice, userManager, loggedInUser, scanner);
                } else if (userRole.equals("Lecturer")) {
                    // Call the method on the Menu instance
                    menu.processLecturerChoice(choice, userManager, loggedInUser, scanner);
                } else if (userRole.equals("Office")) {
                    // Call the method on the Menu instance for Office
                    menu.processOfficeChoice(choice, userManager, loggedInUser, scanner);
                } else {
                    System.out.println("Invalid user role. Please contact the administrator.");
                }
            }
        }
    }
}