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
                    break;
                case "Lecturer":
                    // Handle lecturer menu options
                    break;
            }
        }

        // Close scanner
        scanner.close();
    }
}