/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

/**
 *
 * @author grc29
 */
public class Course {
    private int courseId;
    private String courseName;
    private String programmeName;
    private String lecturerName;
    private String roomName;
    private int enrolledStudents;

    // Constructor, getters, and setters

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}