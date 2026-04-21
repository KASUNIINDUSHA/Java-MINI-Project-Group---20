package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.LecturerEligibility;
import com.java_lms_group_20.Model.LecturerMarksGpa;
import com.java_lms_group_20.Model.Undergraduate;
import com.java_lms_group_20.Repository.LecturerUndergraduateRepository;

import java.sql.SQLException;
import java.util.List;

public class LecturerUndergraduateService {
    private final LecturerUndergraduateRepository repository = new LecturerUndergraduateRepository();

    public List<Undergraduate> getUndergraduateDetails(String keyword) throws SQLException {
        return repository.findUndergraduates(keyword);
    }

    public List<LecturerEligibility> getEligibilityRecords(String studentId) throws SQLException {
        return repository.findEligibility(studentId);
    }

    public List<LecturerMarksGpa> getMarksAndGpaRecords(String studentId) throws SQLException {
        return repository.findMarksAndGpa(studentId);
    }
}
