package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.LecturerEligibility;
import com.java_lms_group_20.Model.LecturerMarksGpa;
import com.java_lms_group_20.Model.Undergraduate;
import com.java_lms_group_20.Util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LecturerUndergraduateRepository {

    public List<Undergraduate> findUndergraduates(String keyword) throws SQLException {
        List<Undergraduate> results = new ArrayList<>();
        String query = (keyword == null) ? "" : keyword.trim();
        String sql = "SELECT ug.studentID, ug.degreeProgram, ug.level, ug.gpa, " +
                "u.firstName, u.lastName, u.email, u.contactNo " +
                "FROM undergraduate ug " +
                "JOIN user u ON u.userID = ug.userID " +
                "WHERE ug.studentID LIKE ? OR u.firstName LIKE ? OR u.lastName LIKE ? " +
                "ORDER BY ug.studentID";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String like = "%" + query + "%";
            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Undergraduate student = new Undergraduate();
                student.setStudentID(rs.getString("studentID"));
                student.setDegreeProgram(rs.getString("degreeProgram"));
                student.setLevel(rs.getInt("level"));
                student.setGpa(rs.getDouble("gpa"));
                student.setFirstName(rs.getString("firstName"));
                student.setLastName(rs.getString("lastName"));
                student.setEmail(rs.getString("email"));
                student.setContactNo(rs.getString("contactNo"));
                results.add(student);
            }
        }
        return results;
    }

    public List<LecturerEligibility> findEligibility(String studentId) throws SQLException {
        List<LecturerEligibility> results = new ArrayList<>();
        String query = (studentId == null) ? "" : studentId.trim();
        String sql = "SELECT undergraduateId, courseCode, attendancePercentage, " +
                "CASE WHEN attendancePercentage >= 80 THEN 'Eligible' ELSE 'Not Eligible' END AS eligibilityStatus " +
                "FROM attendance_summary " +
                "WHERE undergraduateId LIKE ? " +
                "ORDER BY undergraduateId, courseCode";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LecturerEligibility record = new LecturerEligibility();
                record.setUndergraduateId(rs.getString("undergraduateId"));
                record.setCourseCode(rs.getString("courseCode"));
                record.setAttendancePercentage(rs.getDouble("attendancePercentage"));
                record.setEligibilityStatus(rs.getString("eligibilityStatus"));
                results.add(record);
            }
        }
        return results;
    }

    public List<LecturerMarksGpa> findMarksAndGpa(String studentId) throws SQLException {
        List<LecturerMarksGpa> results = new ArrayList<>();
        String query = (studentId == null) ? "" : studentId.trim();
        String sql = "SELECT m.undergraduateId, m.courseCode, m.quiz1, m.quiz2, m.quiz3, m.assignment, m.project, " +
                "m.caMarks, m.midExam, m.endExam, m.finalMarks, m.grade, ug.gpa " +
                "FROM marks m " +
                "JOIN undergraduate ug ON ug.studentID = m.undergraduateId " +
                "WHERE m.undergraduateId LIKE ? " +
                "ORDER BY m.undergraduateId, m.courseCode";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LecturerMarksGpa record = new LecturerMarksGpa();
                record.setUndergraduateId(rs.getString("undergraduateId"));
                record.setCourseCode(rs.getString("courseCode"));
                record.setQuiz1(rs.getDouble("quiz1"));
                record.setQuiz2(rs.getDouble("quiz2"));
                record.setQuiz3(rs.getDouble("quiz3"));
                record.setAssignment(rs.getDouble("assignment"));
                record.setProject(rs.getDouble("project"));
                record.setCaMarks(rs.getDouble("caMarks"));
                record.setMidExam(rs.getDouble("midExam"));
                record.setEndExam(rs.getDouble("endExam"));
                record.setFinalMarks(rs.getDouble("finalMarks"));
                record.setGrade(rs.getString("grade"));
                record.setGpa(rs.getDouble("gpa"));
                results.add(record);
            }
        }
        return results;
    }
}
