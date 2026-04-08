package com.java_lms_group_20.Util;


import java.sql.*;

public class DatabaseInitializer {

    public static void initialize() {


        String countQuery = "SELECT COUNT(*) FROM user";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(countQuery)) {

            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println(">>> No users found. Initializing Default Admin...");
                createSuperAdmin(conn);
            } else {
                System.out.println(">>> System already initialized.");
            }

        } catch (SQLException e) {
            System.err.println("Database Initialization Failed: " + e.getMessage());
        }
    }

    private static void createSuperAdmin(Connection conn) throws SQLException {
        String userSql = "INSERT INTO user (username, password, firstName, lastName, email) VALUES (?, ?, ?, ?, ?)";
        String roleSql = "INSERT INTO user_roles (userID, roleID) VALUES (?, ?)";

        // Use a transaction to ensure both inserts succeed or both fail
        conn.setAutoCommit(false);

        try (PreparedStatement userPstmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
            // 1. Create the User
            userPstmt.setString(1, "admin");
            userPstmt.setString(2, "admin123");
            userPstmt.setString(3, "System");
            userPstmt.setString(4,"Admin");
            userPstmt.setString(5, "admin@lms.lk");
            userPstmt.executeUpdate();

            // 2. Get the new User ID
            ResultSet rs = userPstmt.getGeneratedKeys();
            if (rs.next()) {
                int newUserId = rs.getInt(1);

                // 3. Assign ADMIN Role (ID 1 as defined in your SQL table)
                try (PreparedStatement rolePstmt = conn.prepareStatement(roleSql)) {
                    rolePstmt.setInt(1, newUserId);
                    rolePstmt.setInt(2, 1); // 1 = ADMIN
                    rolePstmt.executeUpdate();
                }
            }

            conn.commit();
            System.out.println(">>> Default Admin 'admin' created successfully.");

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}