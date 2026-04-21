package com.java_lms_group_20.Model;

public class Marks {
    private String undergraduateId;
    private String courseCode;
    private double quiz1;
    private double quiz2;
    private double quiz3;
    private double assignment;
    private double project;
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

    public double getQuiz1() { return quiz1; }
    public void setQuiz1(double quiz1) { this.quiz1 = quiz1; }

    public double getQuiz2() { return quiz2; }
    public void setQuiz2(double quiz2) { this.quiz2 = quiz2; }

    public double getQuiz3() { return quiz3; }
    public void setQuiz3(double quiz3) { this.quiz3 = quiz3; }

    public double getAssignment() { return assignment; }
    public void setAssignment(double assignment) { this.assignment = assignment; }

    public double getProject() { return project; }
    public void setProject(double project) { this.project = project; }

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
