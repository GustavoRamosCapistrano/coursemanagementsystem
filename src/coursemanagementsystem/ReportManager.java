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

public void generateLecturerReport(String format, List<Lecturer> lecturers) throws SQLException {
    switch (format.toUpperCase()) {
        case "TXT":
            generateLecturerReportTXT(lecturers);
            break;
        case "CSV":
            generateLecturerReportCSV(lecturers);
            break;
        case "CONSOLE":
            generateLecturerReportConsole(lecturers);
            break;
        default:
            System.out.println("Invalid report format choice! Please choose either TXT, CSV, or CONSOLE.");
    }
}

public void generateLecturerReportTXT(List<Lecturer> lecturers) {
    try {
        FileWriter writer = new FileWriter("lecturer_report.txt");
        for (Lecturer lecturer : lecturers) {
            writer.write(String.format("Lecturer Name: %-40s\n", lecturer.getLecturerName()));
            writer.write(String.format("Role: %-40s\n", lecturer.getRole()));
            writer.write(String.format("Modules Teaching: %-40s\n", lecturer.getModulesTaught()));
            writer.write(String.format("Number of Students: %-40d\n", lecturer.getStudentCount()));
            writer.write(String.format("Types of Classes: %-40s\n\n", lecturer.getClassesTaught()));
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
        writer.write("Lecturer Name,Role,Modules Teaching,Number of Students,Types of Classes\n");
        for (Lecturer lecturer : lecturers) {
            writer.write(String.format("%s,%s,%s,%d,%s\n",
                    lecturer.getLecturerName(), lecturer.getRole(),
                    lecturer.getModulesTaught(), lecturer.getStudentCount(), lecturer.getClassesTaught()));
        }
        writer.close();
        System.out.println("Lecturer report generated successfully in CSV format.");
    } catch (IOException e) {
        System.out.println("An error occurred while generating the lecturer report.");
        e.printStackTrace();
    }
}
public void generateLecturerReportConsole(List<Lecturer> lecturers) {
    System.out.println("Lecturer Report:");

    for (Lecturer lecturer : lecturers) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Lecturer Name:     " + lecturer.getLecturerName());
        System.out.println("Role:              " + lecturer.getRole());

        System.out.print("Modules Teaching:  ");
        List<String> modulesTaught = lecturer.getModulesTaught();
        System.out.print("[");
        for (int i = 0; i < modulesTaught.size(); i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(modulesTaught.get(i));
        }
        System.out.println("]");

        System.out.println("Number of Students: " + lecturer.getStudentCount());

        System.out.print("Types of Classes:  ");
        List<String> classesTaught = lecturer.getClassesTaught();
        System.out.print("[");
        for (int i = 0; i < classesTaught.size(); i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(classesTaught.get(i));
        }
        System.out.println("]");

        System.out.println("-------------------------------------------------------");
    }

    System.out.println("Lecturer report displayed.");
}

   public void generateOwnLecturerReport(String format, Lecturer lecturer) {
    switch (format.toUpperCase()) {
        case "TXT":
            generateOwnLecturerReportTXT(lecturer);
            break;
        case "CSV":
            generateOwnLecturerReportCSV(lecturer);
            break;
        case "CONSOLE":
            generateOwnLecturerReportConsole(lecturer);
            break;
        default:
            System.out.println("Invalid report format choice! Please choose either TXT, CSV, or CONSOLE.");
    }
}

public void generateOwnLecturerReportTXT(Lecturer lecturer) {
    try (PrintWriter writer = new PrintWriter(new FileWriter("Ownlecturer_report.txt"))) {
        writer.println("Lecturer Report:");
        writer.println("Lecturer Name: " + lecturer.getLecturerName());
        writer.println("Role: " + lecturer.getRole());
        writer.println("Module Name: " + String.join(", ", lecturer.getModulesTaught()));
        writer.println("Enrolled Students: " + lecturer.getStudentCount());
        writer.println("Classes Taught: " + String.join(", ", lecturer.getClassesTaught()));

        System.out.println("Lecturer report saved to Ownlecturer_report.txt");
    } catch (IOException e) {
        System.out.println("An error occurred while saving the report: " + e.getMessage());
    }
}

public void generateOwnLecturerReportCSV(Lecturer lecturer) {
    try (PrintWriter writer = new PrintWriter(new FileWriter("Ownlecturer_report.csv"))) {
        writer.write("Lecturer Name,Role,Module Name,Enrolled Students,Classes Taught\n");
        String formattedLine = String.format("%s,%s,%s,%d,%s\n",
                lecturer.getLecturerName(), lecturer.getRole(),
                String.join(", ", lecturer.getModulesTaught()), lecturer.getStudentCount(), String.join(", ", lecturer.getClassesTaught()));
        writer.write(formattedLine);
        System.out.println("Lecturer report saved to Ownlecturer_report.csv");
    } catch (IOException e) {
        System.out.println("An error occurred while saving the report: " + e.getMessage());
    }
}

public void generateOwnLecturerReportConsole(Lecturer lecturer) {
    System.out.println("Lecturer Report:");

    System.out.println("Lecturer Name: " + lecturer.getLecturerName());
    System.out.println("Role: " + lecturer.getRole());
    System.out.println("Module Name: " + String.join(", ", lecturer.getModulesTaught()));
    System.out.println("Enrolled Students: " + lecturer.getStudentCount());
    System.out.println("Classes Taught: " + String.join(", ", lecturer.getClassesTaught()));

    System.out.println("Lecturer report displayed.");
}
   
    public String generateSeparator(int length) {
    StringBuilder separator = new StringBuilder();
    for (int i = 0; i < length; i++) {
        separator.append("-");
    }
    return separator.toString();
}}
