package com.example.model;

import java.util.Date;

public class Medical {

    private int medicalId;
    private String studentId;
    private Date date;
    private String reason;
    private String status; // Approved / Pending / Rejected

    public Medical() {}

    public Medical(int medicalId, String studentId, Date date, String reason, String status) {
        this.medicalId = medicalId;
        this.studentId = studentId;
        this.date = date;
        this.reason = reason;
        this.status = status;
    }

    public int getMedicalId() {
        return medicalId;
    }

    public void setMedicalId(int medicalId) {
        this.medicalId = medicalId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}