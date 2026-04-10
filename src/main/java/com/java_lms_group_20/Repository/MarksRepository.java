
package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Marks;
import com.java_lms_group_20.Util.DBConnection;
import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class MarksRepository {

    public List<Marks> findByStudentId(String studentId) throws SQLException {
        List<Marks> list = new ArrayList<>();
        String sql = "SELECT * FROM marks WHERE UPPER(undergraduateId) LIKE UPPER(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + studentId + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Marks m = new Marks();
                m.setUndergraduateId(rs.getString("undergraduateId"));
                m.setCourseCode(rs.getString("courseCode"));
                m.setCaMarks(rs.getDouble("caMarks"));
                m.setMidExam(rs.getDouble("midExam"));
                m.setEndExam(rs.getDouble("endExam"));
                m.setFinalMarks(rs.getDouble("finalMarks"));
                m.setGrade(rs.getString("grade"));
                list.add(m);
            }
        }
        return list;
    }

    public boolean updateMarks(Marks m) throws SQLException {
        String sql = "UPDATE marks SET caMarks=?, midExam=?, endExam=?, finalMarks=?, grade=? WHERE undergraduateId=? AND courseCode=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, m.getCaMarks());
            stmt.setDouble(2, m.getMidExam());
            stmt.setDouble(3, m.getEndExam());
            stmt.setDouble(4, m.getFinalMarks());
            stmt.setString(5, m.getGrade());
            stmt.setString(6, m.getUndergraduateId());
            stmt.setString(7, m.getCourseCode());
            return stmt.executeUpdate() > 0;
        }
    }
}