package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Undergraduate;
import com.java_lms_group_20.Util.DBConnection;
import java.sql.*;

public class UndergraduateRepository {

    public boolean save(Undergraduate student) throws SQLException {
        String userSql = "INSERT INTO user (username, password, firstName, lastName, email) VALUES (?, ?, ?, ?, ?)";
        String roleSql = "INSERT INTO user_roles (userID, roleID) VALUES (?, 3)"; // 3 is UNDERGRADUATE
        String studentSql = "INSERT INTO undergraduate (userID, studentID, degreeProgram, level, gpa) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start Transaction

            try (PreparedStatement uStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                uStmt.setString(1, student.getUsername());
                uStmt.setString(2, "1234"); // Default password
                uStmt.setString(3, student.getFirstName());
                uStmt.setString(4, student.getLastName());
                uStmt.setString(5, student.getEmail());
                uStmt.executeUpdate();

                ResultSet rs = uStmt.getGeneratedKeys();
                if (rs.next()) {
                    int newId = rs.getInt(1);

                    // Assign Role
                    try (PreparedStatement rStmt = conn.prepareStatement(roleSql)) {
                        rStmt.setInt(1, newId);
                        rStmt.executeUpdate();
                    }

                    // Save Student specific data
                    try (PreparedStatement sStmt = conn.prepareStatement(studentSql)) {
                        sStmt.setInt(1, newId);
                        sStmt.setString(2, student.getStudentID());
                        sStmt.setString(3, student.getDegreeProgram());
                        sStmt.setInt(4, student.getLevel());
                        sStmt.setDouble(5, student.getGpa());
                        sStmt.executeUpdate();
                    }
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}