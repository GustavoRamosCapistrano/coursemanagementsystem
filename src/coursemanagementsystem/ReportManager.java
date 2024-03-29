/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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

   public void generateCourseReport(String format, List<Course> courses) throws SQLException{
       switch (format.toUpperCase()) {
           case "TXT":
               generateCourseReportTXT(courses);
               break;
           case "CSV":
               generateCourseReportCSV(courses);
               break;
           case "CONSOLE":
               generateCourseReportConsole(courses);
               break;
           default:
               System.out.println("Invalid report format choice! Please choose either TXT, CSV, or CONSOLE.");
       }
}

public void generateCourseReportTXT(List<Course> courses) throws SQLException {
    try {
        // Specify the file path for the TXT report
        String filePath = "course_report.txt";

        // Open FileWriter and BufferedWriter
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // Write column headers
        bufferedWriter.write(String.format("%-60s | %-30s | %-30s | %-30s | %-20s%n",
                "Course Name", "Programme Name", "Lecturer Name", "Room Name", "Enrolled Students"));
        bufferedWriter.newLine();

        // Write separator
        bufferedWriter.write(generateSeparator(60) + " | " + generateSeparator(30) + " | " +
                generateSeparator(30) + " | " + generateSeparator(30) + " | " +
                generateSeparator(20));
        bufferedWriter.newLine();

        // Write each course's information
        for (Course course : courses) {
            bufferedWriter.write(String.format("%-60s | %-30s | %-30s | %-30s | %-20d%n",
                    course.getCourseName(), course.getProgrammeName(), course.getLecturerName(),
                    course.getRoomName(), course.getEnrolledStudents()));
        }

        // Close BufferedWriter
        bufferedWriter.close();

        System.out.println("Course report generated successfully as TXT file.");

    } catch (IOException e) {
        System.out.println("An error occurred while generating the course report.");
        e.printStackTrace();
    }
}

public void generateCourseReportCSV(List<Course> courses) throws SQLException{
    try {
        // Specify the file path for the CSV report
        String filePath = "course_report.csv";

        // Open FileWriter and BufferedWriter
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // Write column headers
        bufferedWriter.write("Course Name,Programme Name,Lecturer Name,Room Name,Enrolled Students");
        bufferedWriter.newLine();

        // Write each course's information
        for (Course course : courses) {
            bufferedWriter.write(course.getCourseName() + ","
                    + course.getProgrammeName() + ","
                    + course.getLecturerName() + ","
                    + course.getRoomName() + ","
                    + course.getEnrolledStudents());
            bufferedWriter.newLine();
        }

        // Close BufferedWriter
        bufferedWriter.close();

        System.out.println("Course report generated successfully as CSV file.");

    } catch (IOException e) {
        System.out.println("An error occurred while generating the course report.");
        e.printStackTrace();
    }
}

public void generateCourseReportConsole(List<Course> courses) throws SQLException {
    // Write column headers to console
    System.out.printf("%-60s | %-30s | %-30s | %-30s | %-20s%n",
            "Course Name", "Programme Name", "Lecturer Name", "Room Name", "Enrolled Students");

    // Write separator
    System.out.println(generateSeparator(60) + " | " + generateSeparator(30) + " | " +
                       generateSeparator(30) + " | " + generateSeparator(30) + " | " +
                       generateSeparator(20));

    // Write each row of the report to console
    for (Course course : courses) {
        System.out.printf("%-60s | %-30s | %-30s | %-30s | %-20d%n",
                course.getCourseName(), course.getProgrammeName(), course.getLecturerName(),
                course.getRoomName(), course.getEnrolledStudents());
    }

    System.out.println("Course report printed to console.");
}

   public void generateStudentReport(String format, List<String> studentsDetails) {
    switch (format.toUpperCase()) {
        case "TXT":
            generateStudentReportTXT(studentsDetails);
            break;
        case "CSV":
            generateStudentReportCSV(studentsDetails);
            break;
        case "CONSOLE":
            generateStudentReportConsole(studentsDetails);
            break;
        default:
            System.out.println("Invalid report format choice! Please choose either TXT, CSV, or CONSOLE.");
    }
}

