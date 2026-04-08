package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Lecturer;
import com.java_lms_group_20.Util.DBConnection;
import java.sql.*;

public class LecturerRepository {

    public boolean save(Lecturer lecturer) throws SQLException {
        String userSql = "INSERT INTO user (username, password, firstName, lastName, email) VALUES (?, ?, ?, ?, ?)";
        String roleSql = "INSERT INTO user_roles (userID, roleID) VALUES (?, 2)";
        String lecturerSql = "INSERT INTO lecturer (userID, lecturerID, department, qualifications, specialization) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement uStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                uStmt.setString(1, lecturer.getUsername());
                uStmt.setString(2, "1234");
                uStmt.setString(3, lecturer.getFirstName());
                uStmt.setString(4, lecturer.getLastName());
                uStmt.setString(5, lecturer.getEmail());
                uStmt.executeUpdate();

                ResultSet rs = uStmt.getGeneratedKeys();
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    try (PreparedStatement rStmt = conn.prepareStatement(roleSql)) {
                        rStmt.setInt(1, newId);
                        rStmt.executeUpdate();
                    }
                    try (PreparedStatement lStmt = conn.prepareStatement(lecturerSql)) {
                        lStmt.setInt(1, newId);
                        lStmt.setString(2, lecturer.getLecturerID());
                        lStmt.setString(3, lecturer.getDepartment());
                        lStmt.setString(4, lecturer.getQualifications());
                        lStmt.setString(5, lecturer.getSpecialization());
                        lStmt.executeUpdate();
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

    public boolean update(Lecturer lecturer) throws SQLException {
        String userSql = "UPDATE user SET firstName=?, lastName=?, email=? WHERE username=?";
        String lecturerSql = "UPDATE lecturer SET department=?, qualifications=?, specialization=? WHERE lecturerID=?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int userRows;
                try (PreparedStatement uStmt = conn.prepareStatement(userSql)) {
                    uStmt.setString(1, lecturer.getFirstName());
                    uStmt.setString(2, lecturer.getLastName());
                    uStmt.setString(3, lecturer.getEmail());
                    uStmt.setString(4, lecturer.getUsername());
                    userRows = uStmt.executeUpdate();
                }

                int lecRows;
                try (PreparedStatement lStmt = conn.prepareStatement(lecturerSql)) {
                    lStmt.setString(1, lecturer.getDepartment());
                    lStmt.setString(2, lecturer.getQualifications());
                    lStmt.setString(3, lecturer.getSpecialization());
                    lStmt.setString(4, lecturer.getLecturerID());
                    lecRows = lStmt.executeUpdate();
                }

                if (userRows > 0 || lecRows > 0) {
                    conn.commit();
                    return true;
                }
                return false;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public boolean delete(String lecturerID) throws SQLException {
        String findUserSql = "SELECT userID FROM lecturer WHERE lecturerID = ?";
        String deleteLecSql = "DELETE FROM lecturer WHERE lecturerID = ?";
        String deleteRoleSql = "DELETE FROM user_roles WHERE userID = ? AND roleID = 2";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int userID = -1;
                try (PreparedStatement ps = conn.prepareStatement(findUserSql)) {
                    ps.setString(1, lecturerID);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) userID = rs.getInt("userID");
                }

                if (userID != -1) {
                    try (PreparedStatement ps = conn.prepareStatement(deleteLecSql)) {
                        ps.setString(1, lecturerID);
                        if (ps.executeUpdate() > 0) {
                            try (PreparedStatement psRole = conn.prepareStatement(deleteRoleSql)) {
                                psRole.setInt(1, userID);
                                psRole.executeUpdate();
                            }
                            conn.commit();
                            return true;
                        }
                    }
                }
                return false;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}