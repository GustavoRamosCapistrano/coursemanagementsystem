/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

/**
 *
 * @author grc29
 */
class Menu {
    public static void displayMenu(String role) {
        System.out.println("Welcome! Your role is: " + role);
        System.out.println("Menu options:");

        // Display menu options based on the user's role
        switch (role) {
            case "Admin":
                System.out.println("1. Manage User Accounts");
                break;
            case "Office":
                System.out.println("1. Generate Reports");
                break;
            case "Lecturer":
                System.out.println("1. View Course Information");
                break;
            default:
                System.out.println("Invalid role.");
        }
    }
}