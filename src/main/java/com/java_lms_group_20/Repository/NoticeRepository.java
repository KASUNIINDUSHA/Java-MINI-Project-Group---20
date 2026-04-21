package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Notice;
import com.java_lms_group_20.Util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoticeRepository {

    public List<Notice> findAll() throws SQLException {
        List<Notice> notices = new ArrayList<>();
        String sql = "SELECT * FROM notice ORDER BY postedAt DESC, noticeID DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                notices.add(map(rs));
            }
        }
        return notices;
    }

    public boolean save(Notice notice) throws SQLException {
        String sql = "INSERT INTO notice (title, message, postedBy) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, notice.getTitle());
            stmt.setString(2, notice.getMessage());
            stmt.setString(3, notice.getPostedBy());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Notice notice) throws SQLException {
        String sql = "UPDATE notice SET title = ?, message = ?, postedBy = ? WHERE noticeID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, notice.getTitle());
            stmt.setString(2, notice.getMessage());
            stmt.setString(3, notice.getPostedBy());
            stmt.setInt(4, notice.getNoticeID());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int noticeId) throws SQLException {
        String sql = "DELETE FROM notice WHERE noticeID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, noticeId);
            return stmt.executeUpdate() > 0;
        }
    }

    private Notice map(ResultSet rs) throws SQLException {
        Notice notice = new Notice();
        notice.setNoticeID(rs.getInt("noticeID"));
        notice.setTitle(rs.getString("title"));
        notice.setMessage(rs.getString("message"));
        notice.setPostedBy(rs.getString("postedBy"));
        notice.setPostedAt(rs.getTimestamp("postedAt"));
        return notice;
    }
}
