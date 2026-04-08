package com.java_lms_group_20.Model;

public class TechnicalOfficer extends User {
    private String techOfficerID;
    private String department;
    private String technicalSkills;

    public TechnicalOfficer() { super(); }

    public String getTechOfficerID() { return techOfficerID; }
    public void setTechOfficerID(String techOfficerID) { this.techOfficerID = techOfficerID; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getTechnicalSkills() { return technicalSkills; }
    public void setTechnicalSkills(String technicalSkills) { this.technicalSkills = technicalSkills; }
}