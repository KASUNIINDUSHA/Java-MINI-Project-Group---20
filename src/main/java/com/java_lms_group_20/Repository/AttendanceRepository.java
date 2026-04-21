package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Attendance;
import com.java_lms_group_20.Util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {

    public List<Attendance> findByStudentId(String studentId) throws SQLException {
        List<Attendance> list = new ArrayList<>();
        // Using LIKE with % allows for searching a specific ID or all IDs if empty
        String sql = "SELECT * FROM attendance WHERE undergraduateId LIKE ? ORDER BY sessionDate DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + (studentId == null ? "" : studentId.trim()) + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Attendance a = new Attendance();
                a.setAttendanceID(rs.getInt("attendanceID"));
                a.setUndergraduateId(rs.getString("undergraduateId"));
                a.setCourseCode(rs.getString("courseCode"));
                a.setSessionType(rs.getString("sessionType"));
                a.setSessionDate(rs.getDate("sessionDate"));
                a.setStatus(rs.getString("status"));
                list.add(a);
            }
        }
        return list;
    }

    public boolean updateStatus(int attendanceID, String status) throws SQLException {
        String sql = "UPDATE attendance SET status = ? WHERE attendanceID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, attendanceID);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean save(Attendance attendance) throws SQLException {
        String sql = "INSERT INTO attendance (undergraduateId, courseCode, sessionType, sessionDate, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, attendance.getUndergraduateId());
            stmt.setString(2, attendance.getCourseCode());
            stmt.setString(3, attendance.getSessionType());
            stmt.setDate(4, attendance.getSessionDate());
            stmt.setString(5, attendance.getStatus());
            return stmt.executeUpdate() > 0;
        }
    }
}
