/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author grc29
 */
public class Lecturer {

    private String lecturerName;
    private String role;
    private List<String> modulesTaught;
    private int studentCount;
    private List<String> classesTaught;

    public Lecturer(String lecturerName, String role, String modulesTaught, int studentCount, String classesTaught) {
        this.lecturerName = lecturerName;
        this.role = role;
        this.modulesTaught = Arrays.asList(modulesTaught.split(", "));
        this.studentCount = studentCount;
        this.classesTaught = Arrays.asList(classesTaught.split(", "));
    }


    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getModulesTaught() {
        return modulesTaught;
    }

    public void setModulesTaught(List<String> modulesTaught) {
        this.modulesTaught = modulesTaught;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public List<String> getClassesTaught() {
        return classesTaught;
    }

    public void setClassesTaught(List<String> classesTaught) {
        this.classesTaught = classesTaught;
    }
}