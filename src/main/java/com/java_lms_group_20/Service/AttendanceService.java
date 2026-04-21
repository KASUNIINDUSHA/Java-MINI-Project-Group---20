package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Attendance;
import com.java_lms_group_20.Model.AttendanceSummary;
import com.java_lms_group_20.Repository.AttendanceRepository;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AttendanceService {
    private final AttendanceRepository repository = new AttendanceRepository();

    // Used by Admin
    public List<Attendance> getRawAttendanceByStudentId(String studentId) throws SQLException {
        return repository.findByStudentId(studentId == null ? "" : studentId.trim());
    }

    // Logic for Student Summary (Subject-wise %)
    public List<AttendanceSummary> getStudentAttendanceSummary(String studentId) throws SQLException {
        List<Attendance> rawData = repository.findByStudentId(studentId);

        // Grouping by Course Code in Java
        Map<String, List<Attendance>> grouped = rawData.stream()
                .collect(Collectors.groupingBy(Attendance::getCourseCode));

        List<AttendanceSummary> summaries = new ArrayList<>();
        for (Map.Entry<String, List<Attendance>> entry : grouped.entrySet()) {
            String code = entry.getKey();
            List<Attendance> sessions = entry.getValue();

            int total = sessions.size();
            int present = (int) sessions.stream()
                    .filter(a -> "Present".equalsIgnoreCase(a.getStatus()))
                    .count();

            summaries.add(new AttendanceSummary(code, total, present));
        }
        return summaries;
    }

    public boolean changeAttendanceStatus(int attendanceID, String status) throws SQLException {
        return repository.updateStatus(attendanceID, status);
    }

    public boolean addAttendance(Attendance attendance) throws SQLException {
        return repository.save(attendance);
    }
}
