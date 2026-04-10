package com.java_lms_group_20.Model;

import java.sql.Date;

public class Attendance {
    private String undergraduateId;
    private String courseCode;
    private Date sessionDate;
    private String status;

    public Attendance() {}

    public String getUndergraduateId() { return undergraduateId; }
    public void setUndergraduateId(String undergraduateId) { this.undergraduateId = undergraduateId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public Date getSessionDate() { return sessionDate; }
    public void setSessionDate(Date sessionDate) { this.sessionDate = sessionDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}