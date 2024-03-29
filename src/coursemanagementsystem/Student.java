/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

/*
 * This class represents a student in the course management system.
 * It contains information such as studentId, studentName, programmeName,
 * enrolledCourses, completedCourses, coursesToRepeat, enrolledModules,
 * completedModules, and repeatModules.
 */

public class Student {
    
    // Attributes
    private int studentId;
    private String studentName;
    private String programmeName;
    private String enrolledCourses;
    private String completedCourses;
    private String coursesToRepeat;
    private int enrolledModules;
    private int completedModules;
    private int repeatModules;

    // Constructor
    public Student(int studentId, String studentName, String programmeName,
               String enrolledCourses, String completedCourses, String coursesToRepeat) {
        // Initialize attributes
        this.studentId = studentId;
        this.studentName = studentName;
        this.programmeName = programmeName;
        this.enrolledCourses = enrolledCourses;
        this.completedCourses = completedCourses;
        this.coursesToRepeat = coursesToRepeat;
    }

    // Getters and setters
    
    // Getter for studentId
    public int getStudentId() {
        return studentId;
    }

    // Getter for studentName
    public String getStudentName() {
        return studentName;
    }

    // Getter for programmeName
    public String getProgrammeName() {
        return programmeName;
    }

    // Getter for enrolledCourses
    public String getEnrolledCourses() {
        return enrolledCourses;
    }

    // Getter for completedCourses
    public String getCompletedCourses() {
        return completedCourses;
    }

    // Getter for coursesToRepeat
    public String getCoursesToRepeat() {
        return coursesToRepeat;
    }

    // Setter for studentId
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // Setter for studentName
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Setter for programmeName
    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    // Setter for enrolledCourses
    public void setEnrolledCourses(String enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    // Setter for completedCourses
    public void setCompletedCourses(String completedCourses) {
        this.completedCourses = completedCourses;
    }

    // Setter for coursesToRepeat
    public void setCoursesToRepeat(String coursesToRepeat) {
        this.coursesToRepeat = coursesToRepeat;
    }

    // Getter for enrolledModules
    public int getEnrolledModules() {
        return enrolledModules;
    }

    // Getter for completedModules
    public int getCompletedModules() {
        return completedModules;
    }

    // Getter for repeatModules
    public int getRepeatModules() {
        return repeatModules;
    }

    // Setter for enrolledModules
    public void setEnrolledModules(int enrolledModules) {
        this.enrolledModules = enrolledModules;
    }

    // Setter for completedModules
    public void setCompletedModules(int completedModules) {
        this.completedModules = completedModules;
    }

    // Setter for repeatModules
    public void setRepeatModules(int repeatModules) {
        this.repeatModules = repeatModules;
    }
}