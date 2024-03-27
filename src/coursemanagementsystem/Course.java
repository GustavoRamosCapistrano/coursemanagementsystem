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

    private String courseName;
    private String programmeName;
    private String lecturerName;
    private String roomName;

    public Course(String courseName, String programmeName, String lecturerName, String roomName) {
        this.courseName = courseName;
        this.programmeName = programmeName;
        this.lecturerName = lecturerName;
        this.roomName = roomName;
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
}
