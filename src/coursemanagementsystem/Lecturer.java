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
public class Lecturer {

    private int lecturerId;
    private String lecturerName;
    private String role;
    private List<String> modulesTaught;
    private int studentCount;
    private List<String> classesTaught;

    public int getLecturerId() {
        return lecturerId;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public String getRole() {
        return role;
    }

    public List<String> getModulesTaught() {
        return modulesTaught;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public List<String> getClassesTaught() {
        return classesTaught;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setModulesTaught(List<String> modulesTaught) {
        this.modulesTaught = modulesTaught;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public void setClassesTaught(List<String> classesTaught) {
        this.classesTaught = classesTaught;
    }
}
