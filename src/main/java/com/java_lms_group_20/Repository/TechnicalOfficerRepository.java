package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.TechnicalOfficer;
import com.java_lms_group_20.Util.DBConnection;
import java.sql.*;
import java.util.Optional;

public class TechnicalOfficerRepository {

    public boolean save(TechnicalOfficer officer) throws SQLException {
        String userSql = "INSERT INTO user (username, password, firstName, lastName, email) VALUES (?, ?, ?, ?, ?)";
        String roleSql = "INSERT INTO user_roles (userID, roleID) VALUES (?, 4)";
        String officerSql = "INSERT INTO technical_officer (userID, techOfficerID, department, technicalSkills) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement uStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                uStmt.setString(1, officer.getUsername());
                uStmt.setString(2, "1234");
                uStmt.setString(3, officer.getFirstName());
                uStmt.setString(4, officer.getLastName());
                uStmt.setString(5, officer.getEmail());
                uStmt.executeUpdate();

                ResultSet rs = uStmt.getGeneratedKeys();
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    try (PreparedStatement rStmt = conn.prepareStatement(roleSql)) {
                        rStmt.setInt(1, newId);
                        rStmt.executeUpdate();
                    }
                    try (PreparedStatement tStmt = conn.prepareStatement(officerSql)) {
                        tStmt.setInt(1, newId);
                        tStmt.setString(2, officer.getTechOfficerID());
                        tStmt.setString(3, officer.getDepartment());
                        tStmt.setString(4, officer.getTechnicalSkills());
                        tStmt.executeUpdate();
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

    public boolean update(TechnicalOfficer officer) throws SQLException {
        String userSql = "UPDATE user SET firstName=?, lastName=?, email=? WHERE username=?";
        String officerSql = "UPDATE technical_officer SET department=?, technicalSkills=? WHERE techOfficerID=?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int userRows;
                try (PreparedStatement uStmt = conn.prepareStatement(userSql)) {
                    uStmt.setString(1, officer.getFirstName());
                    uStmt.setString(2, officer.getLastName());
                    uStmt.setString(3, officer.getEmail());
                    uStmt.setString(4, officer.getUsername());
                    userRows = uStmt.executeUpdate();
                }
                int toRows;
                try (PreparedStatement tStmt = conn.prepareStatement(officerSql)) {
                    tStmt.setString(1, officer.getDepartment());
                    tStmt.setString(2, officer.getTechnicalSkills());
                    tStmt.setString(3, officer.getTechOfficerID());
                    toRows = tStmt.executeUpdate();
                }
                if (userRows > 0 || toRows > 0) {
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

    public boolean delete(String techOfficerID) throws SQLException {
        String findUserSql = "SELECT userID FROM technical_officer WHERE techOfficerID = ?";
        String deleteToSql = "DELETE FROM technical_officer WHERE techOfficerID = ?";
        String deleteRoleSql = "DELETE FROM user_roles WHERE userID = ? AND roleID = 4";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int userID = -1;
                try (PreparedStatement ps = conn.prepareStatement(findUserSql)) {
                    ps.setString(1, techOfficerID);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) userID = rs.getInt("userID");
                }
                if (userID != -1) {
                    try (PreparedStatement ps = conn.prepareStatement(deleteToSql)) {
                        ps.setString(1, techOfficerID);
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

    public Optional<TechnicalOfficer> findByUserID(int userID) throws SQLException {
        String sql = "SELECT u.userID, u.firstName, u.lastName, u.email, u.contactNo, " +
                "t.techOfficerID, t.department, t.technicalSkills " +
                "FROM user u JOIN technical_officer t ON u.userID = t.userID WHERE u.userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                TechnicalOfficer officer = new TechnicalOfficer();
                officer.setUserID(rs.getInt("userID"));
                officer.setFirstName(rs.getString("firstName"));
                officer.setLastName(rs.getString("lastName"));
                officer.setEmail(rs.getString("email"));
                officer.setContactNo(rs.getString("contactNo"));
                officer.setTechOfficerID(rs.getString("techOfficerID"));
                officer.setDepartment(rs.getString("department"));
                officer.setTechnicalSkills(rs.getString("technicalSkills"));
                return Optional.of(officer);
            }
            return Optional.empty();
        }
    }

    public boolean updateOwnProfile(TechnicalOfficer officer) throws SQLException {
        String userSql = "UPDATE user SET firstName=?, lastName=?, email=?, contactNo=? WHERE userID=?";
        String toSql = "UPDATE technical_officer SET department=?, technicalSkills=? WHERE userID=?";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement uStmt = conn.prepareStatement(userSql)) {
                    uStmt.setString(1, officer.getFirstName());
                    uStmt.setString(2, officer.getLastName());
                    uStmt.setString(3, officer.getEmail());
                    uStmt.setString(4, officer.getContactNo());
                    uStmt.setInt(5, officer.getUserID());
                    uStmt.executeUpdate();
                }
                try (PreparedStatement tStmt = conn.prepareStatement(toSql)) {
                    tStmt.setString(1, officer.getDepartment());
                    tStmt.setString(2, officer.getTechnicalSkills());
                    tStmt.setInt(3, officer.getUserID());
                    tStmt.executeUpdate();
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
