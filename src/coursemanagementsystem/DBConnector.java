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

    public DBConnector() {
        // Initialize the database connection when the DBConnector object is created
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to authenticate a user based on username and password
    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Returns true if user exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL error
            return false;
        }
    }

    // Method to add a new user to the database
    public boolean addUser(String username, String password, String role) {
        String sql = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if user was successfully added
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL error
            return false;
        }
    }

    // Method to modify an existing user's password
    public boolean changePassword(String username, String newPassword) {
        String sql = "UPDATE Users SET password = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, newPassword);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if password was successfully updated
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL error
            return false;
        }
    }

    // Method to delete a user from the database
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM Users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if user was successfully deleted
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL error
            return false;
        }
    }

    // Method to get the role of a user based on username
    public String getUserRole(String username) {
        String sql = "SELECT role FROM Users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("role");
                } else {
                    return null; // User not found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL error
            return null;
        }
    }
    
    public List<Course> getAllCourses() {
    List<Course> courses = new ArrayList<>();
    String sql = "SELECT * FROM Courses";
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement statement = conn.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            Course course = new Course();
            course.setCourseId(resultSet.getInt("courseId"));
            course.setCourseName(resultSet.getString("courseName"));
            course.setProgrammeName(resultSet.getString("programmeName"));
            course.setLecturerName(resultSet.getString("lecturerName"));
            course.setRoomName(resultSet.getString("roomName"));
            course.setEnrolledStudents(resultSet.getInt("enrolledStudents"));
            courses.add(course);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return courses;
}
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("studentId"));
                student.setStudentName(resultSet.getString("studentName"));
                student.setProgrammeName(resultSet.getString("programmeName"));
                // Add more attributes as needed
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    public Lecturer getLecturerByName(String lecturerName) {
        Lecturer lecturer = null;
        String sql = "SELECT * FROM Lecturers WHERE lecturerName = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, lecturerName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    lecturer = new Lecturer();
                    lecturer.setLecturerId(resultSet.getInt("lecturerId"));
                    lecturer.setLecturerName(resultSet.getString("lecturerName"));
                    lecturer.setRole(resultSet.getString("role"));
                    // Add more attributes as needed
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lecturer;
    }

    // Method to close database connection
    public void closeConnection() {
        // Close any open resources if needed
    }
}