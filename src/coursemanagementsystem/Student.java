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
    private int enrolledModules;
    private int completedModules;
    private int repeatModules;

    public Student(int studentId, String studentName, String programmeName, int enrolledModules, int completedModules, int repeatModules) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.programmeName = programmeName;
        this.enrolledModules = enrolledModules;
        this.completedModules = completedModules;
        this.repeatModules = repeatModules;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getProgrammeName() {
        return programmeName;
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

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
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
