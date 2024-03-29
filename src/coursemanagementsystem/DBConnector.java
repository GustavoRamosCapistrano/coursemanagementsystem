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
public List<Course> getCoursesReport() throws SQLException {
        List<Course> courses = new ArrayList<>();
        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery("SELECT course_name, programme_name, lecturer_name, room_name, COUNT(student_id) AS enrolled_students\n"
                + "FROM Courses\n"
                + "JOIN Programmes ON Courses.programme_id = Programmes.programme_id\n"
                + "JOIN Lecturers ON Courses.lecturer_id = Lecturers.lecturer_id\n"
                + "LEFT JOIN Rooms ON Courses.room_id = Rooms.room_id\n"
                + "LEFT JOIN Enrollments ON Courses.course_id = Enrollments.course_id\n"
                + "GROUP BY course_name, programme_name, lecturer_name, room_name")) {
            while (rs.next()) {
                String courseName = rs.getString("course_name");
                String programmeName = rs.getString("programme_name");
                String lecturerName = rs.getString("lecturer_name");
                String roomName = rs.getString("room_name");
                int enrolledStudents = rs.getInt("enrolled_students");
                Course course = new Course(courseName, programmeName, lecturerName, roomName, enrolledStudents);
                courses.add(course);
            }
        }
        return courses;
    }

    // Method to retrieve all students from the database
