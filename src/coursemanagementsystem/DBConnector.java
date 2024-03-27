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
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all courses from the database
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.course_name, p.programme_name, l.lecturer_name, r.room_name "
                + "FROM Courses c "
                + "JOIN Programmes p ON c.programme_id = p.programme_id "
                + "JOIN Lecturers l ON c.lecturer_id = l.lecturer_id "
                + "JOIN Rooms r ON c.room_id = r.room_id";
        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement statement = conn.prepareStatement(sql);  ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Course course = new Course(resultSet.getString("course_name"),
                        resultSet.getString("programme_name"),
                        resultSet.getString("lecturer_name"),
                        resultSet.getString("room_name"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    // Method to retrieve all students from the database
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.student_name, p.programme_name, COUNT(e.enrollment_id) AS enrolled_modules, "
                + "SUM(CASE WHEN g.grade IS NOT NULL THEN 1 ELSE 0 END) AS completed_modules, "
                + "SUM(CASE WHEN g.grade < 50 THEN 1 ELSE 0 END) AS repeat_modules "
                + "FROM Students s "
                + "JOIN Programmes p ON s.programme_id = p.programme_id "
                + "LEFT JOIN Enrollments e ON s.student_id = e.student_id "
                + "LEFT JOIN Grades g ON e.enrollment_id = g.enrollment_id "
                + "GROUP BY s.student_id, s.student_name, p.programme_name";
        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  PreparedStatement statement = conn.prepareStatement(sql);  ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Student student = new Student(resultSet.getString("student_name"),
                        resultSet.getString("programme_name"),
                        resultSet.getInt("enrolled_modules"),
                        resultSet.getInt("completed_modules"),
                        resultSet.getInt("repeat_modules"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

// Method to retrieve all lecturers from the database
    public List<Lecturer> getAllLecturers() {
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT lecturer_name, role, COUNT(DISTINCT c.course_id) AS modules_taught, "
                + "COUNT(DISTINCT e.student_id) AS student_count, class_types "
                + "FROM Lecturers l "
                + "JOIN Courses c ON l.lecturer_id = c.lecturer_id "
                + "GROUP BY l.lecturer_id, l.lecturer_name, role, class_types";
        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  Statement statement = conn.createStatement();  ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Lecturer lecturer = new Lecturer(resultSet.getString("lecturer_name"),
                        resultSet.getString("role"),
                        resultSet.getInt("modules_taught"),
                        resultSet.getInt("student_count"),
                        resultSet.getString("class_types"));
                lecturers.add(lecturer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lecturers;
    }

// Method to close database connection
    public void closeConnection() {
        // Close any open resources if needed
    }
}
