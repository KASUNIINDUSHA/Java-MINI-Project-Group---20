package com.java_lms_group_20.Controller.TO;

import com.java_lms_group_20.Controller.LoginController;
import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Util.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TODashboardController {
    @FXML private Label lblWelcome, lblToID, lblDepartment;
    @FXML private StackPane contentArea;
    @FXML private Button btnProfile, btnAttendance, btnMedical, btnNotices, btnTimetable;

    private User currentUser;
    private String department;

    private static final String ACTIVE_STYLE = "-fx-background-color: #1e293b; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8; -fx-cursor: hand;";
    private static final String HOVER_STYLE = "-fx-background-color: #172554; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8; -fx-cursor: hand;";
    private static final String IDLE_STYLE = "-fx-background-color: transparent; -fx-text-fill: #94a3b8; -fx-padding: 12; -fx-cursor: hand;";

    @FXML
    public void initialize() {
        for (Button button : new Button[]{btnProfile, btnAttendance, btnMedical, btnNotices, btnTimetable}) {
            button.setOnMouseEntered(e -> {
                if (!ACTIVE_STYLE.equals(button.getStyle())) button.setStyle(HOVER_STYLE);
            });
            button.setOnMouseExited(e -> {
                if (!ACTIVE_STYLE.equals(button.getStyle())) button.setStyle(IDLE_STYLE);
            });
        }
    }

    public void initUser(User user) {
        this.currentUser = user;
        loadToMeta(user.getUserID());
        showProfile();
    }

    private void loadToMeta(int userId) {
        String sql = "SELECT techOfficerID, department FROM technical_officer WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                lblToID.setText(rs.getString("techOfficerID"));
                department = rs.getString("department");
                lblDepartment.setText(department);
            }
            lblWelcome.setText("Welcome, " + currentUser.getFirstName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showProfile() {
        setActive(btnProfile);
        switchView("/View/TO/to_profile_view.fxml", controller -> {
            if (controller instanceof TOProfileController profileController) {
                profileController.initUser(currentUser);
            }
        });
    }

    @FXML
    private void showAttendance() {
        setActive(btnAttendance);
        switchView("/View/TO/to_attendance_view.fxml", null);
    }

    @FXML
    private void showMedical() {
        setActive(btnMedical);
        switchView("/View/TO/to_medical_view.fxml", null);
    }

    @FXML
    private void showNotices() {
        setActive(btnNotices);
        switchView("/View/TO/to_notice_view.fxml", null);
    }

    @FXML
    private void showTimetable() {
        setActive(btnTimetable);
        switchView("/View/TO/to_timetable_view.fxml", controller -> {
            if (controller instanceof TOTimetableController timetableController) {
                timetableController.loadDepartment(department);
            }
        });
    }

    @FXML
    private void handleSignOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sign Out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to sign out?");
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            LoginController.logout(lblWelcome);
        }
    }

    private void setActive(Button active) {
        for (Button button : new Button[]{btnProfile, btnAttendance, btnMedical, btnNotices, btnTimetable}) {
            button.setStyle(button == active ? ACTIVE_STYLE : IDLE_STYLE);
        }
    }

    private void switchView(String fxmlPath, ControllerInitializer initializer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            if (initializer != null) {
                initializer.init(loader.getController());
            }
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface ControllerInitializer {
        void init(Object controller);
    }
}
