/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author grc29
 */
public class ReportManager {
    private DBConnector dbConnector;

    public ReportManager() {
        dbConnector = new DBConnector();
    }

    // Method to generate a Course Report
    public String generateCourseReport() {
        List<Course> courses = dbConnector.getAllCourses();
        // Construct the report string based on retrieved courses
        StringBuilder report = new StringBuilder("Course Report:\n");
        for (Course course : courses) {
            report.append("Course Name: ").append(course.getCourseName()).append("\n");
            report.append("Programme: ").append(course.getProgrammeName()).append("\n");
            report.append("Number of Students Enrolled: ").append(course.getEnrolledStudents()).append("\n");
            report.append("Lecturer: ").append(course.getLecturerName()).append("\n");
            report.append("Room: ").append(course.getRoomName()).append("\n\n");
        }
        return report.toString();
    }

    // Method to generate a Student Report
    public String generateStudentReport() {
        List<Student> students = dbConnector.getAllStudents();
        // Construct the report string based on retrieved students
        StringBuilder report = new StringBuilder("Student Report:\n");
        for (Student student : students) {
            report.append("Student Name: ").append(student.getStudentName()).append("\n");
            report.append("Student Number: ").append(student.getStudentId()).append("\n");
            report.append("Programme: ").append(student.getProgrammeName()).append("\n");
            report.append("Modules Enrolled: ").append(student.getEnrolledModules()).append("\n");
            report.append("Modules Completed: ").append(student.getCompletedModules()).append("\n");
            report.append("Modules to Repeat: ").append(student.getRepeatModules()).append("\n\n");
        }
        return report.toString();
    }

    // Method to generate a Lecturer Report
    public String generateLecturerReport() {
        List<Lecturer> lecturers = dbConnector.getAllLecturers();
        // Construct the report string based on retrieved lecturers
        StringBuilder report = new StringBuilder("Lecturer Report:\n");
        for (Lecturer lecturer : lecturers) {
            report.append("Lecturer Name: ").append(lecturer.getLecturerName()).append("\n");
            report.append("Role: ").append(lecturer.getRole()).append("\n");
            // Append other relevant lecturer information here
            report.append("\n");
        }
        return report.toString();
    }

    // Method to export report to a TXT file
    public void exportReportToTxt(String report, String filename) {
        try (FileWriter writer = new FileWriter(filename + ".txt")) {
            writer.write(report);
            System.out.println("Report exported to " + filename + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to export report to TXT file.");
        }
    }

    // Method to export report to a CSV file
    public void exportReportToCsv(String report, String filename) {
        try (FileWriter writer = new FileWriter(filename + ".csv")) {
            writer.write(report);
            System.out.println("Report exported to " + filename + ".csv");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to export report to CSV file.");
        }
    }
}