package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Attendance;
import com.java_lms_group_20.Repository.AttendanceRepository;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AttendanceService {
    private final AttendanceRepository repository = new AttendanceRepository();

    public List<Attendance> getAttendanceByStudentId(String studentId) throws SQLException {
        return repository.findByStudentId(studentId == null ? "" : studentId.trim());
    }

    public boolean changeAttendanceStatus(String studentId, String courseCode, Date date, String status) throws SQLException {
        return repository.updateStatus(studentId, courseCode, date, status);
    }
}