public void generateStudentReportTXT(List<String> studentDetails) {
    try {
        // Specify the file path for the TXT report
        String filePath = "student_report.txt";

        // Open FileWriter and BufferedWriter
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // Write column headers
        bufferedWriter.write(String.format("%-40s | %-15s | %-30s | %-40s | %-50s | %-30s%n",
                "Student Name", "Student Number", "Programme", "Enrolled Modules", "Completed Modules", "Modules to Repeat"));
        bufferedWriter.newLine();

        // Write separator
        bufferedWriter.write(generateSeparator(40) + " | " + generateSeparator(15) + " | " +
                generateSeparator(30) + " | " + generateSeparator(40) + " | " +
                generateSeparator(50) + " | " + generateSeparator(30));
        bufferedWriter.newLine();

        // Write each student's information
        for (String studentDetail : studentDetails) {
            String[] details = studentDetail.split("\t");
            bufferedWriter.write(String.format("%-40s | %-15s | %-30s | %-40s | %-50s | %-30s%n",
                    details[0], details[1], details[2], details[3], details[4], details[5]));
        }

        // Close BufferedWriter
        bufferedWriter.close();

        System.out.println("Student report generated successfully as TXT file.");

    } catch (IOException e) {
        System.out.println("An error occurred while generating the student report.");
        e.printStackTrace();
    }
}

public void generateStudentReportCSV(List<String> studentDetails) {
    try {
        // Specify the file path for the CSV report
        String filePath = "student_report.csv";

        // Open FileWriter and BufferedWriter
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // Write column headers
        bufferedWriter.write("Student Name,Student Number,Programme,Enrolled Modules,Completed Modules,Modules to Repeat\n");

        // Write each student's information
        for (String studentDetail : studentDetails) {
            bufferedWriter.write(studentDetail + "\n");
        }

        // Close BufferedWriter
        bufferedWriter.close();

        System.out.println("Student report generated successfully as CSV file.");

    } catch (IOException e) {
        System.out.println("An error occurred while generating the student report.");
        e.printStackTrace();
    }
}

