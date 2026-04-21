package com.java_lms_group_20.Model;

public class LecturerEligibility {
    private String undergraduateId;
    private String courseCode;
    private double attendancePercentage;
    private String eligibilityStatus;

    public String getUndergraduateId() {
        return undergraduateId;
    }

    public void setUndergraduateId(String undergraduateId) {
        this.undergraduateId = undergraduateId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public String getEligibilityStatus() {
        return eligibilityStatus;
    }

    public void setEligibilityStatus(String eligibilityStatus) {
        this.eligibilityStatus = eligibilityStatus;
    }
}
