package coursemanagementsystem;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author grc29
 */
public class CourseManagementSystem {

     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager(new DBConnector());
        Menu.startCourseManagementSystem(scanner, userManager, new DBConnector());
    }
}