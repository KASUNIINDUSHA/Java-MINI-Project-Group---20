package com.java_lms_group_20.Controller.Lecturer;

import com.java_lms_group_20.Controller.LoginController;
import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Util.DBConnection;
import javafx.application.Platform;
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

public class LecturerDashboardController {

    @FXML private Label lblWelcome, lblLecturerID;
    @FXML private StackPane contentArea;
    @FXML private Button btnProfile, btnHome, btnMaterials, btnAttendance, btnMarks, btnMedical, btnNotices;

    private User currentUser;
    private String lecturerID;

    private static final String ACTIVE_STYLE = "-fx-background-color: #1e293b; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8; -fx-cursor: hand;";
    private static final String HOVER_STYLE = "-fx-background-color: #172554; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8; -fx-cursor: hand;";
    private static final String IDLE_STYLE = "-fx-background-color: transparent; -fx-text-fill: #94a3b8; -fx-padding: 12; -fx-cursor: hand;";

    @FXML
    public void initialize() {
        for (Button button : new Button[]{btnProfile, btnHome, btnMaterials, btnAttendance, btnMarks, btnMedical, btnNotices}) {
            button.setOnMouseEntered(e -> {
                if (!ACTIVE_STYLE.equals(button.getStyle())) {
                    button.setStyle(HOVER_STYLE);
                }
            });
            button.setOnMouseExited(e -> {
                if (!ACTIVE_STYLE.equals(button.getStyle())) {
                    button.setStyle(IDLE_STYLE);
                }
            });
        }
    }

    public void initUser(User user) {
        this.currentUser = user;
        this.lecturerID = fetchLecturerID(user.getUserID());

        Platform.runLater(() -> {
            lblWelcome.setText("Welcome, " + user.getFirstName());
            lblLecturerID.setText(lecturerID != null ? lecturerID : "Lec ID: N/A");
        });

        showProfile();
    }

    @FXML
    private void showProfile() {
        setActiveButton(btnProfile);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Lecturer/lecturer_profile_view.fxml"));
            Parent view = loader.load();
            LecturerProfileController controller = loader.getController();
            controller.initUser(currentUser);
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fetchLecturerID(int userID) {
        String sql = "SELECT lecturerID FROM lecturer WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("lecturerID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void showHome() {
        setActiveButton(btnHome);
        switchView("/View/Lecturer/lecturer_undergraduate_details.fxml");
    }

    @FXML
    private void showMaterials() {
        setActiveButton(btnMaterials);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Lecturer/lecturer_course_material_view.fxml"));
            Parent view = loader.load();
            LecturerCourseMaterialController controller = loader.getController();
            controller.initUser(currentUser);
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showAttendance() {
        setActiveButton(btnAttendance);
        switchView("/View/Lecturer/lecturer_eligibility.fxml");
    }

    @FXML
    private void showMarks() {
        setActiveButton(btnMarks);
        switchView("/View/Lecturer/lecturer_marks_gpa.fxml");
    }

    @FXML
    private void showMedical() {
        setActiveButton(btnMedical);
        switchView("/View/Lecturer/lecturer_attendance_medical.fxml");
    }

    @FXML
    private void showNotices() {
        setActiveButton(btnNotices);
        switchView("/View/Lecturer/lecturer_notice_view.fxml");
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

    private void setActiveButton(Button activeButton) {
        for (Button button : new Button[]{btnProfile, btnHome, btnMaterials, btnAttendance, btnMarks, btnMedical, btnNotices}) {
            button.setStyle(button == activeButton ? ACTIVE_STYLE : IDLE_STYLE);
        }
    }

    private void switchView(String fxmlPath) {
        try {
            java.net.URL res = getClass().getResource(fxmlPath);
            if (res == null) {
                System.err.println("Resource not found: " + fxmlPath);
                return; // Stop execution without crashing
            }
            FXMLLoader loader = new FXMLLoader(res);
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error loading: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
