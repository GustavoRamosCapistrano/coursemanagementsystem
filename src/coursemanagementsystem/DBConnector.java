/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author grc29
 */
public class DBConnector {

    private final String DB_URL = "jdbc:mysql://localhost/cms";
    private final String USER = "pooa2024";
    private final String PASSWORD = "pooa2024";

    // Method to establish connection with the database
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // Method to retrieve all courses from the database
   public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT course_id, course_name, programme_id, lecturer_id, room_id, enrolled_students FROM Courses")) {
            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                String courseName = rs.getString("course_name");
                String programmeName = rs.getString("programme_name"); // assuming this is the correct column name
                String lecturerName = rs.getString("lecturer_name"); // assuming this is the correct column name
                String roomName = rs.getString("room_name"); // assuming this is the correct column name
                int enrolledStudents = rs.getInt("enrolled_students");
                Course course = new Course(courseId, courseName, programmeName, lecturerName, roomName, enrolledStudents);
                courses.add(course);
            }
        }
        return courses;
    }

    // Method to retrieve all students from the database
public List<Student> getAllStudents() throws SQLException {
    List<Student> students = new ArrayList<>();
    String query = "SELECT * FROM Students";
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            Student student = new Student(
                resultSet.getInt("student_id"),
                resultSet.getString("student_name"),
                resultSet.getString("programme_name"),
                resultSet.getInt("enrolled_modules"),
                resultSet.getInt("completed_modules"),
                resultSet.getInt("repeat_modules")
            );
            // Assuming you have other attributes for student
            students.add(student);
        }
    }
    return students;
}
    // Method to retrieve all lecturers from the database
    public List<Lecturer> getAllLecturers() throws SQLException {
    List<Lecturer> lecturers = new ArrayList<>();
    String query = "SELECT * FROM Lecturers";
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            Lecturer lecturer = new Lecturer(
                resultSet.getString("lecturer_name"),
                resultSet.getString("role"),
                resultSet.getString("modules_taught"),
                resultSet.getInt("student_count"),
                resultSet.getString("classes_taught")
            );
            // Assuming you have other attributes for lecturer
            lecturers.add(lecturer);
        }
    }
    return lecturers;
}

    public boolean modifyUsername(String loggedInUser, String newUsername) {
    String query = "UPDATE Users SET username = ? WHERE username = ?";
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, newUsername);
        statement.setString(2, loggedInUser);
        
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public String getUserRole(String username) {
    String query = "SELECT role FROM Users WHERE username = ?";
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, username);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString("role");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // Return null if the user role could not be retrieved
}
    // Method to delete a user from the database
    public boolean deleteUser(String username) {
        String query = "DELETE FROM Users WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to change the role of a user in the database
    public boolean modifyUserRole(String username, String newRole) {
        String query = "UPDATE Users SET role = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newRole);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to change the password of a user in the database
    public boolean modifyPassword(String newPassword) {
        String query = "UPDATE Users SET password = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPassword);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
public boolean modifyOwnPassword(String loggedInUser, String newPassword) {
    String query = "UPDATE Users SET password = ? WHERE username = ?";
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, newPassword);
        statement.setString(2, loggedInUser);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean modifyOwnUser(String loggedInUser, String newUsername) {
    String query = "UPDATE Users SET username = ? WHERE username = ?";
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, newUsername);
        statement.setString(2, loggedInUser);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    // Method to add a new user to the database
    public boolean addUser(String username, String password, String role) {
        String query = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to authenticate a user based on username and password
    public boolean authenticateUser(String username, String password) {
        String query = "SELECT COUNT(*) FROM Users WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean modifyUser(String modifyUsername, String newUsername, String newPassword, String newRole) throws SQLException {
    // Your SQL query to update the user's information in the database
    String query = "UPDATE Users SET username=?, password=?, role=? WHERE username=?";
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, newUsername);
        statement.setString(2, newPassword);
        statement.setString(3, newRole);
        statement.setString(4, modifyUsername);
        
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    }
    }
    public boolean usernameExists(String username) {
    String query = "SELECT COUNT(*) FROM Users WHERE username = ?";
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, username);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Return true if username exists, false otherwise
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Return false by default (error or no result)
}
     public List<Course> executeQueryForCourses(String query) throws SQLException {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getString("programme_name"),
                        rs.getString("lecturer_name"),
                        rs.getString("room_name"),
                        rs.getInt("enrolled_students")
                );
                courses.add(course);
            }
        }
        return courses;
    }
     public List<Student> executeQueryForStudents(String query) throws SQLException {
    List<Student> students = new ArrayList<>();
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Student student = new Student(
                    rs.getInt("student_id"),
                    rs.getString("student_name"),
                    rs.getString("programme_name"),
                    rs.getInt("enrolled_modules"),
                    rs.getInt("completed_modules"),
                    rs.getInt("repeat_modules")
            );
            students.add(student);
        }
    }
    return students;
}

public List<Lecturer> executeQueryForLecturers(String query) throws SQLException {
    List<Lecturer> lecturers = new ArrayList<>();
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Lecturer lecturer = new Lecturer(
                    rs.getString("lecturer_name"),
                    rs.getString("role"),
                    rs.getString("modules_taught"),
                    rs.getInt("student_count"),
                    rs.getString("classes_taught")
            );
            lecturers.add(lecturer);
        }
    }
    return lecturers;
}
}

