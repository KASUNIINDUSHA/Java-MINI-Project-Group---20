package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Medical;
import com.java_lms_group_20.Repository.MedicalRepository;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MedicalService {
    private final MedicalRepository repository = new MedicalRepository();

    public List<Medical> getMedicalsByStudentId(String studentId) throws SQLException {
        // If empty, fetch everything; otherwise search specific ID
        String searchStr = (studentId == null) ? "" : studentId.trim();
        return repository.findByStudentId(searchStr);
    }

    public boolean processMedicalStatus(String studentId, Date fromDate, String newStatus) throws SQLException {
        if (studentId == null || fromDate == null) return false;
        return repository.updateStatus(studentId, fromDate, newStatus);
    }

    public boolean addMedical(Medical medical) throws SQLException {
        if (medical == null || medical.getUndergraduateId() == null) return false;
        return repository.save(medical);
    }
}
