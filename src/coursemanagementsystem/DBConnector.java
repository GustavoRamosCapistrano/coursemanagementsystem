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
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // Method to retrieve all courses from the database
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM Courses";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseName(resultSet.getString("course_name"));
                course.setProgrammeName(resultSet.getString("programme_id")); // Assuming this is an ID
                course.setEnrolledStudents(resultSet.getInt("enrolled_students"));
                course.setLecturerName(resultSet.getString("lecturer_id")); // Assuming this is an ID
                course.setRoomName(resultSet.getString("room_id")); // Assuming this is an ID
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
            Student student = new Student();
            student.setStudentName(resultSet.getString("student_name"));
            student.setStudentId(resultSet.getInt("student_id")); // Change to getInt for programme_id
            student.setProgrammeId(resultSet.getInt("programme_id"));
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
                Lecturer lecturer = new Lecturer();
                lecturer.setLecturerName(resultSet.getString("lecturer_name"));
                lecturer.setRole(resultSet.getString("role"));
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
}

