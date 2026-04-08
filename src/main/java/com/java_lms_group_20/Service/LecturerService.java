package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Lecturer;
import com.java_lms_group_20.Repository.LecturerRepository;
import java.sql.SQLException;

public class LecturerService {

    private final LecturerRepository repository = new LecturerRepository();

    // REGISTER
    public boolean registerLecturer(Lecturer lecturer) throws Exception {
        // Validation logic
        if (lecturer.getUsername() == null || lecturer.getUsername().length() < 4) {
            throw new Exception("Username must be at least 4 characters.");
        }
        if (lecturer.getLecturerID() == null || !lecturer.getLecturerID().startsWith("LC-")) {
            throw new Exception("Lecturer ID must follow the LC-XXXX format.");
        }

        return repository.save(lecturer);
    }

    // UPDATE
    public boolean updateLecturer(Lecturer lecturer) throws SQLException {
        // You could add validation here to ensure mandatory fields aren't blank
        return repository.update(lecturer);
    }

    // DELETE
    public boolean deleteLecturer(String lecturerID) throws SQLException {
        return repository.delete(lecturerID);
    }
}