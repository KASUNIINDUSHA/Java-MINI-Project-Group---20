package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Course;
import com.java_lms_group_20.Util.DBConnection;
import java.sql.*;

public class CourseRepository {

    public boolean save(Course course) throws SQLException {
        String sql = "INSERT INTO course (courseCode, courseName, credits) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getCredits());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Course course) throws SQLException {
        String sql = "UPDATE course SET courseName = ?, credits = ? WHERE courseCode = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseName());
            stmt.setInt(2, course.getCredits());
            stmt.setString(3, course.getCourseCode());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(String courseCode) throws SQLException {
        String sql = "DELETE FROM course WHERE courseCode = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseCode);
            return stmt.executeUpdate() > 0;
        }
    }
}