/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.util.Scanner;

/**
 *
 * @author grc29
 */
public class Menu {
    private static final String MENU_OPTIONS_ADMIN = "Menu Options:\n" +
            "1. Add User\n" +
            "2. Modify Password\n" +
            "3. Delete User\n" +
            "4. Change Own Username/Password\n" +
            "5. Exit";

    private static final String MENU_OPTIONS_OFFICE = "Menu Options:\n" +
            "1. Generate Course Report\n" +
            "2. Generate Student Report\n" +
            "3. Generate Lecturer Report\n" +
            "4. Change Own Username/Password\n" +
            "5. Exit";

    private static final String MENU_OPTIONS_LECTURER = "Menu Options:\n" +
            "1. Generate Lecturer Report\n" +
            "2. Change Own Username/Password\n" +
            "3. Exit";

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

    public static int getUserChoice(Scanner scanner) {
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
}