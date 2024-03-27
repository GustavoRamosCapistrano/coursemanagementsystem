/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

/**
 *
 * @author grc29
 */
public class Lecturer {

    private String lecturerName;
    private String role;
    private int modulesTaught;
    private int studentCount;
    private String classTypes;

    public Lecturer(String lecturerName, String role, int modulesTaught, int studentCount, String classTypes) {
        this.lecturerName = lecturerName;
        this.role = role;
        this.modulesTaught = modulesTaught;
        this.studentCount = studentCount;
        this.classTypes = classTypes;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public String getRole() {
        return role;
    }

    public int getModulesTaught() {
        return modulesTaught;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public String getClassTypes() {
        return classTypes;
    }
}
