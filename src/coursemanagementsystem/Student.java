/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.util.List;

/**
 *
 * @author grc29
 */
public class Student {
    private int studentId;
    private String studentName;
    private String programmeName;
    private String enrolledCourses;
    private String completedCourses;
    private String coursesToRepeat;
    private int enrolledModules;
    private int completedModules;
    private int repeatModules;

    public Student(int studentId, String studentName, String programmeName,
               String enrolledCourses, String completedCourses, String coursesToRepeat) {
    this.studentId = studentId;
    this.studentName = studentName;
    this.programmeName = programmeName;
    this.enrolledCourses = enrolledCourses;
    this.completedCourses = completedCourses;
    this.coursesToRepeat = coursesToRepeat;
}

    // Getters and setters
    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public String getEnrolledCourses() {
        return enrolledCourses;
    }

    public String getCompletedCourses() {
        return completedCourses;
    }

    public String getCoursesToRepeat() {
        return coursesToRepeat;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public void setEnrolledCourses(String enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public void setCompletedCourses(String completedCourses) {
        this.completedCourses = completedCourses;
    }

    public void setCoursesToRepeat(String coursesToRepeat) {
        this.coursesToRepeat = coursesToRepeat;
    }

    public int getEnrolledModules() {
        return enrolledModules;
    }

    public int getCompletedModules() {
        return completedModules;
    }

    public int getRepeatModules() {
        return repeatModules;
    }

    public void setEnrolledModules(int enrolledModules) {
        this.enrolledModules = enrolledModules;
    }

    public void setCompletedModules(int completedModules) {
        this.completedModules = completedModules;
    }

    public void setRepeatModules(int repeatModules) {
        this.repeatModules = repeatModules;
    }
}