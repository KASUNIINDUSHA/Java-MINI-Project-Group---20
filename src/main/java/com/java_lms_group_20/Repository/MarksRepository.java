
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
                m.setQuiz1(rs.getDouble("quiz1"));
                m.setQuiz2(rs.getDouble("quiz2"));
                m.setQuiz3(rs.getDouble("quiz3"));
                m.setAssignment(rs.getDouble("assignment"));
                m.setProject(rs.getDouble("project"));
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

    public boolean saveOrUpdateMarks(Marks m) throws SQLException {
        String updateSql = "UPDATE marks SET quiz1=?, quiz2=?, quiz3=?, assignment=?, project=?, caMarks=?, midExam=?, endExam=?, finalMarks=?, grade=? " +
                "WHERE undergraduateId=? AND courseCode=?";
        String insertSql = "INSERT INTO marks (undergraduateId, courseCode, quiz1, quiz2, quiz3, assignment, project, caMarks, midExam, endExam, finalMarks, grade) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setDouble(1, m.getQuiz1());
            stmt.setDouble(2, m.getQuiz2());
            stmt.setDouble(3, m.getQuiz3());
            stmt.setDouble(4, m.getAssignment());
            stmt.setDouble(5, m.getProject());
            stmt.setDouble(6, m.getCaMarks());
            stmt.setDouble(7, m.getMidExam());
            stmt.setDouble(8, m.getEndExam());
            stmt.setDouble(9, m.getFinalMarks());
            stmt.setString(10, m.getGrade());
            stmt.setString(11, m.getUndergraduateId());
            stmt.setString(12, m.getCourseCode());

            int updated = stmt.executeUpdate();
            if (updated > 0) {
                return true;
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, m.getUndergraduateId());
                insertStmt.setString(2, m.getCourseCode());
                insertStmt.setDouble(3, m.getQuiz1());
                insertStmt.setDouble(4, m.getQuiz2());
                insertStmt.setDouble(5, m.getQuiz3());
                insertStmt.setDouble(6, m.getAssignment());
                insertStmt.setDouble(7, m.getProject());
                insertStmt.setDouble(8, m.getCaMarks());
                insertStmt.setDouble(9, m.getMidExam());
                insertStmt.setDouble(10, m.getEndExam());
                insertStmt.setDouble(11, m.getFinalMarks());
                insertStmt.setString(12, m.getGrade());
                return insertStmt.executeUpdate() > 0;
            }
        }
    }
}