public void generateStudentReportConsole(List<String> studentDetails) {
    // Write column headers to console
    System.out.printf("%-40s | %-15s | %-30s | %-40s | %-50s | %-30s%n",
            "Student Name", "Student Number", "Programme", "Enrolled Modules", "Completed Modules", "Modules to Repeat");

    // Write separator
    System.out.println(generateSeparator(40) + " | " + generateSeparator(15) + " | " +
                       generateSeparator(30) + " | " + generateSeparator(40) + " | " +
                       generateSeparator(50) + " | " + generateSeparator(30));

    // Write each student's information to console
    for (String studentDetail : studentDetails) {
        String[] details = studentDetail.split("\t");
        System.out.printf("%-40s | %-15s | %-30s | %-40s | %-50s | %-30s%n",
                details[0], details[1], details[2], details[3], details[4], details[5]);
    }

    System.out.println("Student report printed to console.");
}
//
//    public void generateLecturerReport(String format) throws SQLException {
//        String query = "SELECT lecturer_name, role, modules_taught, student_count, classes_taught "
//                + "FROM Lecturers";
//        List<Lecturer> lecturers = dbConnector.executeQueryForLecturers(query);
//        // Code to generate report based on the format
//        if ("TXT".equalsIgnoreCase(format)) {
//            generateLecturerReportTXT(lecturers); // Call the method with the lecturers list
//        } else if ("CSV".equalsIgnoreCase(format)) {
//            generateLecturerReportCSV(lecturers);
//        } else {
//            System.out.println("Invalid report format choice! Please choose either TXT or CSV.");
//        }
//    }

    public void generateLecturerReportTXT(List<Lecturer> lecturers) {
        try {
            FileWriter writer = new FileWriter("lecturer_report.txt");
            for (Lecturer lecturer : lecturers) {
                writer.write("Lecturer Name: " + lecturer.getLecturerName() + "\n");
                writer.write("Role: " + lecturer.getRole() + "\n");
                writer.write("Modules Teaching: " + lecturer.getModulesTaught() + "\n");
                writer.write("Number of Students: " + lecturer.getStudentCount() + "\n");
                writer.write("Types of Classes: " + lecturer.getClassesTaught() + "\n\n");
            }
            writer.close();
            System.out.println("Lecturer report generated successfully in TXT format.");
        } catch (IOException e) {
            System.out.println("An error occurred while generating the lecturer report.");
            e.printStackTrace();
        }
    }

    public void generateLecturerReportCSV(List<Lecturer> lecturers) {
        try {
            FileWriter writer = new FileWriter("lecturer_report.csv");
            writer.write("Lecturer Name, Role, Modules Teaching, Number of Students, Types of Classes\n");
            for (Lecturer lecturer : lecturers) {
                writer.write(lecturer.getLecturerName() + "," + lecturer.getRole() + ","
                        + lecturer.getModulesTaught() + "," + lecturer.getStudentCount() + ","
                        + lecturer.getClassesTaught() + "\n");
            }
            writer.close();
            System.out.println("Lecturer report generated successfully in CSV format.");
        } catch (IOException e) {
            System.out.println("An error occurred while generating the lecturer report.");
            e.printStackTrace();
        }
    }

    public void displayLecturerReport(List<Lecturer> lecturers) {
        System.out.println("Lecturer Report:");
        System.out.println("-------------------------------------------------------");
        for (Lecturer lecturer : lecturers) {
            System.out.println("Lecturer Name: " + lecturer.getLecturerName());
            System.out.println("Role: " + lecturer.getRole());
            System.out.println("Modules Teaching: " + lecturer.getModulesTaught());
            System.out.println("Number of Students: " + lecturer.getStudentCount());
            System.out.println("Types of Classes: " + lecturer.getClassesTaught());
            System.out.println("-------------------------------------------------------");
        }
    }

    public String generateOwnLecturerReport(String lecturerUsername) throws SQLException {
        StringBuilder reportBuilder = new StringBuilder();

        try ( Connection connection = dbConnector.getConnection();  PreparedStatement statement = connection.prepareStatement(
                "SELECT l.lecturer_name AS Lecturer_Name, "
                + "l.role AS Role, "
                + "c.course_name AS Module_Name, "
                + "COUNT(e.enrollment_id) AS Enrolled_Students, "
                + "l.class_types AS Classes_Taught "
                + "FROM Lecturers l "
                + "INNER JOIN Courses c ON l.lecturer_id = c.lecturer_id "
                + "LEFT JOIN Enrollments e ON c.course_id = e.course_id "
                + "GROUP BY l.lecturer_id, l.lecturer_name, l.role, c.course_name")) {
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

    public void generateOwnLecturerReportInTxt(String reportData) {
        try ( PrintWriter writer = new PrintWriter(new FileWriter("lecturer_report.txt"))) {
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

    public void generateOwnLecturerReportInCsv(String reportData) {
        try ( PrintWriter writer = new PrintWriter(new FileWriter("lecturer_report.csv"))) {
            writer.println(reportData);
            System.out.println("Lecturer report saved to lecturer_report.csv");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the report: " + e.getMessage());
        }
    }

    public void generateOwnLecturerReportInConsole(String reportData) {
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

    public void generateOwnReportInConsoleForString(String reportData, String reportType) {
        // Print the header
        System.out.println(reportType + " Report:");

        // Splitting the report data into lines
        String[] lines = reportData.split("\n");

        // Start from index 1 to skip the header line
        for (int i = 1; i < lines.length; i++) {
            String[] fields = lines[i].split(",");
            for (String field : fields) {
                System.out.print(field.trim() + "   "); // Adjust spacing as needed
            }
            System.out.println(); // Move to the next line after printing all fields
        }
    }

    public void generateReportInCsvForString(String reportData, String reportType) {
        try ( PrintWriter writer = new PrintWriter(new FileWriter(reportType + "_report.csv"))) {
            writer.println(reportData);
            System.out.println(reportType + " report saved to " + reportType + "_report.csv");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the " + reportType + " report: " + e.getMessage());
        }
    }

    public void generateReportInTxtForString(String reportData, String reportType) {
        try ( PrintWriter writer = new PrintWriter(new FileWriter(reportType + "_report.txt"))) {
            writer.println(reportType + " Report:");

            // Splitting the report data into lines
            String[] lines = reportData.split("\n");

            // Start from index 1 to skip the header line
            for (int i = 1; i < lines.length; i++) {
                writer.println(lines[i]);
            }

            System.out.println(reportType + " report saved to " + reportType + "_report.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the " + reportType + " report: " + e.getMessage());
        }
    }
    public String generateSeparator(int length) {
    StringBuilder separator = new StringBuilder();
    for (int i = 0; i < length; i++) {
        separator.append("-");
    }
    return separator.toString();
}}
