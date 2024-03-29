/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursemanagementsystem;

/*
 * This class represents a course in the course management system.
 * It contains information such as courseId, courseName, programmeName,
 * lecturerName, roomName, and number of enrolled students.
 */

public class Course {

    // Attributes
    private int courseId;
    private String courseName;
    private String programmeName;
    private String lecturerName;
    private String roomName;
    private int enrolledStudents;

    // Constructor
    public Course(String courseName, String programmeName, String lecturerName, String roomName, int enrolledStudents) {
        // Initialize attributes
        this.courseName = courseName;
        this.programmeName = programmeName;
        this.lecturerName = lecturerName;
        this.roomName = roomName;
        this.enrolledStudents = enrolledStudents;
    }

    // Getters and setters

    // Getter for courseId
    public int getCourseId() {
        return courseId;
    }

    // Setter for courseId
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    // Getter for courseName
    public String getCourseName() {
        return courseName;
    }

    // Setter for courseName
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Getter for programmeName
    public String getProgrammeName() {
        return programmeName;
    }

    // Setter for programmeName
    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    // Getter for lecturerName
    public String getLecturerName() {
        return lecturerName;
    }

    // Setter for lecturerName
    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    // Getter for roomName
    public String getRoomName() {
        return roomName;
    }

    // Setter for roomName
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    // Getter for enrolledStudents
    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    // Setter for enrolledStudents
    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}