package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Undergraduate;
import com.java_lms_group_20.Repository.UndergraduateRepository;
import java.sql.SQLException;

public class UndergraduateService {
    private final UndergraduateRepository repository = new UndergraduateRepository();

    public boolean registerStudent(Undergraduate student) throws SQLException {
        return repository.save(student);
    }

    public boolean updateStudent(Undergraduate student) throws SQLException {
        return repository.update(student);
    }

    public boolean deleteStudent(String studentID) throws SQLException {
        return repository.delete(studentID);
    }
}