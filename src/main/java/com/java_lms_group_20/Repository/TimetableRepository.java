package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Timetable;
import com.java_lms_group_20.Util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TimetableRepository {

    public List<Timetable> findAll() throws SQLException {
        List<Timetable> rows = new ArrayList<>();
        String sql = "SELECT * FROM timetable ORDER BY department, level, dayOfWeek, startTime";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rows.add(map(rs));
            }
        }
        return rows;
    }

    public List<Timetable> findByDepartment(String department) throws SQLException {
        List<Timetable> rows = new ArrayList<>();
        String sql = "SELECT * FROM timetable WHERE UPPER(department) LIKE UPPER(?) ORDER BY level, dayOfWeek, startTime";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + (department == null ? "" : department.trim()) + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rows.add(map(rs));
                }
            }
        }
        return rows;
    }

    public List<Timetable> findByStudentId(String studentId) throws SQLException {
        List<Timetable> rows = new ArrayList<>();
        String sql = "SELECT t.* FROM timetable t " +
                "JOIN undergraduate ug ON UPPER(ug.degreeProgram) = UPPER(t.department) AND ug.level = t.level " +
                "WHERE ug.studentID = ? " +
                "ORDER BY t.dayOfWeek, t.startTime";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rows.add(map(rs));
                }
            }
        }
        return rows;
    }

    public boolean save(Timetable t) throws SQLException {
        String sql = "INSERT INTO timetable (department, level, dayOfWeek, startTime, endTime, courseCode, venue) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getDepartment());
            stmt.setInt(2, t.getLevel());
            stmt.setString(3, t.getDayOfWeek());
            stmt.setTime(4, t.getStartTime());
            stmt.setTime(5, t.getEndTime());
            stmt.setString(6, t.getCourseCode());
            stmt.setString(7, t.getVenue());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Timetable t) throws SQLException {
        String sql = "UPDATE timetable SET department=?, level=?, dayOfWeek=?, startTime=?, endTime=?, courseCode=?, venue=? WHERE timetableID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getDepartment());
            stmt.setInt(2, t.getLevel());
            stmt.setString(3, t.getDayOfWeek());
            stmt.setTime(4, t.getStartTime());
            stmt.setTime(5, t.getEndTime());
            stmt.setString(6, t.getCourseCode());
            stmt.setString(7, t.getVenue());
            stmt.setInt(8, t.getTimetableID());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int timetableId) throws SQLException {
        String sql = "DELETE FROM timetable WHERE timetableID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, timetableId);
            return stmt.executeUpdate() > 0;
        }
    }

    private Timetable map(ResultSet rs) throws SQLException {
        Timetable t = new Timetable();
        t.setTimetableID(rs.getInt("timetableID"));
        t.setDepartment(rs.getString("department"));
        t.setLevel(rs.getInt("level"));
        t.setDayOfWeek(rs.getString("dayOfWeek"));
        t.setStartTime(rs.getTime("startTime"));
        t.setEndTime(rs.getTime("endTime"));
        t.setCourseCode(rs.getString("courseCode"));
        t.setVenue(rs.getString("venue"));
        t.setUpdatedAt(rs.getTimestamp("updatedAt"));
        return t;
    }

    public static Time parseTime(String value) {
        return Time.valueOf(value);
    }
}
