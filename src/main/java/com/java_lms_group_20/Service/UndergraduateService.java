
package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Undergraduate;
import com.java_lms_group_20.Repository.UndergraduateRepository;

public class UndergraduateService {
    private final UndergraduateRepository repository = new UndergraduateRepository();

    public void registerStudent(Undergraduate student) throws Exception {
        // Business Logic: Validation
        if (student.getUsername().length() < 4) {
            throw new Exception("Username must be at least 4 characters.");
        }
        if (student.getStudentID() == null || student.getStudentID().isEmpty()) {
            throw new Exception("Student ID is required.");
        }

        // Call Repository
        boolean success = repository.save(student);
        if (!success) {
            throw new Exception("Database error: Could not save student.");
        }
    }
}