public List<String> getStudentsReport() throws SQLException {
    List<String> studentDetails = new ArrayList<>();
    String query = "SELECT " +
                   "    s.student_name, " +
                   "    s.student_id, " +
                   "    p.programme_name, " +
                   "    GROUP_CONCAT(DISTINCT c.course_name ORDER BY c.course_name SEPARATOR ', ') AS enrolled_courses, " +
                   "    GROUP_CONCAT(DISTINCT CONCAT(c.course_name, ' (Grade: ', COALESCE(g.grade, 'N/A'), ')') ORDER BY c.course_name SEPARATOR ', ') AS completed_courses, " +
                   "    GROUP_CONCAT(DISTINCT c2.course_name ORDER BY c2.course_name SEPARATOR ', ') AS courses_to_repeat " +
                   "FROM " +
                   "    Students s " +
                   "        LEFT JOIN " +
                   "    Enrollments e ON s.student_id = e.student_id " +
                   "        LEFT JOIN " +
                   "    Courses c ON e.course_id = c.course_id " +
                   "        LEFT JOIN " +
                   "    Programmes p ON s.programme_id = p.programme_id " +
                   "        LEFT JOIN " +
                   "    Grades g ON e.enrollment_id = g.enrollment_id " +
                   "        LEFT JOIN " +
                   "    (SELECT " +
                   "        s.student_id, c.course_name " +
                   "    FROM " +
                   "        Students s " +
                   "    JOIN Enrollments e ON s.student_id = e.student_id " +
                   "    JOIN Courses c ON e.course_id = c.course_id " +
                   "    LEFT JOIN Grades g ON e.enrollment_id = g.enrollment_id " +
                   "    WHERE " +
                   "        g.grade < 5 OR g.grade IS NULL) c2 ON s.student_id = c2.student_id " +
                   "GROUP BY s.student_id, s.student_name, p.programme_name " +
                   "ORDER BY s.student_name;";
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            String studentName = resultSet.getString("student_name");
            int studentId = resultSet.getInt("student_id");
            String programmeName = resultSet.getString("programme_name");
            String enrolledCourses = resultSet.getString("enrolled_courses");
            String completedCourses = resultSet.getString("completed_courses");
            String coursesToRepeat = resultSet.getString("courses_to_repeat");

            String studentDetail = String.format("%s\t%d\t%s\t%s\t%s\t%s",
                    studentName, studentId, programmeName, enrolledCourses, completedCourses, coursesToRepeat);
            studentDetails.add(studentDetail);
        }
    }
    return studentDetails;
}

    // Method to retrieve all lecturers from the database
    public List<Lecturer> getLecturersReport() throws SQLException {
    List<Lecturer> lecturers = new ArrayList<>();
    String query = "SELECT " +
                   "    l.lecturer_id, " +
                   "    l.lecturer_name, " +
                   "    l.role, " +
                   "    GROUP_CONCAT(DISTINCT c.course_name ORDER BY c.course_name SEPARATOR ', ') AS modules_taught, " +
                   "    COUNT(DISTINCT e.student_id) AS student_count, " +
                   "    l.class_types " +
                   "FROM " +
                   "    Lecturers l " +
                   "        LEFT JOIN " +
                   "    Courses c ON l.lecturer_id = c.lecturer_id " +
                   "        LEFT JOIN " +
                   "    Enrollments e ON c.course_id = e.course_id " +
                   "GROUP BY " +
                   "    l.lecturer_id, l.lecturer_name, l.role, l.class_types";
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            int lecturerId = resultSet.getInt("lecturer_id");
            String lecturerName = resultSet.getString("lecturer_name");
            String role = resultSet.getString("role");
            String modulesTaught = resultSet.getString("modules_taught");
            int studentCount = resultSet.getInt("student_count");
            String classTypes = resultSet.getString("class_types");

            Lecturer lecturer = new Lecturer(lecturerName, role, modulesTaught, studentCount, classTypes);
            lecturers.add(lecturer);
        }
    }
    return lecturers;
}
public List<Lecturer> getOwnLecturersReport(String loggedInUser) throws SQLException {
    List<Lecturer> lecturers = new ArrayList<>();
    String query = "SELECT " +
                   "    l.lecturer_name, " +
                   "    l.role, " +
                   "    GROUP_CONCAT(DISTINCT c.course_name ORDER BY c.course_name SEPARATOR ', ') AS modules_taught, " +
                   "    COUNT(DISTINCT e.student_id) AS student_count, " +
                   "    l.class_types " +
                   "FROM " +
                   "    Lecturers l " +
                   "LEFT JOIN " +
                   "    Courses c ON l.lecturer_id = c.lecturer_id " +
                   "LEFT JOIN " +
                   "    Enrollments e ON c.course_id = e.course_id " +
                   "WHERE " +
                   "    l.lecturer_name = ? " +  // Filter by lecturer name
                   "GROUP BY " +
                   "    l.lecturer_name, l.role, l.class_types";
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, loggedInUser);
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String lecturerName = resultSet.getString("lecturer_name");
                String role = resultSet.getString("role");
                String modulesTaught = resultSet.getString("modules_taught");
                int studentCount = resultSet.getInt("student_count");
                String classTypes = resultSet.getString("class_types");

                Lecturer lecturer = new Lecturer(lecturerName, role, modulesTaught, studentCount, classTypes);
                lecturers.add(lecturer);
            }
        }
    }
    return lecturers;
}
public Lecturer getLecturerByUsername(String username) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Lecturer lecturer = null;

    try {
        conn = getConnection();
        String sql = "SELECT l.*, " +
                     "COUNT(DISTINCT e.student_id) AS student_count, " +
                     "GROUP_CONCAT(DISTINCT c.course_name ORDER BY c.course_name SEPARATOR ', ') AS modules_taught " +
                     "FROM Lecturers l " +
                     "LEFT JOIN Courses c ON l.lecturer_id = c.lecturer_id " +
                     "LEFT JOIN Enrollments e ON c.course_id = e.course_id " +
                     "WHERE l.lecturer_name = ? " +
                     "GROUP BY l.lecturer_id, l.lecturer_name, l.role, l.class_types";  // Add GROUP BY clause
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        rs = stmt.executeQuery();

        if (rs.next()) {
            int lecturerId = rs.getInt("lecturer_id");
            String lecturerName = rs.getString("lecturer_name");
            String role = rs.getString("role");
            String modulesTaught = rs.getString("modules_taught");
            int studentCount = rs.getInt("student_count");
            String classTypes = rs.getString("class_types");

            lecturer = new Lecturer(lecturerName, role, modulesTaught, studentCount, classTypes);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    return lecturer;
}
    public boolean modifyUsername(String loggedInUser, String newUsername) {
        String query = "UPDATE Users SET username = ? WHERE username = ?";
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
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
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try ( ResultSet resultSet = statement.executeQuery()) {
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
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
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
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
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
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
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
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
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
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
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
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
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
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try ( ResultSet resultSet = statement.executeQuery()) {
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
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
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
        try ( Connection connection = getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try ( ResultSet resultSet = statement.executeQuery()) {
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
