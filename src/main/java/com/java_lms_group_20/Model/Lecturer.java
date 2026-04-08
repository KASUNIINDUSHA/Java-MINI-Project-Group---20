package com.java_lms_group_20.Model;

public class Lecturer extends User {
    private String lecturerID;
    private String qualifications;
    private String department;
    private String specialization;

    public Lecturer() { super(); }

    public String getLecturerID() { return lecturerID; }
    public void setLecturerID(String lecturerID) { this.lecturerID = lecturerID; }

    public String getQualifications() { return qualifications; }
    public void setQualifications(String qualifications) { this.qualifications = qualifications; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
}