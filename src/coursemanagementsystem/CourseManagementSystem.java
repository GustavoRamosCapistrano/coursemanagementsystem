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
            DBConnector dbConnector = null;
            dbConnector = new DBConnector();

            UserManager userManager = new UserManager(dbConnector);
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

                // Determine user role
                String userRole = userManager.getUserRole(loggedInUser);

                // Display menu based on user role
                Menu.displayMenu(userRole);

                int choice = Menu.getUserChoice(scanner, userRole);

                if (userRole.equals("Admin")) {
                    Menu.processAdminChoice(choice, userManager, loggedInUser, scanner);
                } else if (userRole.equals("Lecturer")) {
                    Menu.processLecturerChoice(choice, userManager, loggedInUser, scanner);
                } else {
                    System.out.println("Invalid user role. Please contact the administrator.");
                }
            }
        }
    }
}