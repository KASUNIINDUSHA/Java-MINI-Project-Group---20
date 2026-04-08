package com.java_lms_group_20.Model;

public class Undergraduate extends User {

    private String studentID;
    private String degreeProgram;
    private int level;
    private double gpa;

    public Undergraduate() {
    }

    public Undergraduate(int userID, String username, String password,
                         String firstName, String lastName,
                         String email, String contactNo, String profilePicture,
                         String studentID, String degreeProgram, int level, double gpa) {

        super(userID, username, password, firstName, lastName, email, contactNo, profilePicture);
        this.studentID = studentID;
        this.degreeProgram = degreeProgram;
        this.level = level;
        this.gpa = gpa;
    }

    public Undergraduate(int userID, String username, String password,
                         String firstName, String lastName,
                         String email, String contactNo,
                         String studentID, String degreeProgram) {

        super(userID, username, password, firstName, lastName, email, contactNo, null);
        this.studentID = studentID;
        this.degreeProgram = degreeProgram;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getDegreeProgram() {
        return degreeProgram;
    }

    public void setDegreeProgram(String degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}