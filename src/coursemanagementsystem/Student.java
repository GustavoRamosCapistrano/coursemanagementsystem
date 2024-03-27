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
    private List<String> enrolledModules;
    private List<String> completedModules;
    private List<String> repeatModules;

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public List<String> getEnrolledModules() {
        return enrolledModules;
    }

    public List<String> getCompletedModules() {
        return completedModules;
    }

    public List<String> getRepeatModules() {
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

    public void setEnrolledModules(List<String> enrolledModules) {
        this.enrolledModules = enrolledModules;
    }

    public void setCompletedModules(List<String> completedModules) {
        this.completedModules = completedModules;
    }

    public void setRepeatModules(List<String> repeatModules) {
        this.repeatModules = repeatModules;
    }
}