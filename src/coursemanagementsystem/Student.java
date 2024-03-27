/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

/**
 *
 * @author grc29
 */
public class Student {

    private String studentName;
    private String programmeName;
    private int enrolledModules;
    private int completedModules;
    private int repeatModules;

    public Student(String studentName, String programmeName, int enrolledModules, int completedModules, int repeatModules) {
        this.studentName = studentName;
        this.programmeName = programmeName;
        this.enrolledModules = enrolledModules;
        this.completedModules = completedModules;
        this.repeatModules = repeatModules;
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
}
