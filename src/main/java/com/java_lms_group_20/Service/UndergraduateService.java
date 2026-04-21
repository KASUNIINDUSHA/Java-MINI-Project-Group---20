package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Undergraduate;
import com.java_lms_group_20.Repository.UndergraduateRepository;
import java.sql.SQLException;
import java.util.Optional;

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

    public Optional<Undergraduate> getByUserID(int userID) throws SQLException {
        return repository.findByUserID(userID);
    }

    public boolean updateOwnProfile(int userID, String contactNo, String profilePicture) throws SQLException {
        return repository.updateOwnProfile(userID, contactNo, profilePicture);
    }
}
