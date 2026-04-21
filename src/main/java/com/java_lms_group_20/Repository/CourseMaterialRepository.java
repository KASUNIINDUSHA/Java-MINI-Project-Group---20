package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.CourseMaterial;
import com.java_lms_group_20.Util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseMaterialRepository {
    public List<CourseMaterial> findByCourseCode(String courseCode) throws SQLException {
        List<CourseMaterial> list = new ArrayList<>();
        String sql = "SELECT * FROM course_material WHERE UPPER(courseCode) LIKE UPPER(?) ORDER BY uploadedAt DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + (courseCode == null ? "" : courseCode.trim()) + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CourseMaterial material = new CourseMaterial();
                material.setMaterialID(rs.getInt("materialID"));
                material.setCourseCode(rs.getString("courseCode"));
                material.setTitle(rs.getString("title"));
                material.setDescription(rs.getString("description"));
                material.setResourceLink(rs.getString("resourceLink"));
                material.setUploadedBy(rs.getString("uploadedBy"));
                material.setUploadedAt(rs.getTimestamp("uploadedAt"));
                list.add(material);
            }
        }
        return list;
    }

    public boolean save(CourseMaterial material) throws SQLException {
        String sql = "INSERT INTO course_material (courseCode, title, description, resourceLink, uploadedBy) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, material.getCourseCode());
            stmt.setString(2, material.getTitle());
            stmt.setString(3, material.getDescription());
            stmt.setString(4, material.getResourceLink());
            stmt.setString(5, material.getUploadedBy());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(CourseMaterial material) throws SQLException {
        String sql = "UPDATE course_material SET courseCode=?, title=?, description=?, resourceLink=? WHERE materialID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, material.getCourseCode());
            stmt.setString(2, material.getTitle());
            stmt.setString(3, material.getDescription());
            stmt.setString(4, material.getResourceLink());
            stmt.setInt(5, material.getMaterialID());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int materialId) throws SQLException {
        String sql = "DELETE FROM course_material WHERE materialID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, materialId);
            return stmt.executeUpdate() > 0;
        }
    }
}
