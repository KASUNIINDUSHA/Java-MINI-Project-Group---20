package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Undergraduate;
import com.java_lms_group_20.Util.DBConnection;
import java.sql.*;
import java.util.Optional;

public class UndergraduateRepository {

    public String getStudentIDByUserID(int userID) {
        String sql = "SELECT studentID FROM undergraduate WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("studentID");
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }



    public boolean save(Undergraduate student) throws SQLException {
        String userSql = "INSERT INTO user (username, password, firstName, lastName, email) VALUES (?, ?, ?, ?, ?)";
        String roleSql = "INSERT INTO user_roles (userID, roleID) VALUES (?, 3)";
        String studentSql = "INSERT INTO undergraduate (userID, studentID, degreeProgram, level, gpa) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement uStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                uStmt.setString(1, student.getUsername());
                uStmt.setString(2, "1234");
                uStmt.setString(3, student.getFirstName());
                uStmt.setString(4, student.getLastName());
                uStmt.setString(5, student.getEmail());
                uStmt.executeUpdate();

                ResultSet rs = uStmt.getGeneratedKeys();
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    try (PreparedStatement rStmt = conn.prepareStatement(roleSql)) {
                        rStmt.setInt(1, newId);
                        rStmt.executeUpdate();
                    }
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

    public boolean update(Undergraduate student) throws SQLException {
        String userSql = "UPDATE user SET firstName=?, lastName=?, email=? WHERE username=?";
        String studentSql = "UPDATE undergraduate SET degreeProgram=?, level=?, gpa=? WHERE studentID=?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int userRows;
                try (PreparedStatement uStmt = conn.prepareStatement(userSql)) {
                    uStmt.setString(1, student.getFirstName());
                    uStmt.setString(2, student.getLastName());
                    uStmt.setString(3, student.getEmail());
                    uStmt.setString(4, student.getUsername());
                    userRows = uStmt.executeUpdate();
                }

                int studentRows;
                try (PreparedStatement sStmt = conn.prepareStatement(studentSql)) {
                    sStmt.setString(1, student.getDegreeProgram());
                    sStmt.setInt(2, student.getLevel());
                    sStmt.setDouble(3, student.getGpa());
                    sStmt.setString(4, student.getStudentID());
                    studentRows = sStmt.executeUpdate();
                }

                if (userRows > 0 || studentRows > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public boolean delete(String studentID) throws SQLException {
        String findUserSql = "SELECT userID FROM undergraduate WHERE studentID = ?";
        String deleteUndergradSql = "DELETE FROM undergraduate WHERE studentID = ?";
        String deleteRoleSql = "DELETE FROM user_roles WHERE userID = ? AND roleID = 3";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int userID = -1;
                try (PreparedStatement ps = conn.prepareStatement(findUserSql)) {
                    ps.setString(1, studentID);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) userID = rs.getInt("userID");
                }

                if (userID != -1) {
                    int rowsDeleted;
                    try (PreparedStatement ps = conn.prepareStatement(deleteUndergradSql)) {
                        ps.setString(1, studentID);
                        rowsDeleted = ps.executeUpdate();
                    }

                    if (rowsDeleted > 0) {
                        try (PreparedStatement psRole = conn.prepareStatement(deleteRoleSql)) {
                            psRole.setInt(1, userID);
                            psRole.executeUpdate();
                        }
                        conn.commit();
                        return true;
                    }
                }
                return false;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public Optional<Undergraduate> findByUserID(int userID) throws SQLException {
        String sql = "SELECT u.userID, u.firstName, u.lastName, u.contactNo, u.profilePicture, " +
                "ug.studentID, ug.degreeProgram, ug.level, ug.gpa " +
                "FROM user u JOIN undergraduate ug ON u.userID = ug.userID WHERE u.userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Undergraduate student = new Undergraduate();
                student.setUserID(rs.getInt("userID"));
                student.setFirstName(rs.getString("firstName"));
                student.setLastName(rs.getString("lastName"));
                student.setContactNo(rs.getString("contactNo"));
                student.setProfilePicture(rs.getString("profilePicture"));
                student.setStudentID(rs.getString("studentID"));
                student.setDegreeProgram(rs.getString("degreeProgram"));
                student.setLevel(rs.getInt("level"));
                student.setGpa(rs.getDouble("gpa"));
                return Optional.of(student);
            }
            return Optional.empty();
        }
    }

    public boolean updateOwnProfile(int userID, String contactNo, String profilePicture) throws SQLException {
        String sql = "UPDATE user SET contactNo=?, profilePicture=? WHERE userID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contactNo);
            stmt.setString(2, profilePicture);
            stmt.setInt(3, userID);
            return stmt.executeUpdate() > 0;
        }
    }
}
