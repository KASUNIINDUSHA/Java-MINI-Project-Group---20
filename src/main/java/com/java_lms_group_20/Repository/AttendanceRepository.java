package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Attendance;
import com.java_lms_group_20.Util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {

    public List<Attendance> findByStudentId(String studentId) throws SQLException {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE UPPER(undergraduateId) LIKE UPPER(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + studentId + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance();
                a.setUndergraduateId(rs.getString("undergraduateId"));
                a.setCourseCode(rs.getString("courseCode"));
                a.setSessionDate(rs.getDate("sessionDate"));
                a.setStatus(rs.getString("status"));
                list.add(a);
            }
        }
        return list;
    }

    public boolean updateStatus(String studentId, String courseCode, Date date, String status) throws SQLException {
        String sql = "UPDATE attendance SET status = ? WHERE undergraduateId = ? AND courseCode = ? AND sessionDate = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, studentId);
            stmt.setString(3, courseCode);
            stmt.setDate(4, date);
            return stmt.executeUpdate() > 0;
        }
    }
}