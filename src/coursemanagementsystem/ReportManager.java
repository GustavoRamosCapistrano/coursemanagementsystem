/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author grc29
 */
public class ReportManager {

    private final DBConnector dbConnector;

    public ReportManager(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public String generateCourseReport() throws SQLException {
        StringBuilder report = new StringBuilder();
        try ( Connection connection = dbConnector.getConnection();  PreparedStatement statement = connection.prepareStatement(
                "SELECT "
                + "c.course_name AS Module_Name, "
                + "p.programme_name AS Programme, "
                + "COUNT(e.enrollment_id) AS Enrolled_Students, "
                + "l.lecturer_name AS Lecturer, "
                + "COALESCE(r.room_name, 'online') AS Room_Assigned "
                + "FROM "
                + "Courses c "
                + "INNER JOIN "
                + "Programmes p ON c.programme_id = p.programme_id "
                + "LEFT JOIN "
                + "Enrollments e ON c.course_id = e.course_id "
                + "LEFT JOIN "
                + "Lecturers l ON c.lecturer_id = l.lecturer_id "
                + "LEFT JOIN "
                + "Rooms r ON c.room_id = r.room_id "
                + "GROUP BY "
                + "c.course_id, c.course_name, p.programme_name, l.lecturer_name, r.room_name")) {
            ResultSet resultSet = statement.executeQuery();
            report.append("Course Report:\n");
            report.append(String.format("%-30s %-30s %-20s %-30s %-10s\n", "Module Name", "Programme", "Enrolled Students", "Lecturer", "Room Assigned"));
            while (resultSet.next()) {
                report.append(String.format("%-30s %-30s %-20d %-30s %-10s\n",
                        resultSet.getString("Module_Name"),
                        resultSet.getString("Programme"),
                        resultSet.getInt("Enrolled_Students"),
                        resultSet.getString("Lecturer"),
                        resultSet.getString("Room_Assigned")));
            }
        } catch (SQLException e) {
            // Handle SQL exception
            System.err.println("An error occurred while generating the course report: " + e.getMessage());
            throw e; // Re-throw the exception to be handled by the calling method
        }
        return report.toString();
    }

    public String generateStudentReport(int studentId) throws SQLException {
        StringBuilder report = new StringBuilder();
        try ( Connection connection = dbConnector.getConnection()) {
            // Retrieve student information
            Map<Integer, String> modulesEnrolled = new HashMap<>();
            Map<Integer, String> modulesCompleted = new HashMap<>();
            Map<Integer, String> modulesToRepeat = new HashMap<>();
            String studentName = "";
            String programmeName = "";
            try ( PreparedStatement studentStatement = connection.prepareStatement(
                    "SELECT student_name, programme_name FROM Students JOIN Programmes USING (programme_id) WHERE student_id = ?")) {
                studentStatement.setInt(1, studentId);
                ResultSet studentResultSet = studentStatement.executeQuery();
                if (studentResultSet.next()) {
                    studentName = studentResultSet.getString("student_name");
                    programmeName = studentResultSet.getString("programme_name");
                }
            }
            // Retrieve modules enrolled by the student
            try ( PreparedStatement enrolledStatement = connection.prepareStatement(
                    "SELECT c.course_id, c.course_name FROM Enrollments e JOIN Courses c ON e.course_id = c.course_id WHERE student_id = ?")) {
                enrolledStatement.setInt(1, studentId);
                ResultSet enrolledResultSet = enrolledStatement.executeQuery();
                while (enrolledResultSet.next()) {
                    modulesEnrolled.put(enrolledResultSet.getInt("course_id"), enrolledResultSet.getString("course_name"));
                }
            }
            // Retrieve completed modules and grades
            try ( PreparedStatement completedStatement = connection.prepareStatement(
                    "SELECT c.course_id, c.course_name, g.grade FROM Enrollments e "
                    + "JOIN Courses c ON e.course_id = c.course_id "
                    + "JOIN Grades g ON e.enrollment_id = g.enrollment_id "
                    + "WHERE e.student_id = ?")) {
                completedStatement.setInt(1, studentId);
                ResultSet completedResultSet = completedStatement.executeQuery();
                while (completedResultSet.next()) {
                    modulesCompleted.put(completedResultSet.getInt("course_id"),
                            completedResultSet.getString("course_name")
                            + " (Grade: " + completedResultSet.getFloat("grade") + ")");
                }
            }
            // Retrieve modules to repeat
            try ( PreparedStatement repeatStatement = connection.prepareStatement(
                    "SELECT c.course_id, c.course_name FROM Courses c "
                    + "LEFT JOIN Enrollments e ON c.course_id = e.course_id "
                    + "WHERE e.enrollment_id IS NULL AND c.programme_id = "
                    + "(SELECT programme_id FROM Students WHERE student_id = ?)")) {
                repeatStatement.setInt(1, studentId);
                ResultSet repeatResultSet = repeatStatement.executeQuery();
                while (repeatResultSet.next()) {
                    modulesToRepeat.put(repeatResultSet.getInt("course_id"), repeatResultSet.getString("course_name"));
                }
            }
            // Format and append the report
            report.append("Student Report for ").append(studentName).append(" (Student ID: ").append(studentId).append("):\n");
            report.append("Programme: ").append(programmeName).append("\n\n");
            report.append("Modules Enrolled:\n");
            for (Map.Entry<Integer, String> entry : modulesEnrolled.entrySet()) {
                report.append("- ").append(entry.getValue()).append("\n");
            }
            report.append("\nModules Completed:\n");
            for (Map.Entry<Integer, String> entry : modulesCompleted.entrySet()) {
                report.append("- ").append(entry.getValue()).append("\n");
            }
            report.append("\nModules to Repeat:\n");
            for (Map.Entry<Integer, String> entry : modulesToRepeat.entrySet()) {
                report.append("- ").append(entry.getValue()).append("\n");
            }
        }
        return report.toString();
    }

    public String generateAllLecturersReport() throws SQLException {
    StringBuilder report = new StringBuilder();
    try (Connection connection = dbConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(
                 "SELECT "
                         + "l.lecturer_name AS Lecturer_Name, "
                         + "l.role AS Role, "
                         + "c.course_name AS Module_Name, "
                         + "COUNT(e.enrollment_id) AS Enrolled_Students, "
                         + "l.class_types AS Classes_Taught "
                         + "FROM "
                         + "Lecturers l "
                         + "INNER JOIN "
                         + "Courses c ON l.lecturer_id = c.lecturer_id "
                         + "LEFT JOIN "
                         + "Enrollments e ON c.course_id = e.course_id "
                         + "GROUP BY "
                         + "l.lecturer_id, l.lecturer_name, l.role, c.course_name")) {
        ResultSet resultSet = statement.executeQuery();
        report.append("Lecturer Report:\n");
        report.append(String.format("%-30s %-20s %-30s %-20s %-30s\n", "Lecturer Name", "Role", "Module Name", "Enrolled Students", "Classes Taught"));
        while (resultSet.next()) {
            report.append(String.format("%-30s %-20s %-30s %-20d %-30s\n",
                    resultSet.getString("Lecturer_Name"),
                    resultSet.getString("Role"),
                    resultSet.getString("Module_Name"),
                    resultSet.getInt("Enrolled_Students"),
                    resultSet.getString("Classes_Taught")));
        }
    } catch (SQLException e) {
        // Handle SQL exception
        System.err.println("An error occurred while generating the lecturer report: " + e.getMessage());
        throw e; // Re-throw the exception to be handled by the calling method
    }
    return report.toString();
}
   public String generateLecturerReport(String lecturerUsername) throws SQLException {
    StringBuilder reportBuilder = new StringBuilder();
    
    try (Connection connection = dbConnector.getConnection();
         PreparedStatement statement = connection.prepareStatement(
                 "SELECT l.lecturer_name AS Lecturer_Name, " +
                         "l.role AS Role, " +
                         "c.course_name AS Module_Name, " +
                         "COUNT(e.enrollment_id) AS Enrolled_Students, " +
                         "l.class_types AS Classes_Taught " +
                         "FROM Lecturers l " +
                         "INNER JOIN Courses c ON l.lecturer_id = c.lecturer_id " +
                         "LEFT JOIN Enrollments e ON c.course_id = e.course_id " +
                         "GROUP BY l.lecturer_id, l.lecturer_name, l.role, c.course_name")) {
        ResultSet resultSet = statement.executeQuery();
        reportBuilder.append("Lecturer Name,Role,Module Name,Enrolled Students,Classes Taught\n");
        while (resultSet.next()) {
            reportBuilder.append(String.format("%s,%s,%s,%d,%s\n",
                    resultSet.getString("Lecturer_Name"),
                    resultSet.getString("Role"),
                    resultSet.getString("Module_Name"),
                    resultSet.getInt("Enrolled_Students"),
                    resultSet.getString("Classes_Taught")));
        }
    } catch (SQLException e) {
        System.out.println("An error occurred while generating the report: " + e.getMessage());
    }

    return reportBuilder.toString();
}

public void generateLecturerReportInTxt(String reportData) {
    try (PrintWriter writer = new PrintWriter(new FileWriter("lecturer_report.txt"))) {
        writer.println("Lecturer Report:");
        writer.println(String.format("%-30s %-20s %-40s %-20s %-30s",
                "Lecturer Name", "Role", "Module Name", "Enrolled Students", "Classes Taught"));

        // Splitting the report data into lines
        String[] lines = reportData.split("\n");

        // Skipping the first line as it's already included in the header
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] fields = line.split(",");
            String formattedLine = String.format("%-30s %-20s %-40s %-20s %-30s",
                    fields[0], fields[1], fields[2], fields[3], fields[4]);
            writer.println(formattedLine);
        }

        System.out.println("Lecturer report saved to lecturer_report.txt");
    } catch (IOException e) {
        System.out.println("An error occurred while saving the report: " + e.getMessage());
    }
}

public void generateLecturerReportInCsv(String reportData) {
    try (PrintWriter writer = new PrintWriter(new FileWriter("lecturer_report.csv"))) {
        writer.println(reportData);
        System.out.println("Lecturer report saved to lecturer_report.csv");
    } catch (IOException e) {
        System.out.println("An error occurred while saving the report: " + e.getMessage());
    }
}

public void generateLecturerReportInConsole(String reportData) {
    // Print the header only once
    System.out.println("Lecturer Report:");
    System.out.printf("%-30s %-20s %-40s %-20s %-30s%n",
            "Lecturer Name", "Role", "Module Name", "Enrolled Students", "Classes Taught");
    
    // Splitting the report data into lines
    String[] lines = reportData.split("\n");

    // Start from index 1 to skip the header line
    for (int i = 1; i < lines.length; i++) {
        String[] fields = lines[i].split(",");
        System.out.printf("%-30s %-20s %-40s %-20s %-30s%n",
                fields[0], fields[1], fields[2], fields[3], fields[4]);
    }
}
}