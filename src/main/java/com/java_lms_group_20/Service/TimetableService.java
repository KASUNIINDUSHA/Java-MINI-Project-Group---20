package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Timetable;
import com.java_lms_group_20.Repository.TimetableRepository;

import java.sql.SQLException;
import java.util.List;

public class TimetableService {
    private final TimetableRepository repository = new TimetableRepository();

    public List<Timetable> getAll() throws SQLException {
        return repository.findAll();
    }

    public List<Timetable> getByDepartment(String department) throws SQLException {
        return repository.findByDepartment(department);
    }

    public List<Timetable> getByStudentId(String studentId) throws SQLException {
        return repository.findByStudentId(studentId);
    }

    public boolean add(Timetable timetable) throws SQLException {
        return repository.save(timetable);
    }

    public boolean update(Timetable timetable) throws SQLException {
        return repository.update(timetable);
    }

    public boolean delete(int timetableId) throws SQLException {
        return repository.delete(timetableId);
    }
}
