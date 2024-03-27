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

    public DBConnector() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    // Method to authenticate a user
    public boolean authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // Method to add a new user
    public boolean addUser(String username, String password, String role) throws SQLException {
        String sql = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Method to modify user password
    public boolean changePassword(String username, String newPassword) {
        String sql = "UPDATE Users SET password = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, newPassword);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error changing password", e);
        }
    }

    // Method to modify user role
    public boolean changeUserRole(String username, String newRole) {
        String sql = "UPDATE Users SET role = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, newRole);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error changing user role", e);
        }
    }

    // Method to delete user
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM Users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
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
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting user role", e);
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
            throw new RuntimeException("Error getting all courses", e);
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
                students.add(student);
            }
        }  catch (SQLException e) {
            throw new RuntimeException("Error getting all students", e);
        }
        return students;
    }
    public List<Lecturer> getAllLecturers() {
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT * FROM Lecturers";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Lecturer lecturer = new Lecturer();
                lecturer.setLecturerId(resultSet.getInt("lecturerId"));
                lecturer.setLecturerName(resultSet.getString("lecturerName"));
                lecturer.setRole(resultSet.getString("role"));
                lecturers.add(lecturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all lecturers", e);
        }
        return lecturers;
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
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting lecturer by name", e);
        }
        return lecturer;
    }

    public void closeConnection() {
        // Close any open resources if needed
    }
}