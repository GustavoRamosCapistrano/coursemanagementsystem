/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a lecturer in the course management system.
 * Each lecturer has a name, role, list of modules taught, student count, and classes taught.
 * 
 * @author grc29
 */
public class Lecturer {

    private String lecturerName;
    private String role;
    private List<String> modulesTaught;
    private int studentCount;
    private List<String> classesTaught;

    /**
     * Constructs a lecturer object with the given parameters.
     * 
     * @param lecturerName The name of the lecturer.
     * @param role The role of the lecturer.
     * @param modulesTaught A string containing the modules taught by the lecturer (comma-separated).
     * @param studentCount The number of students taught by the lecturer.
     * @param classesTaught A string containing the types of classes taught by the lecturer (comma-separated).
     */
    public Lecturer(String lecturerName, String role, String modulesTaught, int studentCount, String classesTaught) {
        this.lecturerName = lecturerName;
        this.role = role;
        if (modulesTaught != null && !modulesTaught.isEmpty()) {
            this.modulesTaught = Arrays.asList(modulesTaught.split(", "));
        } else {
            this.modulesTaught = new ArrayList<>();
        }
        this.studentCount = studentCount;
        this.classesTaught = Arrays.asList(classesTaught.split(", "));
    }

    /**
     * Constructs a lecturer object with the given parameters.
     * 
     * @param lecturerName The name of the lecturer.
     * @param role The role of the lecturer.
     * @param modulesTaught A string containing the modules taught by the lecturer (comma-separated).
     * @param studentCount The number of students taught by the lecturer.
     * @param classesTaught A list containing the types of classes taught by the lecturer.
     */
    public Lecturer(String lecturerName, String role, String modulesTaught, int studentCount, List<String> classesTaught) {
        this.lecturerName = lecturerName;
        this.role = role;
        if (modulesTaught != null && !modulesTaught.isEmpty()) {
            this.modulesTaught = Arrays.asList(modulesTaught.split(", "));
        } else {
            this.modulesTaught = new ArrayList<>();
        }
        this.studentCount = studentCount;
        this.classesTaught = classesTaught;
    }

    /**
     * Retrieves the name of the lecturer.
     * 
     * @return The name of the lecturer.
     */
    public String getLecturerName() {
        return lecturerName;
    }

    /**
     * Sets the name of the lecturer.
     * 
     * @param lecturerName The name of the lecturer.
     */
    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    /**
     * Retrieves the role of the lecturer.
     * 
     * @return The role of the lecturer.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the lecturer.
     * 
     * @param role The role of the lecturer.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retrieves the list of modules taught by the lecturer.
     * 
     * @return The list of modules taught by the lecturer.
     */
    public List<String> getModulesTaught() {
        return modulesTaught;
    }

    /**
     * Sets the list of modules taught by the lecturer.
     * 
     * @param modulesTaught The list of modules taught by the lecturer.
     */
    public void setModulesTaught(List<String> modulesTaught) {
        this.modulesTaught = modulesTaught;
    }

    /**
     * Retrieves the number of students taught by the lecturer.
     * 
     * @return The number of students taught by the lecturer.
     */
    public int getStudentCount() {
        return studentCount;
    }

    /**
     * Sets the number of students taught by the lecturer.
     * 
     * @param studentCount The number of students taught by the lecturer.
     */
    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    /**
     * Retrieves the list of classes taught by the lecturer.
     * 
     * @return The list of classes taught by the lecturer.
     */
    public List<String> getClassesTaught() {
        return classesTaught;
    }

    /**
     * Sets the list of classes taught by the lecturer.
     * 
     * @param classesTaught The list of classes taught by the lecturer.
     */
    public void setClassesTaught(List<String> classesTaught) {
        this.classesTaught = classesTaught;
    }
}
