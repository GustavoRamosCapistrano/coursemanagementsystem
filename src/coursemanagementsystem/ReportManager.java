/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

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
        StringBuilder report = new StringBuilder();
        List<Course> courses = dbConnector.getAllCourses();
        report.append("Course Report:\n");
        report.append(String.format("%-30s %-30s %-30s %-30s\n", "Course Name", "Programme", "Lecturer", "Room"));

        for (Course course : courses) {
            report.append(String.format("%-30s %-30s %-30s %-30s\n", course.getCourseName(), course.getProgrammeName(), course.getLecturerName(), course.getRoomName()));
        }

        return report.toString();
    }

    // Method to generate a Student Report
    public String generateStudentReport() {
        StringBuilder report = new StringBuilder();
        List<Student> students = dbConnector.getAllStudents();
        report.append("Student Report:\n");
        report.append(String.format("%-30s %-30s %-30s %-30s %-30s\n", "Student Name", "Programme", "Enrolled Modules", "Completed Modules", "Repeat Modules"));

        for (Student student : students) {
            report.append(String.format("%-30s %-30s %-30s %-30s %-30s\n", student.getStudentName(), student.getProgrammeName(), student.getEnrolledModules(), student.getCompletedModules(), student.getRepeatModules()));
        }

        return report.toString();
    }

    // Method to generate a Lecturer Report
    public String generateLecturerReport(String lecturerName) {
        StringBuilder report = new StringBuilder();
        List<Lecturer> lecturers = dbConnector.getAllLecturers();
        report.append("Lecturer Report:\n");
        report.append(String.format("%-30s %-30s %-30s %-30s %-30s\n", "Lecturer Name", "Role", "Modules Taught", "Student Count", "Class Types"));

        for (Lecturer lecturer : lecturers) {
            if (lecturer.getLecturerName().equals(lecturerName)) {
                report.append(String.format("%-30s %-30s %-30s %-30s %-30s\n", lecturer.getLecturerName(), lecturer.getRole(), lecturer.getModulesTaught(), lecturer.getStudentCount(), lecturer.getClassTypes()));
                break;
            }
        }

        return report.toString();
    }

    // Method to close database connection
    public void closeConnection() {
        dbConnector.closeConnection();
    }
}