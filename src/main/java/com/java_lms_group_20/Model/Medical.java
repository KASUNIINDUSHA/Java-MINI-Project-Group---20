package com.java_lms_group_20.Model;

import java.sql.Date;

public class Medical {
    private String undergraduateId;
    private String description;
    private Date validFrom;
    private Date validTo;
    private String status;

    public Medical() {}

    public String getUndergraduateId() { return undergraduateId; }
    public void setUndergraduateId(String undergraduateId) { this.undergraduateId = undergraduateId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getValidFrom() { return validFrom; }
    public void setValidFrom(Date validFrom) { this.validFrom = validFrom; }

    public Date getValidTo() { return validTo; }
    public void setValidTo(Date validTo) { this.validTo = validTo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}