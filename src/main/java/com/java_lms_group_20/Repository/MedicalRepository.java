package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Medical;
import com.java_lms_group_20.Util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRepository {

    public List<Medical> findByStudentId(String studentId) throws SQLException {
        List<Medical> list = new ArrayList<>();
        // Robust search to handle slashes and case sensitivity
        String sql = "SELECT * FROM medical WHERE UPPER(undergraduateId) LIKE UPPER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + studentId + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToMedical(rs));
            }
        }
        return list;
    }

    public boolean updateStatus(String studentId, Date fromDate, String newStatus) throws SQLException {
        String sql = "UPDATE medical SET status = ? WHERE undergraduateId = ? AND validFrom = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setString(2, studentId);
            stmt.setDate(3, fromDate);

            return stmt.executeUpdate() > 0;
        }
    }

    private Medical mapResultSetToMedical(ResultSet rs) throws SQLException {
        Medical m = new Medical();
        m.setUndergraduateId(rs.getString("undergraduateId"));
        m.setDescription(rs.getString("description"));
        m.setValidFrom(rs.getDate("validFrom"));
        m.setValidTo(rs.getDate("validTo"));
        m.setStatus(rs.getString("status"));
        return m;
    }
}