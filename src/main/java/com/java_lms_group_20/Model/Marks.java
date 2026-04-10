package com.java_lms_group_20.Model;

public class Marks {
    private String undergraduateId;
    private String courseCode;
    private double caMarks;
    private double midExam;
    private double endExam;
    private double finalMarks;
    private String grade;

    public Marks() {}

    public String getUndergraduateId() { return undergraduateId; }
    public void setUndergraduateId(String undergraduateId) { this.undergraduateId = undergraduateId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public double getCaMarks() { return caMarks; }
    public void setCaMarks(double caMarks) { this.caMarks = caMarks; }

    public double getMidExam() { return midExam; }
    public void setMidExam(double midExam) { this.midExam = midExam; }

    public double getEndExam() { return endExam; }
    public void setEndExam(double endExam) { this.endExam = endExam; }

    public double getFinalMarks() { return finalMarks; }
    public void setFinalMarks(double finalMarks) { this.finalMarks = finalMarks; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}