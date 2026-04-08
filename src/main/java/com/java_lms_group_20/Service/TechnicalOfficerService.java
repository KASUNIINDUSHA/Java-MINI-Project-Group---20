package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.TechnicalOfficer;
import com.java_lms_group_20.Repository.TechnicalOfficerRepository;
import java.sql.SQLException;

public class TechnicalOfficerService {
    private final TechnicalOfficerRepository repository = new TechnicalOfficerRepository();

    public boolean registerTechnicalOfficer(TechnicalOfficer officer) throws SQLException {
        return repository.save(officer);
    }

    public boolean updateTechnicalOfficer(TechnicalOfficer officer) throws SQLException {
        return repository.update(officer);
    }

    public boolean deleteTechnicalOfficer(String techOfficerID) throws SQLException {
        return repository.delete(techOfficerID);
    }
}