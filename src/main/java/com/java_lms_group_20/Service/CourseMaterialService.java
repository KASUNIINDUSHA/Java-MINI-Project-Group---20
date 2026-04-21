package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.CourseMaterial;
import com.java_lms_group_20.Repository.CourseMaterialRepository;

import java.sql.SQLException;
import java.util.List;

public class CourseMaterialService {
    private final CourseMaterialRepository repository = new CourseMaterialRepository();

    public List<CourseMaterial> getByCourseCode(String courseCode) throws SQLException {
        return repository.findByCourseCode(courseCode);
    }

    public boolean add(CourseMaterial material) throws SQLException {
        return repository.save(material);
    }

    public boolean update(CourseMaterial material) throws SQLException {
        return repository.update(material);
    }

    public boolean delete(int materialId) throws SQLException {
        return repository.delete(materialId);
    }
}
